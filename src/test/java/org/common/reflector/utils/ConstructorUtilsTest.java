package org.common.reflector.utils;

import org.junit.jupiter.api.Test;
import org.reflector.ConstructorUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import static org.junit.jupiter.api.Assertions.*;

public class ConstructorUtilsTest {

    private static class SampleClass {
        public SampleClass(String param1, int param2) {}
    }

    @Test
    public void testGetConstructorParameters() throws NoSuchMethodException {
        Constructor<SampleClass> constructor = SampleClass.class.getConstructor(String.class, int.class);
        Parameter[] parameters = ConstructorUtils.getConstructorParameters(constructor);
        assertEquals(2, parameters.length);
        assertEquals("arg0", parameters[0].getName());
        assertEquals(int.class, parameters[1].getType());
    }

    @Test
    public void testGetConstructorParameters_nullConstructor() {
        assertThrows(NullPointerException.class, () -> {
            ConstructorUtils.getConstructorParameters(null);
        });
    }

    @Test
    public void testGetConstructorModifiers() throws NoSuchMethodException {
        Constructor<SampleClass> constructor = SampleClass.class.getConstructor(String.class, int.class);
        int modifiers = ConstructorUtils.getConstructorModifiers(constructor);
        assertTrue(Modifier.isPublic(modifiers));
    }

    @Test
    public void testGetConstructorModifiers_nullConstructor() {
        assertThrows(NullPointerException.class, () -> {
            ConstructorUtils.getConstructorModifiers(null);
        });
    }
}
