package org.reflector;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class MethodEnhancementsUtils {

    private MethodEnhancementsUtils() {}

    /**
     * Retrieves methods annotated with a specific annotation.
     *
     * @param clazz the class from which to retrieve methods
     * @param annotationClass the Class object corresponding to the annotation type
     * @return a list of methods annotated with the specified annotation
     * @throws NullPointerException if the clazz or annotationClass is null
     */
    public static List<Method> getAnnotatedMethods(final Class<?> clazz, Class<? extends Annotation> annotationClass) {
        if (clazz == null || annotationClass == null) {
            throw new NullPointerException("Class and annotation class cannot be null");
        }

        List<Method> annotatedMethods = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(annotationClass)) {
                annotatedMethods.add(method);
            }
        }
        return annotatedMethods;
    }

    /**
     * Retrieves constructors annotated with a specific annotation.
     *
     * @param clazz the class from which to retrieve constructors
     * @param annotationClass the Class object corresponding to the annotation type
     * @return a list of constructors annotated with the specified annotation
     * @throws NullPointerException if the clazz or annotationClass is null
     */
    public static List<Constructor<?>> getAnnotatedConstructors(final Class<?> clazz, Class<? extends Annotation> annotationClass) {
        if (clazz == null || annotationClass == null) {
            throw new NullPointerException("Class and annotation class cannot be null");
        }

        List<Constructor<?>> annotatedConstructors = new ArrayList<>();
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            if (constructor.isAnnotationPresent(annotationClass)) {
                annotatedConstructors.add(constructor);
            }
        }
        return annotatedConstructors;
    }
}

