package org.common.reflector;

import org.common.reflector.data.CustomAnnotationForTest;
import org.common.reflector.data.CustomTestClassForType;
import org.common.reflector.data.CustomTestInvokeClass;
import org.common.reflector.data.SimpleAnnotatedEntry;
import org.junit.jupiter.api.Test;
import org.reflector.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ReflectionUtilsTest {

    @Test
    public void getAllClassNamesTest() {
        Object obj = new CustomTestInvokeClass("SimpleClassSimpleValue");
        assertAll("classNames",
                  () ->  assertEquals("CustomTestInvokeClass", ReflectionUtils.getClassSimpleName(obj)),
                  () ->  assertEquals("org.common.reflector.data.CustomTestInvokeClass", ReflectionUtils.getClassFullName(obj)),
                  () ->  assertEquals("org.common.reflector.data.CustomTestInvokeClass", ReflectionUtils.getClassCanonicalName(obj))
        );
    }

    @Test
    public void getPackageNameTest() {
        Object obj = new CustomTestInvokeClass("SimpleClassSimpleValue");
        assertEquals("org.common.reflector.data", ReflectionUtils.getPackage(obj));
    }

    @Test
    public void getSuperClassNameTest() {
        CustomTestInvokeClass obj = new CustomTestInvokeClass("SimpleClassSimpleValue");
        assertEquals("java.lang.Object", ReflectionUtils.getSuperClassName(obj));
    }

    @Test
    public void getSuperClassTest() {
        CustomTestInvokeClass obj = new CustomTestInvokeClass("SimpleClassSimpleValue");
        assertEquals(Object.class, ReflectionUtils.getSuperClass(obj));
    }

    @Test
    public void getAllClassNamesByClassTest() {
        assertAll("classNames",
                  () ->  assertEquals("CustomTestInvokeClass", ReflectionUtils.getClassSimpleNameByClass(CustomTestInvokeClass.class)),
                  () ->  assertEquals("org.common.reflector.data.CustomTestInvokeClass", ReflectionUtils.getClassFullNameByClass(CustomTestInvokeClass.class)),
                  () ->  assertEquals("org.common.reflector.data.CustomTestInvokeClass", ReflectionUtils.getClassCanonicalNameByClass(CustomTestInvokeClass.class))
        );
    }

    @Test
    public void getPackageNameByClassTest() {
        assertEquals("org.common.reflector.data", ReflectionUtils.getPackageByClass(CustomTestInvokeClass.class));
    }

    @Test
    public void getSuperClassNameByClassTest() {
        assertEquals("java.lang.Object", ReflectionUtils.getSuperClassNameByClass(CustomTestInvokeClass.class));
    }

    @Test
    public void getSuperClassFoClassTest() {
        assertEquals(Object.class, ReflectionUtils.getSuperClass(CustomTestInvokeClass.class));
    }

    @Test
    void getAllPrivateFieldsTest() {

        List<Field> fields = ReflectionUtils.getAllPrivateFields(CustomTestClassForType.class);

        assertAll("privateFields",
                () -> assertEquals(fields.get(0).getName(), "stringField"),
                () -> assertEquals(fields.get(1).getName(), "objectField"),
                () -> assertEquals(fields.get(2).getName(), "floatField"),
                () -> assertEquals(fields.size(), 3)
        );
    }

    @Test
    void invokeClassMethodTest() {
        String classFullNameWithPackage = "org.common.reflector.data.CustomTestInvokeClass";

        String classValue = "SimpleValue";
        String setMethodName = "setValue";
        String getMethodName = "getValue";

        CustomTestInvokeClass instance = (CustomTestInvokeClass) ReflectionUtils.invokeInstance(classFullNameWithPackage);
        Object ret1 = ReflectionUtils.invokeMethod(instance, setMethodName,
                new Class[]{String.class}, new String[]{classValue});
        String ret2 = (String) ReflectionUtils.invokeMethod(instance, getMethodName, null, null);

        assertAll("invokedMethodValues",
                () -> assertEquals(ret1, null),
                () -> assertEquals(ret2, classValue)
        );
    }

    @Test
    void invokeClassInstanceTest() {
        String classFullNameWithPackage = "org.common.reflector.data.CustomTestInvokeClass";
        CustomTestInvokeClass instance = (CustomTestInvokeClass) ReflectionUtils.invokeInstance(classFullNameWithPackage);
        assertAll("classInstance",
                () -> assertNotEquals(instance, null)
        );

    }

    @Test
    void readSingleField() {
        CustomTestClassForType customClass = new CustomTestClassForType();

        Object oneConstant = ReflectionUtils.readField(customClass, "oneConstant");
        System.out.println(oneConstant);

        assertAll("readedSingleField",
                () -> assertEquals(oneConstant, 1),
                () -> assertEquals(oneConstant.getClass(), Integer.class)
        );
    }

    @Test
    void invokeSingleClassMethodTest() {
        String classFullNameWithPackage = "org.common.reflector.data.CustomTestInvokeClass";

        String classValue = "SimpleValue";
        String setMethodName = "setValue";
        String getMethodName = "getValue";

        CustomTestInvokeClass instance = (CustomTestInvokeClass) ReflectionUtils.invokeInstance(classFullNameWithPackage);
        Object ret1 = ReflectionUtils.invokeSingleMethod(instance, setMethodName, String.class, classValue);
        String ret2 = (String) ReflectionUtils.invokeMethod(instance, getMethodName, null, null);

        assertAll("singleClassMethodValue",
                () -> assertEquals(ret1, null),
                () -> assertEquals(ret2, classValue)
        );
    }

    @Test
    void invokeClassInstanceWithParametersTest() {
        Object[] obj = {"SomeValue"};

        String classFullNameWithPackage = "org.common.reflector.data.CustomTestInvokeClass";
        CustomTestInvokeClass instance = (CustomTestInvokeClass) ReflectionUtils.invokeInstance(
                classFullNameWithPackage, obj);
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
                () -> assertEquals(fields.get(0).getName(), "key"),
                () -> assertEquals(fields.get(1).getName(), "value")
        );
    }

    @Test
    void clearUnselectedFieldsTest() {
        SimpleAnnotatedEntry entry = new SimpleAnnotatedEntry();
        entry.setKey("entryKey");
        entry.setValue("entryValue");
        entry.setInfo("entryInfo");

        List<String> valuesList = new ArrayList<>();
        valuesList.add("key");
        valuesList.add("value");

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
        assertAll("privateMethods",
                () -> assertEquals(allPublicProtectedMethods.size(), 1)
        );
    }
}
