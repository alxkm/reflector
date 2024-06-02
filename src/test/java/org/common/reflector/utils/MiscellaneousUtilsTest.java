package org.common.reflector.utils;

import org.junit.jupiter.api.Test;
import org.reflector.MiscellaneousUtils;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;

public class MiscellaneousUtilsTest {

    private static class SampleClass {
        public SampleClass() {}
    }

    @Test
    public void testNewInstance() throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        SampleClass instance = MiscellaneousUtils.newInstance(SampleClass.class);
        assertNotNull(instance);
    }

    @Test
    public void testNewInstance_nullClass() {
        assertThrows(NullPointerException.class, () -> {
            MiscellaneousUtils.newInstance(null);
        });
    }

    @Test
    public void testGetArrayComponentType() {
        Class<?> arrayClass = int[].class;
        Class<?> componentType = MiscellaneousUtils.getArrayComponentType(arrayClass);
        assertEquals(int.class, componentType);
    }

    @Test
    public void testGetArrayComponentType_nullArrayClass() {
        assertThrows(NullPointerException.class, () -> {
            MiscellaneousUtils.getArrayComponentType(null);
        });
    }
}
