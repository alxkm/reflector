package org.common.reflector.utils;

import org.common.reflector.data.CustomTestClassForType;
import org.common.reflector.data.SimpleAnnotatedEntry;
import org.common.reflector.data.annotation.CustomAnnotationForTest;
import org.common.reflector.util.TestConstant;
import org.junit.jupiter.api.Test;
import org.reflector.FieldUtils;
import org.reflector.FieldsExtraUtils;
import org.reflector.exception.FieldAccessException;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class FieldUtilsAdditionalTest {

    private static class SuperClass {
        private int superClassPrivateField;
        public String superClassPublicField;
    }

    private static class SubClass extends SuperClass {
        private double subClassPrivateField;
        protected boolean subClassProtectedField;
    }

    @Test
    public void testGetAllFields() {
        List<Field> fields = FieldUtils.getAllFields(SubClass.class);

        assertEquals(4, fields.size());

        assertTrue(fields.stream().anyMatch(field -> field.getName().equals("superClassPrivateField")));
        assertTrue(fields.stream().anyMatch(field -> field.getName().equals("superClassPublicField")));
        assertTrue(fields.stream().anyMatch(field -> field.getName().equals("subClassPrivateField")));
        assertTrue(fields.stream().anyMatch(field -> field.getName().equals("subClassProtectedField")));
    }

    @Test
    public void testGetAllFields_SuperClassOnly() {
        List<Field> fields = FieldUtils.getAllFields(SuperClass.class);

        assertEquals(2, fields.size());

        assertTrue(fields.stream().anyMatch(field -> field.getName().equals("superClassPrivateField")));
        assertTrue(fields.stream().anyMatch(field -> field.getName().equals("superClassPublicField")));
    }

    @Test
    public void testGetAllFields_nullClass() {
        assertThrows(NullPointerException.class, () -> {
            FieldUtils.getAllFields(null);
        });
    }

    @Test
    public void testGetAllFieldsMap() {
        Map<String, Field> fieldsMap = FieldUtils.getAllFieldsMap(SubClass.class);

        assertEquals(4, fieldsMap.size());

        assertTrue(fieldsMap.containsKey("superClassPrivateField"));
        assertTrue(fieldsMap.containsKey("superClassPublicField"));
        assertTrue(fieldsMap.containsKey("subClassPrivateField"));
        assertTrue(fieldsMap.containsKey("subClassProtectedField"));
    }

    @Test
    public void testGetAllFieldsMap_SuperClassOnly() {
        Map<String, Field> fieldsMap = FieldUtils.getAllFieldsMap(SuperClass.class);

        assertEquals(2, fieldsMap.size());

        assertTrue(fieldsMap.containsKey("superClassPrivateField"));
        assertTrue(fieldsMap.containsKey("superClassPublicField"));
    }

    @Test
    public void testGetAllFieldsMap_nullClass() {
        assertThrows(NullPointerException.class, () -> {
            FieldUtils.getAllFieldsMap(null);
        });
    }

    @Test
    public void testGetAllPrivateFields() {
        List<Field> fields = FieldUtils.getAllPrivateFields(SubClass.class);

        assertEquals(2, fields.size());

        assertTrue(fields.stream().anyMatch(field -> field.getName().equals("superClassPrivateField")));
        assertTrue(fields.stream().anyMatch(field -> field.getName().equals("subClassPrivateField")));
    }

    @Test
    public void testGetAllPrivateFields_SuperClassOnly() {
        List<Field> fields = FieldUtils.getAllPrivateFields(SuperClass.class);

        assertEquals(1, fields.size());

        assertTrue(fields.stream().anyMatch(field -> field.getName().equals("superClassPrivateField")));
    }

    @Test
    public void testGetAllPrivateFields_noPrivateFields() {
        class NoPrivateFieldsClass {
            public int publicField;
            protected int protectedField;
        }

        List<Field> fields = FieldUtils.getAllPrivateFields(NoPrivateFieldsClass.class);

        assertTrue(fields.isEmpty());
    }

    @Test
    public void testGetAllPrivateFields_nullClass() {
        assertThrows(NullPointerException.class, () -> {
            FieldUtils.getAllPrivateFields(null);
        });
    }

    @Test
    public void testGetAllPrivateFieldsMap() {
        Map<String, Field> fieldsMap = FieldsExtraUtils.getAllPrivateFieldsMap(SubClass.class);

        assertEquals(2, fieldsMap.size());

        assertTrue(fieldsMap.containsKey("superClassPrivateField"));
        assertTrue(fieldsMap.containsKey("subClassPrivateField"));
    }

    @Test
    public void testGetAllPrivateFieldsMap_SuperClassOnly() {
        Map<String, Field> fieldsMap = FieldsExtraUtils.getAllPrivateFieldsMap(SuperClass.class);

        assertEquals(1, fieldsMap.size());

        assertTrue(fieldsMap.containsKey("superClassPrivateField"));
    }

    @Test
    public void testGetAllPrivateFieldsMap_noPrivateFields() {
        class NoPrivateFieldsClass {
            public int publicField;
            protected int protectedField;
        }

        Map<String, Field> fieldsMap = FieldsExtraUtils.getAllPrivateFieldsMap(NoPrivateFieldsClass.class);

        assertTrue(fieldsMap.isEmpty());
    }

    @Test
    public void testGetAllPrivateFieldsMap_nullClass() {
        assertThrows(NullPointerException.class, () -> {
            FieldsExtraUtils.getAllPrivateFieldsMap(null);
        });
    }

    // test getAllAnnotatedFields

    @Retention(RetentionPolicy.RUNTIME)
    @interface TestAnnotation {}

    private static class TestClass {
        @TestAnnotation
        private int annotatedField1;

        private String nonAnnotatedField;

        @TestAnnotation
        public double annotatedField2;
    }

    @Test
    public void testGetAllAnnotatedFields() {
        List<Field> annotatedFields = FieldsExtraUtils.getAllAnnotatedFields(TestClass.class, TestAnnotation.class);

        assertEquals(2, annotatedFields.size());
        assertTrue(annotatedFields.stream().anyMatch(field -> field.getName().equals("annotatedField1")));
        assertTrue(annotatedFields.stream().anyMatch(field -> field.getName().equals("annotatedField2")));
    }

    @Test
    public void testGetAllAnnotatedFields_noFields() {
        List<Field> annotatedFields = FieldsExtraUtils.getAllAnnotatedFields(TestClass.class, Override.class);
        assertTrue(annotatedFields.isEmpty());
    }

    @Test
    public void testGetAllAnnotatedFields_nullClass() {
        assertThrows(NullPointerException.class, () -> {
            FieldsExtraUtils.getAllAnnotatedFields(null, TestAnnotation.class);
        });
    }

    @Test
    public void testGetAllAnnotatedFields_nullAnnotation() {
        assertThrows(NullPointerException.class, () -> {
            FieldsExtraUtils.getAllAnnotatedFields(TestClass.class, null);
        });
    }

    // read field

    private static class TestClass1 {
        private int privateField = 42;
        public String publicField = "Hello";
    }

    @Test
    public void testReadField_PrivateField() {
        TestClass1 obj = new TestClass1();
        Object value = FieldUtils.readField(obj, "privateField");
        assertEquals(42, value);
    }

    @Test
    public void testReadField_PublicField() {
        TestClass1 obj = new TestClass1();
        Object value = FieldUtils.readField(obj, "publicField");
        assertEquals("Hello", value);
    }

    @Test
    public void testReadField_NonExistingField() {
        TestClass1 obj = new TestClass1();
        assertThrows(FieldAccessException.class, () -> {
            FieldUtils.readField(obj, "nonExistingField");
        });
    }

    @Test
    public void testReadField_NullObject() {
        assertThrows(NullPointerException.class, () -> {
            FieldUtils.readField(null, "fieldName");
        });
    }

    @Test
    public void testReadField_NullFieldName() {
        TestClass1 obj = new TestClass1();
        assertThrows(NullPointerException.class, () -> {
            FieldUtils.readField(obj, null);
        });
    }

    @Test
    void readSingleField() {
        CustomTestClassForType customClass = new CustomTestClassForType();

        Object oneConstant = FieldUtils.readField(customClass, TestConstant.ONE_CONSTANT);
        System.out.println(oneConstant);

        assertAll("readSingleField",
                () -> assertEquals(oneConstant, 1),
                () -> assertEquals(oneConstant.getClass(), Integer.class)
        );
    }

    @Test
    public void getAllFieldsMap() {
        Map<String, Field> fields = FieldUtils.getAllFieldsMap(CustomTestClassForType.class);

        assertAll("allFields",
                () -> assertEquals(fields.get(TestConstant.STRING_FIELD).getName(), TestConstant.STRING_FIELD),
                () -> assertEquals(fields.get(TestConstant.OBJECT_FIELD).getName(), TestConstant.OBJECT_FIELD),
                () -> assertEquals(fields.get(TestConstant.FLOAT_FIELD).getName(), TestConstant.FLOAT_FIELD),
                () -> assertEquals(fields.get(TestConstant.NOT_PRIVATE_FIELD).getName(), TestConstant.NOT_PRIVATE_FIELD),
                () -> assertEquals(fields.get(TestConstant.ONE_CONSTANT).getName(), TestConstant.ONE_CONSTANT),
                () -> assertEquals(fields.size(), 5)
        );
    }

    @Test
    void getAllAnnotatedFieldsTest() {
        List<Field> fields = FieldsExtraUtils.getAllAnnotatedFields(SimpleAnnotatedEntry.class, CustomAnnotationForTest.class);
        assertAll("classMultipleParametersInstance",
                () -> assertEquals(fields.size(), 2),
                () -> assertEquals(fields.get(0).getName(), TestConstant.KEY),
                () -> assertEquals(fields.get(1).getName(), TestConstant.VALUE)
        );
    }

    @Test
    public void getAllPrivateFieldsMap() {
        Map<String, Field> fields = FieldsExtraUtils.getAllPrivateFieldsMap(CustomTestClassForType.class);
        int fieldsCounter = 3;
        assertAll("privateFields",
                () -> assertEquals(fields.get(TestConstant.STRING_FIELD).getName(), TestConstant.STRING_FIELD),
                () -> assertEquals(fields.get(TestConstant.OBJECT_FIELD).getName(), TestConstant.OBJECT_FIELD),
                () -> assertEquals(fields.get(TestConstant.FLOAT_FIELD).getName(), TestConstant.FLOAT_FIELD),
                () -> assertEquals(fields.size(), fieldsCounter)
        );
    }
}
