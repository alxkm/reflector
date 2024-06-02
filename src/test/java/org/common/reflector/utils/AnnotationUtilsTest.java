package org.common.reflector.utils;

import org.common.reflector.data.annotation.ClassAnnotation;
import org.common.reflector.data.annotation.ClassAnnotation1;
import org.common.reflector.data.annotation.CustomAnnotationForTest;
import org.common.reflector.data.annotation.CustomMethodAnnotation;
import org.common.reflector.data.annotation.ParameterAnnotation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.reflector.AnnotationUtils;
import org.reflector.ReflectionUtils;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AnnotationUtilsTest {

    @Retention(RetentionPolicy.RUNTIME)
    private @interface TestAnnotation1 {}

    @Retention(RetentionPolicy.RUNTIME)
    private @interface TestAnnotation2 {}

    @TestAnnotation1
    @TestAnnotation2
    private static class SampleClass {
        @TestAnnotation1
        @TestAnnotation2
        private static void sampleMethod() {}

        @TestAnnotation
        public void annotatedMethod() {}

        public void nonAnnotatedMethod() {}
    }

    @Test
    public void testGetAnnotationsByType_class() {
        TestAnnotation1[] annotations = AnnotationUtils.getAnnotationsByType(SampleClass.class, TestAnnotation1.class);
        assertEquals(1, annotations.length);
    }

    @Test
    public void testGetAnnotationsByType_method() throws NoSuchMethodException {
        TestAnnotation1[] annotations = AnnotationUtils.getAnnotationsByType(SampleClass.class.getDeclaredMethod("sampleMethod"), TestAnnotation1.class);
        assertEquals(1, annotations.length);
    }

    @Test
    public void testGetAnnotationsByType_noAnnotations() {
        TestAnnotation1[] annotations = AnnotationUtils.getAnnotationsByType(String.class, TestAnnotation1.class);
        assertEquals(0, annotations.length);
    }

    @Test
    public void testGetAnnotationsByType_nullElement() {
        assertThrows(NullPointerException.class, () -> {
            AnnotationUtils.getAnnotationsByType(null, TestAnnotation1.class);
        });
    }

    @Test
    public void testGetAnnotationsByType_nullAnnotationClass() {
        assertThrows(NullPointerException.class, () -> {
            AnnotationUtils.getAnnotationsByType(SampleClass.class, null);
        });
    }

    @Test
    public void testGetDeclaredAnnotations_class() {
        Annotation[] annotations = AnnotationUtils.getDeclaredAnnotations(SampleClass.class);
        assertEquals(2, annotations.length);
    }

    @Test
    public void testGetDeclaredAnnotations_method() throws NoSuchMethodException {
        Annotation[] annotations = AnnotationUtils.getDeclaredAnnotations(SampleClass.class.getDeclaredMethod("sampleMethod"));
        assertEquals(2, annotations.length);
    }

    @Test
    public void testGetDeclaredAnnotations_noAnnotations() {
        Annotation[] annotations = AnnotationUtils.getDeclaredAnnotations(String.class);
        assertEquals(0, annotations.length);
    }

    @Test
    public void testGetDeclaredAnnotations_nullElement() {
        assertThrows(NullPointerException.class, () -> {
            AnnotationUtils.getDeclaredAnnotations(null);
        });
    }

    @Test
    public void testGetMethodDeclaredAnnotations_withAnnotation() throws NoSuchMethodException {
        Method method = SampleClass.class.getDeclaredMethod("annotatedMethod");
        Annotation[] annotations = AnnotationUtils.getMethodDeclaredAnnotations(method);
        assertEquals(1, annotations.length);
        assertTrue(annotations[0] instanceof TestAnnotation);
    }

    @Test
    public void testGetMethodDeclaredAnnotations_withoutAnnotation() throws NoSuchMethodException {
        Method method = SampleClass.class.getDeclaredMethod("nonAnnotatedMethod");
        Annotation[] annotations = AnnotationUtils.getMethodDeclaredAnnotations(method);
        assertEquals(0, annotations.length);
    }

    @Test
    public void testGetMethodDeclaredAnnotations_nullMethod() {
        assertThrows(NullPointerException.class, () -> {
            AnnotationUtils.getMethodDeclaredAnnotations(null);
        });
    }

    //getClassAnnotations tests

    @ClassAnnotation
    @ClassAnnotation1
    class SimpleAnnotatedClass {
    }

    class SimpleNonAnnotatedClass {
    }

    @Test
    public void testGetClassAnnotations_AnnotatedClass() {
        Annotation[] annotations = AnnotationUtils.getClassAnnotations(SimpleAnnotatedClass.class);
        assertNotNull(annotations);
        assertEquals(2, annotations.length);
        assertTrue(annotations[0] instanceof ClassAnnotation || annotations[0] instanceof ClassAnnotation1);
        assertTrue(annotations[1] instanceof ClassAnnotation || annotations[1] instanceof ClassAnnotation1);
    }

    @Test
    public void testGetClassAnnotations_NonAnnotatedClass() {
        Annotation[] annotations = AnnotationUtils.getClassAnnotations(SimpleNonAnnotatedClass.class);
        assertNotNull(annotations);
        assertEquals(0, annotations.length);
    }

    @Test
    public void testGetClassAnnotations_NullClass() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            AnnotationUtils.getClassAnnotations(null);
        });
        String expectedMessage = "Class must not be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @ClassAnnotation
    class AnnotatedClass {
    }

    class NonAnnotatedClass {
    }

    @Test
    public void testIsAnnotationPresent_AnnotatedClass() {
        boolean result = AnnotationUtils.isAnnotationOnClassPresent(AnnotatedClass.class, ClassAnnotation.class);
        assertTrue(result);
    }

    @Test
    public void testIsAnnotationPresent_NonAnnotatedClass() {
        boolean result = AnnotationUtils.isAnnotationOnClassPresent(NonAnnotatedClass.class, ClassAnnotation.class);
        assertFalse(result);
    }

    @Test
    public void testIsAnnotationPresent_NullClass() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            AnnotationUtils.isAnnotationOnClassPresent(null, ClassAnnotation.class);
        });
        String expectedMessage = "Class must not be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testIsAnnotationPresent_NullAnnotationClass() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            AnnotationUtils.isAnnotationOnClassPresent(AnnotatedClass.class, null);
        });
        String expectedMessage = "Annotation class must not be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }


    // is method parameter annotated


    class TestClassWithAnnotaedAndNotMethod {
        public void annotatedMethod(@ParameterAnnotation String param) {
        }

        public void nonAnnotatedMethod(String param) {
        }
    }

    @Test
    public void testIsMethodParameterAnnotated_AnnotatedParameter() throws NoSuchMethodException {
        Method method = TestClassWithAnnotaedAndNotMethod.class.getMethod("annotatedMethod", String.class);
        boolean result = AnnotationUtils.isMethodParameterAnnotated(method, ParameterAnnotation.class);
        assertTrue(result);
    }

    @Test
    public void testIsMethodParameterAnnotated_NonAnnotatedParameter() throws NoSuchMethodException {
        Method method = TestClassWithAnnotaedAndNotMethod.class.getMethod("nonAnnotatedMethod", String.class);
        boolean result = AnnotationUtils.isMethodParameterAnnotated(method, ParameterAnnotation.class);
        assertFalse(result);
    }

    @Test
    public void testIsMethodParameterAnnotated_NullMethod() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            AnnotationUtils.isMethodParameterAnnotated(null, ParameterAnnotation.class);
        });
        String expectedMessage = "Method must not be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testIsMethodParameterAnnotated_NullAnnotationClass() throws NoSuchMethodException {
        Method method = TestClassWithAnnotaedAndNotMethod.class.getMethod("annotatedMethod", String.class);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            AnnotationUtils.isMethodParameterAnnotated(method, null);
        });
        String expectedMessage = "Annotation class must not be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    // get field annotations

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
        Annotation[] annotations = AnnotationUtils.getFieldAnnotations(deprecatedField);
        assertNotNull(annotations);
        assertEquals(1, annotations.length);
        assertTrue(annotations[0] instanceof Deprecated);
    }

    @Test
    public void testGetFieldAnnotations_SuppressedField() {
        Annotation[] annotations = AnnotationUtils.getFieldAnnotations(customAnnotatedField);
        assertNotNull(annotations);
        assertEquals(1, annotations.length);
        assertTrue(annotations[0] instanceof CustomAnnotationForTest);
    }

    @Test
    public void testGetFieldAnnotations_NonAnnotatedField() {
        Annotation[] annotations = AnnotationUtils.getFieldAnnotations(nonAnnotatedField);
        assertNotNull(annotations);
        assertEquals(0, annotations.length);
    }

    @Test
    public void testGetFieldAnnotations_NullField() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            AnnotationUtils.getFieldAnnotations(null);
        });

        String expectedMessage = "Field must not be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    // is method annotated

    class TestClass {
        @CustomMethodAnnotation
        public void annotatedMethod() {
        }

        public void nonAnnotatedMethod() {
        }
    }

    @Test
    public void testIsMethodAnnotated_AnnotatedMethod() throws NoSuchMethodException {
        Method method = TestClass.class.getMethod("annotatedMethod");
        boolean result = AnnotationUtils.isMethodAnnotated(method, CustomMethodAnnotation.class);
        assertTrue(result);
    }

    @Test
    public void testIsMethodAnnotated_NonAnnotatedMethod() throws NoSuchMethodException {
        Method method = TestClass.class.getMethod("nonAnnotatedMethod");
        boolean result = AnnotationUtils.isMethodAnnotated(method, CustomMethodAnnotation.class);
        assertFalse(result);
    }

    @Test
    public void testIsMethodAnnotated_NullMethod() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            AnnotationUtils.isMethodAnnotated(null, CustomMethodAnnotation.class);
        });
        String expectedMessage = "Method must not be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testIsMethodAnnotated_NullAnnotationClass() throws NoSuchMethodException {
        Method method = TestClass.class.getMethod("annotatedMethod");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            AnnotationUtils.isMethodAnnotated(method, null);
        });
        String expectedMessage = "Annotation class must not be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }


}

