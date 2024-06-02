package org.common.reflector.utils;

import org.junit.jupiter.api.Test;
import org.reflector.SecurityUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import static org.junit.jupiter.api.Assertions.*;

public class SecurityUtilsTest {

    private static class SampleClass {
        private SampleClass() {}
        private void privateMethod() {}
    }

    @Test
    public void testSetMethodAccessible() throws NoSuchMethodException {
        Method method = SampleClass.class.getDeclaredMethod("privateMethod");
        assertFalse(method.isAccessible());
        SecurityUtils.setMethodAccessible(method);
        assertTrue(method.isAccessible());
    }

    @Test
    public void testSetMethodAccessible_nullMethod() {
        assertThrows(NullPointerException.class, () -> {
            SecurityUtils.setMethodAccessible(null);
        });
    }

    @Test
    public void testSetConstructorAccessible() throws NoSuchMethodException {
        Constructor<SampleClass> constructor = SampleClass.class.getDeclaredConstructor();
        assertFalse(constructor.isAccessible());
        SecurityUtils.setConstructorAccessible(constructor);
        assertTrue(constructor.isAccessible());
    }

    @Test
    public void testSetConstructorAccessible_nullConstructor() {
        assertThrows(NullPointerException.class, () -> {
            SecurityUtils.setConstructorAccessible(null);
        });
    }
}

