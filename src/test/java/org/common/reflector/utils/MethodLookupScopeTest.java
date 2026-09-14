package org.common.reflector.utils;

import org.junit.jupiter.api.Test;
import org.reflector.MethodEnhancementsUtils;
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
import java.util.function.IntPredicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pins the two behaviours that are easy to get wrong when reading the method helpers:
 * the modifier predicates are combined with OR, and the lookup does not walk the hierarchy.
 */
class MethodLookupScopeTest {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface Mark {
    }

    static class Base {
        @Mark
        private void inheritedMarked() {
        }
    }

    static class Child extends Base {
        @Mark
        private void declaredMarked() {
        }

        public static void publicStatic() {
        }

        public void publicInstance() {
        }

        private static void privateStatic() {
        }

        private void privateInstance() {
        }
    }

    private static List<String> names(List<Method> methods) {
        List<String> names = new ArrayList<>();
        for (Method method : methods) {
            names.add(method.getName());
        }
        return names;
    }

    @Test
    void modifierPredicatesAreCombinedWithOr() {
        List<String> found = names(MethodUtils.getAllMethodsWithModifiers(
                Child.class, Arrays.<IntPredicate>asList(Modifier::isPublic, Modifier::isStatic)));

        // OR: anything public, plus anything static. AND would leave publicStatic alone.
        assertTrue(found.contains("publicStatic"));
        assertTrue(found.contains("publicInstance"));
        assertTrue(found.contains("privateStatic"));
        assertFalse(found.contains("privateInstance"));
    }

    @Test
    void modifierLookupIgnoresInheritedMethods() {
        List<String> found = names(MethodUtils.getAllPrivateMethods(Child.class));

        assertTrue(found.contains("declaredMarked"));
        assertFalse(found.contains("inheritedMarked"));
    }

    @Test
    void annotatedMethodLookupIgnoresInheritedMethods() {
        List<String> found = names(MethodEnhancementsUtils.getAnnotatedMethods(Child.class, Mark.class));

        assertEquals(Arrays.asList("declaredMarked"), found);
    }
}
