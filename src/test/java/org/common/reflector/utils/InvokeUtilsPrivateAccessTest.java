package org.common.reflector.utils;

import org.junit.jupiter.api.Test;
import org.reflector.InvokeUtils;
import org.reflector.exception.InstanceInvocationException;
import org.reflector.exception.MethodInvokeException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InvokeUtilsPrivateAccessTest {

    static class Greeter {
        private final String name;

        Greeter(String name) {
            this.name = name;
        }

        private String greet(String greeting) {
            return greeting + ", " + name;
        }

        private String shout() {
            return name.toUpperCase();
        }

        private void explode() {
            throw new IllegalStateException("boom");
        }
    }

    @Test
    void invokeMethodReachesPrivateMethodWithArguments() {
        Greeter greeter = new Greeter("Ada");

        Object result = InvokeUtils.invokeMethod(greeter, "greet",
                new Class<?>[]{String.class}, new Object[]{"Hello"});

        assertEquals("Hello, Ada", result);
    }

    @Test
    void invokeMethodReachesPrivateMethodWithoutArguments() {
        Object result = InvokeUtils.invokeMethod(new Greeter("Ada"), "shout", null, null);

        assertEquals("ADA", result);
    }

    @Test
    void invokeMethodKeepsTheOriginalCause() {
        MethodInvokeException thrown = assertThrows(MethodInvokeException.class,
                () -> InvokeUtils.invokeMethod(new Greeter("Ada"), "explode", null, null));

        assertNotNull(thrown.getCause());
    }

    @Test
    void invokeMethodFailsForUnknownMethod() {
        MethodInvokeException thrown = assertThrows(MethodInvokeException.class,
                () -> InvokeUtils.invokeMethod(new Greeter("Ada"), "noSuchMethod", null, null));

        assertNotNull(thrown.getCause());
    }

    @Test
    void invokeInstanceKeepsTheOriginalCause() {
        InstanceInvocationException thrown = assertThrows(InstanceInvocationException.class,
                () -> InvokeUtils.invokeInstance("com.example.NoSuchClass"));

        assertNotNull(thrown.getCause());
    }
}
