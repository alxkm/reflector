package org.reflector;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class MethodUtils {

    private MethodUtils() {}

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

    /**
     * Retrieves all default methods from the interfaces implemented by the specified class.
     *
     * @param clazz the class whose interfaces' default methods are to be retrieved.
     * @return a list of default methods from the interfaces implemented by the specified class.
     * @throws IllegalArgumentException if the class parameter is null.
     */
    public static List<Method> getDefaultMethodsOfInterfaces(final Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Class parameter cannot be null");
        }

        return Stream.of(clazz.getInterfaces())
                .flatMap(ifc -> Stream.of(ifc.getMethods()))
                .filter(Method::isDefault)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all declared methods of the specified class, including default methods from its interfaces.
     *
     * @param clazz the class whose declared methods and default interface methods are to be retrieved.
     * @return an array of {@link Method} objects reflecting all declared methods of the class,
     *         including default methods from its interfaces.
     * @throws IllegalArgumentException if the class parameter is null.
     * @throws IllegalStateException if an error occurs while retrieving the methods.
     */
    public static Method[] getDeclaredMethods(final Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Class parameter cannot be null");
        }

        try {
            Method[] declaredMethods = clazz.getDeclaredMethods();
            List<Method> defaultMethods = getDefaultMethodsOfInterfaces(clazz);

            Method[] result = new Method[declaredMethods.length + defaultMethods.size()];
            System.arraycopy(declaredMethods, 0, result, 0, declaredMethods.length);

            int index = declaredMethods.length;
            for (Method defaultMethod : defaultMethods) {
                result[index++] = defaultMethod;
            }

            return result;
        } catch (Throwable ex) {
            throw new IllegalStateException("Failed to get declared methods for Class [" + clazz.getName() + "] from ClassLoader [" + clazz.getClassLoader() + "]", ex);
        }
    }

    /**
     * Retrieves all declared methods of the specified class, including default methods from its interfaces,
     * and returns them as a list.
     *
     * @param clazz the class whose declared methods and default interface methods are to be retrieved.
     * @return a list of {@link Method} objects reflecting all declared methods of the class,
     *         including default methods from its interfaces.
     * @throws IllegalArgumentException if the class parameter is null.
     * @throws IllegalStateException if an error occurs while retrieving the methods.
     */
    public static List<Method> getDeclaredMethodsList(final Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Class parameter cannot be null");
        }

        try {
            List<Method> methods = new ArrayList<>(Arrays.asList(clazz.getDeclaredMethods()));
            methods.addAll(getDefaultMethodsOfInterfaces(clazz));
            return methods;
        } catch (Throwable ex) {
            throw new IllegalStateException("Failed to get declared methods for Class [" + clazz.getName() + "] from ClassLoader [" + clazz.getClassLoader() + "]", ex);
        }
    }

    /**
     * Finds a method by name in the specified class or its superclasses and interfaces.
     *
     * @param clazz the class in which to search for the method.
     * @param name the name of the method to search for.
     * @return the {@link Method} object if a method with the specified name is found, or null if not found.
     * @throws IllegalArgumentException if the class or method name parameter is null.
     */
    public static Method findMethodByName(final Class<?> clazz, final String name) {
        if (clazz == null || name == null) {
            throw new IllegalArgumentException("Class and method name parameters cannot be null");
        }

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
}
