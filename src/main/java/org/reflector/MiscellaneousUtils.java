package org.reflector;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class MiscellaneousUtils {

    /**
     * Creates a new instance of a class using its no-argument constructor.
     *
     * @param clazz the class of which to create an instance
     * @return a new instance of the specified class
     * @throws NullPointerException if the class is null
     * @throws InstantiationException if the class represents an abstract class, an interface, an array class, a primitive type, or void;
     *                                or if the class has no nullary constructor
     * @throws IllegalAccessException if the class or its nullary constructor is not accessible
     * @throws InvocationTargetException if the nullary constructor throws an exception
     */
    public static <T> T newInstance(Class<T> clazz) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (clazz == null) {
            throw new NullPointerException("Class cannot be null");
        }
        Constructor<T> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }

    /**
     * Retrieves the component type of an array class.
     *
     * @param arrayClass the array class
     * @return the component type of the array class
     * @throws NullPointerException if the array class is null
     */
    public static Class<?> getArrayComponentType(Class<?> arrayClass) {
        if (arrayClass == null) {
            throw new NullPointerException("Array class cannot be null");
        }
        return arrayClass.getComponentType();
    }
}

