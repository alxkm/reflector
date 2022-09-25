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
import org.reflector.ReflectionUtil;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReflectionUtilTest {

    @Test
    public void getAllClassNamesTest() {
        Object obj = new CustomTestInvokeClass(TestConstant.SIMPLE_CLASS_SIMPLE_VALUE);
        assertAll("classNames",
                  () ->  assertEquals(TestConstant.CUSTOM_TEST_INVOKE_CLASS, ReflectionUtil.getClassSimpleName(obj)),
                  () ->  assertEquals("org.common.reflector.data.CustomTestInvokeClass", ReflectionUtil.getClassFullName(obj)),
                  () ->  assertEquals("org.common.reflector.data.CustomTestInvokeClass", ReflectionUtil.getClassCanonicalName(obj))
        );
    }

    @Test
    public void getPackageNameTest() {
        Object obj = new CustomTestInvokeClass(TestConstant.SIMPLE_CLASS_SIMPLE_VALUE);
        assertEquals("org.common.reflector.data", ReflectionUtil.getPackage(obj));
    }

    @Test
    public void getSuperClassNameTest() {
        CustomTestInvokeClass obj = new CustomTestInvokeClass(TestConstant.SIMPLE_CLASS_SIMPLE_VALUE);
        assertEquals(TestConstant.JAVA_LANG_OBJECT, ReflectionUtil.getSuperClassName(obj));
    }

    @Test
    public void getSuperClassTest() {
        CustomTestInvokeClass obj = new CustomTestInvokeClass(TestConstant.SIMPLE_CLASS_SIMPLE_VALUE);
        assertEquals(Object.class, ReflectionUtil.getSuperClass(obj));
    }

    @Test
    public void getAllClassNamesByClassTest() {
        assertAll("classNames",
                  () ->  assertEquals(TestConstant.CUSTOM_TEST_INVOKE_CLASS, ReflectionUtil.getClassSimpleNameByClass(CustomTestInvokeClass.class)),
                  () ->  assertEquals(TestConstant.CUSTOM_TEST_INVOKE_CLASS_PACKAGE, ReflectionUtil.getClassFullNameByClass(CustomTestInvokeClass.class)),
                  () ->  assertEquals(TestConstant.CUSTOM_TEST_INVOKE_CLASS_PACKAGE, ReflectionUtil.getClassCanonicalNameByClass(CustomTestInvokeClass.class))
        );
    }

    @Test
    public void getPackageNameByClassTest() {
        assertEquals(TestConstant.ORG_COMMON_REFLECTOR_DATA, ReflectionUtil.getPackageByClass(CustomTestInvokeClass.class));
    }

    @Test
    public void getSuperClassNameByClassTest() {
        assertEquals(TestConstant.JAVA_LANG_OBJECT, ReflectionUtil.getSuperClassNameByClass(CustomTestInvokeClass.class));
    }

    @Test
    public void getSuperClassFoClassTest() {
        assertEquals(Object.class, ReflectionUtil.getSuperClass(CustomTestInvokeClass.class));
    }

    @Test
    void getAllPrivateFieldsTest() {

        List<Field> fields = ReflectionUtil.getAllPrivateFields(CustomTestClassForType.class);

        assertAll("privateFields",
                () -> assertEquals(fields.get(0).getName(), TestConstant.STRING_FIELD),
                () -> assertEquals(fields.get(1).getName(), TestConstant.OBJECT_FIELD),
                () -> assertEquals(fields.get(2).getName(), TestConstant.FLOAT_FIELD),
                () -> assertEquals(fields.size(), 3)
        );
    }

    @Test
    void invokeClassMethodTest() {
        CustomTestInvokeClass instance = (CustomTestInvokeClass) ReflectionUtil.invokeInstance(
                TestConstant.CUSTOM_TEST_INVOKE_CLASS_PACKAGE);
        Object ret1 = ReflectionUtil.invokeMethod(instance, TestConstant.SET_VALUE,
                                                  new Class[]{String.class}, new String[]{TestConstant.SIMPLE_VALUE});
        String ret2 = (String) ReflectionUtil.invokeMethod(instance, TestConstant.GET_VALUE, null, null);

        assertAll("invokedMethodValues",
                () -> assertEquals(ret1, null),
                () -> assertEquals(ret2, TestConstant.SIMPLE_VALUE)
        );
    }

    @Test
    void invokeClassInstanceTest() {
        CustomTestInvokeClass instance = (CustomTestInvokeClass) ReflectionUtil.invokeInstance(
                TestConstant.CUSTOM_TEST_INVOKE_CLASS_PACKAGE);
        assertAll("classInstance",
                () -> assertNotEquals(instance, null)
        );

    }

    @Test
    void readSingleField() {
        CustomTestClassForType customClass = new CustomTestClassForType();

        Object oneConstant = ReflectionUtil.readField(customClass, TestConstant.ONE_CONSTANT);
        System.out.println(oneConstant);

        assertAll("readSingleField",
                () -> assertEquals(oneConstant, 1),
                () -> assertEquals(oneConstant.getClass(), Integer.class)
        );
    }

    @Test
    void invokeSingleClassMethodTest() {
        CustomTestInvokeClass instance = (CustomTestInvokeClass) ReflectionUtil.invokeInstance(TestConstant.CUSTOM_TEST_INVOKE_CLASS_PACKAGE);
        Object ret1 = ReflectionUtil.invokeSingleMethod(instance, TestConstant.SET_VALUE, String.class, TestConstant.SIMPLE_VALUE);
        String ret2 = (String) ReflectionUtil.invokeMethod(instance, TestConstant.GET_VALUE, null, null);

        assertAll("singleClassMethodValue",
                () -> assertEquals(ret1, null),
                () -> assertEquals(ret2, TestConstant.SIMPLE_VALUE)
        );
    }

    @Test
    void invokeClassInstanceWithParametersTest() {
        Object[] obj = {TestConstant.SOME_VALUE};
        CustomTestInvokeClass instance = (CustomTestInvokeClass) ReflectionUtil.invokeInstance(
                TestConstant.CUSTOM_TEST_INVOKE_CLASS_PACKAGE, obj);
        assertAll("classMultipleParametersInstance",
                () -> assertNotEquals(instance, null),
                () -> assertEquals(instance.getValue(), obj[0])
        );
    }

    @Test
    void getAllAnnotatedFieldsTest() {
        List<Field> fields = ReflectionUtil.getAllAnnotatedFields(SimpleAnnotatedEntry.class, CustomAnnotationForTest.class);
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

        ReflectionUtil.clearUnselectedFields(entry, valuesList);

        assertAll("classMultipleParametersInstance",
                () -> assertEquals(entry.getInfo(), null),
                () -> assertNotEquals(entry.getKey(), null),
                () -> assertNotEquals(entry.getValue(), null)
        );
    }

    @Test
    public void getAllPublicMethodsTest() {
        List<Method> allPublicProtectedMethods = ReflectionUtil.getAllPublicProtectedMethods(SimpleAnnotatedEntry.class);
        assertAll("publicProtectedMethods",
                () -> assertEquals(allPublicProtectedMethods.size(), 17)
        );
    }

    @Test
    public void getAllPrivateMethodsTest() {
        List<Method> allPublicProtectedMethods = ReflectionUtil.getAllPrivateMethods(SimpleAnnotatedEntry.class);
        assertEquals(allPublicProtectedMethods.get(0).getName(), TestConstant.DO_SOMETHING_METHOD_NAME);
    }

    @Test
    public void getAllFieldsMap() {
        Map<String, Field> fields = ReflectionUtil.getAllFieldsMap(CustomTestClassForType.class);

        assertAll("allFields",
                  () -> assertEquals(fields.get("stringField").getName(), "stringField"),
                  () -> assertEquals(fields.get("objectField").getName(), "objectField"),
                  () -> assertEquals(fields.get("floatField").getName(), "floatField"),
                  () -> assertEquals(fields.get("notPrivateField").getName(), "notPrivateField"),
                  () -> assertEquals(fields.get("oneConstant").getName(), "oneConstant"),
                  () -> assertEquals(fields.size(), 5)
        );
    }

    @Test
    public void getAllPrivateFieldsMap() {
        Map<String, Field> fields = ReflectionUtil.getAllPrivateFieldsMap(CustomTestClassForType.class);

        assertAll("privateFields",
                  () -> assertEquals(fields.get("stringField").getName(), "stringField"),
                  () -> assertEquals(fields.get("objectField").getName(), "objectField"),
                  () -> assertEquals(fields.get("floatField").getName(), "floatField"),
                  () -> assertEquals(fields.size(), 3)
        );
    }

    @Test
    public void getConstructors() {
        Constructor<?>[] constructors = ReflectionUtil.getConstructors(SimpleEntryClass.class);
        assertEquals(constructors.length, 3);
    }

    @Test
    public void getDeclaredConstructors() {
        Constructor<?>[] constructors = ReflectionUtil.getDeclaredConstructors(SimpleEntryClass.class);
        assertEquals(constructors.length, 4);
    }

    @Test
    public void copyObjectTest() {
        SimpleEntryClass simpleEntryClass = new SimpleEntryClass("K", "V");
        SimpleEntryClass simpleEntryClassCopy = (SimpleEntryClass) ReflectionUtil.copy(simpleEntryClass);
        assertEquals(simpleEntryClass, simpleEntryClassCopy);
    }

    @Test
    public void findAllClassesByPackageTest() throws IOException, URISyntaxException, ClassNotFoundException {
        List<Class<?>> classesByPackage = ReflectionUtil.getClassesByPackage(TestConstant.REFLECTOR_PACKAGE);
        assertTrue(classesByPackage.size() >= 7);
    }

    @Test
    public void methodAnnotationTest() {
        List<Method> allPublicProtectedMethods = ReflectionUtil.getAllPublicProtectedMethods(MethodAnnotatedClass.class);
        Method[] methods = new Method[allPublicProtectedMethods.size()];
        for (int i = 0; i < allPublicProtectedMethods.size(); i++) {
            methods[i] = allPublicProtectedMethods.get(i);
        }
        Map<Method, Annotation[]> methodDeclaredAnnotations = ReflectionUtil.getMethodDeclaredAnnotations(methods);
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
        List<Method> allPublicProtectedMethods = ReflectionUtil.getAllPublicMethods(MethodAnnotatedClass.class);
        List<String> expected = new ArrayList<>(Arrays.asList("getClass",
                                                              "annotatedMethod",
                                                              "wait",
                                                              "hashCode",
                                                              "equals",
                                                              "notifyAll",
                                                              "toString",
                                                              "notify"));
        List<String> actual = allPublicProtectedMethods.stream().map(Method::getName).collect(Collectors.toList());
        assertFalse(expected.size() == actual.size() && expected.containsAll(actual) && actual.containsAll(expected));
    }

    @Test
    public void getAnnotatedClassesTest() throws IOException, URISyntaxException, ClassNotFoundException {
        List<Class<?>> classes = ReflectionUtil.getAllAnnotatedClassesByPackage(TestConstant.REFLECTOR_DATA_PACKAGE, ClassAnnotation.class);
        int expectedAnnotationClassesQuantity = 2;
        assertEquals(expectedAnnotationClassesQuantity, classes.size());
    }

    @Test
    public void getNotAnnotatedClassesTest() throws IOException, URISyntaxException, ClassNotFoundException {
        List<Class<?>> classes = ReflectionUtil.getAllAnnotatedClassesByPackage(TestConstant.REFLECTOR_DATA_PACKAGE, CustomMethodAnnotation.class);
        int expectedAnnotationClassesQuantity = 0;
        assertEquals(expectedAnnotationClassesQuantity, classes.size());
    }
}
