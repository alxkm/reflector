package org.reflector;

public final class GeneralUtils {

    private GeneralUtils() {}

    /**
     * Checks if a given class is an interface.
     *
     * @param clazz the class to check
     * @return true if the class is an interface, false otherwise
     */
    public static boolean isInterface(Class<?> clazz) {
        if (clazz == null) {
            throw new NullPointerException("Class cannot be null");
        }
        return clazz.isInterface();
    }

    /**
     * Checks if a given class is an array.
     *
     * @param clazz the class to check
     * @return true if the class is an array, false otherwise
     */
    public static boolean isArray(Class<?> clazz) {
        if (clazz == null) {
            throw new NullPointerException("Class cannot be null");
        }
        return clazz.isArray();
    }

    /**
     * Checks if a given class is an enum.
     *
     * @param clazz the class to check
     * @return true if the class is an enum, false otherwise
     */
    public static boolean isEnum(Class<?> clazz) {
        if (clazz == null) {
            throw new NullPointerException("Class cannot be null");
        }
        return clazz.isEnum();
    }

    /**
     * Checks if a given class is an annotation.
     *
     * @param clazz the class to check
     * @return true if the class is an annotation, false otherwise
     */
    public static boolean isAnnotation(Class<?> clazz) {
        if (clazz == null) {
            throw new NullPointerException("Class cannot be null");
        }
        return clazz.isAnnotation();
    }

    /**
     * Checks if a given class is anonymous.
     *
     * @param clazz the class to check
     * @return true if the class is anonymous, false otherwise
     */
    public static boolean isAnonymousClass(Class<?> clazz) {
        if (clazz == null) {
            throw new NullPointerException("Class cannot be null");
        }
        return clazz.isAnonymousClass();
    }

    /**
     * Retrieves the inner classes declared within a class.
     *
     * @param clazz the class to check
     * @return an array of inner classes declared within the class
     */
    public static Class<?>[] getInnerClasses(Class<?> clazz) {
        if (clazz == null) {
            throw new NullPointerException("Class cannot be null");
        }
        return clazz.getDeclaredClasses();
    }
}

