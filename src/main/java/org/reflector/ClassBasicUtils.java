package org.reflector;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

public final class ClassBasicUtils {
    private ClassBasicUtils() {

    }

    /**
     * Gets the full name (including the package name) of the class of the given object.
     *
     * @param obj the object whose class full name is to be retrieved
     * @return the full name of the class of the object
     * @throws IllegalArgumentException if the input object is null
     */
    public static String getClassFullName(final Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Object must not be null");
        }
        return obj.getClass().getName();
    }

    /**
     * Gets the canonical name of the class of the given object.
     *
     * @param obj the object whose class canonical name is to be retrieved
     * @return the canonical name of the class of the object
     * @throws IllegalArgumentException if the input object is null
     */
    public static String getClassCanonicalName(final Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Object must not be null");
        }
        return obj.getClass().getCanonicalName();
    }

    /**
     * Gets the simple name of the class of the given object.
     *
     * @param obj the object whose class simple name is to be retrieved
     * @return the simple name of the class of the object
     * @throws IllegalArgumentException if the input object is null
     */
    public static String getClassSimpleName(final Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Object must not be null");
        }
        return obj.getClass().getSimpleName();
    }

    /**
     * Gets the package name of the class of the given object.
     *
     * @param obj the object whose class package name is to be retrieved
     * @return the package name of the class of the object, or null if the class has no package
     * @throws IllegalArgumentException if the input object is null
     */
    public static String getPackage(final Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Object must not be null");
        }
        Package pkg = obj.getClass().getPackage();
        return (pkg != null) ? pkg.getName() : null;
    }

    /**
     * Gets the full name (including the package name) of the given class.
     *
     * @param clazz the class whose full name is to be retrieved
     * @return the full name of the class, or an empty string if the class is null
     */
    public static String getClassFullNameByClass(final Class<?> clazz) {
        if (clazz == null) {
            return "";
        }
        return clazz.getName();
    }

    /**
     * Gets the canonical name of the given class.
     *
     * @param clazz the class whose canonical name is to be retrieved
     * @return the canonical name of the class, or null if the class is null
     */
    public static String getClassCanonicalNameByClass(final Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        return clazz.getCanonicalName();
    }

    /**
     * Gets the simple name of the given class.
     *
     * @param clazz the class whose simple name is to be retrieved
     * @return the simple name of the class
     * @throws IllegalArgumentException if the input class is null
     */
    public static String getClassSimpleNameByClass(final Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Class must not be null");
        }
        return clazz.getSimpleName();
    }

    /**
     * Gets the package name of the given class.
     *
     * @param clazz the class whose package name is to be retrieved
     * @return the package name of the class, or null if the class has no package
     * @throws IllegalArgumentException if the input class is null
     */
    public static String getPackageByClass(final Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Class must not be null");
        }

        Package pkg = clazz.getPackage();
        return (pkg != null) ? pkg.getName() : null;
    }

    /**
     * Gets the name of the superclass of the given object's class.
     *
     * @param obj the object whose superclass name is to be retrieved
     * @return the name of the superclass, or null if the class has no superclass
     * @throws IllegalArgumentException if the input object is null
     */
    public static String getSuperClassNameForObject(final Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Object must not be null");
        }

        Class<?> superClass = obj.getClass().getSuperclass();
        if (superClass == null) {
            return null;
        }

        return superClass.getName();
    }

    /**
     * Retrieves the name of the superclass of the given class.
     *
     * <p>
     * This method returns the name of the superclass of the provided class. If the provided class is null,
     * it throws an IllegalArgumentException. If the provided class does not have a superclass (i.e., it is an interface,
     * a primitive type, an array class, or the class is java.lang.Object), the method returns null.
     * </p>
     *
     * @param clazz the class for which the superclass name is to be retrieved
     * @return the name of the superclass of the provided class, or null if the class has no superclass
     * @throws IllegalArgumentException if the provided class is null
     */
    public static String getSuperClassNameByClass(final Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Class must not be null");
        }

        Class<?> superClass = clazz.getSuperclass();
        if (superClass == null) {
            return null;
        }

        return superClass.getName();
    }

    /**
     * Retrieves the enclosing class of the given class if it is an inner class.
     *
     * @param clazz the class whose enclosing class is to be retrieved
     * @return the enclosing class if the given class is an inner class, null otherwise
     * @throws IllegalArgumentException if the provided class is null
     */
    public static Class<?> getEnclosingClass(final Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Class must not be null");
        }

        return clazz.getEnclosingClass();
    }

    /**
     * Retrieves the superclass of the given object's class.
     *
     * @param obj the object whose class's superclass is to be retrieved
     * @return the superclass of the given object's class
     * @throws IllegalArgumentException if the provided object is null
     */
    public static Class<?> getSuperClass(final Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Object must not be null");
        }
        return obj.getClass().getSuperclass();
    }

    /**
     * Retrieves the interfaces implemented by the given class.
     *
     * @param clazz the class whose implemented interfaces are to be retrieved
     * @return a list of classes representing the interfaces implemented by the given class
     * @throws IllegalArgumentException if the provided class is null
     */
    public static List<Class<?>> getInterfaces(final Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Class must not be null");
        }

        return Arrays.asList(clazz.getInterfaces());
    }
}
