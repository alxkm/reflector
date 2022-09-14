package org.reflector;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.function.Predicate;
import org.reflector.exception.FieldAccessException;
import org.reflector.exception.InstanceInvocationException;
import org.reflector.exception.MethodInvokeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Simple utility class for working with the reflection API
 *
 * provides work with methods, fields, names, classes, instantiation
 *
 * simple object copy
 *
 */
public final class ReflectionUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtils.class);

    /**
     * The private constructor of {@link ReflectionUtils}.
     * */
    private ReflectionUtils() {
    }

    public static String getClassFullName(final Object obj) {
        return obj.getClass().getName();
    }

    public static String getClassCanonicalName(final Object obj) {
        return obj.getClass().getCanonicalName();
    }

    public static String getClassSimpleName(final Object obj) {
        return obj.getClass().getSimpleName();
    }

    public static String getPackage(final Object obj) {
        return obj.getClass().getPackage().getName();
    }

    public static String getClassFullNameByClass(final Class<?> clazz) {
        return clazz.getName();
    }

    public static String getClassCanonicalNameByClass(final Class<?> clazz) {
        return clazz.getCanonicalName();
    }

    public static String getClassSimpleNameByClass(final Class<?> clazz) {
        return clazz.getSimpleName();
    }

    public static String getPackageByClass(final Class<?> clazz) {
        return clazz.getPackage().getName();
    }

    public static String getSuperClassName(final Object obj) {
        return obj.getClass().getSuperclass().getName();
    }

    public static String getSuperClassNameByClass(final Class<?> clazz) {
        return clazz.getSuperclass().getName();
    }

    public static Class<?> getSuperClass(final Object obj) {
        return obj.getClass().getSuperclass();
    }

    public static void clearUnselectedFields(final Object object, final Collection<String> fields) {
        if (fields != null && !fields.isEmpty()) {
            for (Field field : getAllFields(object.getClass())) {
                if (!fields.contains(field.getName())) {
                    try {
                        field.setAccessible(true);
                        field.set(object, null);
                    } catch (Exception e) {
                        LOGGER.error("Could not clear private field. " + e.getMessage());
                    }
                }
            }
        }
    }

    public static Annotation[] getAnnotations(Class<?> clazz) {
        return clazz.getAnnotations();
    }

    public static Annotation[] getMethodDeclaredAnnotations(Method method) {
        return method.getDeclaredAnnotations();
    }


    public static boolean isMethodHasAnnotation(Method method, Class clazz) {
        Annotation annotation = method.getAnnotation(clazz);
        return clazz.isInstance(annotation);
    }

    public static <T> boolean isMethodParameterAnnotated(Method method, Class<T> clazz) {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (Annotation[] annotations : parameterAnnotations) {
            for (Annotation annotation : annotations) {
                if (clazz.isInstance(annotation)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static <T> boolean isFieldParameterAnnotated(Field field, Class<T> clazz) {
        Annotation[] annotations = field.getDeclaredAnnotations();
        for (Annotation annotation : annotations) {
            if (clazz.isInstance(annotation)) {
                return true;
            }
        }
        return false;
    }

    public static List<Field> getAllFields(final Class<?> type) {
        return getAllFields(new ArrayList<>(), type);
    }

    private static List<Field> getAllFields(final List<Field> fields, final Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));

        if (type.getSuperclass() != null) {
            getAllFields(fields, type.getSuperclass());
        }

        return fields;
    }

    public static List<Field> getAllPrivateFields(final Class<?> clazz) {
        List<Field> fields = new ArrayList<>();

        Field[] classFields = clazz.getDeclaredFields();

        for (Field field : classFields) {
            if (Modifier.isPrivate(field.getModifiers())) {
                fields.add(field);
            }
        }
        return fields;
    }

    public static Map<String, Field> getAllFieldsMap(final Class<?> clazz) {
        List<Field> allFields = getAllFields(clazz);
        return getFieldsMap(allFields);
    }

    public static Map<String, Field> getAllPrivateFieldsMap(final Class<?> clazz) {
        List<Field> allFields = getAllPrivateFields(clazz);
        return getFieldsMap(allFields);
    }

    private static Map<String, Field> getFieldsMap(List<Field> fields) {
        Map<String, Field> map = new HashMap<>();
        for (Field field : fields) {
            map.put(field.getName(), field);
        }
        return map;
    }

    public static List<Method> getAllPrivateMethods(final Class<?> clazz) {
        return getAllMethodsWithModifiers(clazz, Collections.singletonList(Modifier::isPrivate));
    }

    public static List<Method> getAllPublicProtectedMethods(final Class<?> clazz) {
        return getAllMethodsWithModifiers(clazz, Arrays.asList(Modifier::isPublic, Modifier::isProtected));
    }

    public static List<Field> getAllAnnotatedFields(final Class<?> type,
                                                    final Class<? extends Annotation> annotation) {
        List<Field> fieldList = new ArrayList<>();
        for (Field allField : getAllFields(type)) {
            if (allField.getAnnotation(annotation) != null) {
                fieldList.add(allField);
            }
        }
        for (Field field : fieldList) {
            field.setAccessible(true);
        }
        return fieldList;
    }

    public static Object readField(final Object object,
                                   final String fieldName) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception e) {
            LOGGER.error("Could not invoke method ", e);
        }
        throw new FieldAccessException("Requested field is not accessible");
    }

    private static List<Method> getAllMethodsWithModifiers(final Class<?> clazz1, final List<Predicate<Integer>> predicates) {
        List<Method> result = new ArrayList<>();
        Stack<Class<?>> classStack = new Stack<>();
        classStack.push(clazz1);
        while (!classStack.isEmpty()) {
            Class<?> clazz = classStack.pop();
            for (Method method : clazz.getDeclaredMethods()) {
                int modifiers = method.getModifiers();

                List<Boolean> modifiersChecks = new ArrayList<>();
                for (Predicate<Integer> predicate : predicates) {
                    if (!predicate.test(modifiers)) {
                        modifiersChecks.add(false);
                    } else {
                        modifiersChecks.add(true);
                    }
                }
                boolean allModifiersCheck = false;
                for (Boolean modifiersCheck : modifiersChecks) {
                    if (modifiersCheck) {
                        allModifiersCheck = true;
                        break;
                    }
                }

                if (allModifiersCheck) {
                    result.add(method);
                }
            }

            if (clazz.getSuperclass() != null) {
                classStack.push(clazz.getSuperclass());
            }
        }
        return result;
    }

    public static Object invokeMethod(final Object objectToInvokeOn,
                                      final String methodName,
                                      final Class<?>[] parameterTypes,
                                      final Object[] args) {
        try {
            Method method = objectToInvokeOn.getClass().getDeclaredMethod(methodName, parameterTypes);
            return method.invoke(objectToInvokeOn, args);
        } catch (Exception e) {
            LOGGER.error("Could not invoke method ", e);
        }
        throw new MethodInvokeException("Error during method invoke has been happened");
    }

    public static Object invokeSingleMethod(final Object objectToInvokeOn,
                                            final String methodName,
                                            final Class<?> parameterType,
                                            final Object parameter) {
        try {
            final Class<?> clazz = objectToInvokeOn.getClass();
            final Method method = clazz.getMethod(methodName, parameterType);
            return method.invoke(objectToInvokeOn, parameter);
        } catch (Exception e) {
            LOGGER.error("Could not invoke {{}} method ", methodName, e);
        }
        throw new MethodInvokeException("Error during method invoke has been happened");
    }

    public static Object invokeInstance(final String className) throws InstanceInvocationException {
        try {
            return Class.forName(className).newInstance();
        } catch (Exception e) {
            LOGGER.error("Could not instantiate class object ", e);
        }
        throw new InstanceInvocationException("Error during instance invoke has been happened");
    }

    public static Object invokeInstance(final String classFullName, final Object... args) throws InstanceInvocationException {
        try {
            final Class<?> clazz = Class.forName(classFullName);
            final Class<?>[] ctorTypes = getArrayValuesTypes(args);
            final Constructor<?> ctor = getAccessibleConstructor(ctorTypes, clazz);
            return ctor.newInstance(args);
        } catch (Exception e) {
            LOGGER.error("Could not instantiate class {{}} object ", classFullName, e);
        }
        throw new InstanceInvocationException("Error during instance invoke has been happened");
    }

    public static <T> T invokeInstance(final Class<T> clazz, final Object... args) throws InstanceInvocationException {
        try {
            final Class<?>[] ctorTypes = getArrayValuesTypes(args);
            final Constructor<T> ctor = getAccessibleConstructor(ctorTypes, clazz);
            return ctor.newInstance(args);
        } catch (Exception e) {
            LOGGER.error("Could not instantiate class {{}} object ", clazz, e);
        }
        throw new InstanceInvocationException("Error during instance invoke has been happened");
    }

    private static Class<?>[] getArrayValuesTypes(final Object[] args) {
        final Class<?>[] ctorTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            ctorTypes[i] = args[i].getClass();
        }
        return ctorTypes;
    }

    private static <T> Constructor<T> getAccessibleConstructor(final Class<?>[] contTypes, final Class<T> clazz) throws NoSuchMethodException {
        final Constructor<T> ctor = clazz.getConstructor(contTypes);
        ctor.setAccessible(true);
        return ctor;
    }

    public static Constructor<?>[] getConstructors(final Class<?> clazz) {
        return clazz.getConstructors();
    }

    public static Constructor<?>[] getDeclaredConstructors(final Class<?> clazz) {
        return clazz.getDeclaredConstructors();
    }

    private static boolean isFieldPrimitiveType(final Field field) {
        return field.getType().isPrimitive()
                || field.getType().equals(String.class)
                || field.getType().getSuperclass().equals(Number.class)
                || field.getType().equals(Boolean.class);
    }

    public static Object copy(final Object object) {
        Object copyObj = null;
        try {
            copyObj = object.getClass().newInstance();
            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.get(object) == null || Modifier.isFinal(field.getModifiers())) {
                    continue;
                }
                if (isFieldPrimitiveType(field)) {
                    field.set(copyObj, field.get(object));
                } else {
                    Object childObj = field.get(object);
                    if(childObj == object) {
                        field.set(copyObj,copyObj);
                    } else {
                        field.set(copyObj, copy(field.get(object)));
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error during copy object", e);
        }
        return copyObj;
    }
}
