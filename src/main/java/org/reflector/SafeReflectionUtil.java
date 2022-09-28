package org.reflector;

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
}
