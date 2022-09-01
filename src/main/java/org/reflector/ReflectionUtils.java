package org.reflector;

import org.reflector.exception.FieldAccessException;
import org.reflector.exception.InstanceInvocationException;
import org.reflector.exception.MethodInvokeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.function.Predicate;

public final class ReflectionUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtils.class);

    /**
     * The private constructor of {@link ReflectionUtils}.
     */
    private ReflectionUtils() {
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

    public static List<Field> getAllFields(Class<?> type) {
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

    public static <T> List<Method> getAllPrivateMethods(final Class<?> clazz) {
        return getAllMethodsWithModifiers(clazz, Collections.singletonList(Modifier::isPrivate));
    }

    public static <T> List<Method> getAllPublicProtectedMethods(final Class<?> clazz) {
        return getAllMethodsWithModifiers(clazz, Arrays.asList(Modifier::isPublic, Modifier::isProtected));
    }

    private static <T> List<Method> getAllMethodsWithModifiers(final Class<?> clazz1, final List<Predicate<Integer>> predicates) {
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

    private static Class<?>[] getArrayValuesTypes(Object[] args) {
        final Class<?>[] ctorTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            ctorTypes[i] = args[i].getClass();
        }
        return ctorTypes;
    }

    private static <T> Constructor<T> getAccessibleConstructor(Class<?>[] contTypes, Class<T> clazz) throws NoSuchMethodException {
        final Constructor<T> ctor = clazz.getConstructor(contTypes);
        ctor.setAccessible(true);
        return ctor;
    }
}
