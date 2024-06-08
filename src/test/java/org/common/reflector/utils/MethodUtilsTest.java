package org.common.reflector.utils;

import org.common.reflector.data.MethodAnnotatedClass;
import org.common.reflector.data.Person;
import org.common.reflector.data.SimpleAnnotatedEntry;
import org.common.reflector.util.TestConstant;
import org.junit.jupiter.api.Test;
import org.reflector.MethodUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    //getDefaultMethodsOfInterfaces

    /**
     * An interface with a default method for testing.
     */
    interface TestInterfaceWithMethod1 {
        default void defaultMethod1() {
            System.out.println("Default Method 1");
        }

        void nonDefaultMethod1();
    }

    /**
     * Another interface with a default method for testing.
     */
    interface TestInterfaceWithMethod2 {
        default void defaultMethod2() {
            System.out.println("Default Method 2");
        }

        void nonDefaultMethod2();
    }

    /**
     * A class implementing the test interfaces.
     */
    class AggregatedTestClass implements TestInterfaceWithMethod1, TestInterfaceWithMethod2 {
        @Override
        public void nonDefaultMethod1() {}

        @Override
        public void nonDefaultMethod2() {}
    }

    /**
     * Tests if getDefaultMethodsOfInterfaces retrieves the correct default methods.
     */
    @Test
    void testGetDefaultMethodsOfInterfaces() throws NoSuchMethodException {
        List<Method> defaultMethods = MethodUtils.getDefaultMethodsOfInterfaces(AggregatedTestClass.class);

        assertEquals(2, defaultMethods.size());

        Method defaultMethod1 = TestInterfaceWithMethod1.class.getMethod("defaultMethod1");
        Method defaultMethod2 = TestInterfaceWithMethod2.class.getMethod("defaultMethod2");

        assertTrue(defaultMethods.contains(defaultMethod1));
        assertTrue(defaultMethods.contains(defaultMethod2));
    }

    /**
     * Tests if getDefaultMethodsOfInterfaces returns an empty list when no default methods are present.
     */
    @Test
    void testGetDefaultMethodsOfInterfaces_noDefaultMethods() {
        class NoDefaultMethodClass {}

        List<Method> defaultMethods = MethodUtils.getDefaultMethodsOfInterfaces(NoDefaultMethodClass.class);
        assertTrue(defaultMethods.isEmpty());
    }

    /**
     * Tests if getDefaultMethodsOfInterfaces returns an empty list when the class has no interfaces.
     */
    @Test
    void testGetDefaultMethodsOfInterfaces_classWithNoInterfaces() {
        class NoInterfacesClass {}

        List<Method> defaultMethods = MethodUtils.getDefaultMethodsOfInterfaces(NoInterfacesClass.class);
        assertTrue(defaultMethods.isEmpty());
    }

    /**
     * Tests if getDefaultMethodsOfInterfaces throws an IllegalArgumentException when the class is null.
     */
    @Test
    void testGetDefaultMethodsOfInterfaces_nullClass() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            MethodUtils.getDefaultMethodsOfInterfaces(null);
        });

        assertEquals("Class parameter cannot be null", exception.getMessage());
    }


    /**
     * An interface with a default method for testing.
     */
    interface TestInterfaceWithDefaultAndNotDefaultMethods1 {
        default void defaultMethod1() {
            System.out.println("Default Method 1");
        }

        void nonDefaultMethod1();
    }

    /**
     * Another interface with a default method for testing.
     */
    interface TestInterfaceWithDefaultAndNotDefaultMethods2 {
        default void defaultMethod2() {
            System.out.println("Default Method 2");
        }

        void nonDefaultMethod2();
    }

    /**
     * A class implementing the test interfaces.
     */
    class AggregatedTestClassForInterfacesWithDefaultMethods implements TestInterfaceWithDefaultAndNotDefaultMethods1, TestInterfaceWithDefaultAndNotDefaultMethods2 {
        @Override
        public void nonDefaultMethod1() {}

        @Override
        public void nonDefaultMethod2() {}

        public void declaredMethod1() {}

        private void declaredMethod2() {}
    }

    /**
     * Tests if getDeclaredMethodsList retrieves the correct declared methods and default methods.
     */
    @Test
    void testGetDeclaredMethodsList() throws NoSuchMethodException {
        List<Method> methods = MethodUtils.getDeclaredMethodsList(AggregatedTestClassForInterfacesWithDefaultMethods.class);

        assertEquals(6, methods.size());

        Method declaredMethod1 = AggregatedTestClassForInterfacesWithDefaultMethods.class.getDeclaredMethod("declaredMethod1");
        Method declaredMethod2 = AggregatedTestClassForInterfacesWithDefaultMethods.class.getDeclaredMethod("declaredMethod2");
        Method defaultMethod1 = TestInterfaceWithDefaultAndNotDefaultMethods1.class.getMethod("defaultMethod1");
        Method defaultMethod2 = TestInterfaceWithDefaultAndNotDefaultMethods2.class.getMethod("defaultMethod2");

        assertTrue(methods.contains(declaredMethod1));
        assertTrue(methods.contains(declaredMethod2));
        assertTrue(methods.contains(defaultMethod1));
        assertTrue(methods.contains(defaultMethod2));
    }

    /**
     * Tests if getDeclaredMethodsList returns only declared methods when no default methods are present.
     */
    @Test
    void testGetDeclaredMethodsList_noDefaultMethods() {
        class NoDefaultMethodClass {
            public void declaredMethod() {}
        }

        List<Method> methods = MethodUtils.getDeclaredMethodsList(NoDefaultMethodClass.class);
        assertEquals(1, methods.size());
    }

    /**
     * Tests if getDeclaredMethodsList returns only declared methods when the class has no interfaces.
     */
    @Test
    void testGetDeclaredMethodsList_classWithNoInterfaces() {
        class NoInterfacesClass {
            public void declaredMethod() {}
        }

        List<Method> methods = MethodUtils.getDeclaredMethodsList(NoInterfacesClass.class);
        assertEquals(1, methods.size());
    }

    /**
     * Tests if getDeclaredMethodsList throws an IllegalArgumentException when the class parameter is null.
     */
    @Test
    void testGetDeclaredMethodsList_nullClass() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            MethodUtils.getDeclaredMethodsList(null);
        });

        assertEquals("Class parameter cannot be null", exception.getMessage());
    }

    //MethodUtils.findMethodByName


    interface TestInterface1 {
        default void defaultMethod1() {
            System.out.println("Default Method 1");
        }

        void nonDefaultMethod1();
    }

    interface TestInterface2 {
        default void defaultMethod2() {
            System.out.println("Default Method 2");
        }

        void nonDefaultMethod2();
    }

    class TestClass implements TestInterface1, TestInterface2 {
        @Override
        public void nonDefaultMethod1() {}

        @Override
        public void nonDefaultMethod2() {}

        public void declaredMethod1() {}

        private void declaredMethod2() {}
    }

    class SubTestClass extends TestClass {
        public void subDeclaredMethod() {}
    }

    /**
     * Tests if findMethodByName retrieves the correct method by name from the class hierarchy.
     */
    @Test
    void testFindMethodByName() throws NoSuchMethodException {
        Method method = MethodUtils.findMethodByName(SubTestClass.class, "subDeclaredMethod");
        assertNotNull(method);
        assertEquals("subDeclaredMethod", method.getName());

        method = MethodUtils.findMethodByName(SubTestClass.class, "declaredMethod1");
        assertNotNull(method);
        assertEquals("declaredMethod1", method.getName());

        method = MethodUtils.findMethodByName(SubTestClass.class, "defaultMethod1");
        assertNotNull(method);
        assertEquals("defaultMethod1", method.getName());
    }

    /**
     * Tests if findMethodByName returns null when the method name is not found.
     */
    @Test
    void testFindMethodByName_notFound() {
        Method method = MethodUtils.findMethodByName(SubTestClass.class, "nonExistentMethod");
        assertNull(method);
    }

    /**
     * Tests if findMethodByName throws an IllegalArgumentException when the class parameter is null.
     */
    @Test
    void testFindMethodByName_nullClass() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            MethodUtils.findMethodByName(null, "someMethod");
        });

        assertEquals("Class and method name parameters cannot be null", exception.getMessage());
    }

    /**
     * Tests if findMethodByName throws an IllegalArgumentException when the method name parameter is null.
     */
    @Test
    void testFindMethodByName_nullMethodName() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            MethodUtils.findMethodByName(SubTestClass.class, null);
        });

        assertEquals("Class and method name parameters cannot be null", exception.getMessage());
    }
}
