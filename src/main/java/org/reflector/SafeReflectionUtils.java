package org.reflector;

import java.util.Optional;

public final class SafeReflectionUtils {
    private SafeReflectionUtils() {}

    public static <T> Optional<T> readField(Object object, String fieldName) {
        try {
            Optional.of(ReflectionUtils.readField(object, fieldName));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static <T> Optional<T> invokeMethod(Object objectToInvokeOn, String methodName, Class<?>[] parameterTypes, Object[] args) {
        try {
            Optional.of(ReflectionUtils.invokeMethod(objectToInvokeOn, methodName, parameterTypes, args));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static <T> Optional<T> invokeSingleMethod(Object objectToInvokeOn, String methodName, Class<?> parameterType, Object parameter) {
        try {
            Optional.of(ReflectionUtils.invokeSingleMethod(objectToInvokeOn, methodName, parameterType, parameter));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static <T> Optional<T> invokeInstance(String className) {
        try {
            Optional.of(ReflectionUtils.invokeInstance(className));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }

    public static <T> Optional<T> invokeInstance(String classFullName, Class<?>[] contTypes, Object[] obj) {
        try {
            Optional.of(ReflectionUtils.invokeInstance(classFullName, contTypes, obj));
        } catch (Exception ignored) {
        }
        return Optional.empty();
    }
}
