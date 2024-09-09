package org.reflector;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class FieldsExtraUtils {

    private FieldsExtraUtils() {}

    /**
     * Retrieves all private fields of a given class, including fields declared in its superclasses.
     *
     * @param clazz the class from which to retrieve private fields
     * @return a list of all private fields of the specified class
     * @throws NullPointerException if the clazz is null
     */
    public static List<Field> getAllPrivateFields(final Class<?> clazz) {
        return FieldUtils.getAllPrivateFields(clazz);
    }

    /**
     * Retrieves all private fields of a given class, including fields declared in its superclasses,
     * and returns them as a map with field names as keys.
     *
     * @param clazz the class from which to retrieve private fields
     * @return a map of all private fields of the specified class with field names as keys
     * @throws NullPointerException if the clazz is null
     */
    public static Map<String, Field> getAllPrivateFieldsMap(final Class<?> clazz) {
        List<Field> allFields = getAllPrivateFields(clazz);
        return getFieldsMap(allFields);
    }

    /**
     * Helper method to convert a list of fields to a map with field names as keys.
     *
     * @param fields the list of fields to convert to a map
     * @return a map with field names as keys and Field objects as values
     */
    public static Map<String, Field> getFieldsMap(final List<Field> fields) {
        Map<String, Field> map = new HashMap<>();
        for (Field field : fields) {
            map.put(field.getName(), field);
        }
        return map;
    }

    /**
     * Retrieves all fields annotated with a specific annotation from a given class,
     * including fields declared in its superclasses.
     *
     * @param type       the class from which to retrieve annotated fields
     * @param annotation the annotation class to search for
     * @return a list of fields annotated with the specified annotation
     * @throws NullPointerException if type or annotation is null
     */
    public static List<Field> getAllAnnotatedFields(final Class<?> type, final Class<? extends Annotation> annotation) {
        if (type == null || annotation == null) {
            throw new NullPointerException("Class type and annotation cannot be null");
        }

        List<Field> annotatedFields = new ArrayList<>();
        for (Field field : FieldUtils.getAllFields(type)) {
            if (field.isAnnotationPresent(annotation)) {
                field.setAccessible(true);
                annotatedFields.add(field);
            }
        }
        return annotatedFields;
    }
}