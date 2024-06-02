package org.common.reflector.utils;

import org.common.reflector.data.CustomTestInvokeClass;
import org.common.reflector.util.TestConstant;
import org.junit.jupiter.api.Test;
import org.reflector.InvokeUtils;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class InvokeUtilsTest {
    @Test
    void invokeClassMethodTest() {
        CustomTestInvokeClass instance = (CustomTestInvokeClass) InvokeUtils.invokeInstance(
                TestConstant.CUSTOM_TEST_INVOKE_CLASS_PACKAGE);
        Object ret1 = InvokeUtils.invokeMethod(instance, TestConstant.SET_VALUE,
                new Class[]{String.class}, new String[]{TestConstant.SIMPLE_VALUE});
        String ret2 = (String) InvokeUtils.invokeMethod(instance, TestConstant.GET_VALUE, null, null);

        assertAll("invokedMethodValues",
                () -> assertEquals(ret1, null),
                () -> assertEquals(ret2, TestConstant.SIMPLE_VALUE)
        );
    }

    @Test
    void invokeClassInstanceTest() {
        CustomTestInvokeClass instance = (CustomTestInvokeClass) InvokeUtils.invokeInstance(
                TestConstant.CUSTOM_TEST_INVOKE_CLASS_PACKAGE);
        assertAll("classInstance",
                () -> assertNotEquals(instance, null)
        );

    }

    @Test
    void invokeSingleClassMethodTest() {
        CustomTestInvokeClass instance = (CustomTestInvokeClass) InvokeUtils.invokeInstance(TestConstant.CUSTOM_TEST_INVOKE_CLASS_PACKAGE);
        Object ret1 = InvokeUtils.invokeSingleMethod(instance, TestConstant.SET_VALUE, String.class, TestConstant.SIMPLE_VALUE);
        String ret2 = (String) InvokeUtils.invokeMethod(instance, TestConstant.GET_VALUE, null, null);

        assertAll("singleClassMethodValue",
                () -> assertEquals(ret1, null),
                () -> assertEquals(ret2, TestConstant.SIMPLE_VALUE)
        );
    }

    @Test
    void invokeClassInstanceWithParametersTest() {
        Object[] obj = {TestConstant.SOME_VALUE};
        CustomTestInvokeClass instance = (CustomTestInvokeClass) InvokeUtils.invokeInstance(
                TestConstant.CUSTOM_TEST_INVOKE_CLASS_PACKAGE, obj);
        assertAll("classMultipleParametersInstance",
                () -> assertNotEquals(instance, null),
                () -> assertEquals(instance.getValue(), obj[0])
        );
    }
}
