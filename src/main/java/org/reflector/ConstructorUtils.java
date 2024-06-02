package org.reflector;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

public class ConstructorUtils {

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
}
