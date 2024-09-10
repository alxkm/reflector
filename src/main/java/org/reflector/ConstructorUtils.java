package org.reflector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;

public final class ConstructorUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConstructorUtils.class);

    private ConstructorUtils() {
    }

    /**
     * Retrieves the parameters of a constructor.
     *
     * @param constructor the constructor from which to retrieve parameters
     * @return an array of parameters of the constructor
     * @throws NullPointerException if the constructor is null
     */
    public static Parameter[] getConstructorParameters(Constructor<?> constructor) {
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
    public static int getConstructorModifiers(Constructor<?> constructor) {
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

        try {
            return clazz.getConstructors();
        } catch (Exception e) {
            LOGGER.error("Error retrieving constructors for class '{}'", clazz.getName(), e);
            throw e;
        }
    }

    /**
     * Retrieves all declared constructors of the specified class, including public, protected, default (package), and private constructors.
     *
     * @param clazz the class from which to retrieve declared constructors
     * @return an array of declared constructors of the specified class
     * @throws NullPointerException if the class is null
     */
    public static Constructor<?>[] getDeclaredConstructors(final Class<?> clazz) {
        if (clazz == null) {
            throw new NullPointerException("Class type cannot be null");
        }

        try {
            return clazz.getDeclaredConstructors();
        } catch (Exception e) {
            LOGGER.error("Error retrieving declared constructors for class '{}'", clazz.getName(), e);
            throw e;
        }
    }
}
