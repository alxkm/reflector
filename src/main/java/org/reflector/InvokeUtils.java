package org.reflector;

import org.reflector.exception.InstanceInvocationException;
import org.reflector.exception.MethodInvokeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public final class InvokeUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(InvokeUtils.class);

    private InvokeUtils() {
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
}
