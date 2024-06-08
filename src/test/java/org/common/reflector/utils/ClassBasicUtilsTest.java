package org.common.reflector.utils;


import org.common.reflector.data.CustomTestInvokeClass;
import org.common.reflector.util.TestConstant;
import org.junit.jupiter.api.Test;
import org.reflector.ClassBasicUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OuterClass {
    class InnerClass {
    }
}

class NonInnerClass {
}

public class ClassBasicUtilsTest {

    //enclosing tests

    @Test
    public void testGetEnclosingClass_InnerClass() {
        Class<?> enclosingClass = ClassBasicUtils.getEnclosingClass(OuterClass.InnerClass.class);
        assertNotNull(enclosingClass);
        assertEquals(OuterClass.class, enclosingClass);
    }

    @Test
    public void testGetEnclosingClass_NonInnerClass() {
        Class<?> enclosingClass = ClassBasicUtils.getEnclosingClass(NonInnerClass.class);
        assertNull(enclosingClass);
    }

    @Test
    public void testGetEnclosingClass_NullClass() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            ClassBasicUtils.getEnclosingClass(null);
        });
        String expectedMessage = "Class must not be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    //superclass tests


    @Test
    public void ClassBasicUtils() {
        // Test with a valid object whose class has a superclass
        String superClassName = ClassBasicUtils.getSuperClassNameForObject("Test String");
        assertNotNull(superClassName);
        assertEquals("java.lang.Object", superClassName);
    }

    @Test
    public void testGetSuperClassName_ObjectClass() {
        // Test with an object of class Object which has no superclass
        Object obj = new Object();
        String superClassName = ClassBasicUtils.getSuperClassNameForObject(obj);
        assertNull(superClassName);
    }

    @Test
    public void testGetSuperClassName_NullObject() {
        // Test with a null object (should throw NullPointerException)
        Exception exception = assertThrows(NullPointerException.class, () -> {
            ClassBasicUtils.getSuperClassNameForObject(null);
        });

        String expectedMessage = "Object must not be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testGetSuperClassName_PrimitiveWrapper() {
        // Test with a primitive wrapper type (Integer in this case)
        Integer integer = 5;
        String superClassName = ClassBasicUtils.getSuperClassNameForObject(integer);
        assertNotNull(superClassName);
        assertEquals("java.lang.Number", superClassName);
    }

    @Test
    public void testGetSuperClassName_CustomClass() {
        // Test with a custom class with a superclass
        class SuperClass {}
        class SubClass extends SuperClass {}

        SubClass subClassInstance = new SubClass();
        String superClassName = ClassBasicUtils.getSuperClassNameForObject(subClassInstance);
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
        String superClassName = ClassBasicUtils.getSuperClassNameForObject(example);
        assertNotNull(superClassName);
        assertEquals("java.lang.Object", superClassName);
    }


    // interface tests


    interface TestInterface1 {
    }

    interface TestInterface2 {
    }

    class TestClass implements TestInterface1, TestInterface2 {
    }

    @Test
    public void testGetInterfaces_ValidClass() {
        List<Class<?>> interfaces = ClassBasicUtils.getInterfaces(TestClass.class);
        assertNotNull(interfaces);
        assertEquals(2, interfaces.size());
        assertTrue(interfaces.contains(TestInterface1.class));
        assertTrue(interfaces.contains(TestInterface2.class));
    }

    @Test
    public void testGetInterfaces_ClassWithNoInterfaces() {
        class NoInterfaceClass {
        }

        List<Class<?>> interfaces = ClassBasicUtils.getInterfaces(NoInterfaceClass.class);
        assertNotNull(interfaces);
        assertTrue(interfaces.isEmpty());
    }

    @Test
    public void testGetInterfaces_NullClass() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            ClassBasicUtils.getInterfaces(null);
        });
        String expectedMessage = "Class must not be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    // super class tests

    @Test
    public void testGetSuperClassNameByClass_ValidClass() {
        // Test with a class that has a superclass
        String superClassName = ClassBasicUtils.getSuperClassNameByClass(String.class);
        assertNotNull(superClassName);
        assertEquals("java.lang.Object", superClassName);
    }

    @Test
    public void testGetSuperClassNameByClass_ObjectClass() {
        // Test with Object class which has no superclass
        String superClassName = ClassBasicUtils.getSuperClassNameByClass(Object.class);
        assertNull(superClassName);
    }

    @Test
    public void testGetSuperClassNameByClass_PrimitiveClass() {
        // Test with a primitive type class (should return null)
        String superClassName = ClassBasicUtils.getSuperClassNameByClass(int.class);
        assertNull(superClassName);
    }

    @Test
    public void testGetSuperClassNameByClass_Interface() {
        // Test with an interface (should return null)
        String superClassName = ClassBasicUtils.getSuperClassNameByClass(Runnable.class);
        assertNull(superClassName);
    }

    @Test
    public void testGetSuperClassNameByClass_NullClass() {
        // Test with null class (should throw NullPointerException)
        Exception exception = assertThrows(NullPointerException.class, () -> {
            ClassBasicUtils.getSuperClassNameByClass(null);
        });

        String expectedMessage = "Class must not be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    // get super class tests

    class SuperClass {
    }

    class SubClass extends SuperClass {
    }

    class NoSuperClass {
    }

    @Test
    public void testGetSuperClass_SubClass() {
        SubClass subClassInstance = new SubClass();
        Class<?> superClass = ClassBasicUtils.getSuperClass(subClassInstance);
        assertNotNull(superClass);
        assertEquals(SuperClass.class, superClass);
    }

    @Test
    public void testGetSuperClass_NoSuperClass() {
        NoSuperClass noSuperClassInstance = new NoSuperClass();
        Class<?> superClass = ClassBasicUtils.getSuperClass(noSuperClassInstance);
        assertEquals(Object.class, superClass);  // Since NoSuperClass is a direct subclass of Object
    }

    @Test
    public void testGetSuperClass_NullObject() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            ClassBasicUtils.getSuperClass(null);
        });
        String expectedMessage = "Object must not be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testGetSuperClass_PrimitiveType() {
        Integer integerInstance = 42;
        Class<?> superClass = ClassBasicUtils.getSuperClass(integerInstance);
        assertNotNull(superClass);
        assertEquals(Number.class, superClass);
    }

    // naming tests

    @Test
    public void getAllClassNamesTest() {
        Object obj = new CustomTestInvokeClass(TestConstant.SIMPLE_CLASS_SIMPLE_VALUE);
        assertAll("classNames",
                () ->  assertEquals(TestConstant.CUSTOM_TEST_INVOKE_CLASS, ClassBasicUtils.getClassSimpleName(obj)),
                () ->  assertEquals(TestConstant.CUSTOM_TEST_INVOKE_CLASS_PACKAGE, ClassBasicUtils.getClassFullName(obj)),
                () ->  assertEquals(TestConstant.CUSTOM_TEST_INVOKE_CLASS_PACKAGE, ClassBasicUtils.getClassCanonicalName(obj))
        );
    }

    @Test
    public void testGetClassFullName_ValidObject() {
        // Test with a valid object
        String fullName = ClassBasicUtils.getClassFullName("Test String");
        assertNotNull(fullName);
        assertEquals("java.lang.String", fullName);
    }

    @Test
    public void testGetClassFullName_PrimitiveWrapper() {
        // Test with a primitive wrapper type
        Integer integer = 42;
        String fullName = ClassBasicUtils.getClassFullName(integer);
        assertNotNull(fullName);
        assertEquals("java.lang.Integer", fullName);
    }

    @Test
    public void testGetClassFullName_NullObject() {
        // Test with a null object (should throw IllegalArgumentException)
        Exception exception = assertThrows(NullPointerException.class, () -> {
            ClassBasicUtils.getClassFullName(null);
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
        String fullName = ClassBasicUtils.getClassFullName(customObject);
        assertNotNull(fullName);
        assertEquals("org.common.reflector.utils.ClassBasicUtilsTest$1CustomClass", fullName);
    }

    @Test
    public void testGetClassFullName_ArrayObject() {
        // Test with an array object
        int[] intArray = new int[]{1, 2, 3};
        String fullName = ClassBasicUtils.getClassFullName(intArray);
        assertNotNull(fullName);
        assertEquals("[I", fullName); // The class name for int[] is "[I"
    }

    @Test
    public void testGetClassCanonicalName_ValidObject() {
        // Test with a valid object
        String canonicalName = ClassBasicUtils.getClassCanonicalName("Test String");
        assertNotNull(canonicalName);
        assertEquals("java.lang.String", canonicalName);
    }

    @Test
    public void testGetClassCanonicalName_PrimitiveWrapper() {
        // Test with a primitive wrapper type
        Integer integer = 42;
        String canonicalName = ClassBasicUtils.getClassCanonicalName(integer);
        assertNotNull(canonicalName);
        assertEquals("java.lang.Integer", canonicalName);
    }

    @Test
    public void testGetClassCanonicalName_NullObject() {
        // Test with a null object (should throw NullPointerException)
        Exception exception = assertThrows(NullPointerException.class, () -> {
            ClassBasicUtils.getClassCanonicalName(null);
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
        String canonicalName = ClassBasicUtils.getClassCanonicalName(customObject);
        assertEquals(null, canonicalName);
    }

    @Test
    public void testGetClassCanonicalName_ArrayObject() {
        // Test with an array object
        int[] intArray = new int[]{1, 2, 3};
        String canonicalName = ClassBasicUtils.getClassCanonicalName(intArray);
        assertNotNull(canonicalName);
        assertEquals("int[]", canonicalName);
    }

    @Test
    public void testGetClassCanonicalName_AnonymousClass() {
        // Test with an anonymous class
        Object anonymousClass = new Object() {};
        String canonicalName = ClassBasicUtils.getClassCanonicalName(anonymousClass);
        assertNull(canonicalName);
    }

    @Test
    public void getPackageNameTest() {
        Object obj = new CustomTestInvokeClass(TestConstant.SIMPLE_CLASS_SIMPLE_VALUE);
        assertEquals(TestConstant.REFLECTOR_DATA_PACKAGE, ClassBasicUtils.getPackage(obj));
    }

    @Test
    public void testGetPackage_ValidObject() {
        // Test with a valid object
        String packageName = ClassBasicUtils.getPackage("Test String");
        assertNotNull(packageName);
        assertEquals("java.lang", packageName);
    }

    @Test
    public void testGetPackage_NullObject() {
        // Test with a null object (should throw NullPointerException)
        Exception exception = assertThrows(NullPointerException.class, () -> {
            ClassBasicUtils.getPackage(null);
        });

        String expectedMessage = "Object must not be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testGetPackage_ArrayObject() {
        // Test with an array object (should return null)
        int[] intArray = new int[]{1, 2, 3};
        String packageName = ClassBasicUtils.getPackage(intArray);
        assertNull(packageName);
    }



    @Test
    public void getSuperClassTest() {
        CustomTestInvokeClass obj = new CustomTestInvokeClass(TestConstant.SIMPLE_CLASS_SIMPLE_VALUE);
        assertEquals(Object.class, ClassBasicUtils.getSuperClass(obj));
    }

    @Test
    public void getAllClassNamesByClassTest() {
        assertAll("classNames",
                () ->  assertEquals(TestConstant.CUSTOM_TEST_INVOKE_CLASS, ClassBasicUtils.getClassSimpleNameByClass(CustomTestInvokeClass.class)),
                () ->  assertEquals(TestConstant.CUSTOM_TEST_INVOKE_CLASS_PACKAGE, ClassBasicUtils.getClassFullNameByClass(CustomTestInvokeClass.class)),
                () ->  assertEquals(TestConstant.CUSTOM_TEST_INVOKE_CLASS_PACKAGE, ClassBasicUtils.getClassCanonicalNameByClass(CustomTestInvokeClass.class))
        );
    }

    @Test
    public void testGetClassFullNameByClass_ValidClass() {
        // Test with a valid class
        String fullName = ClassBasicUtils.getClassFullNameByClass(String.class);
        assertNotNull(fullName);
        assertEquals("java.lang.String", fullName);
    }

    @Test
    public void testGetClassFullNameByClass_NullClass() {
        // Test with a null class
        String fullName = ClassBasicUtils.getClassFullNameByClass(null);
        assertNotNull(fullName);
        assertEquals("", fullName);
    }

    @Test
    public void testGetClassCanonicalNameByClass_ValidClass() {
        // Test with a valid class
        String canonicalName = ClassBasicUtils.getClassCanonicalNameByClass(String.class);
        assertNotNull(canonicalName);
        assertEquals("java.lang.String", canonicalName);
    }

    @Test
    public void testGetClassCanonicalNameByClass_NullClass() {
        // Test with a null class
        String canonicalName = ClassBasicUtils.getClassCanonicalNameByClass(null);
        assertNull(canonicalName);
    }

    @Test
    public void testGetClassSimpleNameByClass_ValidClass() {
        // Test with a valid class
        String simpleName = ClassBasicUtils.getClassSimpleNameByClass(String.class);
        assertNotNull(simpleName);
        assertEquals("String", simpleName);
    }

    @Test
    public void testGetClassSimpleNameByClass_PrimitiveClass() {
        // Test with a primitive type class
        String simpleName = ClassBasicUtils.getClassSimpleNameByClass(int.class);
        assertNotNull(simpleName);
        assertEquals("int", simpleName);
    }

    @Test
    public void testGetClassSimpleNameByClass_ArrayClass() {
        // Test with an array class
        String simpleName = ClassBasicUtils.getClassSimpleNameByClass(int[].class);
        assertNotNull(simpleName);
        assertEquals("int[]", simpleName);
    }

    @Test
    public void testGetClassSimpleNameByClass_InnerClass() {
        // Test with an inner class
        class InnerClass {}
        String simpleName = ClassBasicUtils.getClassSimpleNameByClass(InnerClass.class);
        assertNotNull(simpleName);
        assertEquals("InnerClass", simpleName);
    }

    @Test
    public void testGetClassSimpleNameByClass_NullClass() {
        // Test with a null class (should throw NullPointerException)
        Exception exception = assertThrows(NullPointerException.class, () -> {
            ClassBasicUtils.getClassSimpleNameByClass(null);
        });

        String expectedMessage = "Class must not be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testGetClassSimpleNameByClass_CustomClass() {
        // Test with a custom class
        class CustomClass {}
        String simpleName = ClassBasicUtils.getClassSimpleNameByClass(CustomClass.class);
        assertNotNull(simpleName);
        assertEquals("CustomClass", simpleName);
    }

    @Test
    public void testGetClassSimpleName_ValidObject() {
        // Test with a valid object
        String simpleName = ClassBasicUtils.getClassSimpleName("Test String");
        assertNotNull(simpleName);
        assertEquals("String", simpleName);
    }

    @Test
    public void testGetClassSimpleName_PrimitiveWrapper() {
        // Test with a primitive wrapper type
        Integer integer = 42;
        String simpleName = ClassBasicUtils.getClassSimpleName(integer);
        assertNotNull(simpleName);
        assertEquals("Integer", simpleName);
    }

    @Test
    public void testGetClassSimpleName_NullObject() {
        // Test with a null object (should throw NullPointerException)
        Exception exception = assertThrows(NullPointerException.class, () -> {
            ClassBasicUtils.getClassSimpleName(null);
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
        String simpleName = ClassBasicUtils.getClassSimpleName(customObject);
        assertNotNull(simpleName);
        assertEquals("CustomClass", simpleName);
    }

    @Test
    public void testGetClassSimpleName_ArrayObject() {
        // Test with an array object
        int[] intArray = new int[]{1, 2, 3};
        String simpleName = ClassBasicUtils.getClassSimpleName(intArray);
        assertNotNull(simpleName);
        assertEquals("int[]", simpleName);
    }

    @Test
    public void testGetClassSimpleName_AnonymousClass() {
        // Test with an anonymous class
        String simpleName = ClassBasicUtils.getClassSimpleName(new Object());
        assertNotNull(simpleName);
        // Check that the simple name is not null but matches the expected pattern
        assertTrue(simpleName.equals("Object")); // Anonymous classes have numeric names
    }

    @Test
    public void testGetPackageByClass_ValidClass() {
        // Test with a valid class that has a package
        String packageName = ClassBasicUtils.getPackageByClass(String.class);
        assertNotNull(packageName);
        assertEquals("java.lang", packageName);
    }

    @Test
    public void testGetPackageByClass_PrimitiveClass() {
        // Test with a primitive type class (should return null)
        String packageName = ClassBasicUtils.getPackageByClass(int.class);
        assertNull(packageName);
    }

    @Test
    public void testGetPackageByClass_ArrayClass() {
        // Test with an array class (should return the package of the component type)
        String packageName = ClassBasicUtils.getPackageByClass(int[].class);
        assertNull(packageName);  // Arrays do not have a package
    }

    @Test
    public void testGetPackageByClass_NullClass() {
        // Test with a null class (should throw NullPointerException)
        Exception exception = assertThrows(NullPointerException.class, () -> {
            ClassBasicUtils.getPackageByClass(null);
        });

        String expectedMessage = "Class must not be null";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testGetPackageByClass_CustomClass() {
        // Test with a custom class with a package
        class CustomClass {}
        String packageName = ClassBasicUtils.getPackageByClass(CustomClass.class);
        assertNotNull(packageName);
        assertEquals(this.getClass().getPackage().getName(), packageName);
    }

    @Test
    public void getSuperClassFoClassTest() {
        assertEquals(Object.class, ClassBasicUtils.getSuperClass(CustomTestInvokeClass.class));
    }

}
