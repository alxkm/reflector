package org.reflector;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class AnnotationUtils {

    private AnnotationUtils() {}

    /**
     * Retrieves all annotations present on the given class.
     *
     * @param clazz the class whose annotations are to be retrieved
     * @return an array of annotations present on the given class
     * @throws NullPointerException if the provided class is null
     */
    public static Annotation[] getClassAnnotations(final Class<?> clazz) {
        if (clazz == null) {
            throw new NullPointerException("Class must not be null");
        }

        return clazz.getAnnotations();
    }

    /**
     * Retrieves annotations by type from a class or element.
     *
     * @param <T>             the type of the annotation to query for and return if present
     * @param element         the element from which to get the annotations
     * @param annotationClass the Class object corresponding to the annotation type
     * @return an array of all annotations of the specified annotation type if present on this element, else an empty array
     * @throws NullPointerException if the element or annotationClass is null
     */
    public static <T extends Annotation> T[] getAnnotationsByType(AnnotatedElement element, Class<T> annotationClass) {
        if (element == null || annotationClass == null) {
            throw new NullPointerException("Element and annotation class cannot be null");
        }
        return element.getAnnotationsByType(annotationClass);
    }

    /**
     * Gets annotations declared directly on a class, method, or field.
     *
     * @param element the element from which to get the annotations
     * @return an array of annotations directly declared on the element
     * @throws NullPointerException if the element is null
     */
    public static Annotation[] getDeclaredAnnotations(AnnotatedElement element) {
        if (element == null) {
            throw new NullPointerException("Element cannot be null");
        }
        return element.getDeclaredAnnotations();
    }

    /**
     * Retrieves the annotations declared on a method.
     *
     * @param method the method to retrieve annotations from
     * @return an array of annotations declared on the method
     * @throws NullPointerException if the method is null
     */
    public static Annotation[] getMethodDeclaredAnnotations(final Method method) {
        if (method == null) {
            throw new NullPointerException("Method must not be null");
        }
        return method.getDeclaredAnnotations();
    }

    /**
     * Retrieves a map of methods to their declared annotations for the given array of methods.
     *
     * @param methods the array of methods whose declared annotations are to be retrieved
     * @return a map where the keys are the methods and the values are arrays of their declared annotations
     * @throws NullPointerException if the methods array is null
     */
    public static Map<Method, Annotation[]> getMethodsDeclaredAnnotations(final Method[] methods) {
        if (methods == null) {
            throw new NullPointerException("Methods array must not be null");
        }
        return Stream.of(methods).filter(Objects::nonNull).collect(Collectors.toMap(method -> method, Method::getDeclaredAnnotations));
    }

    /**
     * Checks if a specific annotation is present on the given class.
     *
     * @param clazz           the class to check for the presence of the annotation
     * @param annotationClass the annotation class to look for
     * @param <T>             the type of the annotation
     * @return true if the specified annotation is present on the class, false otherwise
     * @throws IllegalArgumentException if the provided class or annotation class is null
     */
    public static <T extends Annotation> boolean isAnnotationOnClassPresent(final Class<?> clazz, final Class<T> annotationClass) {
        if (clazz == null) {
            throw new IllegalArgumentException("Class must not be null");
        }
        if (annotationClass == null) {
            throw new IllegalArgumentException("Annotation class must not be null");
        }

        return clazz.isAnnotationPresent(annotationClass);
    }

    /**
     * Checks if any parameter of the given method is annotated with the specified annotation class.
     *
     * @param method the method whose parameters are to be checked
     * @param clazz  the annotation class to look for on the method parameters
     * @param <T>    the type of the annotation
     * @return true if any parameter of the method is annotated with the specified annotation, false otherwise
     * @throws NullPointerException if the provided method or annotation class is null
     */
    public static <T extends Annotation> boolean isMethodParameterAnnotated(final Method method, final Class<T> clazz) {
        if (method == null) {
            throw new NullPointerException("Method must not be null");
        }
        if (clazz == null) {
            throw new NullPointerException("Annotation class must not be null");
        }

        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (Annotation[] annotations : parameterAnnotations) {
            for (Annotation annotation : annotations) {
                if (clazz.isInstance(annotation)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Gets all annotations present on a given field.
     *
     * @param field the field whose annotations are to be retrieved
     * @return an array of annotations present on the field
     * @throws NullPointerException if the provided method or annotation class is null
     */
    public static Annotation[] getFieldAnnotations(final Field field) {
        if (field == null) {
            throw new NullPointerException("Field must not be null");
        }
        return field.getAnnotations();
    }


    /**
     * Checks if the given method is annotated with the specified annotation class.
     *
     * @param method the method to check for the annotation
     * @param clazz  the annotation class to look for on the method
     * @param <T>    the type of the annotation
     * @return true if the method is annotated with the specified annotation, false otherwise
     * @throws NullPointerException if the provided method or annotation class is null
     */
    public static <T extends Annotation> boolean isMethodAnnotated(final Method method, final Class<T> clazz) {
        if (method == null) {
            throw new NullPointerException("Method must not be null");
        }
        if (clazz == null) {
            throw new NullPointerException("Annotation class must not be null");
        }

        return method.getAnnotation(clazz) != null;
    }
}

