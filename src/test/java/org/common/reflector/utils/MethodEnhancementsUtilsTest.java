package org.common.reflector.utils;

import org.junit.jupiter.api.Test;
import org.reflector.MethodEnhancementsUtils;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

public class MethodEnhancementsUtilsTest {

    @Retention(RetentionPolicy.RUNTIME)
    private @interface TestAnnotation {}

    private static class SampleClass {
        @TestAnnotation
        public SampleClass() {}

        @TestAnnotation
        public void annotatedMethod() {}

        public void nonAnnotatedMethod() {}
    }

    @Test
    public void testGetAnnotatedMethods() {
        List<Method> methods = MethodEnhancementsUtils.getAnnotatedMethods(SampleClass.class, TestAnnotation.class);
        assertEquals(1, methods.size());
        assertEquals("annotatedMethod", methods.get(0).getName());
    }

    @Test
    public void testGetAnnotatedMethods_noAnnotations() {
        List<Method> methods = MethodEnhancementsUtils.getAnnotatedMethods(String.class, TestAnnotation.class);
        assertEquals(0, methods.size());
    }

    @Test
    public void testGetAnnotatedMethods_nullClass() {
        assertThrows(NullPointerException.class, () -> {
            MethodEnhancementsUtils.getAnnotatedMethods(null, TestAnnotation.class);
        });
    }

    @Test
    public void testGetAnnotatedMethods_nullAnnotationClass() {
        assertThrows(NullPointerException.class, () -> {
            MethodEnhancementsUtils.getAnnotatedMethods(SampleClass.class, null);
        });
    }

    @Test
    public void testGetAnnotatedConstructors() {
        List<Constructor<?>> constructors = MethodEnhancementsUtils.getAnnotatedConstructors(SampleClass.class, TestAnnotation.class);
        assertEquals(1, constructors.size());
    }

    @Test
    public void testGetAnnotatedConstructors_noAnnotations() {
        List<Constructor<?>> constructors = MethodEnhancementsUtils.getAnnotatedConstructors(String.class, TestAnnotation.class);
        assertEquals(0, constructors.size());
    }

    @Test
    public void testGetAnnotatedConstructors_nullClass() {
        assertThrows(NullPointerException.class, () -> {
            MethodEnhancementsUtils.getAnnotatedConstructors(null, TestAnnotation.class);
        });
    }

    @Test
    public void testGetAnnotatedConstructors_nullAnnotationClass() {
        assertThrows(NullPointerException.class, () -> {
            MethodEnhancementsUtils.getAnnotatedConstructors(SampleClass.class, null);
        });
    }
}

