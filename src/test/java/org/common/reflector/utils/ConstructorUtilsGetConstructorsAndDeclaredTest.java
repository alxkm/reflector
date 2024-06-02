package org.common.reflector.utils;

import org.junit.jupiter.api.Test;
import org.reflector.ConstructorUtils;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConstructorUtilsGetConstructorsAndDeclaredTest {
    private static class TestClass {
        public TestClass() {}

        public TestClass(int x) {}

        private TestClass(String s) {}
    }

    @Test
    public void testGetConstructors_PublicConstructors() {
        Constructor<?>[] constructors = ConstructorUtils.getConstructors(TestClass.class);

        assertEquals(2, constructors.length);

        assertTrue(constructors[0].getParameterCount() == 0 || constructors[0].getParameterCount() == 1);
        assertTrue(constructors[1].getParameterCount() == 0 || constructors[1].getParameterCount() == 1);
    }

    @Test
    public void testGetConstructors_NoPublicConstructors() {
        class PrivateConstructorClass {
            private PrivateConstructorClass() {}
        }

        Constructor<?>[] constructors = ConstructorUtils.getConstructors(PrivateConstructorClass.class);

        assertEquals(0, constructors.length);
    }

    @Test
    public void testGetConstructors_ObjectClass() {
        Constructor<?>[] constructors = ConstructorUtils.getConstructors(Object.class);

        assertEquals(1, constructors.length);
        assertEquals(0, constructors[0].getParameterCount());
    }

    @Test
    public void testGetConstructors_NullClass() {
        assertThrows(NullPointerException.class, () -> {
            ConstructorUtils.getConstructors(null);
        });
    }

    @Test
    public void testGetDeclaredConstructors() {
        Constructor<?>[] constructors = ConstructorUtils.getDeclaredConstructors(TestClass.class);

        assertEquals(3, constructors.length);

        assertTrue(constructors[0].getParameterCount() == 0 || constructors[0].getParameterCount() == 1 || constructors[0].getParameterCount() == 2);
        assertTrue(constructors[1].getParameterCount() == 0 || constructors[1].getParameterCount() == 1 || constructors[1].getParameterCount() == 2);
        assertTrue(constructors[2].getParameterCount() == 0 || constructors[2].getParameterCount() == 1 || constructors[2].getParameterCount() == 2);
    }

    @Test
    public void testGetDeclaredConstructors_NoDeclaredConstructors() {
        class NoConstructorClass {}

        Constructor<?>[] constructors = ConstructorUtils.getDeclaredConstructors(NoConstructorClass.class);

        assertEquals(1, constructors.length); // Implicit default constructor
    }

    @Test
    public void testGetDeclaredConstructors_ObjectClass() {
        Constructor<?>[] constructors = ConstructorUtils.getDeclaredConstructors(Object.class);

        assertEquals(1, constructors.length);
        assertEquals(0, constructors[0].getParameterCount());
    }

    @Test
    public void testGetDeclaredConstructors_NullClass() {
        assertThrows(NullPointerException.class, () -> {
            ConstructorUtils.getDeclaredConstructors(null);
        });
    }
}
