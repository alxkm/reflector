package org.common.reflector.utils;

import org.common.reflector.data.MethodAnnotatedClass;
import org.common.reflector.data.Person;
import org.common.reflector.data.SimpleAnnotatedEntry;
import org.common.reflector.data.annotation.CustomMethodAnnotation;
import org.common.reflector.util.TestConstant;
import org.junit.jupiter.api.Test;
import org.reflector.AnnotationUtils;
import org.reflector.MethodUtils;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class TestClassParameter {
    public void testMethod(String param1, int param2) throws IllegalArgumentException, NullPointerException {
    }

    public int returnMethod() {
        return 0;
    }

    public void noExceptionMethod() {
    }

    public void noVarArgsMethod() {
    }
}

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
@interface TestAnnotation {
    String value() default "default";
}

class TestClass1 {
    @TestAnnotation
    public void testMethod() {
    }

    public void varArgsMethod(String... args) {
    }

    public void noVarArgsMethod(String arg) {
    }
}

public class MethodUtilsTest {

    @Test
    public void testGetParameterTypes_ValidMethod() throws NoSuchMethodException {
        Method method = TestClassParameter.class.getMethod("testMethod", String.class, int.class);
        Class<?>[] parameterTypes = MethodUtils.getParameterTypes(method);
        assertNotNull(parameterTypes);
        assertEquals(2, parameterTypes.length);
        assertEquals(String.class, parameterTypes[0]);
        assertEquals(int.class, parameterTypes[1]);
    }

    @Test
    public void testGetParameterTypes_NullMethod() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            MethodUtils.getParameterTypes(null);
        });
        String expectedMessage = "Method must not be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testGetReturnType_ValidMethod() throws NoSuchMethodException {
        Method method = TestClassParameter.class.getMethod("returnMethod");
        Class<?> returnType = MethodUtils.getReturnType(method);
        assertNotNull(returnType);
        assertEquals(int.class, returnType);
    }

    @Test
    public void testGetReturnType_NullMethod() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            MethodUtils.getReturnType(null);
        });
        String expectedMessage = "Method must not be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testGetExceptionTypes_ValidMethod() throws NoSuchMethodException {
        Method method = TestClassParameter.class.getMethod("testMethod", String.class, int.class);
        Class<?>[] exceptionTypes = MethodUtils.getExceptionTypes(method);
        assertNotNull(exceptionTypes);
        assertEquals(2, exceptionTypes.length);
        assertEquals(IllegalArgumentException.class, exceptionTypes[0]);
        assertEquals(NullPointerException.class, exceptionTypes[1]);
    }

    @Test
    public void testGetExceptionTypes_NoExceptionMethod() throws NoSuchMethodException {
        Method method = TestClassParameter.class.getMethod("noVarArgsMethod");
        Class<?>[] exceptionTypes = MethodUtils.getExceptionTypes(method);
        assertNotNull(exceptionTypes);
        assertEquals(0, exceptionTypes.length);
    }

    @Test
    public void testGetExceptionTypes_NullMethod() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            MethodUtils.getExceptionTypes(null);
        });
        String expectedMessage = "Method must not be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testGetMethodModifiers_PublicMethod() throws NoSuchMethodException {
        Method method = TestClass1.class.getMethod("testMethod");
        int modifiers = MethodUtils.getMethodModifiers(method);
        assertTrue(Modifier.isPublic(modifiers));
    }

    @Test
    public void testGetMethodModifiers_NullMethod() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            MethodUtils.getMethodModifiers(null);
        });
        String expectedMessage = "Method must not be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testIsMethodVarArgs_VarArgsMethod() throws NoSuchMethodException {
        Method method = TestClass1.class.getMethod("varArgsMethod", String[].class);
        boolean isVarArgs = MethodUtils.isMethodVarArgs(method);
        assertTrue(isVarArgs);
    }

    @Test
    public void testIsMethodVarArgs_NoVarArgsMethod() throws NoSuchMethodException {
        Method method = TestClass1.class.getMethod("noVarArgsMethod", String.class);
        boolean isVarArgs = MethodUtils.isMethodVarArgs(method);
        assertFalse(isVarArgs);
    }

    @Test
    public void testIsMethodVarArgs_NullMethod() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            MethodUtils.isMethodVarArgs(null);
        });
        String expectedMessage = "Method must not be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testGetDefaultValue_AnnotationMethod() throws NoSuchMethodException {
        Method method = TestAnnotation.class.getMethod("value");
        Object defaultValue = MethodUtils.getDefaultValue(method);
        assertNotNull(defaultValue);
        assertEquals("default", defaultValue);
    }

    @Test
    public void testGetDefaultValue_NoDefaultValue() throws NoSuchMethodException {
        Method method = TestClass1.class.getMethod("testMethod");
        Object defaultValue = MethodUtils.getDefaultValue(method);
        assertNull(defaultValue);
    }

    @Test
    public void testGetDefaultValue_NullMethod() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            MethodUtils.getDefaultValue(null);
        });
        String expectedMessage = "Method must not be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    private static class SampleClass {
        private void privateMethod() {}
        protected void protectedMethod() {}
        public void publicMethod() {}
        public static void publicStaticMethod() {}
    }

    @Test
    public void testGetAllPrivateMethods() {
        List<Method> methods = MethodUtils.getAllPrivateMethods(SampleClass.class);
        assertEquals(1, methods.size());
        assertEquals("privateMethod", methods.get(0).getName());
    }

    @Test
    public void testGetAllPublicProtectedMethods() {
        List<Method> methods = MethodUtils.getAllPublicProtectedMethods(SampleClass.class);
        assertEquals(3, methods.size());
        assertTrue(methods.stream().anyMatch(method -> method.getName().equals("protectedMethod")));
        assertTrue(methods.stream().anyMatch(method -> method.getName().equals("publicMethod")));
        assertTrue(methods.stream().anyMatch(method -> method.getName().equals("publicStaticMethod")));
    }

    @Test
    public void testGetAllPublicMethods() {
        List<Method> methods = MethodUtils.getAllPublicMethods(SampleClass.class);
        assertEquals(2, methods.size());
        assertTrue(methods.stream().anyMatch(method -> method.getName().equals("publicMethod")));
        assertTrue(methods.stream().anyMatch(method -> method.getName().equals("publicStaticMethod")));
    }

    @Test
    public void testGetAllPrivateMethods_nullClass() {
        assertThrows(NullPointerException.class, () -> {
            MethodUtils.getAllPrivateMethods(null);
        });
    }

    @Test
    public void testGetAllPublicProtectedMethods_nullClass() {
        assertThrows(NullPointerException.class, () -> {
            MethodUtils.getAllPublicProtectedMethods(null);
        });
    }

    @Test
    public void testGetAllPublicMethods_nullClass() {
        assertThrows(NullPointerException.class, () -> {
            MethodUtils.getAllPublicMethods(null);
        });
    }

    @Test
    public void testGetAllMethodsWithModifiers_nullModifiers() {
        assertThrows(NullPointerException.class, () -> {
            MethodUtils.getAllMethodsWithModifiers(SampleClass.class, null);
        });
    }

    @Test
    public void getAllPublicMethodsTest() {
        List<Method> allPublicProtectedMethods = MethodUtils.getAllPublicProtectedMethods(SimpleAnnotatedEntry.class);
        assertAll("publicProtectedMethods",
                () -> assertEquals(allPublicProtectedMethods.size(), 6)
        );
    }

    @Test
    public void getAllPrivateMethodsTest() {
        List<Method> allPublicProtectedMethods = MethodUtils.getAllPrivateMethods(SimpleAnnotatedEntry.class);
        assertEquals(TestConstant.DO_SOMETHING_METHOD_NAME, allPublicProtectedMethods.get(0).getName());
    }

    @Test
    public void testGetAllMethodsWithModifiers_nullPredicates() {
        assertThrows(NullPointerException.class, () -> {
            MethodUtils.getAllMethodsWithModifiers(SampleClass.class, null);
        });
    }

    @Test
    public void testGetAllMethodsWithModifiers_emptyPredicates() {
        List<Method> methods = MethodUtils.getAllMethodsWithModifiers(SampleClass.class, List.of());
        assertTrue(methods.isEmpty());
    }



    @Test
    public void doesHasAnnotations() {
        List<Method> allPublicProtectedMethods = MethodUtils.getAllPublicMethods(MethodAnnotatedClass.class);
        List<String> expected = new ArrayList<>(Arrays.asList(TestConstant.GET_CLASS_METHOD_METHOD_ANNOTATED_CLASS,
                TestConstant.ANNOTATED_METHOD_ANNOTATED_CLASS,
                TestConstant.WAIT_METHOD_ANNOTATED_CLASS,
                TestConstant.HASH_CODE_METHOD_ANNOTATED_CLASS,
                TestConstant.EQUALS_METHOD_ANNOTATED_CLASS,
                TestConstant.NOTIFY_ALL_METHOD_ANNOTATED_CLASS,
                TestConstant.TO_STRING_METHOD_ANNOTATED_CLASS,
                TestConstant.NOTIFY_METHOD_ANNOTATED_CLASS));
        List<String> actual = allPublicProtectedMethods.stream().map(Method::getName).collect(Collectors.toList());
        assertFalse(expected.size() == actual.size() && expected.containsAll(actual) && actual.containsAll(expected));
    }


    @Test
    public void findMethodsTestByName() {
        Method method = MethodUtils.findMethodByName(Person.class, TestConstant.METHOD_NAME_GET_ID);
        assertEquals(TestConstant.METHOD_NAME_GET_ID, method.getName());
    }
}
