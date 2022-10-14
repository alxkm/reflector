package org.reflector;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 * Simple wrapper for utility class for working with the reflection API
 *
 */
public final class SafeReflectionUtil {

    /**
     * The private constructor of {@link ReflectionUtil}.
     * */
    private SafeReflectionUtil() {}

    public static Optional<String> getClassFullName(final Object obj) {
        try {
            Optional.of(ReflectionUtil.getClassFullName(obj));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<String> getClassCanonicalName(final Object obj) {
        try {
            Optional.of(ReflectionUtil.getClassCanonicalName(obj));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<String> getClassSimpleName(final Object obj) {
        try {
            Optional.of(ReflectionUtil.getClassSimpleName(obj));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<String> getPackage(final Object obj) {
        try {
            Optional.of(ReflectionUtil.getPackage(obj));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<String> getClassFullNameByClass(final Class<?> clazz) {
        try {
            Optional.of(ReflectionUtil.getClassFullNameByClass(clazz));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<String> getClassCanonicalNameByClass(final Class<?> clazz) {
        try {
            Optional.of(ReflectionUtil.getClassCanonicalNameByClass(clazz));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<String> getClassSimpleNameByClass(final Class<?> clazz) {
        try {
            Optional.of(ReflectionUtil.getClassSimpleNameByClass(clazz));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<String> getPackageByClass(final Class<?> clazz) {
        try {
            Optional.of(ReflectionUtil.getPackageByClass(clazz));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<String> getSuperClassName(final Class<?> clazz) {
        try {
            Optional.of(ReflectionUtil.getSuperClassName(clazz));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<String> getSuperClassNameByClass(final Class<?> clazz) {
        try {
            Optional.of(ReflectionUtil.getSuperClassNameByClass(clazz));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<String> getSuperClass(final Class<?> clazz) {
        try {
            Optional.of(ReflectionUtil.getSuperClass(clazz));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<String> clearUnselectedFields(final Object object, final Collection<String> fields)  {
        try {
           ReflectionUtil.clearUnselectedFields(object, fields);
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<Annotation[]> getClassAnnotations(final Class<?> clazz)   {
        try {
            Optional.of(ReflectionUtil.getClassAnnotations(clazz));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<Annotation[]> getMethodDeclaredAnnotations(final Method method)   {
        try {
            Optional.of(ReflectionUtil.getMethodDeclaredAnnotations(method));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<Map<Method, Annotation[]>> getMethodDeclaredAnnotations(final Method[] methods) {
        try {
            Optional.of(ReflectionUtil.getMethodDeclaredAnnotations(methods));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<Boolean> isMethodAnnotated(final Method method, final Class clazz) {
        try {
            Optional.of(ReflectionUtil.isMethodAnnotated(method, clazz));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static <T> Optional<Boolean> isMethodParameterAnnotated(final Method method, final Class<T> clazz) {
        try {
            Optional.of(ReflectionUtil.isMethodParameterAnnotated(method, clazz));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static <T> Optional<Boolean> isFieldAnnotated(final Field field, final Class<T> clazz) {
        try {
            return Optional.of(ReflectionUtil.isFieldAnnotated(field, clazz));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static <T> Optional<Boolean> isFieldExactAnnotated(final Field field, final Class type) {
        try {
            return Optional.of(ReflectionUtil.isFieldExactAnnotated(field, type));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<List<Field>> getAllFields(final Class<?> type) {
        try {
            Optional.of(ReflectionUtil.getAllFields(type));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<List<Field>> getAllFields(final List<Field> fields, final Class<?> type) {
        try {
            return Optional.of(ReflectionUtil.getAllFields(type));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<List<Field>> getAllPrivateFields(final Class<?> clazz)  {
        try {
            return Optional.of(ReflectionUtil.getAllPrivateFields(clazz));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<Map<String, Field>> getAllFieldsMap(final Class<?> clazz)  {
        try {
            return Optional.of(ReflectionUtil.getAllFieldsMap(clazz));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<Map<String, Field>> getAllPrivateFieldsMap(final Class<?> clazz)  {
        try {
            Optional.of(ReflectionUtil.getAllPrivateFieldsMap(clazz));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<List<Method>> getAllPrivateMethods(final Class<?> clazz)  {
        try {
            Optional.of(ReflectionUtil.getAllPrivateMethods(clazz));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<List<Method>> getAllPublicProtectedMethods(final Class<?> clazz)  {
        try {
            Optional.of(ReflectionUtil.getAllPublicProtectedMethods(clazz));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<List<Method>> getAllPublicMethods(final Class<?> clazz)  {
        try {
            Optional.of(ReflectionUtil.getAllPublicMethods(clazz));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<List<Field>> getAllAnnotatedFields(final Class<?> type, final Class<? extends Annotation> annotation)  {
        try {
            Optional.of(ReflectionUtil.getAllAnnotatedFields(type, annotation));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static <T> Optional<T> readField(Object object, String fieldName) {
        try {
            Optional.of(ReflectionUtil.readField(object, fieldName));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static <T> Optional<T> invokeMethod(Object objectToInvokeOn, String methodName, Class<?>[] parameterTypes, Object[] args) {
        try {
            Optional.of(ReflectionUtil.invokeMethod(objectToInvokeOn, methodName, parameterTypes, args));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static <T> Optional<T> invokeSingleMethod(Object objectToInvokeOn, String methodName, Class<?> parameterType, Object parameter) {
        try {
            Optional.of(ReflectionUtil.invokeSingleMethod(objectToInvokeOn, methodName, parameterType, parameter));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static <T> Optional<T> invokeInstance(String className) {
        try {
            Optional.of(ReflectionUtil.invokeInstance(className));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static <T> Optional<T> invokeInstance(String classFullName, Class<?>[] contTypes, Object[] obj) {
        try {
            Optional.of(ReflectionUtil.invokeInstance(classFullName, contTypes, obj));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static <T> Optional<T> invokeInstance(final Class<T> clazz, final Object... args) {
        try {
            Optional.of(ReflectionUtil.invokeInstance(clazz, args));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static <T> Optional<Class<?>[]> getArrayValuesTypesByArgs(final Object[] args) {
        try {
            Optional.of(ReflectionUtil.getArrayValuesTypesByArgs(args));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static <T> Optional<Constructor<T>> getAccessibleConstructor(final Class<?>[] contTypes, final Class<T> clazz) {
        try {
            Optional.of(ReflectionUtil.getAccessibleConstructor(contTypes, clazz));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<Constructor<?>[]> getConstructors(final Class<?> clazz) {
        try {
            Optional.of(ReflectionUtil.getConstructors(clazz));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<Constructor<?>[]> getDeclaredConstructors(final Class<?> clazz) {
        try {
            Optional.of(ReflectionUtil.getDeclaredConstructors(clazz));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<Object> copy(final Object object) {
        try {
            Optional.of(ReflectionUtil.copy(object));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<List<Class<?>>> getClassesByPackage(final String packageName) {
        try {
            Optional.of(ReflectionUtil.getClassesByPackage(packageName));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<List<Class<?>>> getClassesByDirectoryAndPackage(final File directory, final String packageName) {
        try {
            Optional.of(ReflectionUtil.getClassesByDirectoryAndPackage(directory, packageName));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<List<Class<?>>> getAllAnnotatedClassesByPackage(final String packageName, final Class annotation) {
        try {
            Optional.of(ReflectionUtil.getAllAnnotatedClassesByPackage(packageName, annotation));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }
}
