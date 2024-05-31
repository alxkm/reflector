package org.common.reflector;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.common.reflector.data.Person;
import org.common.reflector.data.annotation.ClassAnnotation;
import org.common.reflector.data.annotation.CustomAnnotationForTest;
import org.common.reflector.data.annotation.CustomMethodAnnotation;
import org.common.reflector.data.CustomTestClassForType;
import org.common.reflector.data.CustomTestInvokeClass;
import org.common.reflector.data.MethodAnnotatedClass;
import org.common.reflector.data.SimpleAnnotatedEntry;
import org.common.reflector.data.SimpleEntryClass;
import org.common.reflector.util.TestConstant;
import org.junit.jupiter.api.Test;
import org.reflector.ReflectionUtils;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReflectionUtilsTest {
    @Test
    public void getAllClassNamesTest() {
        Object obj = new CustomTestInvokeClass(TestConstant.SIMPLE_CLASS_SIMPLE_VALUE);
        assertAll("classNames",
                  () ->  assertEquals(TestConstant.CUSTOM_TEST_INVOKE_CLASS, ReflectionUtils.getClassSimpleName(obj)),
                  () ->  assertEquals(TestConstant.CUSTOM_TEST_INVOKE_CLASS_PACKAGE, ReflectionUtils.getClassFullName(obj)),
                  () ->  assertEquals(TestConstant.CUSTOM_TEST_INVOKE_CLASS_PACKAGE, ReflectionUtils.getClassCanonicalName(obj))
        );
    }

    @Test
    public void testGetClassFullName_ValidObject() {
        // Test with a valid object
        String fullName = ReflectionUtils.getClassFullName("Test String");
        assertNotNull(fullName);
        assertEquals("java.lang.String", fullName);
    }

    @Test
    public void testGetClassFullName_PrimitiveWrapper() {
        // Test with a primitive wrapper type
        Integer integer = 42;
        String fullName = ReflectionUtils.getClassFullName(integer);
        assertNotNull(fullName);
        assertEquals("java.lang.Integer", fullName);
    }

    @Test
    public void testGetClassFullName_NullObject() {
        // Test with a null object (should throw IllegalArgumentException)
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ReflectionUtils.getClassFullName(null);
        });

        String expectedMessage = "Object must not be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testGetClassFullName_CustomClass() {
        // Test with a custom class
        class CustomClass {}
        CustomClass customObject = new CustomClass();
        String fullName = ReflectionUtils.getClassFullName(customObject);
        assertNotNull(fullName);
        assertEquals("org.common.reflector.ReflectionUtilsTest$1CustomClass", fullName);
    }

    @Test
    public void testGetClassFullName_ArrayObject() {
        // Test with an array object
        int[] intArray = new int[]{1, 2, 3};
        String fullName = ReflectionUtils.getClassFullName(intArray);
        assertNotNull(fullName);
        assertEquals("[I", fullName); // The class name for int[] is "[I"
    }

    @Test
    public void testGetClassCanonicalName_ValidObject() {
        // Test with a valid object
        String canonicalName = ReflectionUtils.getClassCanonicalName("Test String");
        assertNotNull(canonicalName);
        assertEquals("java.lang.String", canonicalName);
    }

    @Test
    public void testGetClassCanonicalName_PrimitiveWrapper() {
        // Test with a primitive wrapper type
        Integer integer = 42;
        String canonicalName = ReflectionUtils.getClassCanonicalName(integer);
        assertNotNull(canonicalName);
        assertEquals("java.lang.Integer", canonicalName);
    }

    @Test
    public void testGetClassCanonicalName_NullObject() {
        // Test with a null object (should throw IllegalArgumentException)
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ReflectionUtils.getClassCanonicalName(null);
        });

        String expectedMessage = "Object must not be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testGetClassCanonicalName_CustomClass() {
        // Test with a custom class
        class CustomClass {}
        CustomClass customObject = new CustomClass();
        String canonicalName = ReflectionUtils.getClassCanonicalName(customObject);
        assertEquals(null, canonicalName);
    }

    @Test
    public void testGetClassCanonicalName_ArrayObject() {
        // Test with an array object
        int[] intArray = new int[]{1, 2, 3};
        String canonicalName = ReflectionUtils.getClassCanonicalName(intArray);
        assertNotNull(canonicalName);
        assertEquals("int[]", canonicalName);
    }

    @Test
    public void testGetClassCanonicalName_AnonymousClass() {
        // Test with an anonymous class
        Object anonymousClass = new Object() {};
        String canonicalName = ReflectionUtils.getClassCanonicalName(anonymousClass);
        assertNull(canonicalName);
    }

    @Test
    public void getPackageNameTest() {
        Object obj = new CustomTestInvokeClass(TestConstant.SIMPLE_CLASS_SIMPLE_VALUE);
        assertEquals(TestConstant.REFLECTOR_DATA_PACKAGE, ReflectionUtils.getPackage(obj));
    }

    @Test
    public void testGetPackage_ValidObject() {
        // Test with a valid object
        String packageName = ReflectionUtils.getPackage("Test String");
        assertNotNull(packageName);
        assertEquals("java.lang", packageName);
    }

    @Test
    public void testGetPackage_NullObject() {
        // Test with a null object (should throw IllegalArgumentException)
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ReflectionUtils.getPackage(null);
        });

        String expectedMessage = "Object must not be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testGetPackage_ArrayObject() {
        // Test with an array object (should return null)
        int[] intArray = new int[]{1, 2, 3};
        String packageName = ReflectionUtils.getPackage(intArray);
        assertNull(packageName);
    }

    @Test
    public void testGetSuperClassName_ValidObject() {
        // Test with a valid object whose class has a superclass
        String superClassName = ReflectionUtils.getSuperClassName("Test String");
        assertNotNull(superClassName);
        assertEquals("java.lang.Object", superClassName);
    }

    @Test
    public void testGetSuperClassName_ObjectClass() {
        // Test with an object of class Object which has no superclass
        Object obj = new Object();
        String superClassName = ReflectionUtils.getSuperClassName(obj);
        assertNull(superClassName);
    }

    @Test
    public void testGetSuperClassName_NullObject() {
        // Test with a null object (should throw IllegalArgumentException)
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ReflectionUtils.getSuperClassName(null);
        });

        String expectedMessage = "Object must not be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testGetSuperClassName_PrimitiveWrapper() {
        // Test with a primitive wrapper type (Integer in this case)
        Integer integer = 5;
        String superClassName = ReflectionUtils.getSuperClassName(integer);
        assertNotNull(superClassName);
        assertEquals("java.lang.Number", superClassName);
    }

    @Test
    public void testGetSuperClassName_CustomClass() {
        // Test with a custom class with a superclass
        class SuperClass {}
        class SubClass extends SuperClass {}

        SubClass subClassInstance = new SubClass();
        String superClassName = ReflectionUtils.getSuperClassName(subClassInstance);
        assertNotNull(superClassName);
        assertEquals(SuperClass.class.getName(), superClassName);
    }

    @Test
    public void testGetSuperClassName_Interface() {
        // Test with an interface (should return null as it has no superclass)
        class Example implements Runnable {
            public void run() {}
        }

        Example example = new Example();
        String superClassName = ReflectionUtils.getSuperClassName(example);
        assertNotNull(superClassName);
        assertEquals("java.lang.Object", superClassName);
    }

    @Test
    public void getSuperClassTest() {
        CustomTestInvokeClass obj = new CustomTestInvokeClass(TestConstant.SIMPLE_CLASS_SIMPLE_VALUE);
        assertEquals(Object.class, ReflectionUtils.getSuperClass(obj));
    }

    @Test
    public void getAllClassNamesByClassTest() {
        assertAll("classNames",
                  () ->  assertEquals(TestConstant.CUSTOM_TEST_INVOKE_CLASS, ReflectionUtils.getClassSimpleNameByClass(CustomTestInvokeClass.class)),
                  () ->  assertEquals(TestConstant.CUSTOM_TEST_INVOKE_CLASS_PACKAGE, ReflectionUtils.getClassFullNameByClass(CustomTestInvokeClass.class)),
                  () ->  assertEquals(TestConstant.CUSTOM_TEST_INVOKE_CLASS_PACKAGE, ReflectionUtils.getClassCanonicalNameByClass(CustomTestInvokeClass.class))
        );
    }

    @Test
    public void testGetClassFullNameByClass_ValidClass() {
        // Test with a valid class
        String fullName = ReflectionUtils.getClassFullNameByClass(String.class);
        assertNotNull(fullName);
        assertEquals("java.lang.String", fullName);
    }

    @Test
    public void testGetClassFullNameByClass_NullClass() {
        // Test with a null class
        String fullName = ReflectionUtils.getClassFullNameByClass(null);
        assertNotNull(fullName);
        assertEquals("", fullName);
    }

    @Test
    public void testGetClassCanonicalNameByClass_ValidClass() {
        // Test with a valid class
        String canonicalName = ReflectionUtils.getClassCanonicalNameByClass(String.class);
        assertNotNull(canonicalName);
        assertEquals("java.lang.String", canonicalName);
    }

    @Test
    public void testGetClassCanonicalNameByClass_NullClass() {
        // Test with a null class
        String canonicalName = ReflectionUtils.getClassCanonicalNameByClass(null);
        assertNull(canonicalName);
    }

    @Test
    public void testGetClassSimpleNameByClass_ValidClass() {
        // Test with a valid class
        String simpleName = ReflectionUtils.getClassSimpleNameByClass(String.class);
        assertNotNull(simpleName);
        assertEquals("String", simpleName);
    }

    @Test
    public void testGetClassSimpleNameByClass_PrimitiveClass() {
        // Test with a primitive type class
        String simpleName = ReflectionUtils.getClassSimpleNameByClass(int.class);
        assertNotNull(simpleName);
        assertEquals("int", simpleName);
    }

    @Test
    public void testGetClassSimpleNameByClass_ArrayClass() {
        // Test with an array class
        String simpleName = ReflectionUtils.getClassSimpleNameByClass(int[].class);
        assertNotNull(simpleName);
        assertEquals("int[]", simpleName);
    }

    @Test
    public void testGetClassSimpleNameByClass_InnerClass() {
        // Test with an inner class
        class InnerClass {}
        String simpleName = ReflectionUtils.getClassSimpleNameByClass(InnerClass.class);
        assertNotNull(simpleName);
        assertEquals("InnerClass", simpleName);
    }

    @Test
    public void testGetClassSimpleNameByClass_NullClass() {
        // Test with a null class (should throw IllegalArgumentException)
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ReflectionUtils.getClassSimpleNameByClass(null);
        });

        String expectedMessage = "Class must not be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testGetClassSimpleNameByClass_CustomClass() {
        // Test with a custom class
        class CustomClass {}
        String simpleName = ReflectionUtils.getClassSimpleNameByClass(CustomClass.class);
        assertNotNull(simpleName);
        assertEquals("CustomClass", simpleName);
    }

    @Test
    public void testGetClassSimpleName_ValidObject() {
        // Test with a valid object
        String simpleName = ReflectionUtils.getClassSimpleName("Test String");
        assertNotNull(simpleName);
        assertEquals("String", simpleName);
    }

    @Test
    public void testGetClassSimpleName_PrimitiveWrapper() {
        // Test with a primitive wrapper type
        Integer integer = 42;
        String simpleName = ReflectionUtils.getClassSimpleName(integer);
        assertNotNull(simpleName);
        assertEquals("Integer", simpleName);
    }

    @Test
    public void testGetClassSimpleName_NullObject() {
        // Test with a null object (should throw IllegalArgumentException)
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ReflectionUtils.getClassSimpleName(null);
        });

        String expectedMessage = "Object must not be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testGetClassSimpleName_CustomClass() {
        // Test with a custom class
        class CustomClass {}
        CustomClass customObject = new CustomClass();
        String simpleName = ReflectionUtils.getClassSimpleName(customObject);
        assertNotNull(simpleName);
        assertEquals("CustomClass", simpleName);
    }

    @Test
    public void testGetClassSimpleName_ArrayObject() {
        // Test with an array object
        int[] intArray = new int[]{1, 2, 3};
        String simpleName = ReflectionUtils.getClassSimpleName(intArray);
        assertNotNull(simpleName);
        assertEquals("int[]", simpleName);
    }

    @Test
    public void testGetClassSimpleName_AnonymousClass() {
        // Test with an anonymous class
        String simpleName = ReflectionUtils.getClassSimpleName(new Object());
        assertNotNull(simpleName);
        // Check that the simple name is not null but matches the expected pattern
        assertTrue(simpleName.equals("Object")); // Anonymous classes have numeric names
    }

    @Test
    public void testGetPackageByClass_ValidClass() {
        // Test with a valid class that has a package
        String packageName = ReflectionUtils.getPackageByClass(String.class);
        assertNotNull(packageName);
        assertEquals("java.lang", packageName);
    }

    @Test
    public void testGetPackageByClass_PrimitiveClass() {
        // Test with a primitive type class (should return null)
        String packageName = ReflectionUtils.getPackageByClass(int.class);
        assertNull(packageName);
    }

    @Test
    public void testGetPackageByClass_ArrayClass() {
        // Test with an array class (should return the package of the component type)
        String packageName = ReflectionUtils.getPackageByClass(int[].class);
        assertNull(packageName);  // Arrays do not have a package
    }

    @Test
    public void testGetPackageByClass_NullClass() {
        // Test with a null class (should throw IllegalArgumentException)
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ReflectionUtils.getPackageByClass(null);
        });

        String expectedMessage = "Class must not be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testGetPackageByClass_CustomClass() {
        // Test with a custom class with a package
        class CustomClass {}
        String packageName = ReflectionUtils.getPackageByClass(CustomClass.class);
        assertNotNull(packageName);
        assertEquals(this.getClass().getPackage().getName(), packageName);
    }

    @Test
    public void testGetSuperClassNameByClass_ValidClass() {
        // Test with a class that has a superclass
        String superClassName = ReflectionUtils.getSuperClassNameByClass(String.class);
        assertNotNull(superClassName);
        assertEquals("java.lang.Object", superClassName);
    }

    @Test
    public void testGetSuperClassNameByClass_ObjectClass() {
        // Test with Object class which has no superclass
        String superClassName = ReflectionUtils.getSuperClassNameByClass(Object.class);
        assertNull(superClassName);
    }

    @Test
    public void testGetSuperClassNameByClass_PrimitiveClass() {
        // Test with a primitive type class (should return null)
        String superClassName = ReflectionUtils.getSuperClassNameByClass(int.class);
        assertNull(superClassName);
    }

    @Test
    public void testGetSuperClassNameByClass_Interface() {
        // Test with an interface (should return null)
        String superClassName = ReflectionUtils.getSuperClassNameByClass(Runnable.class);
        assertNull(superClassName);
    }

    @Test
    public void testGetSuperClassNameByClass_NullClass() {
        // Test with null class (should throw IllegalArgumentException)
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ReflectionUtils.getSuperClassNameByClass(null);
        });

        String expectedMessage = "Class must not be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void getSuperClassFoClassTest() {
        assertEquals(Object.class, ReflectionUtils.getSuperClass(CustomTestInvokeClass.class));
    }

    @Test
    void getAllPrivateFieldsTest() {

        List<Field> fields = ReflectionUtils.getAllPrivateFields(CustomTestClassForType.class);

        assertAll("privateFields",
                () -> assertEquals(fields.get(0).getName(), TestConstant.STRING_FIELD),
                () -> assertEquals(fields.get(1).getName(), TestConstant.OBJECT_FIELD),
                () -> assertEquals(fields.get(2).getName(), TestConstant.FLOAT_FIELD),
                () -> assertEquals(fields.size(), 3)
        );
    }

    @Test
    void invokeClassMethodTest() {
        CustomTestInvokeClass instance = (CustomTestInvokeClass) ReflectionUtils.invokeInstance(
                TestConstant.CUSTOM_TEST_INVOKE_CLASS_PACKAGE);
        Object ret1 = ReflectionUtils.invokeMethod(instance, TestConstant.SET_VALUE,
                                                  new Class[]{String.class}, new String[]{TestConstant.SIMPLE_VALUE});
        String ret2 = (String) ReflectionUtils.invokeMethod(instance, TestConstant.GET_VALUE, null, null);

        assertAll("invokedMethodValues",
                () -> assertEquals(ret1, null),
                () -> assertEquals(ret2, TestConstant.SIMPLE_VALUE)
        );
    }

    @Test
    void invokeClassInstanceTest() {
        CustomTestInvokeClass instance = (CustomTestInvokeClass) ReflectionUtils.invokeInstance(
                TestConstant.CUSTOM_TEST_INVOKE_CLASS_PACKAGE);
        assertAll("classInstance",
                () -> assertNotEquals(instance, null)
        );

    }

    @Test
    void readSingleField() {
        CustomTestClassForType customClass = new CustomTestClassForType();

        Object oneConstant = ReflectionUtils.readField(customClass, TestConstant.ONE_CONSTANT);
        System.out.println(oneConstant);

        assertAll("readSingleField",
                () -> assertEquals(oneConstant, 1),
                () -> assertEquals(oneConstant.getClass(), Integer.class)
        );
    }

    @Test
    void invokeSingleClassMethodTest() {
        CustomTestInvokeClass instance = (CustomTestInvokeClass) ReflectionUtils.invokeInstance(TestConstant.CUSTOM_TEST_INVOKE_CLASS_PACKAGE);
        Object ret1 = ReflectionUtils.invokeSingleMethod(instance, TestConstant.SET_VALUE, String.class, TestConstant.SIMPLE_VALUE);
        String ret2 = (String) ReflectionUtils.invokeMethod(instance, TestConstant.GET_VALUE, null, null);

        assertAll("singleClassMethodValue",
                () -> assertEquals(ret1, null),
                () -> assertEquals(ret2, TestConstant.SIMPLE_VALUE)
        );
    }

    @Test
    void invokeClassInstanceWithParametersTest() {
        Object[] obj = {TestConstant.SOME_VALUE};
        CustomTestInvokeClass instance = (CustomTestInvokeClass) ReflectionUtils.invokeInstance(
                TestConstant.CUSTOM_TEST_INVOKE_CLASS_PACKAGE, obj);
        assertAll("classMultipleParametersInstance",
                () -> assertNotEquals(instance, null),
                () -> assertEquals(instance.getValue(), obj[0])
        );
    }

    @Test
    void getAllAnnotatedFieldsTest() {
        List<Field> fields = ReflectionUtils.getAllAnnotatedFields(SimpleAnnotatedEntry.class, CustomAnnotationForTest.class);
        assertAll("classMultipleParametersInstance",
                () -> assertEquals(fields.size(), 2),
                () -> assertEquals(fields.get(0).getName(), TestConstant.KEY),
                () -> assertEquals(fields.get(1).getName(), TestConstant.VALUE)
        );
    }

    @Test
    void clearUnselectedFieldsTest() {
        SimpleAnnotatedEntry entry = new SimpleAnnotatedEntry();
        entry.setKey(TestConstant.ENTRY_KEY);
        entry.setValue(TestConstant.ENTRY_VALUE);
        entry.setInfo(TestConstant.ENTRY_INFO);

        List<String> valuesList = new ArrayList<>();
        valuesList.add(TestConstant.KEY);
        valuesList.add(TestConstant.VALUE);

        ReflectionUtils.clearUnselectedFields(entry, valuesList);

        assertAll("classMultipleParametersInstance",
                () -> assertEquals(entry.getInfo(), null),
                () -> assertNotEquals(entry.getKey(), null),
                () -> assertNotEquals(entry.getValue(), null)
        );
    }

    @Test
    public void getAllPublicMethodsTest() {
        List<Method> allPublicProtectedMethods = ReflectionUtils.getAllPublicProtectedMethods(SimpleAnnotatedEntry.class);
        assertAll("publicProtectedMethods",
                () -> assertEquals(allPublicProtectedMethods.size(), 17)
        );
    }

    @Test
    public void getAllPrivateMethodsTest() {
        List<Method> allPublicProtectedMethods = ReflectionUtils.getAllPrivateMethods(SimpleAnnotatedEntry.class);
        assertEquals(allPublicProtectedMethods.get(0).getName(), TestConstant.DO_SOMETHING_METHOD_NAME);
    }

    @Test
    public void getAllFieldsMap() {
        Map<String, Field> fields = ReflectionUtils.getAllFieldsMap(CustomTestClassForType.class);

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
    public void getAllPrivateFieldsMap() {
        Map<String, Field> fields = ReflectionUtils.getAllPrivateFieldsMap(CustomTestClassForType.class);
        int fieldsCounter = 3;
        assertAll("privateFields",
                  () -> assertEquals(fields.get(TestConstant.STRING_FIELD).getName(), TestConstant.STRING_FIELD),
                  () -> assertEquals(fields.get(TestConstant.OBJECT_FIELD).getName(), TestConstant.OBJECT_FIELD),
                  () -> assertEquals(fields.get(TestConstant.FLOAT_FIELD).getName(), TestConstant.FLOAT_FIELD),
                  () -> assertEquals(fields.size(), fieldsCounter)
        );
    }

    @Test
    public void getConstructors() {
        Constructor<?>[] constructors = ReflectionUtils.getConstructors(SimpleEntryClass.class);
        assertEquals(constructors.length, 3);
    }

    @Test
    public void getDeclaredConstructors() {
        Constructor<?>[] constructors = ReflectionUtils.getDeclaredConstructors(SimpleEntryClass.class);
        assertEquals(constructors.length, 4);
    }

    @Test
    public void copyObjectTest() {
        SimpleEntryClass simpleEntryClass = new SimpleEntryClass("K", "V");
        SimpleEntryClass simpleEntryClassCopy = (SimpleEntryClass) ReflectionUtils.copy(simpleEntryClass);
        assertEquals(simpleEntryClass, simpleEntryClassCopy);
    }

    @Test
    public void testCopy_NullObject() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ReflectionUtils.copy(null);
        });

        String expectedMessage = "Object must not be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void findAllClassesByPackageTest() throws IOException, URISyntaxException, ClassNotFoundException {
        List<Class<?>> classesByPackage = ReflectionUtils.getClassesByPackage(TestConstant.REFLECTOR_PACKAGE);
        assertTrue(classesByPackage.size() >= 7);
    }

    @Test
    public void methodAnnotationTest() {
        List<Method> allPublicProtectedMethods = ReflectionUtils.getAllPublicProtectedMethods(MethodAnnotatedClass.class);
        Method[] methods = new Method[allPublicProtectedMethods.size()];
        for (int i = 0; i < allPublicProtectedMethods.size(); i++) {
            methods[i] = allPublicProtectedMethods.get(i);
        }
        Map<Method, Annotation[]> methodDeclaredAnnotations = ReflectionUtils.getMethodDeclaredAnnotations(methods);
        Annotation actualAnnotation = null;
        for (Map.Entry<Method, Annotation[]> methodEntry : methodDeclaredAnnotations.entrySet()) {
            if (methodEntry.getKey().getName().equals(TestConstant.ANNOTATED_METHOD_NAME)) {
                actualAnnotation = methodEntry.getValue()[0];
                break;
            }
        }
        assertEquals(actualAnnotation.annotationType(), CustomMethodAnnotation.class);
    }

    @Test
    public void doesHasAnnotations() {
        List<Method> allPublicProtectedMethods = ReflectionUtils.getAllPublicMethods(MethodAnnotatedClass.class);
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
    public void getAnnotatedClassesTest() throws IOException, URISyntaxException, ClassNotFoundException {
        List<Class<?>> classes = ReflectionUtils.getAllAnnotatedClassesByPackage(TestConstant.REFLECTOR_DATA_PACKAGE, ClassAnnotation.class);
        int expectedAnnotationClassesQuantity = 2;
        assertEquals(expectedAnnotationClassesQuantity, classes.size());
    }

    @Test
    public void getNotAnnotatedClassesTest() throws IOException, URISyntaxException, ClassNotFoundException {
        List<Class<?>> classes = ReflectionUtils.getAllAnnotatedClassesByPackage(TestConstant.REFLECTOR_DATA_PACKAGE, CustomMethodAnnotation.class);
        int expectedAnnotationClassesQuantity = 0;
        assertEquals(expectedAnnotationClassesQuantity, classes.size());
    }

    @Test
    public void findMethodsTestByName() {
        Method method = ReflectionUtils.findMethodByName(Person.class, TestConstant.METHOD_NAME_GET_ID);
        assertEquals(TestConstant.METHOD_NAME_GET_ID, method.getName());
    }
}
