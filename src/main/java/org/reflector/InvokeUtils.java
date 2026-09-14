package org.reflector;

import org.reflector.exception.InstanceInvocationException;
import org.reflector.exception.MethodInvokeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.Arrays;
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
            final Method method = objectToInvokeOn.getClass().getDeclaredMethod(methodName, parameterTypes);
            method.setAccessible(true);
            return method.invoke(objectToInvokeOn, args);
        } catch (Exception e) {
            LOGGER.error("Could not invoke method {{}}", methodName, e);
            throw new MethodInvokeException("Could not invoke method " + methodName, e);
        }
    }

    /**
     * Invokes a single-parameter method on an object.
     *
     * <p>Resolves public methods only. Use
     * {@link #invokeMethod(Object, String, Class[], Object[])} to reach a private one.</p>
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
            // A public method on a package-private class is still not callable without this.
            method.setAccessible(true);
            return method.invoke(objectToInvokeOn, parameter);
        } catch (Exception e) {
            LOGGER.error("Could not invoke method {{}}", methodName, e);
            throw new MethodInvokeException("Could not invoke method " + methodName, e);
        }
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
            final Constructor<?> ctor = Class.forName(className).getDeclaredConstructor();
            ctor.setAccessible(true);
            return ctor.newInstance();
        } catch (Exception e) {
            LOGGER.error("Could not instantiate class {{}}", className, e);
            throw new InstanceInvocationException("Could not instantiate class " + className, e);
        }
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
            LOGGER.error("Could not instantiate class {{}}", classFullName, e);
            throw new InstanceInvocationException("Could not instantiate class " + classFullName, e);
        }
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
            LOGGER.error("Could not instantiate class {{}}", clazz, e);
            throw new InstanceInvocationException("Could not instantiate class " + clazz, e);
        }
    }

    /**
     * Gets the runtime types of the arguments.
     *
     * <p>A null argument yields a null entry, which {@link #getAccessibleConstructor} treats as
     * matching any reference parameter.</p>
     *
     * @param args the arguments, may be null
     * @return an array of argument types, empty when args is null
     */
    public static Class<?>[] getArrayValuesTypesByArgs(final Object[] args) {
        if (args == null) {
            return new Class<?>[0];
        }
        final Class<?>[] ctorTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            ctorTypes[i] = (args[i] != null) ? args[i].getClass() : null;
        }
        return ctorTypes;
    }

    /**
     * Gets a constructor with accessible flag set.
     *
     * <p>An exact match on the declared parameter types is preferred. Failing that, the declared
     * constructors are scanned for one whose parameters can accept the given types, which is what
     * makes a constructor taking {@code int} reachable when the argument is an {@code Integer}.
     * Non-public constructors are considered by the second pass.</p>
     *
     * @param contTypes the types of the constructor parameters
     * @param clazz     the class
     * @param <T>       the type of the class
     * @return the constructor
     * @throws NoSuchMethodException if no matching constructor is found
     */
    @SuppressWarnings("unchecked")
    public static <T> Constructor<T> getAccessibleConstructor(final Class<?>[] contTypes, final Class<T> clazz) throws NoSuchMethodException {
        if (clazz == null) {
            throw new NullPointerException("Class cannot be null");
        }
        final Class<?>[] types = (contTypes != null) ? contTypes : new Class<?>[0];

        try {
            final Constructor<T> exact = clazz.getConstructor(types);
            exact.setAccessible(true);
            return exact;
        } catch (NoSuchMethodException exactMissing) {
            // fall through to the assignability scan below
        }

        for (Constructor<?> candidate : clazz.getDeclaredConstructors()) {
            if (isApplicable(candidate.getParameterTypes(), types)) {
                candidate.setAccessible(true);
                return (Constructor<T>) candidate;
            }
        }

        throw new NoSuchMethodException(clazz.getName() + ".<init>" + Arrays.toString(types));
    }

    /**
     * Tells whether arguments of the given types can be passed to the given parameter list.
     *
     * @param parameterTypes the declared parameter types of a constructor
     * @param argumentTypes  the runtime types of the arguments
     * @return true if each argument is assignable to the matching parameter
     */
    private static boolean isApplicable(final Class<?>[] parameterTypes, final Class<?>[] argumentTypes) {
        if (parameterTypes.length != argumentTypes.length) {
            return false;
        }
        for (int i = 0; i < parameterTypes.length; i++) {
            final Class<?> parameter = parameterTypes[i];
            final Class<?> argument = argumentTypes[i];
            if (argument == null) {
                if (parameter.isPrimitive()) {
                    return false;
                }
                continue;
            }
            if (parameter.isAssignableFrom(argument)) {
                continue;
            }
            if (parameter.isPrimitive() && wrapperOf(parameter) == argument) {
                continue;
            }
            return false;
        }
        return true;
    }

    /**
     * Maps a primitive type to its wrapper class.
     *
     * @param primitive the primitive type
     * @return the matching wrapper class, or null if the type is not primitive
     */
    private static Class<?> wrapperOf(final Class<?> primitive) {
        if (primitive == int.class) {
            return Integer.class;
        }
        if (primitive == long.class) {
            return Long.class;
        }
        if (primitive == boolean.class) {
            return Boolean.class;
        }
        if (primitive == double.class) {
            return Double.class;
        }
        if (primitive == float.class) {
            return Float.class;
        }
        if (primitive == short.class) {
            return Short.class;
        }
        if (primitive == byte.class) {
            return Byte.class;
        }
        if (primitive == char.class) {
            return Character.class;
        }
        return null;
    }
}
