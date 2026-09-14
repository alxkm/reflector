package org.common.reflector.utils;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.reflector.AnnotationUtils;
import org.reflector.ClassBasicUtils;
import org.reflector.ConstructorUtils;
import org.reflector.FieldUtils;
import org.reflector.FieldsExtraUtils;
import org.reflector.GeneralUtils;
import org.reflector.InvokeUtils;
import org.reflector.MethodEnhancementsUtils;
import org.reflector.MethodUtils;
import org.reflector.MiscellaneousUtils;
import org.reflector.ObjectUtils;
import org.reflector.PackageUtils;
import org.reflector.ReflectionUtils;
import org.reflector.SecurityUtils;

import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.IntPredicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * ReflectionUtils is a facade: every method forwards to one of the focused utility classes.
 * The failure mode is a delegate wired to the wrong target or drifting out of sync, which no
 * amount of reading catches. These tests call each facade method and the utility it forwards
 * to with the same arguments, then compare the results.
 *
 * <p>{@link #everyFacadeMethodIsExercised()} fails when a facade method has no case here, so a
 * newly added delegate cannot slip through untested.</p>
 */
@SuppressWarnings("deprecation") // the facade still exposes the deprecated helpers
class ReflectionUtilsFacadeTest {

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
    @interface Marked {
        String value() default "default-value";
    }

    @Marked
    public static class Base {
        private String baseField;
    }

    @Marked
    public static class Sample extends Base {
        private static final String CONSTANT = "constant";

        @Marked
        private String name;
        private int count;

        public Sample() {
        }

        @Marked
        public Sample(String name) {
            this.name = name;
        }

        @Marked
        public String greet(@Marked String greeting) throws IllegalStateException {
            return greeting + ", " + name;
        }

        private void hidden() {
        }

        public static void staticMethod() {
        }

        public void varArgs(String... parts) {
        }

        static class Inner {
        }
    }

    enum SampleEnum {
        ONE
    }

    /** The utility classes the facade forwards to. The legacy class is deliberately excluded. */
    private static final Class<?>[] TARGETS = {
            AnnotationUtils.class, ClassBasicUtils.class, ConstructorUtils.class, FieldUtils.class,
            FieldsExtraUtils.class, GeneralUtils.class, InvokeUtils.class, MethodEnhancementsUtils.class,
            MethodUtils.class, MiscellaneousUtils.class, ObjectUtils.class, PackageUtils.class,
            SecurityUtils.class,
    };

    /** How the result of a facade call is compared with the result of the delegate call. */
    private enum Compare {
        /** Results must be equal. */
        VALUE,
        /** Each call builds a new object, so only the runtime type can match. */
        TYPE,
        /** Nothing to compare, the call only has to succeed on both sides. */
        NONE
    }

    private static final class Case {
        final String name;
        final Class<?>[] parameterTypes;
        final Object[] arguments;
        final Compare compare;

        Case(String name, Class<?>[] parameterTypes, Object[] arguments, Compare compare) {
            this.name = name;
            this.parameterTypes = parameterTypes;
            this.arguments = arguments;
            this.compare = compare;
        }

        String key() {
            return signature(name, parameterTypes);
        }
    }

    private static String signature(String name, Class<?>[] parameterTypes) {
        StringBuilder sb = new StringBuilder(name).append('(');
        for (int i = 0; i < parameterTypes.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(parameterTypes[i].getSimpleName());
        }
        return sb.append(')').toString();
    }

    private static Case of(String name, Class<?>[] types, Object[] arguments) {
        return new Case(name, types, arguments, Compare.VALUE);
    }

    private static Case of(String name, Class<?>[] types, Object[] arguments, Compare compare) {
        return new Case(name, types, arguments, compare);
    }

    private static Class<?>[] types(Class<?>... types) {
        return types;
    }

    private static Object[] args(Object... arguments) {
        return arguments;
    }

    private static List<Case> cases() throws Exception {
        Field field = Sample.class.getDeclaredField("name");
        Method greet = Sample.class.getDeclaredMethod("greet", String.class);
        Method varArgs = Sample.class.getDeclaredMethod("varArgs", String[].class);
        Method annotationValue = Marked.class.getDeclaredMethod("value");
        Constructor<?> constructor = Sample.class.getDeclaredConstructor(String.class);
        Sample instance = new Sample("Ada");

        List<Case> cases = new ArrayList<>();

        // AnnotationUtils
        cases.add(of("getClassAnnotations", types(Class.class), args(Sample.class)));
        cases.add(of("getAnnotationsByType", types(AnnotatedElement.class, Class.class), args(Sample.class, Marked.class)));
        cases.add(of("getDeclaredAnnotations", types(AnnotatedElement.class), args(Sample.class)));
        cases.add(of("getMethodDeclaredAnnotations", types(Method.class), args(greet)));
        cases.add(of("getMethodsDeclaredAnnotations", types(Method[].class), args((Object) new Method[]{greet})));
        cases.add(of("isAnnotationOnClassPresent", types(Class.class, Class.class), args(Sample.class, Marked.class)));
        cases.add(of("isMethodParameterAnnotated", types(Method.class, Class.class), args(greet, Marked.class)));
        cases.add(of("getFieldAnnotations", types(Field.class), args(field)));
        cases.add(of("isMethodAnnotated", types(Method.class, Class.class), args(greet, Marked.class)));

        // ConstructorUtils
        cases.add(of("getConstructorParameters", types(Constructor.class), args(constructor)));
        cases.add(of("getConstructorModifiers", types(Constructor.class), args(constructor)));
        cases.add(of("getConstructors", types(Class.class), args(Sample.class)));
        cases.add(of("getDeclaredConstructors", types(Class.class), args(Sample.class)));

        // FieldUtils and FieldsExtraUtils
        cases.add(of("getAllPrivateFields", types(Class.class), args(Sample.class)));
        cases.add(of("getAllPrivateFieldsMap", types(Class.class), args(Sample.class)));
        cases.add(of("getFieldsMap", types(List.class), args(Collections.singletonList(field))));
        cases.add(of("getAllAnnotatedFields", types(Class.class, Class.class), args(Sample.class, Marked.class)));
        cases.add(of("getFieldType", types(Class.class, String.class), args(Sample.class, "name")));
        cases.add(of("getFieldModifiers", types(Class.class, String.class), args(Sample.class, "name")));
        cases.add(of("isFieldFinal", types(Class.class, String.class), args(Sample.class, "CONSTANT")));
        cases.add(of("isFieldStatic", types(Class.class, String.class), args(Sample.class, "CONSTANT")));
        cases.add(of("setFieldAccessible", types(Class.class, String.class), args(Sample.class, "name"), Compare.NONE));
        cases.add(of("isFieldAnnotated", types(Field.class, Class.class), args(field, Marked.class)));
        cases.add(of("isFieldExactAnnotated", types(Field.class, Class.class), args(field, Marked.class)));
        cases.add(of("getAllFields", types(Class.class), args(Sample.class)));
        cases.add(of("getAllFieldsMap", types(Class.class), args(Sample.class)));
        cases.add(of("readField", types(Object.class, String.class), args(new Sample("Ada"), "name")));
        cases.add(of("clearUnselectedFields", types(Object.class, Collection.class),
                args(new Sample("Ada"), Collections.singletonList("name")), Compare.NONE));

        // ClassBasicUtils
        cases.add(of("getClassFullName", types(Object.class), args(instance)));
        cases.add(of("getClassCanonicalName", types(Object.class), args(instance)));
        cases.add(of("getClassSimpleName", types(Object.class), args(instance)));
        cases.add(of("getPackage", types(Object.class), args(instance)));
        cases.add(of("getClassFullNameByClass", types(Class.class), args(Sample.class)));
        cases.add(of("getClassCanonicalNameByClass", types(Class.class), args(Sample.class)));
        cases.add(of("getClassSimpleNameByClass", types(Class.class), args(Sample.class)));
        cases.add(of("getPackageByClass", types(Class.class), args(Sample.class)));
        cases.add(of("getSuperClassNameForObject", types(Object.class), args(instance)));
        cases.add(of("getSuperClassNameByClass", types(Class.class), args(Sample.class)));
        cases.add(of("getSuperClass", types(Object.class), args(instance)));
        cases.add(of("getEnclosingClass", types(Class.class), args(Sample.Inner.class)));
        cases.add(of("getInterfaces", types(Class.class), args(ArrayList.class)));

        // GeneralUtils
        cases.add(of("isInterface", types(Class.class), args(Collection.class)));
        cases.add(of("isArray", types(Class.class), args(String[].class)));
        cases.add(of("isEnum", types(Class.class), args(SampleEnum.class)));
        cases.add(of("isAnnotation", types(Class.class), args(Marked.class)));
        cases.add(of("isAnonymousClass", types(Class.class), args(Sample.class)));
        cases.add(of("getInnerClasses", types(Class.class), args(Sample.class)));

        // InvokeUtils
        cases.add(of("invokeMethod", types(Object.class, String.class, Class[].class, Object[].class),
                args(new Sample("Ada"), "greet", new Class<?>[]{String.class}, new Object[]{"Hello"})));
        cases.add(of("invokeSingleMethod", types(Object.class, String.class, Class.class, Object.class),
                args(new Sample("Ada"), "greet", String.class, "Hello")));
        cases.add(of("invokeInstance", types(String.class), args(Sample.class.getName()), Compare.TYPE));
        cases.add(of("invokeInstance", types(String.class, Object[].class),
                args(Sample.class.getName(), new Object[]{"Ada"}), Compare.TYPE));
        cases.add(of("invokeInstance", types(Class.class, Object[].class),
                args(Sample.class, new Object[]{"Ada"}), Compare.TYPE));
        cases.add(of("getArrayValuesTypesByArgs", types(Object[].class), args((Object) new Object[]{"text", 1})));
        cases.add(of("getAccessibleConstructor", types(Class[].class, Class.class),
                args(new Class<?>[]{String.class}, Sample.class)));

        // MethodEnhancementsUtils
        cases.add(of("getAnnotatedMethods", types(Class.class, Class.class), args(Sample.class, Marked.class)));
        cases.add(of("getAnnotatedConstructors", types(Class.class, Class.class), args(Sample.class, Marked.class)));

        // MethodUtils
        cases.add(of("getParameterTypes", types(Method.class), args(greet)));
        cases.add(of("getReturnType", types(Method.class), args(greet)));
        cases.add(of("getExceptionTypes", types(Method.class), args(greet)));
        cases.add(of("getMethodModifiers", types(Method.class), args(greet)));
        cases.add(of("isMethodVarArgs", types(Method.class), args(varArgs)));
        cases.add(of("getDefaultValue", types(Method.class), args(annotationValue)));
        cases.add(of("getAllPrivateMethods", types(Class.class), args(Sample.class)));
        cases.add(of("getAllPublicProtectedMethods", types(Class.class), args(Sample.class)));
        cases.add(of("getAllPublicMethods", types(Class.class), args(Sample.class)));
        cases.add(of("getAllMethodsWithModifiers", types(Class.class, List.class),
                args(Sample.class, Collections.<IntPredicate>singletonList(Modifier::isPublic))));
        cases.add(of("getDefaultMethodsOfInterfaces", types(Class.class), args(ArrayList.class)));
        cases.add(of("getDeclaredMethods", types(Class.class), args(Sample.class)));
        cases.add(of("getDeclaredMethodsList", types(Class.class), args(Sample.class)));
        cases.add(of("findMethodByName", types(Class.class, String.class), args(Sample.class, "greet")));

        // MiscellaneousUtils
        cases.add(of("newInstance", types(Class.class), args(Sample.class), Compare.TYPE));
        cases.add(of("getArrayComponentType", types(Class.class), args(String[].class)));

        // ObjectUtils
        cases.add(of("isFieldPrimitiveType", types(Field.class), args(field)));
        cases.add(of("isSimpleValueType", types(Field.class), args(field)));
        cases.add(of("copy", types(Object.class), args(new Sample("Ada")), Compare.TYPE));

        // PackageUtils
        cases.add(of("getClassesByPackage", types(String.class), args("org.reflector.exception")));
        cases.add(of("getClassesByDirectoryAndPackage", types(File.class, String.class),
                args(new File("no-such-directory"), "org.reflector")));
        cases.add(of("getAllAnnotatedClassesByPackage", types(String.class, Class.class),
                args("org.reflector.exception", Marked.class)));

        // SecurityUtils
        cases.add(of("setMethodAccessible", types(Method.class), args(greet), Compare.NONE));
        cases.add(of("setConstructorAccessible", types(Constructor.class), args(constructor), Compare.NONE));

        return cases;
    }

    @TestFactory
    Collection<DynamicTest> facadeDelegatesToTheUtilityClasses() throws Exception {
        List<DynamicTest> tests = new ArrayList<>();
        for (final Case testCase : cases()) {
            tests.add(DynamicTest.dynamicTest(testCase.key(), () -> verifyDelegation(testCase)));
        }
        return tests;
    }

    private void verifyDelegation(Case testCase) throws Exception {
        Method facade = ReflectionUtils.class.getDeclaredMethod(testCase.name, testCase.parameterTypes);
        Method target = findTarget(testCase);

        Object fromFacade = invoke(facade, testCase.arguments);
        Object fromTarget = invoke(target, testCase.arguments);

        switch (testCase.compare) {
            case VALUE:
                assertTrue(deepEquals(fromFacade, fromTarget),
                        testCase.key() + " returned " + describe(fromFacade) + " but "
                                + target.getDeclaringClass().getSimpleName() + " returned " + describe(fromTarget));
                break;
            case TYPE:
                assertNotNull(fromFacade, testCase.key() + " returned null");
                assertNotNull(fromTarget, testCase.key() + " delegate returned null");
                assertEquals(fromTarget.getClass(), fromFacade.getClass(), testCase.key() + " built a different type");
                break;
            case NONE:
            default:
                break;
        }
    }

    /** Finds the utility class method the facade forwards to. */
    private Method findTarget(Case testCase) {
        for (Class<?> target : TARGETS) {
            try {
                return target.getDeclaredMethod(testCase.name, testCase.parameterTypes);
            } catch (NoSuchMethodException ignored) {
                // try the next utility class
            }
        }
        fail("No utility class declares " + testCase.key()
                + ". Either the facade forwards somewhere it should not, or the utility was renamed.");
        return null;
    }

    private static Object invoke(Method method, Object[] arguments) {
        method.setAccessible(true);
        try {
            return method.invoke(null, arguments);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Cannot call " + method, e);
        } catch (InvocationTargetException e) {
            throw new IllegalStateException(method.getDeclaringClass().getSimpleName() + "." + method.getName()
                    + " threw " + e.getCause(), e.getCause());
        }
    }

    /**
     * Equality that sees through arrays, including arrays nested as map values, where the
     * built in {@code Map.equals} falls back to array identity.
     */
    private static boolean deepEquals(Object left, Object right) {
        if (left == null || right == null) {
            return left == right;
        }
        if (left.getClass().isArray() && right.getClass().isArray()) {
            return Arrays.deepEquals(new Object[]{left}, new Object[]{right});
        }
        if (left instanceof Map && right instanceof Map) {
            Map<?, ?> leftMap = (Map<?, ?>) left;
            Map<?, ?> rightMap = (Map<?, ?>) right;
            if (leftMap.size() != rightMap.size() || !leftMap.keySet().equals(rightMap.keySet())) {
                return false;
            }
            for (Map.Entry<?, ?> entry : leftMap.entrySet()) {
                if (!deepEquals(entry.getValue(), rightMap.get(entry.getKey()))) {
                    return false;
                }
            }
            return true;
        }
        if (left instanceof List && right instanceof List) {
            List<?> leftList = (List<?>) left;
            List<?> rightList = (List<?>) right;
            if (leftList.size() != rightList.size()) {
                return false;
            }
            for (int i = 0; i < leftList.size(); i++) {
                if (!deepEquals(leftList.get(i), rightList.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return left.equals(right);
    }

    private static String describe(Object value) {
        if (value == null) {
            return "null";
        }
        if (value.getClass().isArray()) {
            return Arrays.deepToString(new Object[]{value});
        }
        return value.toString();
    }

    @Test
    void everyFacadeMethodIsExercised() throws Exception {
        Set<String> covered = new LinkedHashSet<>();
        for (Case testCase : cases()) {
            covered.add(testCase.key());
        }

        Set<String> missing = new TreeSet<>();
        for (Method method : ReflectionUtils.class.getDeclaredMethods()) {
            if (!Modifier.isPublic(method.getModifiers()) || !Modifier.isStatic(method.getModifiers())
                    || method.isSynthetic()) {
                continue;
            }
            String key = signature(method.getName(), method.getParameterTypes());
            if (!covered.contains(key)) {
                missing.add(key);
            }
        }

        assertTrue(missing.isEmpty(), "These ReflectionUtils methods have no delegation case: " + missing
                + ". Add one to cases() so the facade stays covered.");
    }

    @Test
    void everyCaseNamesARealFacadeMethod() throws Exception {
        Set<String> stale = new TreeSet<>();
        for (Case testCase : cases()) {
            try {
                ReflectionUtils.class.getDeclaredMethod(testCase.name, testCase.parameterTypes);
            } catch (NoSuchMethodException e) {
                stale.add(testCase.key());
            }
        }
        assertTrue(stale.isEmpty(), "Delegation cases that no longer match a facade method: " + stale);
    }
}
