package org.reflector;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;

public final class ConstructorUtils {

    private ConstructorUtils() {
    }

    /**
     * Retrieves the parameters of a constructor.
     *
     * @param constructor the constructor from which to retrieve parameters
     * @return an array of parameters of the constructor
     * @throws NullPointerException if the constructor is null
     */
    public static Parameter[] getConstructorParameters(final Constructor<?> constructor) {
        if (constructor == null) {
            throw new NullPointerException("Constructor cannot be null");
        }
        return constructor.getParameters();
    }

    /**
     * Retrieves the modifiers of a constructor.
     *
     * @param constructor the constructor from which to retrieve modifiers
     * @return an integer representing the modifiers of the constructor
     * @throws NullPointerException if the constructor is null
     */
    public static int getConstructorModifiers(final Constructor<?> constructor) {
        if (constructor == null) {
            throw new NullPointerException("Constructor cannot be null");
        }
        return constructor.getModifiers();
    }

    /**
     * Retrieves all public constructors of the specified class.
     *
     * @param clazz the class from which to retrieve constructors
     * @return an array of public constructors of the specified class
     * @throws NullPointerException if the class is null
     */
    public static Constructor<?>[] getConstructors(final Class<?> clazz) {
        if (clazz == null) {
            throw new NullPointerException("Class type cannot be null");
        }
        return clazz.getConstructors();
    }

    /**
     * Retrieves all declared constructors of the specified class, including public, protected,
     * default (package) and private constructors.
     *
     * @param clazz the class from which to retrieve declared constructors
     * @return an array of declared constructors of the specified class
     * @throws NullPointerException if the class is null
     */
    public static Constructor<?>[] getDeclaredConstructors(final Class<?> clazz) {
        if (clazz == null) {
            throw new NullPointerException("Class type cannot be null");
        }
        return clazz.getDeclaredConstructors();
    }
}
