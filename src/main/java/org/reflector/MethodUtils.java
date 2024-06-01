package org.reflector;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.IntPredicate;

public class MethodUtils {
    /**
     * Retrieves the parameter types of the given method.
     *
     * @param method the method whose parameter types are to be retrieved
     * @return an array of Classes representing the parameter types of the method
     * @throws IllegalArgumentException if the provided method is null
     */
    public static Class<?>[] getParameterTypes(final Method method) {
        if (method == null) {
            throw new IllegalArgumentException("Method must not be null");
        }

        return method.getParameterTypes();
    }

    /**
     * Gets the return type of the given method.
     *
     * @param method the method whose return type is to be retrieved
     * @return the Class representing the return type of the method
     * @throws IllegalArgumentException if the provided method is null
     */
    public static Class<?> getReturnType(final Method method) {
        if (method == null) {
            throw new IllegalArgumentException("Method must not be null");
        }

        return method.getReturnType();
    }

    /**
     * Gets the types of exceptions thrown by the given method.
     *
     * @param method the method whose exception types are to be retrieved
     * @return an array of Classes representing the exception types thrown by the method
     * @throws IllegalArgumentException if the provided method is null
     */
    public static Class<?>[] getExceptionTypes(final Method method) {
        if (method == null) {
            throw new IllegalArgumentException("Method must not be null");
        }

        return method.getExceptionTypes();
    }

    /**
     * Retrieves the modifiers of the given method.
     *
     * @param method the method whose modifiers are to be retrieved
     * @return an int representing the modifiers of the method
     * @throws IllegalArgumentException if the provided method is null
     */
    public static int getMethodModifiers(final Method method) {
        if (method == null) {
            throw new IllegalArgumentException("Method must not be null");
        }
        return method.getModifiers();
    }

    /**
     * Checks if the given method takes a variable number of arguments.
     *
     * @param method the method to be checked
     * @return true if the method takes a variable number of arguments, false otherwise
     * @throws IllegalArgumentException if the provided method is null
     */
    public static boolean isMethodVarArgs(final Method method) {
        if (method == null) {
            throw new IllegalArgumentException("Method must not be null");
        }
        return method.isVarArgs();
    }

    /**
     * Gets the default value of the given method's annotation element.
     *
     * @param method the method whose annotation element's default value is to be retrieved
     * @return the default value of the annotation element, or null if none
     * @throws IllegalArgumentException if the provided method is null
     */
    public static Object getDefaultValue(final Method method) {
        if (method == null) {
            throw new IllegalArgumentException("Method must not be null");
        }
        return method.getDefaultValue();
    }

    /**
     * Retrieves all private methods of a class.
     *
     * @param clazz the class from which to retrieve methods
     * @return a list of private methods of the specified class
     * @throws NullPointerException if the clazz is null
     */
    public static List<Method> getAllPrivateMethods(final Class<?> clazz) {
        return getAllMethodsWithModifiers(clazz, Collections.singletonList(Modifier::isPrivate));
    }

    /**
     * Retrieves all public and protected methods of a class.
     *
     * @param clazz the class from which to retrieve methods
     * @return a list of public and protected methods of the specified class
     * @throws NullPointerException if the clazz is null
     */
    public static List<Method> getAllPublicProtectedMethods(final Class<?> clazz) {
        return getAllMethodsWithModifiers(clazz, Arrays.asList(Modifier::isPublic, Modifier::isProtected));
    }

    /**
     * Retrieves all public methods of a class.
     *
     * @param clazz the class from which to retrieve methods
     * @return a list of public methods of the specified class
     * @throws NullPointerException if the clazz is null
     */
    public static List<Method> getAllPublicMethods(final Class<?> clazz) {
        return getAllMethodsWithModifiers(clazz, Collections.singletonList(Modifier::isPublic));
    }

    /**
     * Retrieves all methods of a class that match the given modifiers.
     *
     * @param clazz     the class from which to retrieve methods
     * @param modifiers the list of predicates to match the method modifiers
     * @return a list of methods that match the given modifiers
     * @throws NullPointerException if the clazz or modifiers are null
     */
    public static List<Method> getAllMethodsWithModifiers(final Class<?> clazz, final List<IntPredicate> modifiers) {
        if (clazz == null) {
            throw new NullPointerException("Class cannot be null");
        }
        if (modifiers == null) {
            throw new NullPointerException("Modifiers cannot be null");
        }

        List<Method> methods = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()) {
            boolean matches = modifiers.stream().anyMatch(modifier -> modifier.test(method.getModifiers()));
            if (matches) {
                methods.add(method);
            }
        }
        return methods;
    }
}
