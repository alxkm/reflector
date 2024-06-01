package org.reflector;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class FieldUtils {

    private FieldUtils() {

    }

    /**
     * Retrieves the type of a specified field in the given class.
     *
     * @param clazz the class from which the field type is to be retrieved
     * @param fieldName the name of the field whose type is to be retrieved
     * @return the type of the specified field
     * @throws NoSuchFieldException if the specified field does not exist
     * @throws NullPointerException if the clazz or fieldName is null
     */
    public static Class<?> getFieldType(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        if (clazz == null || fieldName == null) {
            throw new NullPointerException("Class and field name cannot be null");
        }
        Field field = clazz.getDeclaredField(fieldName);
        return field.getType();
    }

    /**
     * Retrieves the modifiers of a specified field in the given class.
     *
     * @param clazz the class from which the field modifiers are to be retrieved
     * @param fieldName the name of the field whose modifiers are to be retrieved
     * @return the modifiers of the specified field
     * @throws NoSuchFieldException if the specified field does not exist
     * @throws NullPointerException if the clazz or fieldName is null
     */
    public static int getFieldModifiers(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        if (clazz == null || fieldName == null) {
            throw new NullPointerException("Class and field name cannot be null");
        }
        Field field = clazz.getDeclaredField(fieldName);
        return field.getModifiers();
    }

    /**
     * Checks if a specified field in the given class is final.
     *
     * @param clazz the class from which the field is to be checked
     * @param fieldName the name of the field to be checked
     * @return true if the specified field is final, false otherwise
     * @throws NoSuchFieldException if the specified field does not exist
     * @throws NullPointerException if the clazz or fieldName is null
     */
    public static boolean isFieldFinal(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        if (clazz == null || fieldName == null) {
            throw new NullPointerException("Class and field name cannot be null");
        }
        Field field = clazz.getDeclaredField(fieldName);
        return Modifier.isFinal(field.getModifiers());
    }

    /**
     * Checks if a specified field in the given class is static.
     *
     * @param clazz the class from which the field is to be checked
     * @param fieldName the name of the field to be checked
     * @return true if the specified field is static, false otherwise
     * @throws NoSuchFieldException if the specified field does not exist
     * @throws NullPointerException if the clazz or fieldName is null
     */
    public static boolean isFieldStatic(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        if (clazz == null || fieldName == null) {
            throw new NullPointerException("Class and field name cannot be null");
        }
        Field field = clazz.getDeclaredField(fieldName);
        return Modifier.isStatic(field.getModifiers());
    }

    /**
     * Sets a specified field in the given class to be accessible.
     *
     * @param clazz the class containing the field
     * @param fieldName the name of the field to be set accessible
     * @throws NoSuchFieldException if the specified field does not exist
     * @throws NullPointerException if the clazz or fieldName is null
     */
    public static void setFieldAccessible(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        if (clazz == null || fieldName == null) {
            throw new NullPointerException("Class and field name cannot be null");
        }
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
    }

    /**
     * Checks if a field is annotated with a specific annotation.
     *
     * @param field the field to check
     * @param annotationClass the annotation class to look for
     * @param <T> the type of the annotation
     * @return true if the field is annotated with the specified annotation, false otherwise
     * @throws NullPointerException if the field or annotationClass is null
     */
    public static <T extends Annotation> boolean isFieldAnnotated(final Field field, final Class<T> annotationClass) {
        if (field == null) {
            throw new NullPointerException("Field cannot be null");
        }
        if (annotationClass == null) {
            throw new NullPointerException("Annotation class cannot be null");
        }

        return field.isAnnotationPresent(annotationClass);
    }

    /**
     * Checks if a field is exactly annotated with a specific annotation.
     *
     * @param field the field to check
     * @param annotationClass the annotation class to look for
     * @param <T> the type of the annotation
     * @return true if the field is exactly annotated with the specified annotation, false otherwise
     * @throws NullPointerException if the field or annotationClass is null
     */
    public static <T extends Annotation> boolean isFieldExactAnnotated(final Field field, final Class<T> annotationClass) {
        if (field == null) {
            throw new NullPointerException("Field cannot be null");
        }
        if (annotationClass == null) {
            throw new NullPointerException("Annotation class cannot be null");
        }

        T annotation = field.getAnnotation(annotationClass);
        return annotation != null;
    }

    /**
     * Retrieves all fields of a given class, including fields declared in its superclasses.
     *
     * @param type the class from which to retrieve fields
     * @return a list of all fields of the specified class
     * @throws NullPointerException if the type is null
     */
    public static List<Field> getAllFields(final Class<?> type) {
        if (type == null) {
            throw new NullPointerException("Class type cannot be null");
        }

        List<Field> fields = new ArrayList<>();
        Class<?> currentType = type;

        while (currentType != null) {
            fields.addAll(Arrays.asList(currentType.getDeclaredFields()));
            currentType = currentType.getSuperclass();
        }

        return fields;
    }

    /**
     * Retrieves all private fields of a given class, including fields declared in its superclasses.
     *
     * @param clazz the class from which to retrieve private fields
     * @return a list of all private fields of the specified class
     * @throws NullPointerException if the clazz is null
     */
    public static List<Field> getAllPrivateFields(final Class<?> clazz) {
        if (clazz == null) {
            throw new NullPointerException("Class type cannot be null");
        }

        List<Field> fields = new ArrayList<>();
        Class<?> currentClass = clazz;

        while (currentClass != null) {
            Field[] classFields = currentClass.getDeclaredFields();
            for (Field field : classFields) {
                if (Modifier.isPrivate(field.getModifiers())) {
                    fields.add(field);
                }
            }
            currentClass = currentClass.getSuperclass();
        }

        return fields;
    }
}
