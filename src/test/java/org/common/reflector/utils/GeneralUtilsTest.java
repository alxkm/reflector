package org.common.reflector.utils;

import org.junit.jupiter.api.Test;
import org.reflector.GeneralUtils;

import static org.junit.jupiter.api.Assertions.*;


public class GeneralUtilsTest {

    private static interface SampleInterface {
    }

    private static class SampleClass {
        private class InnerClass {
        }
    }

    private enum SampleEnum {ONE, TWO, THREE}

    @interface SampleAnnotation {
    }

    @Test
    public void testIsInterface() {
        assertTrue(GeneralUtils.isInterface(SampleInterface.class));
        assertFalse(GeneralUtils.isInterface(SampleClass.class));
    }

    @Test
    public void testIsArray() {
        assertTrue(GeneralUtils.isArray(int[].class));
        assertFalse(GeneralUtils.isArray(int.class));
    }

    @Test
    public void testIsEnum() {
        assertTrue(GeneralUtils.isEnum(SampleEnum.class));
        assertFalse(GeneralUtils.isEnum(SampleClass.class));
    }

    @Test
    public void testIsAnnotation() {
        assertTrue(GeneralUtils.isAnnotation(SampleAnnotation.class));
        assertFalse(GeneralUtils.isAnnotation(SampleClass.class));
    }

    @Test
    public void testIsAnonymousClass() {
        SampleClass sampleClass = new SampleClass() {
        };
        assertTrue(GeneralUtils.isAnonymousClass(sampleClass.getClass()));
        assertFalse(GeneralUtils.isAnonymousClass(SampleClass.class));
    }

    @Test
    public void testGetInnerClasses() {
        Class<?>[] innerClasses = GeneralUtils.getInnerClasses(SampleClass.class);
        assertEquals(1, innerClasses.length);
        assertEquals("org.common.reflector.utils.GeneralUtilsTest$SampleClass$InnerClass", innerClasses[0].getName());
    }

    @Test
    public void testIsInterface_nullClass() {
        assertThrows(NullPointerException.class, () -> {
            GeneralUtils.isInterface(null);
        });
    }

    @Test
    public void testIsArray_nullClass() {
        assertThrows(NullPointerException.class, () -> {
            GeneralUtils.isArray(null);
        });
    }

    @Test
    public void testIsEnum_nullClass() {
        assertThrows(NullPointerException.class, () -> {
            GeneralUtils.isEnum(null);
        });
    }

    @Test
    public void testIsAnnotation_nullClass() {
        assertThrows(NullPointerException.class, () -> {
            GeneralUtils.isAnnotation(null);
        });
    }

    @Test
    public void testIsAnonymousClass_nullClass() {
        assertThrows(NullPointerException.class, () -> {
            GeneralUtils.isAnonymousClass(null);
        });
    }

    @Test
    public void testGetInnerClasses_nullClass() {
        assertThrows(NullPointerException.class, () -> {
            GeneralUtils.getInnerClasses(null);
        });
    }
}

