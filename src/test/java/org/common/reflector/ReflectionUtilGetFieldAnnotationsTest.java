package org.common.reflector;

import org.common.reflector.data.annotation.CustomAnnotationForTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.reflector.ReflectionUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReflectionUtilGetFieldAnnotationsTest {
    private static Field deprecatedField;
    private static Field customAnnotatedField;
    private static Field nonAnnotatedField;

    @BeforeAll
    public static void setUp() throws NoSuchFieldException {
        class Example {
            @Deprecated
            private String deprecatedField;
            @CustomAnnotationForTest
            private int customAnnotatedField;
            private double nonAnnotatedField;
        }

        deprecatedField = Example.class.getDeclaredField("deprecatedField");
        customAnnotatedField = Example.class.getDeclaredField("customAnnotatedField");
        nonAnnotatedField = Example.class.getDeclaredField("nonAnnotatedField");
    }

    @Test
    public void testGetFieldAnnotations_DeprecatedField() {
        Annotation[] annotations = ReflectionUtil.getFieldAnnotations(deprecatedField);
        assertNotNull(annotations);
        assertEquals(1, annotations.length);
        assertTrue(annotations[0] instanceof Deprecated);
    }

    @Test
    public void testGetFieldAnnotations_SuppressedField() {
        Annotation[] annotations = ReflectionUtil.getFieldAnnotations(customAnnotatedField);
        assertNotNull(annotations);
        assertEquals(1, annotations.length);
        assertTrue(annotations[0] instanceof CustomAnnotationForTest);
    }

    @Test
    public void testGetFieldAnnotations_NonAnnotatedField() {
        Annotation[] annotations = ReflectionUtil.getFieldAnnotations(nonAnnotatedField);
        assertNotNull(annotations);
        assertEquals(0, annotations.length);
    }

    @Test
    public void testGetFieldAnnotations_NullField() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ReflectionUtil.getFieldAnnotations(null);
        });

        String expectedMessage = "Field must not be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}