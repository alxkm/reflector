package org.reflector;

import org.reflector.exception.InstanceInvocationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public final class ObjectUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectUtils.class);

    private ObjectUtils() {
    }

    /**
     * Checks whether a field holds a simple value that can be copied by reference.
     *
     * <p>True for primitives, their wrapper classes and {@code String}. The name of
     * {@link #isFieldPrimitiveType(Field)} suggests a narrower check than it performs, which is
     * why this one exists.</p>
     *
     * @param field the field to check
     * @return true if the field type is a primitive, a wrapper or {@code String}
     * @throws NullPointerException if the field is null
     */
    public static boolean isSimpleValueType(final Field field) {
        if (field == null) {
            throw new NullPointerException("Field cannot be null");
        }
        return isFieldPrimitiveType(field);
    }

    /**
     * Checks if the type of a field is a primitive type or a wrapper class.
     *
     * @param field the field to check
     * @return true if the type of the field is a primitive type or a wrapper class, false otherwise
     * @deprecated the name says primitive but the check also answers true for the wrapper classes
     *             and for {@code String}. Use {@link #isSimpleValueType(Field)}, which says what it
     *             does, or {@code field.getType().isPrimitive()} for a real primitive check.
     */
    @Deprecated
    public static boolean isFieldPrimitiveType(final Field field) {
        return field.getType().isPrimitive() ||
                field.getType() == String.class ||
                field.getType() == Integer.class ||
                field.getType() == Long.class ||
                field.getType() == Boolean.class ||
                field.getType() == Byte.class ||
                field.getType() == Character.class ||
                field.getType() == Short.class ||
                field.getType() == Float.class ||
                field.getType() == Double.class;
    }

    /**
     * Creates a deep copy of the given object.
     *
     * <p>The class needs a no-argument constructor of any visibility. Primitives, their wrappers
     * and {@code String} are copied by value, other fields are copied recursively, and
     * {@code final} fields are skipped.</p>
     *
     * @param object the object to be copied
     * @return the deep copy of the object
     * @throws InstanceInvocationException if the class has no usable no-argument constructor
     * @throws IllegalStateException if copying a field fails
     */
    public static Object copy(final Object object) {
        if (object == null) {
            throw new NullPointerException("Object cannot be null");
        }

        final Object copyObj = newInstanceOf(object.getClass());
        try {
            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.get(object) == null || Modifier.isFinal(field.getModifiers())) {
                    continue;
                }
                if (isSimpleValueType(field)) {
                    field.set(copyObj, field.get(object));
                } else {
                    final Object childObj = field.get(object);
                    field.set(copyObj, (childObj == object) ? copyObj : copy(childObj));
                }
            }
        } catch (IllegalAccessException | RuntimeException e) {
            LOGGER.error("Error during copy object", e);
            throw new IllegalStateException(
                    "Failed to copy fields of [" + object.getClass().getName() + "]", e);
        }
        return copyObj;
    }

    /**
     * Creates an instance through the no-argument constructor, whatever its visibility.
     *
     * @param clazz the class to instantiate
     * @return a new instance
     * @throws InstanceInvocationException if there is no usable no-argument constructor
     */
    private static Object newInstanceOf(final Class<?> clazz) {
        try {
            final Constructor<?> ctor = clazz.getDeclaredConstructor();
            ctor.setAccessible(true);
            return ctor.newInstance();
        } catch (ReflectiveOperationException | RuntimeException e) {
            LOGGER.error("Cannot copy {}: no usable no-argument constructor", clazz.getName(), e);
            throw new InstanceInvocationException(
                    "Cannot copy " + clazz.getName() + ": no usable no-argument constructor", e);
        }
    }
}
