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

    /**
     * Invokes a method on an object.
     *
     * @param objectToInvokeOn the object to invoke the method on
     * @param methodName        the name of the method to invoke
     * @param parameterTypes    the parameter types of the method
     * @param args              the arguments to pass to the method
     * @return the result of the method invocation
     * @throws MethodInvokeException if an error occurs during method invocation
     */
    public static Object invokeMethod(final Object objectToInvokeOn, final String methodName, final Class<?>[] parameterTypes, final Object[] args) {
        try {
            Method method = objectToInvokeOn.getClass().getDeclaredMethod(methodName, parameterTypes);
            return method.invoke(objectToInvokeOn, args);
        } catch (Exception e) {
            LOGGER.error("Could not invoke method", e);
        }
        throw new MethodInvokeException("Error during method invoke has been happened");
    }

    /**
     * Invokes a single-parameter method on an object.
     *
     * @param objectToInvokeOn the object to invoke the method on
     * @param methodName        the name of the method to invoke
     * @param parameterType     the type of the parameter of the method
     * @param parameter         the parameter value to pass to the method
     * @return the result of the method invocation
     * @throws MethodInvokeException if an error occurs during method invocation
     */
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

    /**
     * Instantiates a class without constructor arguments.
     *
     * @param className the name of the class to instantiate
     * @return the new instance of the class
     * @throws InstanceInvocationException if an error occurs during instance invocation
     */
    public static Object invokeInstance(final String className) throws InstanceInvocationException {
        try {
            return Class.forName(className).newInstance();
        } catch (Exception e) {
            LOGGER.error("Could not instantiate class object ", e);
        }
        throw new InstanceInvocationException("Error during instance invoke has been happened");
    }

    /**
     * Instantiates a class with constructor arguments.
     *
     * @param classFullName the fully qualified name of the class to instantiate
     * @param args          the arguments to pass to the constructor
     * @return the new instance of the class
     * @throws InstanceInvocationException if an error occurs during instance invocation
     */
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

    /**
     * Instantiates a class with constructor arguments.
     *
     * @param clazz the class to instantiate
     * @param args  the arguments to pass to the constructor
     * @param <T>   the type of the class to instantiate
     * @return the new instance of the class
     * @throws InstanceInvocationException if an error occurs during instance invocation
     */
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

    /**
     * Gets the types of the arguments.
     *
     * @param args the arguments
     * @return an array of argument types
     */
    public static Class<?>[] getArrayValuesTypesByArgs(final Object[] args) {
        final Class<?>[] ctorTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            ctorTypes[i] = args[i].getClass();
        }
        return ctorTypes;
    }

    /**
     * Gets a constructor with accessible flag set.
     *
     * @param contTypes the types of the constructor parameters
     * @param clazz     the class
     * @param <T>       the type of the class
     * @return the constructor
     * @throws NoSuchMethodException if the constructor is not found
     */
    public static <T> Constructor<T> getAccessibleConstructor(final Class<?>[] contTypes, final Class<T> clazz) throws NoSuchMethodException {
        final Constructor<T> ctor = clazz.getConstructor(contTypes);
        ctor.setAccessible(true);
        return ctor;
    }
}
