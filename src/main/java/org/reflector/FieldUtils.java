package org.reflector;

import org.reflector.exception.FieldAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class FieldUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(FieldUtils.class);

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
     * Checks if a field is annotated with a specific annotation.
     *
     * @param field the field to check
     * @param annotationClass the annotation class to look for
     * @param <T> the type of the annotation
     * @return true if the field is annotated with the specified annotation, false otherwise
     * @throws NullPointerException if the field or annotationClass is null
     * @deprecated identical to {@link #isFieldAnnotated(Field, Class)}. {@code isAnnotationPresent}
     *             is defined by the JDK as {@code getAnnotation(x) != null}, and a field annotation
     *             is never inherited, so there is nothing for "exact" to distinguish. Use
     *             {@link #isFieldAnnotated(Field, Class)}.
     */
    @Deprecated
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

    /**
     * Retrieves all fields of a given class, including fields declared in its superclasses,
     * and returns them as a map with field names as keys.
     *
     * @param clazz the class from which to retrieve fields
     * @return a map of all fields of the specified class with field names as keys
     * @throws NullPointerException if the clazz is null
     */
    public static Map<String, Field> getAllFieldsMap(final Class<?> clazz) {
        List<Field> allFields = getAllFields(clazz);
        Map<String, Field> fieldsMap = new HashMap<>();
        for (Field field : allFields) {
            fieldsMap.put(field.getName(), field);
        }
        return fieldsMap;
    }

    /**
     * Reads the value of a field from an object.
     *
     * <p>The field is looked up on the runtime class of the object and then on each superclass,
     * so a field declared by a parent is found as well. When several classes in the hierarchy
     * declare a field with the same name, the one closest to the runtime class wins.</p>
     *
     * @param object    the object from which to read the field
     * @param fieldName the name of the field to read
     * @return the value of the field in the object
     * @throws NullPointerException if the object or the field name is null
     * @throws FieldAccessException if no such field exists or it cannot be accessed
     */
    public static Object readField(final Object object, final String fieldName) {
        if (object == null) {
            throw new NullPointerException("Object cannot be null");
        }
        if (fieldName == null) {
            throw new NullPointerException("Field name cannot be null");
        }

        for (Class<?> current = object.getClass(); current != null; current = current.getSuperclass()) {
            final Field field;
            try {
                field = current.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                continue;
            }
            try {
                field.setAccessible(true);
                return field.get(object);
            } catch (IllegalAccessException e) {
                LOGGER.error("Error reading field '{}'", fieldName, e);
                throw new FieldAccessException("Requested field is not accessible: " + fieldName, e);
            }
        }

        LOGGER.error("No field '{}' on {}", fieldName, object.getClass().getName());
        throw new FieldAccessException("No field '" + fieldName + "' on " + object.getClass().getName());
    }

    /**
     * Clears the values of unselected fields of the given object.
     *
     * <p>For each field of the object's class, if the field name is not present in the specified
     * collection of selected fields, the field is reset. A reference field is set to
     * {@code null}, a primitive field to its default value - {@code 0}, {@code false} or the
     * null character - since a primitive cannot hold {@code null}.
     *
     * <p>Static and final fields are left alone.
     *
     * @param object the object whose fields are to be cleared
     * @param selectedFields a collection containing the names of the fields to keep
     * @throws NullPointerException if the object is null
     */
    public static void clearUnselectedFields(final Object object, final Collection<String> selectedFields) {
        if (object == null) {
            throw new NullPointerException("Object cannot be null");
        }

        if (selectedFields == null || selectedFields.isEmpty()) {
            return;
        }

        for (Field field : getAllFields(object.getClass())) {
            if (selectedFields.contains(field.getName())) {
                continue;
            }
            final int modifiers = field.getModifiers();
            if (Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers)) {
                continue;
            }
            try {
                field.setAccessible(true);
                field.set(object, defaultValueOf(field.getType()));
            } catch (Exception e) {
                LOGGER.error("Failed to clear field '{}'. Error: {}", field.getName(), e.getMessage());
            }
        }
    }

    /**
     * Returns the value a freshly allocated field of the given type holds.
     *
     * @param type the field type
     * @return {@code null} for a reference type, otherwise the zero value of the primitive
     */
    private static Object defaultValueOf(final Class<?> type) {
        if (!type.isPrimitive()) {
            return null;
        }
        if (type == boolean.class) {
            return Boolean.FALSE;
        }
        if (type == char.class) {
            return Character.valueOf(' ');
        }
        if (type == byte.class) {
            return (byte) 0;
        }
        if (type == short.class) {
            return (short) 0;
        }
        if (type == int.class) {
            return 0;
        }
        if (type == long.class) {
            return 0L;
        }
        if (type == float.class) {
            return 0.0f;
        }
        return 0.0d;
    }
}
