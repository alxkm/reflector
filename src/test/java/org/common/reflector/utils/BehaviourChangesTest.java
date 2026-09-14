package org.common.reflector.utils;

import org.junit.jupiter.api.Test;
import org.reflector.FieldUtils;
import org.reflector.InvokeUtils;
import org.reflector.MethodUtils;
import org.reflector.ObjectUtils;
import org.reflector.exception.FieldAccessException;
import org.reflector.exception.InstanceInvocationException;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pins the behaviours that changed, so a later refactor cannot quietly undo them.
 */
class BehaviourChangesTest {

    public static class Account {
        private String id = "acc-1";

        public Account() {
        }
    }

    public static class Person extends Account {
        private String name;
        private int count = 7;
        private boolean active = true;
        private char marker = 'x';
        private static String shared = "shared";
        private final String tag = "tag";

        public Person() {
        }

        public Person(String name, int count) {
            this.name = name;
            this.count = count;
        }
    }

    public static class NoDefaultConstructor {
        private NoDefaultConstructor(int unused) {
        }
    }

    @Test
    void readFieldFindsInheritedFields() {
        assertEquals("acc-1", FieldUtils.readField(new Person(), "id"));
    }

    @Test
    void readFieldReportsAMissingFieldAsFieldAccessException() {
        FieldAccessException thrown = assertThrows(FieldAccessException.class,
                () -> FieldUtils.readField(new Person(), "noSuchField"));

        assertTrue(thrown.getMessage().contains("noSuchField"));
    }

    @Test
    void readFieldRejectsNullArguments() {
        assertThrows(NullPointerException.class, () -> FieldUtils.readField(null, "name"));
        assertThrows(NullPointerException.class, () -> FieldUtils.readField(new Person(), null));
    }

    @Test
    void clearUnselectedFieldsResetsPrimitivesToTheirDefault() {
        Person person = new Person("Ada", 99);
        person.active = true;
        person.marker = 'z';

        FieldUtils.clearUnselectedFields(person, Collections.singletonList("name"));

        assertEquals("Ada", person.name);
        assertEquals(0, person.count);
        assertEquals(false, person.active);
        assertEquals('\0', person.marker);
    }

    @Test
    void clearUnselectedFieldsLeavesStaticAndFinalFieldsAlone() {
        Person person = new Person("Ada", 1);

        FieldUtils.clearUnselectedFields(person, Collections.singletonList("name"));

        assertEquals("shared", Person.shared);
        assertEquals("tag", person.tag);
    }

    @Test
    void clearUnselectedFieldsClearsInheritedReferenceFields() {
        Person person = new Person("Ada", 1);

        FieldUtils.clearUnselectedFields(person, Collections.singletonList("name"));

        assertNull(FieldUtils.readField(person, "id"));
    }

    @Test
    void constructorResolutionAcceptsAWrapperForAPrimitiveParameter() {
        Person person = InvokeUtils.invokeInstance(Person.class, "Grace", 45);

        assertEquals("Grace", person.name);
        assertEquals(45, person.count);
    }

    @Test
    void constructorResolutionStillPrefersAnExactMatch() throws Exception {
        Constructor<Person> ctor = InvokeUtils.getAccessibleConstructor(
                new Class<?>[]{String.class, int.class}, Person.class);

        assertEquals(2, ctor.getParameterCount());
        assertEquals(int.class, ctor.getParameterTypes()[1]);
    }

    @Test
    void constructorResolutionReportsAMissingConstructor() {
        assertThrows(NoSuchMethodException.class, () -> InvokeUtils.getAccessibleConstructor(
                new Class<?>[]{Integer.class, Integer.class, Integer.class}, Person.class));
    }

    @Test
    void argumentTypesToleratesNulls() {
        Class<?>[] types = InvokeUtils.getArrayValuesTypesByArgs(new Object[]{"text", null});

        assertEquals(String.class, types[0]);
        assertNull(types[1]);
        assertEquals(0, InvokeUtils.getArrayValuesTypesByArgs(null).length);
    }

    @Test
    void copyThrowsWhenThereIsNoNoArgumentConstructor() {
        InstanceInvocationException thrown = assertThrows(InstanceInvocationException.class,
                () -> ObjectUtils.copy(new NoDefaultConstructor(1)));

        assertNotNull(thrown.getCause());
    }

    @Test
    void copyStillCopiesFieldByField() {
        Person source = new Person("Ada", 42);

        Person duplicate = (Person) ObjectUtils.copy(source);

        assertEquals("Ada", duplicate.name);
        assertEquals(42, duplicate.count);
    }

    @Test
    void nullArgumentsThrowNullPointerExceptionAcrossTheLibrary() {
        assertThrows(NullPointerException.class, () -> MethodUtils.getReturnType(null));
        assertThrows(NullPointerException.class, () -> MethodUtils.getParameterTypes(null));
        assertThrows(NullPointerException.class, () -> MethodUtils.findMethodByName(null, "x"));
        assertThrows(NullPointerException.class, () -> MethodUtils.getDeclaredMethods(null));
        assertThrows(NullPointerException.class, () -> ObjectUtils.copy(null));
        assertThrows(NullPointerException.class,
                () -> FieldUtils.clearUnselectedFields(null, Arrays.asList("a")));
    }

    @Test
    void isSimpleValueTypeCoversPrimitivesWrappersAndString() throws Exception {
        assertTrue(ObjectUtils.isSimpleValueType(Person.class.getDeclaredField("name")));
        assertTrue(ObjectUtils.isSimpleValueType(Person.class.getDeclaredField("count")));
        assertThrows(NullPointerException.class, () -> ObjectUtils.isSimpleValueType(null));
    }
}
