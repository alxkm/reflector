package org.common.reflector.utils;

import org.junit.jupiter.api.Test;
import org.reflector.FieldUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class FieldUtilsTest {
    private static class SampleClass1 {
        private int intField;
        private String stringField;
        private double doubleField;
    }

    @Test
    public void testGetFieldType_intField() throws NoSuchFieldException {
        Class<?> fieldType = FieldUtils.getFieldType(SampleClass1.class, "intField");
        assertEquals(int.class, fieldType);
    }

    @Test
    public void testGetFieldType_stringField() throws NoSuchFieldException {
        Class<?> fieldType = FieldUtils.getFieldType(SampleClass1.class, "stringField");
        assertEquals(String.class, fieldType);
    }

    @Test
    public void testGetFieldType_doubleField() throws NoSuchFieldException {
        Class<?> fieldType = FieldUtils.getFieldType(SampleClass1.class, "doubleField");
        assertEquals(double.class, fieldType);
    }

    @Test
    public void testGetFieldType_nonExistentField() {
        assertThrows(NoSuchFieldException.class, () -> {
            FieldUtils.getFieldType(SampleClass1.class, "nonExistentField");
        });
    }

    @Test
    public void testGetFieldType_nullClass() {
        assertThrows(NullPointerException.class, () -> {
            FieldUtils.getFieldType(null, "intField");
        });
    }

    @Test
    public void testGetFieldType_nullFieldName() {
        assertThrows(NullPointerException.class, () -> {
            FieldUtils.getFieldType(SampleClass1.class, null);
        });
    }


    private static class SampleClass2 {
        public int publicField;
        private String privateField;
        protected double protectedField;
        static int staticField;
        final int finalField = 0;
        volatile int volatileField;
        transient int transientField;
    }

    @Test
    public void testGetFieldModifiers_publicField() throws NoSuchFieldException {
        int modifiers = FieldUtils.getFieldModifiers(SampleClass2.class, "publicField");
        assertTrue(Modifier.isPublic(modifiers));
    }

    @Test
    public void testGetFieldModifiers_privateField() throws NoSuchFieldException {
        int modifiers = FieldUtils.getFieldModifiers(SampleClass2.class, "privateField");
        assertTrue(Modifier.isPrivate(modifiers));
    }

    @Test
    public void testGetFieldModifiers_protectedField() throws NoSuchFieldException {
        int modifiers = FieldUtils.getFieldModifiers(SampleClass2.class, "protectedField");
        assertTrue(Modifier.isProtected(modifiers));
    }

    @Test
    public void testGetFieldModifiers_staticField() throws NoSuchFieldException {
        int modifiers = FieldUtils.getFieldModifiers(SampleClass2.class, "staticField");
        assertTrue(Modifier.isStatic(modifiers));
    }

    @Test
    public void testGetFieldModifiers_finalField() throws NoSuchFieldException {
        int modifiers = FieldUtils.getFieldModifiers(SampleClass2.class, "finalField");
        assertTrue(Modifier.isFinal(modifiers));
    }

    @Test
    public void testGetFieldModifiers_volatileField() throws NoSuchFieldException {
        int modifiers = FieldUtils.getFieldModifiers(SampleClass2.class, "volatileField");
        assertTrue(Modifier.isVolatile(modifiers));
    }

    @Test
    public void testGetFieldModifiers_transientField() throws NoSuchFieldException {
        int modifiers = FieldUtils.getFieldModifiers(SampleClass2.class, "transientField");
        assertTrue(Modifier.isTransient(modifiers));
    }

    @Test
    public void testGetFieldModifiers_nonExistentField() {
        assertThrows(NoSuchFieldException.class, () -> {
            FieldUtils.getFieldModifiers(SampleClass2.class, "nonExistentField");
        });
    }

    @Test
    public void testGetFieldModifiers_nullClass() {
        assertThrows(NullPointerException.class, () -> {
            FieldUtils.getFieldModifiers(null, "publicField");
        });
    }

    @Test
    public void testGetFieldModifiers_nullFieldName() {
        assertThrows(NullPointerException.class, () -> {
            FieldUtils.getFieldModifiers(SampleClass2.class, null);
        });
    }

    private static class SampleClass3 {
        public int publicField;
        private String privateField;
        protected double protectedField;
        static int staticField;
        final int finalField = 0;
        volatile int volatileField;
        transient int transientField;
    }

    @Test
    public void testIsFieldFinal_finalField() throws NoSuchFieldException {
        boolean isFinal = FieldUtils.isFieldFinal(SampleClass3.class, "finalField");
        assertTrue(isFinal);
    }

    @Test
    public void testIsFieldFinal_nonFinalField() throws NoSuchFieldException {
        boolean isFinal = FieldUtils.isFieldFinal(SampleClass3.class, "publicField");
        assertFalse(isFinal);
    }

    @Test
    public void testIsFieldFinal_staticField() throws NoSuchFieldException {
        boolean isFinal = FieldUtils.isFieldFinal(SampleClass3.class, "staticField");
        assertFalse(isFinal);
    }

    @Test
    public void testIsFieldFinal_volatileField() throws NoSuchFieldException {
        boolean isFinal = FieldUtils.isFieldFinal(SampleClass3.class, "volatileField");
        assertFalse(isFinal);
    }

    @Test
    public void testIsFieldFinal_transientField() throws NoSuchFieldException {
        boolean isFinal = FieldUtils.isFieldFinal(SampleClass3.class, "transientField");
        assertFalse(isFinal);
    }

    @Test
    public void testIsFieldFinal_nonExistentField() {
        assertThrows(NoSuchFieldException.class, () -> {
            FieldUtils.isFieldFinal(SampleClass3.class, "nonExistentField");
        });
    }

    @Test
    public void testIsFieldFinal_nullClass() {
        assertThrows(NullPointerException.class, () -> {
            FieldUtils.isFieldFinal(null, "finalField");
        });
    }

    @Test
    public void testIsFieldFinal_nullFieldName() {
        assertThrows(NullPointerException.class, () -> {
            FieldUtils.isFieldFinal(SampleClass3.class, null);
        });
    }

    private static class SampleClass4 {
        public int publicField;
        private String privateField;
        protected double protectedField;
        static int staticField;
        final int finalField = 0;
        volatile int volatileField;
        transient int transientField;
    }

    @Test
    public void testIsFieldStatic_staticField() throws NoSuchFieldException {
        boolean isStatic = FieldUtils.isFieldStatic(SampleClass4.class, "staticField");
        assertTrue(isStatic);
    }

    @Test
    public void testIsFieldStatic_nonStaticField() throws NoSuchFieldException {
        boolean isStatic = FieldUtils.isFieldStatic(SampleClass4.class, "publicField");
        assertFalse(isStatic);
    }

    @Test
    public void testIsFieldStatic_finalField() throws NoSuchFieldException {
        boolean isStatic = FieldUtils.isFieldStatic(SampleClass4.class, "finalField");
        assertFalse(isStatic);
    }

    @Test
    public void testIsFieldStatic_volatileField() throws NoSuchFieldException {
        boolean isStatic = FieldUtils.isFieldStatic(SampleClass4.class, "volatileField");
        assertFalse(isStatic);
    }

    @Test
    public void testIsFieldStatic_transientField() throws NoSuchFieldException {
        boolean isStatic = FieldUtils.isFieldStatic(SampleClass4.class, "transientField");
        assertFalse(isStatic);
    }

    @Test
    public void testIsFieldStatic_nonExistentField() {
        assertThrows(NoSuchFieldException.class, () -> {
            FieldUtils.isFieldStatic(SampleClass4.class, "nonExistentField");
        });
    }

    @Test
    public void testIsFieldStatic_nullClass() {
        assertThrows(NullPointerException.class, () -> {
            FieldUtils.isFieldStatic(null, "staticField");
        });
    }

    @Test
    public void testIsFieldStatic_nullFieldName() {
        assertThrows(NullPointerException.class, () -> {
            FieldUtils.isFieldStatic(SampleClass4.class, null);
        });
    }


    private static class SampleClass5 {
        private int privateField = 42;
    }

    @Test
    public void testSetFieldAccessible() throws NoSuchFieldException, IllegalAccessException {
        SampleClass5 instance = new SampleClass5();
        FieldUtils.setFieldAccessible(SampleClass5.class, "privateField");
        Field field = SampleClass5.class.getDeclaredField("privateField");
        assertEquals(false, field.isAccessible());
    }

    @Test
    public void testSetFieldAccessible_nonExistentField() {
        assertThrows(NoSuchFieldException.class, () -> {
            FieldUtils.setFieldAccessible(SampleClass5.class, "nonExistentField");
        });
    }

    @Test
    public void testSetFieldAccessible_nullClass() {
        assertThrows(NullPointerException.class, () -> {
            FieldUtils.setFieldAccessible(null, "privateField");
        });
    }

    @Test
    public void testSetFieldAccessible_nullFieldName() {
        assertThrows(NullPointerException.class, () -> {
            FieldUtils.setFieldAccessible(SampleClass5.class, null);
        });
    }

    // is field annotated


    @Retention(RetentionPolicy.RUNTIME)
    private @interface TestAnnotation {}

    private static class SampleClass6 {
        @TestAnnotation
        private String annotatedField;
        private String nonAnnotatedField;
    }

    @Test
    public void testIsFieldAnnotated_withAnnotation() throws NoSuchFieldException {
        Field field = SampleClass6.class.getDeclaredField("annotatedField");
        assertTrue(FieldUtils.isFieldAnnotated(field, TestAnnotation.class));
    }

    @Test
    public void testIsFieldAnnotated_withoutAnnotation() throws NoSuchFieldException {
        Field field = SampleClass6.class.getDeclaredField("nonAnnotatedField");
        assertFalse(FieldUtils.isFieldAnnotated(field, TestAnnotation.class));
    }

    @Test
    public void testIsFieldAnnotated_nullField() {
        assertThrows(NullPointerException.class, () -> {
            FieldUtils.isFieldAnnotated(null, TestAnnotation.class);
        });
    }

    @Test
    public void testIsFieldAnnotated_nullAnnotationClass() throws NoSuchFieldException {
        Field field = SampleClass6.class.getDeclaredField("annotatedField");
        assertThrows(NullPointerException.class, () -> {
            FieldUtils.isFieldAnnotated(field, null);
        });
    }

    // exact annotated


    private static class SampleClass7 {
        @TestAnnotation
        private String annotatedField;
        private String nonAnnotatedField;
    }

    @Test
    public void testIsFieldExactAnnotated_withAnnotation() throws NoSuchFieldException {
        Field field = SampleClass7.class.getDeclaredField("annotatedField");
        assertTrue(FieldUtils.isFieldExactAnnotated(field, TestAnnotation.class));
    }

    @Test
    public void testIsFieldExactAnnotated_withoutAnnotation() throws NoSuchFieldException {
        Field field = SampleClass7.class.getDeclaredField("nonAnnotatedField");
        assertFalse(FieldUtils.isFieldExactAnnotated(field, TestAnnotation.class));
    }

    @Test
    public void testIsFieldExactAnnotated_nullField() {
        assertThrows(NullPointerException.class, () -> {
            FieldUtils.isFieldExactAnnotated(null, TestAnnotation.class);
        });
    }

    @Test
    public void testIsFieldExactAnnotated_nullAnnotationClass() throws NoSuchFieldException {
        Field field = SampleClass7.class.getDeclaredField("annotatedField");
        assertThrows(NullPointerException.class, () -> {
            FieldUtils.isFieldExactAnnotated(field, null);
        });
    }

    // get all fields

    private static class SuperSimpleClass {
        private int superClassField;
        public String superClassPublicField;
    }

    private static class SubSimpleClass extends SuperSimpleClass {
        private double subClassField;
        protected boolean subClassProtectedField;
    }

    @Test
    public void testGetAllFields() {
        List<Field> fields = FieldUtils.getAllFields(SubSimpleClass.class);

        assertEquals(4, fields.size());

        assertTrue(fields.stream().anyMatch(field -> field.getName().equals("superClassField")));
        assertTrue(fields.stream().anyMatch(field -> field.getName().equals("superClassPublicField")));
        assertTrue(fields.stream().anyMatch(field -> field.getName().equals("subClassField")));
        assertTrue(fields.stream().anyMatch(field -> field.getName().equals("subClassProtectedField")));
    }

    @Test
    public void testGetAllFields_SuperClassOnly() {
        List<Field> fields = FieldUtils.getAllFields(SuperSimpleClass.class);

        assertEquals(2, fields.size());

        assertTrue(fields.stream().anyMatch(field -> field.getName().equals("superClassField")));
        assertTrue(fields.stream().anyMatch(field -> field.getName().equals("superClassPublicField")));
    }

    @Test
    public void testGetAllFields_nullClass() {
        assertThrows(NullPointerException.class, () -> {
            FieldUtils.getAllFields(null);
        });
    }

    // get all private fields

    private static class SuperClass {
        private int superClassPrivateField;
        public String superClassPublicField;
    }

    private static class SubClass extends SuperClass {
        private double subClassPrivateField;
        protected boolean subClassProtectedField;
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
}
