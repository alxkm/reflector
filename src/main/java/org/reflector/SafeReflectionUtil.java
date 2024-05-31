package org.reflector;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
     * The private constructor of {@link ReflectionUtils}.
     * */
    private SafeReflectionUtil() {}

    public static Optional<String> getClassFullName(final Object obj) {
        try {
            return Optional.of(ReflectionUtils.getClassFullName(obj));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<String> getClassCanonicalName(final Object obj) {
        try {
            return Optional.of(ReflectionUtils.getClassCanonicalName(obj));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<String> getClassSimpleName(final Object obj) {
        try {
            return Optional.of(ReflectionUtils.getClassSimpleName(obj));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<String> getPackage(final Object obj) {
        try {
            return Optional.of(ReflectionUtils.getPackage(obj));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<String> getClassFullNameByClass(final Class<?> clazz) {
        try {
            return Optional.of(ReflectionUtils.getClassFullNameByClass(clazz));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<String> getClassCanonicalNameByClass(final Class<?> clazz) {
        try {
            return Optional.of(ReflectionUtils.getClassCanonicalNameByClass(clazz));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<String> getClassSimpleNameByClass(final Class<?> clazz) {
        try {
            return Optional.of(ReflectionUtils.getClassSimpleNameByClass(clazz));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<String> getPackageByClass(final Class<?> clazz) {
        try {
            return Optional.of(ReflectionUtils.getPackageByClass(clazz));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<String> getSuperClassName(final Class<?> clazz) {
        try {
            return Optional.of(ReflectionUtils.getSuperClassName(clazz));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<String> getSuperClassNameByClass(final Class<?> clazz) {
        try {
            return Optional.of(ReflectionUtils.getSuperClassNameByClass(clazz));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<?> getSuperClass(final Class<?> clazz) {
        try {
            return Optional.of(ReflectionUtils.getSuperClass(clazz));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static void clearUnselectedFields(final Object object, final Collection<String> fields)  {
        try {
           ReflectionUtils.clearUnselectedFields(object, fields);
        } catch (Exception ignored) {
        }
    }

    public static Optional<Annotation[]> getClassAnnotations(final Class<?> clazz)   {
        try {
            return Optional.of(ReflectionUtils.getClassAnnotations(clazz));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<Annotation[]> getMethodDeclaredAnnotations(final Method method)   {
        try {
            return Optional.of(ReflectionUtils.getMethodDeclaredAnnotations(method));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<Map<Method, Annotation[]>> getMethodDeclaredAnnotations(final Method[] methods) {
        try {
            return Optional.of(ReflectionUtils.getMethodDeclaredAnnotations(methods));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<Boolean> isMethodAnnotated(final Method method, final Class clazz) {
        try {
            return Optional.of(ReflectionUtils.isMethodAnnotated(method, clazz));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static <T> Optional<Boolean> isMethodParameterAnnotated(final Method method, final Class<T> clazz) {
        try {
            return Optional.of(ReflectionUtils.isMethodParameterAnnotated(method, clazz));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static <T> Optional<Boolean> isFieldAnnotated(final Field field, final Class<T> clazz) {
        try {
            return Optional.of(ReflectionUtils.isFieldAnnotated(field, clazz));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static <T> Optional<Boolean> isFieldExactAnnotated(final Field field, final Class type) {
        try {
            return Optional.of(ReflectionUtils.isFieldExactAnnotated(field, type));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<List<Field>> getAllFields(final Class<?> type) {
        try {
            return Optional.of(ReflectionUtils.getAllFields(type));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<List<Field>> getAllFields(final List<Field> fields, final Class<?> type) {
        try {
            return Optional.of(ReflectionUtils.getAllFields(type));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<List<Field>> getAllPrivateFields(final Class<?> clazz)  {
        try {
            return Optional.of(ReflectionUtils.getAllPrivateFields(clazz));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<Map<String, Field>> getAllFieldsMap(final Class<?> clazz)  {
        try {
            return Optional.of(ReflectionUtils.getAllFieldsMap(clazz));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<Map<String, Field>> getAllPrivateFieldsMap(final Class<?> clazz)  {
        try {
            return Optional.of(ReflectionUtils.getAllPrivateFieldsMap(clazz));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<List<Method>> getAllPrivateMethods(final Class<?> clazz)  {
        try {
           return Optional.of(ReflectionUtils.getAllPrivateMethods(clazz));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<List<Method>> getAllPublicProtectedMethods(final Class<?> clazz)  {
        try {
            return Optional.of(ReflectionUtils.getAllPublicProtectedMethods(clazz));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<List<Method>> getAllPublicMethods(final Class<?> clazz)  {
        try {
            return Optional.of(ReflectionUtils.getAllPublicMethods(clazz));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<List<Field>> getAllAnnotatedFields(final Class<?> type, final Class<? extends Annotation> annotation)  {
        try {
            return Optional.of(ReflectionUtils.getAllAnnotatedFields(type, annotation));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<Object> readField(final Object object, final String fieldName) {
        try {
            return Optional.of(ReflectionUtils.readField(object, fieldName));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<Object> invokeMethod(final Object objectToInvokeOn,
                                                final String methodName,
                                                final Class<?>[] parameterTypes,
                                                final Object[] args) {
        try {
            return Optional.of(ReflectionUtils.invokeMethod(objectToInvokeOn, methodName, parameterTypes,  args));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<Object> invokeSingleMethod(final Object objectToInvokeOn,
                                                      final String methodName,
                                                      final Class<?> parameterType,
                                                      final Object parameter) {
        try {
            return Optional.of(ReflectionUtils.invokeSingleMethod(objectToInvokeOn, methodName, parameterType, parameter));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<Object> invokeInstance(String className) {
        try {
            return Optional.of(ReflectionUtils.invokeInstance(className));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<Object> invokeInstance(final String classFullName,
                                                  final Class<?>[] contTypes,
                                                  final Object[] obj) {
        try {
            return Optional.of(ReflectionUtils.invokeInstance(classFullName, contTypes, obj));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static <T> Optional<Object> invokeInstance(final Class<T> clazz, final Object... args) {
        try {
            return Optional.of(ReflectionUtils.invokeInstance(clazz, args));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<Class<?>[]> getArrayValuesTypesByArgs(final Object[] args) {
        try {
            return Optional.of(ReflectionUtils.getArrayValuesTypesByArgs(args));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static <T> Optional<Constructor<T>> getAccessibleConstructor(final Class<?>[] contTypes, final Class<T> clazz) {
        try {
            return Optional.of(ReflectionUtils.getAccessibleConstructor(contTypes, clazz));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<Constructor<?>[]> getConstructors(final Class<?> clazz) {
        try {
            return Optional.of(ReflectionUtils.getConstructors(clazz));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<Constructor<?>[]> getDeclaredConstructors(final Class<?> clazz) {
        try {
            return Optional.of(ReflectionUtils.getDeclaredConstructors(clazz));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<Object> copy(final Object object) {
        try {
            return Optional.of(ReflectionUtils.copy(object));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<List<Class<?>>> getClassesByPackage(final String packageName) {
        try {
            return Optional.of(ReflectionUtils.getClassesByPackage(packageName));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<List<Class<?>>> getClassesByDirectoryAndPackage(final File directory, final String packageName) {
        try {
            return Optional.of(ReflectionUtils.getClassesByDirectoryAndPackage(directory, packageName));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static Optional<List<Class<?>>> getAllAnnotatedClassesByPackage(final String packageName, final Class annotation) {
        try {
            return Optional.of(ReflectionUtils.getAllAnnotatedClassesByPackage(packageName, annotation));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }
}
