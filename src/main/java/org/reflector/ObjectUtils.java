package org.reflector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ObjectUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectUtils.class);

    public static boolean isFieldPrimitiveType(final Field field) {
        return field.getType().isPrimitive() || field.getType() == String.class || field.getType() == Integer.class || field.getType() == Long.class || field.getType() == Boolean.class || field.getType() == Byte.class || field.getType() == Character.class || field.getType() == Short.class || field.getType() == Float.class || field.getType() == Double.class;
    }

    /**
     * Creates a deep copy of the given object.
     *
     * @param object the object to be copied
     * @return the deep copy of the object
     * @throws IllegalStateException if copying fails
     */
    public static Object copy(final Object object) {
        Object copyObj = null;
        try {
            try {
                copyObj = object.getClass().newInstance();
            } catch (Exception ex) {
                LOGGER.error("Error copy for object{{}}", object);
                return null;
            }
            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.get(object) == null || Modifier.isFinal(field.getModifiers())) {
                    continue;
                }
                if (isFieldPrimitiveType(field)) {
                    field.set(copyObj, field.get(object));
                } else {
                    Object childObj = field.get(object);
                    field.set(copyObj, (childObj == object) ? copyObj : copy(field.get(object)));
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error during copy object", e);
            throw new IllegalStateException("Failed to get declared methods for Class [" + object.getClass().getName() + "] from ClassLoader [" + object.getClass().getClassLoader() + "]", e);
        }
        return copyObj;
    }
}
