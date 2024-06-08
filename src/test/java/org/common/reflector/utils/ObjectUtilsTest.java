package org.common.reflector.utils;

import org.common.reflector.data.SimpleEntryClass;
import org.junit.jupiter.api.Test;
import org.reflector.ObjectUtils;
import org.reflector.ReflectionUtilsLegacy;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObjectUtilsTest {

    @Test
    public void testCopy_NullObject() {
        assertEquals(ReflectionUtilsLegacy.copy(null), null);
    }

    @Test
    public void copyObjectTest() {
        SimpleEntryClass simpleEntryClass = new SimpleEntryClass("K", "V");
        SimpleEntryClass simpleEntryClassCopy = (SimpleEntryClass) ObjectUtils.copy(simpleEntryClass);
        assertEquals(simpleEntryClass, simpleEntryClassCopy);
    }
}
