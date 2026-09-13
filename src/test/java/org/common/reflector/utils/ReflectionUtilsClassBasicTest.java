package org.common.reflector.utils;

import org.common.reflector.data.Person;
import org.junit.jupiter.api.Test;
import org.reflector.ReflectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The facade has to expose the same class metadata helpers as {@code ClassBasicUtils}.
 */
class ReflectionUtilsClassBasicTest {

    private static final String PACKAGE = "org.common.reflector.data";

    private final Person person = new Person();

    @Test
    void readsNamesFromAnObject() {
        assertAll(
                () -> assertEquals(PACKAGE + ".Person", ReflectionUtils.getClassFullName(person)),
                () -> assertEquals(PACKAGE + ".Person", ReflectionUtils.getClassCanonicalName(person)),
                () -> assertEquals("Person", ReflectionUtils.getClassSimpleName(person)),
                () -> assertEquals(PACKAGE, ReflectionUtils.getPackage(person))
        );
    }

    @Test
    void readsNamesFromAClass() {
        assertAll(
                () -> assertEquals(PACKAGE + ".Person", ReflectionUtils.getClassFullNameByClass(Person.class)),
                () -> assertEquals(PACKAGE + ".Person", ReflectionUtils.getClassCanonicalNameByClass(Person.class)),
                () -> assertEquals("Person", ReflectionUtils.getClassSimpleNameByClass(Person.class)),
                () -> assertEquals(PACKAGE, ReflectionUtils.getPackageByClass(Person.class))
        );
    }

    @Test
    void readsTheSuperClass() {
        assertAll(
                () -> assertEquals(Object.class, ReflectionUtils.getSuperClass(person)),
                () -> assertEquals("java.lang.Object", ReflectionUtils.getSuperClassNameForObject(person)),
                () -> assertEquals("java.lang.Object", ReflectionUtils.getSuperClassNameByClass(Person.class)),
                () -> assertNull(ReflectionUtils.getSuperClassNameByClass(Object.class))
        );
    }

    @Test
    void readsEnclosingClassAndInterfaces() {
        List<Class<?>> interfaces = ReflectionUtils.getInterfaces(ArrayList.class);

        assertAll(
                () -> assertEquals(Map.class, ReflectionUtils.getEnclosingClass(Map.Entry.class)),
                () -> assertNull(ReflectionUtils.getEnclosingClass(Person.class)),
                () -> assertTrue(interfaces.contains(List.class))
        );
    }

    @Test
    void rejectsNullArguments() {
        assertAll(
                () -> assertThrows(NullPointerException.class, () -> ReflectionUtils.getClassFullName(null)),
                () -> assertThrows(NullPointerException.class, () -> ReflectionUtils.getClassSimpleName(null)),
                () -> assertThrows(NullPointerException.class, () -> ReflectionUtils.getPackage(null)),
                () -> assertThrows(NullPointerException.class, () -> ReflectionUtils.getSuperClass(null)),
                () -> assertThrows(NullPointerException.class, () -> ReflectionUtils.getInterfaces(null)),
                () -> assertThrows(NullPointerException.class, () -> ReflectionUtils.getEnclosingClass(null))
        );
    }

    @Test
    void returnsEmptyStringForNullClassFullName() {
        assertEquals("", ReflectionUtils.getClassFullNameByClass(null));
        assertNull(ReflectionUtils.getClassCanonicalNameByClass(null));
    }
}
