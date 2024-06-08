package org.reflector;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.IntPredicate;

import org.reflector.exception.FieldAccessException;
import org.reflector.exception.InstanceInvocationException;
import org.reflector.exception.MethodInvokeException;

/**
 *
 * Simple utility class for working with the reflection API
 *
 * provides work with methods, fields, names, classes, instantiation
 *
 * simple object copy
 *
 */
public final class ReflectionUtils {

    private ReflectionUtils() {}

    // AnnotationUtils

    /**
     * Retrieves all annotations present on the given class.
     *
     * @param clazz the class whose annotations are to be retrieved
     * @return an array of annotations present on the given class
     * @throws IllegalArgumentException if the provided class is null
     */
    public static Annotation[] getClassAnnotations(final Class<?> clazz) {
        return AnnotationUtils.getClassAnnotations(clazz);
    }

    /**
     * Retrieves annotations by type from a class or element.
     *
     * @param <T>             the type of the annotation to query for and return if present
     * @param element         the element from which to get the annotations
     * @param annotationClass the Class object corresponding to the annotation type
     * @return an array of all annotations of the specified annotation type if present on this element, else an empty array
     * @throws NullPointerException if the element or annotationClass is null
     */
    public static <T extends Annotation> T[] getAnnotationsByType(AnnotatedElement element, Class<T> annotationClass) {
        return AnnotationUtils.getAnnotationsByType(element, annotationClass);
    }

    /**
     * Gets annotations declared directly on a class, method, or field.
     *
     * @param element the element from which to get the annotations
     * @return an array of annotations directly declared on the element
     * @throws NullPointerException if the element is null
     */
    public static Annotation[] getDeclaredAnnotations(AnnotatedElement element) {
        return AnnotationUtils.getDeclaredAnnotations(element);
    }

    /**
     * Retrieves the annotations declared on a method.
     *
     * @param method the method to retrieve annotations from
     * @return an array of annotations declared on the method
     * @throws NullPointerException if the method is null
     */
    public static Annotation[] getMethodDeclaredAnnotations(final Method method) {
        return AnnotationUtils.getMethodDeclaredAnnotations(method);
    }

    /**
     * Retrieves a map of methods to their declared annotations for the given array of methods.
     *
     * @param methods the array of methods whose declared annotations are to be retrieved
     * @return a map where the keys are the methods and the values are arrays of their declared annotations
     * @throws NullPointerException if the methods array is null
     */
    public static Map<Method, Annotation[]> getMethodsDeclaredAnnotations(final Method[] methods) {
        return AnnotationUtils.getMethodsDeclaredAnnotations(methods);
    }

    /**
     * Checks if a specific annotation is present on the given class.
     *
     * @param clazz           the class to check for the presence of the annotation
     * @param annotationClass the annotation class to look for
     * @param <T>             the type of the annotation
     * @return true if the specified annotation is present on the class, false otherwise
     * @throws NullPointerException if the provided class or annotation class is null
     */
    public static <T extends Annotation> boolean isAnnotationOnClassPresent(final Class<?> clazz, final Class<T> annotationClass) {
        return AnnotationUtils.isAnnotationOnClassPresent(clazz, annotationClass);
    }

    /**
     * Checks if any parameter of the given method is annotated with the specified annotation class.
     *
     * @param method the method whose parameters are to be checked
     * @param clazz  the annotation class to look for on the method parameters
     * @param <T>    the type of the annotation
     * @return true if any parameter of the method is annotated with the specified annotation, false otherwise
     * @throws NullPointerException if the provided method or annotation class is null
     */
    public static <T extends Annotation> boolean isMethodParameterAnnotated(final Method method, final Class<T> clazz) {
        return AnnotationUtils.isMethodParameterAnnotated(method, clazz);
    }

    /**
     * Gets all annotations present on a given field.
     *
     * @param field the field whose annotations are to be retrieved
     * @return an array of annotations present on the field
     * @throws NullPointerException if the provided field is null
     */
    public static Annotation[] getFieldAnnotations(final Field field) {
        return AnnotationUtils.getFieldAnnotations(field);
    }

    /**
     * Checks if the given method is annotated with the specified annotation class.
     *
     * @param method the method to check for the annotation
     * @param clazz  the annotation class to look for on the method
     * @param <T>    the type of the annotation
     * @return true if the method is annotated with the specified annotation, false otherwise
     * @throws NullPointerException if the provided method or annotation class is null
     */
    public static <T extends Annotation> boolean isMethodAnnotated(final Method method, final Class<T> clazz) {
        return AnnotationUtils.isMethodAnnotated(method, clazz);
    }

    // ConstructorUtils


    /**
     * Retrieves the parameters of a constructor.
     *
     * @param constructor the constructor from which to retrieve parameters
     * @return an array of parameters of the constructor
     * @throws NullPointerException if the constructor is null
     */
    public static Parameter[] getConstructorParameters(Constructor<?> constructor) {
        return ConstructorUtils.getConstructorParameters(constructor);
    }

    /**
     * Retrieves the modifiers of a constructor.
     *
     * @param constructor the constructor from which to retrieve modifiers
     * @return an integer representing the modifiers of the constructor
     * @throws NullPointerException if the constructor is null
     */
    public static int getConstructorModifiers(Constructor<?> constructor) {
        return ConstructorUtils.getConstructorModifiers(constructor);
    }

    /**
     * Retrieves all public constructors of the specified class.
     *
     * @param clazz the class from which to retrieve constructors
     * @return an array of public constructors of the specified class
     * @throws NullPointerException if the class is null
     */
    public static Constructor<?>[] getConstructors(final Class<?> clazz) {
        return ConstructorUtils.getConstructors(clazz);
    }

    /**
     * Retrieves all declared constructors of the specified class, including public, protected, default (package), and private constructors.
     *
     * @param clazz the class from which to retrieve declared constructors
     * @return an array of declared constructors of the specified class
     * @throws NullPointerException if the class is null
     */
    public static Constructor<?>[] getDeclaredConstructors(final Class<?> clazz) {
        return ConstructorUtils.getDeclaredConstructors(clazz);
    }

    // FieldsExtraUtils


    /**
     * Retrieves all private fields of a given class, including fields declared in its superclasses.
     *
     * @param clazz the class from which to retrieve private fields
     * @return a list of all private fields of the specified class
     * @throws NullPointerException if the clazz is null
     */
    public static List<Field> getAllPrivateFields(final Class<?> clazz) {
        return FieldsExtraUtils.getAllPrivateFields(clazz);
    }

    /**
     * Retrieves all private fields of a given class, including fields declared in its superclasses,
     * and returns them as a map with field names as keys.
     *
     * @param clazz the class from which to retrieve private fields
     * @return a map of all private fields of the specified class with field names as keys
     * @throws NullPointerException if the clazz is null
     */
    public static Map<String, Field> getAllPrivateFieldsMap(final Class<?> clazz) {
        return FieldsExtraUtils.getAllPrivateFieldsMap(clazz);
    }

    /**
     * Helper method to convert a list of fields to a map with field names as keys.
     *
     * @param fields the list of fields to convert to a map
     * @return a map with field names as keys and Field objects as values
     */
    public static Map<String, Field> getFieldsMap(final List<Field> fields) {
        return FieldsExtraUtils.getFieldsMap(fields);
    }

    /**
     * Retrieves all fields annotated with a specific annotation from a given class,
     * including fields declared in its superclasses.
     *
     * @param type       the class from which to retrieve annotated fields
     * @param annotation the annotation class to search for
     * @return a list of fields annotated with the specified annotation
     * @throws NullPointerException if type or annotation is null
     */
    public static List<Field> getAllAnnotatedFields(final Class<?> type, final Class<? extends Annotation> annotation) {
        return FieldsExtraUtils.getAllAnnotatedFields(type, annotation);
    }

    // FieldUtils

    /**
     * Retrieves the type of a specified field in the given class.
     *
     * @param clazz the class from which the field type is to be retrieved
     * @param fieldName the name of the field whose type is to be retrieved
     * @return the type of the specified field
     * @throws NoSuchFieldException if the specified field does not exist
     * @throws NullPointerException if the clazz or fieldName is null
     */
    public static Class<?> getFieldType(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        return FieldUtils.getFieldType(clazz, fieldName);
    }

    /**
     * Retrieves the modifiers of a specified field in the given class.
     *
     * @param clazz the class from which the field modifiers are to be retrieved
     * @param fieldName the name of the field whose modifiers are to be retrieved
     * @return the modifiers of the specified field
     * @throws NoSuchFieldException if the specified field does not exist
     * @throws NullPointerException if the clazz or fieldName is null
     */
    public static int getFieldModifiers(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        return FieldUtils.getFieldModifiers(clazz, fieldName);
    }

    /**
     * Checks if a specified field in the given class is final.
     *
     * @param clazz the class from which the field is to be checked
     * @param fieldName the name of the field to be checked
     * @return true if the specified field is final, false otherwise
     * @throws NoSuchFieldException if the specified field does not exist
     * @throws NullPointerException if the clazz or fieldName is null
     */
    public static boolean isFieldFinal(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        return FieldUtils.isFieldFinal(clazz, fieldName);
    }

    /**
     * Checks if a specified field in the given class is static.
     *
     * @param clazz the class from which the field is to be checked
     * @param fieldName the name of the field to be checked
     * @return true if the specified field is static, false otherwise
     * @throws NoSuchFieldException if the specified field does not exist
     * @throws NullPointerException if the clazz or fieldName is null
     */
    public static boolean isFieldStatic(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        return FieldUtils.isFieldStatic(clazz, fieldName);
    }

    /**
     * Sets a specified field in the given class to be accessible.
     *
     * @param clazz the class containing the field
     * @param fieldName the name of the field to be set accessible
     * @throws NoSuchFieldException if the specified field does not exist
     * @throws NullPointerException if the clazz or fieldName is null
     */
    public static void setFieldAccessible(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        FieldUtils.setFieldAccessible(clazz, fieldName);
    }

    /**
     * Checks if a field is annotated with a specific annotation.
     *
     * @param field the field to check
     * @param annotationClass the annotation class to look for
     * @param <T> the type of the annotation
     * @return true if the field is annotated with the specified annotation, false otherwise
     * @throws NullPointerException if the field or annotationClass is null
     */
    public static <T extends Annotation> boolean isFieldAnnotated(final Field field, final Class<T> annotationClass) {
        return FieldUtils.isFieldAnnotated(field, annotationClass);
    }

    /**
     * Checks if a field is exactly annotated with a specific annotation.
     *
     * @param field the field to check
     * @param annotationClass the annotation class to look for
     * @param <T> the type of the annotation
     * @return true if the field is exactly annotated with the specified annotation, false otherwise
     * @throws NullPointerException if the field or annotationClass is null
     */
    public static <T extends Annotation> boolean isFieldExactAnnotated(final Field field, final Class<T> annotationClass) {
        return FieldUtils.isFieldExactAnnotated(field, annotationClass);
    }

    /**
     * Retrieves all fields of a given class, including fields declared in its superclasses.
     *
     * @param type the class from which to retrieve fields
     * @return a list of all fields of the specified class
     * @throws NullPointerException if the type is null
     */
    public static List<Field> getAllFields(final Class<?> type) {
        return FieldUtils.getAllFields(type);
    }

//    /**
//     * Retrieves all private fields of a given class, including fields declared in its superclasses.
//     *
//     * @param clazz the class from which to retrieve private fields
//     * @return a list of all private fields of the specified class
//     * @throws NullPointerException if the clazz is null
//     */
//    public static List<Field> getAllPrivateFields(final Class<?> clazz) {
//        return FieldUtils.getAllPrivateFields(clazz);
//    }

    /**
     * Retrieves all fields of a given class, including fields declared in its superclasses,
     * and returns them as a map with field names as keys.
     *
     * @param clazz the class from which to retrieve fields
     * @return a map of all fields of the specified class with field names as keys
     * @throws NullPointerException if the clazz is null
     */
    public static Map<String, Field> getAllFieldsMap(final Class<?> clazz) {
        return FieldUtils.getAllFieldsMap(clazz);
    }

    /**
     * Reads the value of a field from an object.
     *
     * @param object    the object from which to read the field
     * @param fieldName the name of the field to read
     * @return the value of the field in the object
     * @throws FieldAccessException if the field cannot be accessed
     */
    public static Object readField(final Object object, final String fieldName) {
        return FieldUtils.readField(object, fieldName);
    }

    /**
     * Clears the values of unselected fields of the given object.
     *
     * <p>For each field of the object's class, if the field name is not present in the specified
     * collection of selected fields, the field value is set to null.
     *
     * @param object the object whose fields are to be cleared
     * @param selectedFields a collection containing the names of the fields to keep
     * @throws IllegalArgumentException if the object is null
     */
    public static void clearUnselectedFields(final Object object, final Collection<String> selectedFields) {
        FieldUtils.clearUnselectedFields(object, selectedFields);
    }

    // GeneralUtils

    /**
     * Checks if a given class is an interface.
     *
     * @param clazz the class to check
     * @return true if the class is an interface, false otherwise
     */
    public static boolean isInterface(Class<?> clazz) {
        return GeneralUtils.isInterface(clazz);
    }

    /**
     * Checks if a given class is an array.
     *
     * @param clazz the class to check
     * @return true if the class is an array, false otherwise
     */
    public static boolean isArray(Class<?> clazz) {
        return GeneralUtils.isArray(clazz);
    }

    /**
     * Checks if a given class is an enum.
     *
     * @param clazz the class to check
     * @return true if the class is an enum, false otherwise
     */
    public static boolean isEnum(Class<?> clazz) {
        return GeneralUtils.isEnum(clazz);
    }

    /**
     * Checks if a given class is an annotation.
     *
     * @param clazz the class to check
     * @return true if the class is an annotation, false otherwise
     */
    public static boolean isAnnotation(Class<?> clazz) {
        return GeneralUtils.isAnnotation(clazz);
    }

    /**
     * Checks if a given class is anonymous.
     *
     * @param clazz the class to check
     * @return true if the class is anonymous, false otherwise
     */
    public static boolean isAnonymousClass(Class<?> clazz) {
        return GeneralUtils.isAnonymousClass(clazz);
    }

    /**
     * Retrieves the inner classes declared within a class.
     *
     * @param clazz the class to check
     * @return an array of inner classes declared within the class
     */
    public static Class<?>[] getInnerClasses(Class<?> clazz) {
        return GeneralUtils.getInnerClasses(clazz);
    }

    // InvokeUtils

    /**
     * Invokes a method on an object.
     *
     * @param objectToInvokeOn the object to invoke the method on
     * @param methodName        the name of the method to invoke
     * @param parameterTypes    the parameter types of the method
     * @param args              the arguments to pass to the method
     * @return the result of the method invocation
     * @throws MethodInvokeException if an error occurs during method invocation
     */
    public static Object invokeMethod(final Object objectToInvokeOn, final String methodName, final Class<?>[] parameterTypes, final Object[] args) {
        return InvokeUtils.invokeMethod(objectToInvokeOn, methodName, parameterTypes, args);
    }

    /**
     * Invokes a single-parameter method on an object.
     *
     * @param objectToInvokeOn the object to invoke the method on
     * @param methodName        the name of the method to invoke
     * @param parameterType     the type of the parameter of the method
     * @param parameter         the parameter value to pass to the method
     * @return the result of the method invocation
     * @throws MethodInvokeException if an error occurs during method invocation
     */
    public static Object invokeSingleMethod(final Object objectToInvokeOn, final String methodName, final Class<?> parameterType, final Object parameter) {
        return InvokeUtils.invokeSingleMethod(objectToInvokeOn, methodName, parameterType, parameter);
    }

    /**
     * Instantiates a class without constructor arguments.
     *
     * @param className the name of the class to instantiate
     * @return the new instance of the class
     * @throws InstanceInvocationException if an error occurs during instance invocation
     */
    public static Object invokeInstance(final String className) throws InstanceInvocationException {
        return InvokeUtils.invokeInstance(className);
    }

    /**
     * Instantiates a class with constructor arguments.
     *
     * @param classFullName the fully qualified name of the class to instantiate
     * @param args          the arguments to pass to the constructor
     * @return the new instance of the class
     * @throws InstanceInvocationException if an error occurs during instance invocation
     */
    public static Object invokeInstance(final String classFullName, final Object... args) throws InstanceInvocationException {
        return InvokeUtils.invokeInstance(classFullName, args);
    }

    /**
     * Instantiates a class with constructor arguments.
     *
     * @param clazz the class to instantiate
     * @param args  the arguments to pass to the constructor
     * @param <T>   the type of the class to instantiate
     * @return the new instance of the class
     * @throws InstanceInvocationException if an error occurs during instance invocation
     */
    public static <T> T invokeInstance(final Class<T> clazz, final Object... args) throws InstanceInvocationException {
        return InvokeUtils.invokeInstance(clazz, args);
    }

    /**
     * Gets the types of the arguments.
     *
     * @param args the arguments
     * @return an array of argument types
     */
    public static Class<?>[] getArrayValuesTypesByArgs(final Object[] args) {
        return InvokeUtils.getArrayValuesTypesByArgs(args);
    }

    /**
     * Gets a constructor with accessible flag set.
     *
     * @param contTypes the types of the constructor parameters
     * @param clazz     the class
     * @param <T>       the type of the class
     * @return the constructor
     * @throws NoSuchMethodException if the constructor is not found
     */
    public static <T> Constructor<T> getAccessibleConstructor(final Class<?>[] contTypes, final Class<T> clazz) throws NoSuchMethodException {
        return InvokeUtils.getAccessibleConstructor(contTypes, clazz);
    }

    //MethodEnhancementsUtils

    /**
     * Retrieves methods annotated with a specific annotation.
     *
     * @param clazz the class from which to retrieve methods
     * @param annotationClass the Class object corresponding to the annotation type
     * @return a list of methods annotated with the specified annotation
     * @throws NullPointerException if the clazz or annotationClass is null
     */
    public static List<Method> getAnnotatedMethods(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        return MethodEnhancementsUtils.getAnnotatedMethods(clazz, annotationClass);
    }

    /**
     * Retrieves constructors annotated with a specific annotation.
     *
     * @param clazz the class from which to retrieve constructors
     * @param annotationClass the Class object corresponding to the annotation type
     * @return a list of constructors annotated with the specified annotation
     * @throws NullPointerException if the clazz or annotationClass is null
     */
    public static List<Constructor<?>> getAnnotatedConstructors(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        return MethodEnhancementsUtils.getAnnotatedConstructors(clazz, annotationClass);
    }

    //MethodUtils

    /**
     * Retrieves the parameter types of the given method.
     *
     * @param method the method whose parameter types are to be retrieved
     * @return an array of Classes representing the parameter types of the method
     * @throws IllegalArgumentException if the provided method is null
     */
    public static Class<?>[] getParameterTypes(final Method method) {
        return MethodUtils.getParameterTypes(method);
    }

    /**
     * Gets the return type of the given method.
     *
     * @param method the method whose return type is to be retrieved
     * @return the Class representing the return type of the method
     * @throws IllegalArgumentException if the provided method is null
     */
    public static Class<?> getReturnType(final Method method) {
        return MethodUtils.getReturnType(method);
    }

    /**
     * Gets the types of exceptions thrown by the given method.
     *
     * @param method the method whose exception types are to be retrieved
     * @return an array of Classes representing the exception types thrown by the method
     * @throws IllegalArgumentException if the provided method is null
     */
    public static Class<?>[] getExceptionTypes(final Method method) {
        return MethodUtils.getExceptionTypes(method);
    }

    /**
     * Retrieves the modifiers of the given method.
     *
     * @param method the method whose modifiers are to be retrieved
     * @return an int representing the modifiers of the method
     * @throws IllegalArgumentException if the provided method is null
     */
    public static int getMethodModifiers(final Method method) {
        return MethodUtils.getMethodModifiers(method);
    }

    /**
     * Checks if the given method takes a variable number of arguments.
     *
     * @param method the method to be checked
     * @return true if the method takes a variable number of arguments, false otherwise
     * @throws IllegalArgumentException if the provided method is null
     */
    public static boolean isMethodVarArgs(final Method method) {
        return MethodUtils.isMethodVarArgs(method);
    }

    /**
     * Gets the default value of the given method's annotation element.
     *
     * @param method the method whose annotation element's default value is to be retrieved
     * @return the default value of the annotation element, or null if none
     * @throws IllegalArgumentException if the provided method is null
     */
    public static Object getDefaultValue(final Method method) {
        return MethodUtils.getDefaultValue(method);
    }

    /**
     * Retrieves all private methods of a class.
     *
     * @param clazz the class from which to retrieve methods
     * @return a list of private methods of the specified class
     * @throws NullPointerException if the clazz is null
     */
    public static List<Method> getAllPrivateMethods(final Class<?> clazz) {
        return MethodUtils.getAllPrivateMethods(clazz);
    }

    /**
     * Retrieves all public and protected methods of a class.
     *
     * @param clazz the class from which to retrieve methods
     * @return a list of public and protected methods of the specified class
     * @throws NullPointerException if the clazz is null
     */
    public static List<Method> getAllPublicProtectedMethods(final Class<?> clazz) {
        return MethodUtils.getAllPublicProtectedMethods(clazz);
    }

    /**
     * Retrieves all public methods of a class.
     *
     * @param clazz the class from which to retrieve methods
     * @return a list of public methods of the specified class
     * @throws NullPointerException if the clazz is null
     */
    public static List<Method> getAllPublicMethods(final Class<?> clazz) {
        return MethodUtils.getAllPublicMethods(clazz);
    }

    /**
     * Retrieves all methods of a class that match the given modifiers.
     *
     * @param clazz     the class from which to retrieve methods
     * @param modifiers the list of predicates to match the method modifiers
     * @return a list of methods that match the given modifiers
     * @throws NullPointerException if the clazz or modifiers are null
     */
    public static List<Method> getAllMethodsWithModifiers(final Class<?> clazz, final List<IntPredicate> modifiers) {
        return MethodUtils.getAllMethodsWithModifiers(clazz, modifiers);
    }

    /**
     * Retrieves all default methods from the interfaces implemented by the specified class.
     *
     * @param clazz the class whose interfaces' default methods are to be retrieved.
     * @return a list of default methods from the interfaces implemented by the specified class.
     * @throws IllegalArgumentException if the class parameter is null.
     */
    public static List<Method> getDefaultMethodsOfInterfaces(final Class<?> clazz) {
        return MethodUtils.getDefaultMethodsOfInterfaces(clazz);
    }

    /**
     * Retrieves all declared methods of the specified class, including default methods from its interfaces.
     *
     * @param clazz the class whose declared methods and default interface methods are to be retrieved.
     * @return an array of {@link Method} objects reflecting all declared methods of the class,
     *         including default methods from its interfaces.
     * @throws IllegalArgumentException if the class parameter is null.
     * @throws IllegalStateException if an error occurs while retrieving the methods.
     */
    public static Method[] getDeclaredMethods(final Class<?> clazz) {
        return MethodUtils.getDeclaredMethods(clazz);
    }

    /**
     * Retrieves all declared methods of the specified class, including default methods from its interfaces,
     * and returns them as a list.
     *
     * @param clazz the class whose declared methods and default interface methods are to be retrieved.
     * @return a list of {@link Method} objects reflecting all declared methods of the class,
     *         including default methods from its interfaces.
     * @throws IllegalArgumentException if the class parameter is null.
     * @throws IllegalStateException if an error occurs while retrieving the methods.
     */
    public static List<Method> getDeclaredMethodsList(final Class<?> clazz) {
        return MethodUtils.getDeclaredMethodsList(clazz);
    }

    /**
     * Finds a method by name in the specified class or its superclasses and interfaces.
     *
     * @param clazz the class in which to search for the method.
     * @param name the name of the method to search for.
     * @return the {@link Method} object if a method with the specified name is found, or null if not found.
     * @throws IllegalArgumentException if the class or method name parameter is null.
     */
    public static Method findMethodByName(final Class<?> clazz, final String name) {
        return MethodUtils.findMethodByName(clazz, name);
    }

    //MiscellaneousUtils

    /**
     * Creates a new instance of a class using its no-argument constructor.
     *
     * @param clazz the class of which to create an instance
     * @return a new instance of the specified class
     * @throws NullPointerException if the class is null
     * @throws InstantiationException if the class represents an abstract class, an interface, an array class, a primitive type, or void;
     *                                or if the class has no nullary constructor
     * @throws IllegalAccessException if the class or its nullary constructor is not accessible
     * @throws InvocationTargetException if the nullary constructor throws an exception
     */
    public static <T> T newInstance(Class<T> clazz) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return MiscellaneousUtils.newInstance(clazz);
    }

    /**
     * Retrieves the component type of an array class.
     *
     * @param arrayClass the array class
     * @return the component type of the array class
     * @throws NullPointerException if the array class is null
     */
    public static Class<?> getArrayComponentType(Class<?> arrayClass) {
        return MiscellaneousUtils.getArrayComponentType(arrayClass);
    }

    //ObjectUtils

    /**
     * Checks if the type of a field is a primitive type or a wrapper class.
     *
     * @param field the field to check
     * @return true if the type of the field is a primitive type or a wrapper class, false otherwise
     */
    public static boolean isFieldPrimitiveType(final Field field) {
        return ObjectUtils.isFieldPrimitiveType(field);
    }

    /**
     * Creates a deep copy of the given object.
     *
     * @param object the object to be copied
     * @return the deep copy of the object
     * @throws IllegalStateException if copying fails
     */
    public static Object copy(final Object object) {
        return ObjectUtils.copy(object);
    }

    //PackageUtils

    /**
     * Retrieves all classes within a package.
     *
     * @param packageName the name of the package
     * @return a list of classes within the specified package
     * @throws ClassNotFoundException if a class cannot be found
     * @throws IOException            if an I/O error occurs
     * @throws URISyntaxException     if a URI syntax error occurs
     */
    public static List<Class<?>> getClassesByPackage(final String packageName) throws ClassNotFoundException, IOException, URISyntaxException {
        return PackageUtils.getClassesByPackage(packageName);
    }

    /**
     * Retrieves all classes within a directory and its subdirectories.
     *
     * @param directory    the directory to search for classes
     * @param packageName  the name of the package
     * @return a list of classes within the specified directory and package
     * @throws ClassNotFoundException if a class cannot be found
     */
    public static List<Class<?>> getClassesByDirectoryAndPackage(final File directory, final String packageName) throws ClassNotFoundException {
        return PackageUtils.getClassesByDirectoryAndPackage(directory, packageName);
    }

    /**
     * Retrieves all classes within a package that are annotated with a specific annotation.
     *
     * @param packageName the name of the package
     * @param annotation  the annotation to filter classes by
     * @return a list of classes within the specified package that are annotated with the specified annotation
     * @throws IOException            if an I/O error occurs
     * @throws URISyntaxException     if a URI syntax error occurs
     * @throws ClassNotFoundException if a class cannot be found
     */
    public static List<Class<?>> getAllAnnotatedClassesByPackage(final String packageName, final Class annotation) throws IOException, URISyntaxException, ClassNotFoundException {
        return PackageUtils.getAllAnnotatedClassesByPackage(packageName, annotation);
    }

    //SecurityUtils

    /**
     * Sets a method to be accessible.
     *
     * @param method the method to be set accessible
     * @throws NullPointerException if the method is null
     */
    public static void setMethodAccessible(Method method) {
        SecurityUtils.setMethodAccessible(method);
    }

    /**
     * Sets a constructor to be accessible.
     *
     * @param constructor the constructor to be set accessible
     * @throws NullPointerException if the constructor is null
     */
    public static void setConstructorAccessible(Constructor<?> constructor) {
        SecurityUtils.setConstructorAccessible(constructor);
    }
}

