package org.reflector;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.reflector.exception.FieldAccessException;
import org.reflector.exception.InstanceInvocationException;
import org.reflector.exception.MethodInvokeException;
import org.reflector.util.ReflectionConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple utility class for working with the reflection API
 * <p>
 * provides work with methods, fields, names, classes, instantiation
 * <p>
 * simple object copy
 */
public final class ReflectionUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtils.class);
    private static final ClassLoader CLASSLOADER;

    static {
        final ClassLoader threadClassLoader = Thread.currentThread().getContextClassLoader();
        CLASSLOADER = (threadClassLoader != null) ? threadClassLoader : ReflectionUtils.class.getClassLoader();
    }

    /**
     * The private constructor of {@link ReflectionUtils}.
     */
    private ReflectionUtils() {
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
        List<Field> allFields = FieldUtils.getAllFields(clazz);
        return getFieldsMap(allFields);
    }

    public static Map<String, Field> getAllPrivateFieldsMap(final Class<?> clazz) {
        List<Field> allFields = getAllPrivateFields(clazz);
        return getFieldsMap(allFields);
    }

    private static Map<String, Field> getFieldsMap(final List<Field> fields) {
        Map<String, Field> map = new HashMap<>();
        for (Field field : fields) {
            map.put(field.getName(), field);
        }
        return map;
    }

    public static List<Field> getAllAnnotatedFields(final Class<?> type, final Class<? extends Annotation> annotation) {
        List<Field> fieldList = new ArrayList<>();
        for (Field allField : FieldUtils.getAllFields(type)) {
            if (allField.getAnnotation(annotation) != null) {
                fieldList.add(allField);
                allField.setAccessible(true);
            }
        }
        return fieldList;
    }

    public static Object readField(final Object object, final String fieldName) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception e) {
            LOGGER.error("Could not invoke method", e);
        }
        throw new FieldAccessException("Requested field is not accessible");
    }

    public static Object invokeMethod(final Object objectToInvokeOn, final String methodName, final Class<?>[] parameterTypes, final Object[] args) {
        try {
            Method method = objectToInvokeOn.getClass().getDeclaredMethod(methodName, parameterTypes);
            return method.invoke(objectToInvokeOn, args);
        } catch (Exception e) {
            LOGGER.error("Could not invoke method", e);
        }
        throw new MethodInvokeException("Error during method invoke has been happened");
    }

    public static Object invokeSingleMethod(final Object objectToInvokeOn, final String methodName, final Class<?> parameterType, final Object parameter) {
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
            final Class<?>[] ctorTypes = getArrayValuesTypesByArgs(args);
            final Constructor<?> ctor = getAccessibleConstructor(ctorTypes, clazz);
            return ctor.newInstance(args);
        } catch (Exception e) {
            LOGGER.error("Could not instantiate class {{}} object ", classFullName, e);
        }
        throw new InstanceInvocationException("Error during instance invoke has been happened");
    }

    public static <T> T invokeInstance(final Class<T> clazz, final Object... args) throws InstanceInvocationException {
        try {
            final Class<?>[] ctorTypes = getArrayValuesTypesByArgs(args);
            final Constructor<T> ctor = getAccessibleConstructor(ctorTypes, clazz);
            return ctor.newInstance(args);
        } catch (Exception e) {
            LOGGER.error("Could not instantiate class {{}} object ", clazz, e);
        }
        throw new InstanceInvocationException("Error during instance invoke has been happened");
    }

    public static Class<?>[] getArrayValuesTypesByArgs(final Object[] args) {
        final Class<?>[] ctorTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            ctorTypes[i] = args[i].getClass();
        }
        return ctorTypes;
    }

    public static <T> Constructor<T> getAccessibleConstructor(final Class<?>[] contTypes, final Class<T> clazz) throws NoSuchMethodException {
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

    /**
     * Creates a deep copy of the given object.
     *
     * @param object the object to be copied
     * @return the deep copy of the object
     * @throws IllegalStateException if copying fails
     */
    public static Object copy(final Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Object must not be null");
        }
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
                    field.set(copyObj, (childObj == object) ? copyObj : copy(field.get(object)));
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error during copy object", e);
            throw new IllegalStateException("Failed to get declared methods for Class [" + object.getClass().getName() + "] from ClassLoader [" + object.getClass().getClassLoader() + "]", e);
        }
        return copyObj;
    }

    public static boolean isFieldPrimitiveType(final Field field) {
        return field.getType().isPrimitive() || field.getType() == String.class || field.getType() == Integer.class || field.getType() == Long.class || field.getType() == Boolean.class || field.getType() == Byte.class || field.getType() == Character.class || field.getType() == Short.class || field.getType() == Float.class || field.getType() == Double.class;
    }

    public static List<Class<?>> getClassesByPackage(final String packageName) throws ClassNotFoundException, IOException, URISyntaxException {
        String path = packageName.replace(ReflectionConstant.DOT_SYMBOL, ReflectionConstant.SLASH);
        Enumeration<URL> resources = CLASSLOADER.getResources(path);
        List<File> directories = new ArrayList<>();
        while (resources.hasMoreElements()) {
            directories.add(new File(new URI(resources.nextElement().toString()).getPath()));
        }
        List<Class<?>> classes = new ArrayList<>();
        for (File directory : directories) {
            classes.addAll(getClassesByDirectoryAndPackage(directory, packageName));
        }
        return classes;
    }

    public static List<Class<?>> getClassesByDirectoryAndPackage(final File directory, final String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    classes.addAll(getClassesByDirectoryAndPackage(file, packageName + ReflectionConstant.DOT + file.getName()));
                } else if (file.getName().endsWith(ReflectionConstant.CLASS)) {
                    classes.add(Class.forName(packageName + ReflectionConstant.DOT + file.getName().substring(0, file.getName().length() - ReflectionConstant.CLASS_NAME_CONSTANT)));
                }
            }
        }
        return classes;
    }

    public static List<Class<?>> getAllAnnotatedClassesByPackage(final String packageName, final Class annotation) throws IOException, URISyntaxException, ClassNotFoundException {
        List<Class<?>> classesByPackage = getClassesByPackage(packageName);
        List<Class<?>> classes = new ArrayList<>();
        for (Class<?> aClass : classesByPackage) {
            if (aClass.isAnnotationPresent(annotation)) {
                classes.add(aClass);
            }
        }
        return classes;
    }

    public static List<Method> getDefaultMethodsOfInterfaces(final Class<?> clazz) {
        List<Method> result = new ArrayList<>();
        for (Class<?> ifc : clazz.getInterfaces()) {
            for (Method method : ifc.getMethods()) {
                if (method.isDefault()) {
                    result.add(method);
                }
            }
        }
        return result;
    }

    public static Method[] getDeclaredMethods(final Class<?> clazz) {
        Method[] result;
        try {
            Method[] declaredMethods = clazz.getDeclaredMethods();
            List<Method> defaultMethods = getDefaultMethodsOfInterfaces(clazz);
            result = new Method[declaredMethods.length + defaultMethods.size()];
            System.arraycopy(declaredMethods, 0, result, 0, declaredMethods.length);
            int index = declaredMethods.length;
            for (Method defaultMethod : defaultMethods) {
                result[index] = defaultMethod;
                index++;
            }
        } catch (Throwable ex) {
            throw new IllegalStateException("Failed to get declared methods for Class [" + clazz.getName() + "] from ClassLoader [" + clazz.getClassLoader() + "]", ex);
        }

        return result;
    }

    public static List<Method> getDeclaredMethodsList(final Class<?> clazz) {
        List<Method> methods = new ArrayList<>();
        try {
            methods.addAll(Arrays.asList(clazz.getDeclaredMethods()));
            methods.addAll(getDefaultMethodsOfInterfaces(clazz));
        } catch (Throwable ex) {
            throw new IllegalStateException("Failed to get declared methods for Class [" + clazz.getName() + "] from ClassLoader [" + clazz.getClassLoader() + "]", ex);
        }
        return methods;
    }

    public static Method findMethodByName(final Class<?> clazz, final String name) {
        Class<?> classSearchType = clazz;
        while (classSearchType != null) {
            Method[] methods = (classSearchType.isInterface() ? classSearchType.getMethods() : getDeclaredMethods(classSearchType));
            for (Method method : methods) {
                if (name.equals(method.getName())) {
                    return method;
                }
            }
            classSearchType = classSearchType.getSuperclass();
        }
        return null;
    }

    public static void clearUnselectedFields(final Object object, final Collection<String> fields) {
        if (fields != null && !fields.isEmpty()) {
            for (Field field : FieldUtils.getAllFields(object.getClass())) {
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
}
