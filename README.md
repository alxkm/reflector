# Reflector <img src="https://www.svgrepo.com/show/144446/mirror-horizontally.svg" height="32px" alt="Reflector" />

[![Java CI with Gradle](https://github.com/alxkm/reflector/actions/workflows/gradle.yml/badge.svg)](https://github.com/alxkm/reflector/actions/workflows/gradle.yml)[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
<!--[![Build Status](https://app.travis-ci.com/alxkm/reflector.svg?branch=master)](https://app.travis-ci.com/alxkm/reflector)-->

Reflector is library, which offers a comprehensive suite of utilities for harnessing the power of the Java Reflection API. With a focus on simplicity and efficiency, it empowers developers to interact with class metadata, methods, fields, constructors, and annotations effortlessly.

**Library main usage is:**
- get class methods private or not
- get class fields
- select annotated fields
- select and clear some fields
- invoke new instance of class
- invoke object method
- read object field
- read object field as map
- other (get class name, package, super)
- get method
- get declared methods
- get default interfaces methods

**Required java version is java 8**

# Java Reflection Utils

# Features
- **AnnotationUtils**: Simplifies the handling of annotations on classes, methods, and fields.
- **ClassBasicUtils**: Provides fundamental utilities for working with class metadata.
- **ConstructorUtils**: Aids in accessing constructor-related information with ease.
- **FieldsExtraUtils**: Extends field-related utilities, offering additional functionalities like retrieving private fields and annotated fields.
- **FieldUtils**: Offers a wide range of utilities for field manipulation and access.
- **GeneralUtils**: Contains miscellaneous utility methods for various common tasks.
- **InvokeUtils**: Facilitates method invocation on objects and classes.
- **MethodEnhancementsUtils**: Enhances method-related utilities with additional functionalities.
- **MethodUtils**: Simplifies method-related tasks, such as method invocation and retrieval.
- **MiscellaneousUtils**: Houses miscellaneous utility methods for various purposes.
- **ObjectUtils**: Provides utilities for working with objects, including copying and comparison.
- **PackageUtils**: Facilitates working with packages, including class retrieval and scanning.
- **ReflectionUtils**: The core of the library, offering a wide range of reflection-related utilities.
- **ReflectionUtilsLegacy**: Contains legacy reflection utilities for compatibility purposes.
- **SecurityUtils**: Offers utilities for handling security-related tasks, ensuring safe reflection operations.


# ReflectionUtils Documentation

# Example Usage Quick start


## Annotations

### getClassAnnotations

**Description:**
Retrieves all annotations present on the given class.

**Parameters:**
- `clazz` (Class<?>): The class whose annotations are to be retrieved.

**Returns:**
- `Annotation[]`: An array of annotations present on the given class.

**Throws:**
- `IllegalArgumentException`: If the provided class is null.

**Example Usage:**
```java
Annotation[] annotations = ReflectionUtils.getClassAnnotations(MyClass.class);
```

---

### getAnnotationsByType

**Description:**
Retrieves annotations by type from a class or element.

**Parameters:**
- `<T>`: The type of the annotation to query for and return if present.
- `element` (AnnotatedElement): The element from which to get the annotations.
- `annotationClass` (Class<T>): The Class object corresponding to the annotation type.

**Returns:**
- `T[]`: An array of all annotations of the specified annotation type if present on this element, else an empty array.

**Throws:**
- `NullPointerException`: If the element or annotationClass is null.

**Example Usage:**
```java
MyAnnotation[] annotations = ReflectionUtils.getAnnotationsByType(myElement, MyAnnotation.class);
```

---

### getDeclaredAnnotations

**Description:**
Gets annotations declared directly on a class, method, or field.

**Parameters:**
- `element` (AnnotatedElement): The element from which to get the annotations.

**Returns:**
- `Annotation[]`: An array of annotations directly declared on the element.

**Throws:**
- `NullPointerException`: If the element is null.

**Example Usage:**
```java
Annotation[] annotations = ReflectionUtils.getDeclaredAnnotations(myElement);
```

---

### getMethodDeclaredAnnotations

**Description:**
Retrieves the annotations declared on a method.

**Parameters:**
- `method` (Method): The method to retrieve annotations from.

**Returns:**
- `Annotation[]`: An array of annotations declared on the method.

**Throws:**
- `NullPointerException`: If the method is null.

**Example Usage:**
```java
Annotation[] annotations = ReflectionUtils.getMethodDeclaredAnnotations(myMethod);
```

---

### getMethodsDeclaredAnnotations

**Description:**
Retrieves a map of methods to their declared annotations for the given array of methods.

**Parameters:**
- `methods` (Method[]): The array of methods whose declared annotations are to be retrieved.

**Returns:**
- `Map<Method, Annotation[]>`: A map where the keys are the methods and the values are arrays of their declared annotations.

**Throws:**
- `NullPointerException`: If the methods array is null.

**Example Usage:**
```java
Map<Method, Annotation[]> methodAnnotations = ReflectionUtils.getMethodsDeclaredAnnotations(myMethodsArray);
```

---

### isAnnotationOnClassPresent

**Description:**
Checks if a specific annotation is present on the given class.

**Parameters:**
- `clazz` (Class<?>): The class to check for the presence of the annotation.
- `annotationClass` (Class<T>): The annotation class to look for.

**Returns:**
- `boolean`: True if the specified annotation is present on the class, false otherwise.

**Throws:**
- `NullPointerException`: If the provided class or annotation class is null.

**Example Usage:**
```java
boolean isPresent = ReflectionUtils.isAnnotationOnClassPresent(MyClass.class, MyAnnotation.class);
```

---

### isMethodParameterAnnotated

**Description:**
Checks if any parameter of the given method is annotated with the specified annotation class.

**Parameters:**
- `method` (Method): The method whose parameters are to be checked.
- `clazz` (Class<T>): The annotation class to look for on the method parameters.

**Returns:**
- `boolean`: True if any parameter of the method is annotated with the specified annotation, false otherwise.

**Throws:**
- `NullPointerException`: If the provided method or annotation class is null.

**Example Usage:**
```java
boolean isAnnotated = ReflectionUtils.isMethodParameterAnnotated(myMethod, MyAnnotation.class);
```

---

### getFieldAnnotations

**Description:**
Gets all annotations present on a given field.

**Parameters:**
- `field` (Field): The field whose annotations are to be retrieved.

**Returns:**
- `Annotation[]`: An array of annotations present on the field.

**Throws:**
- `NullPointerException`: If the provided field is null.

**Example Usage:**
```java
Annotation[] annotations = ReflectionUtils.getFieldAnnotations(myField);
```

---

### isMethodAnnotated

**Description:**
Checks if the given method is annotated with the specified annotation class.

**Parameters:**
- `method` (Method): The method to check for the annotation.
- `clazz` (Class<T>): The annotation class to look for on the method.

**Returns:**
- `boolean`: True if the method is annotated with the specified annotation, false otherwise.

**Throws:**
- `NullPointerException`: If the provided method or annotation class is null.

**Example Usage:**
```java
boolean isAnnotated = ReflectionUtils.isMethodAnnotated(myMethod, MyAnnotation.class);
```

---

# Constructors

### getConstructorParameters

**Description:**
Retrieves the parameters of a constructor.

**Parameters:**
- `constructor` (Constructor<?>): The constructor from which to retrieve parameters.

**Returns:**
- `Parameter[]`: An array of parameters of the constructor.

**Throws:**
- `NullPointerException`: If the constructor is null.

**Example Usage:**
```java
Parameter[] parameters = ReflectionUtils.getConstructorParameters(myConstructor);
```

---

### getConstructorModifiers

**Description:**
Retrieves the modifiers of a constructor.

**Parameters:**
- `constructor` (Constructor<?>): The constructor from which to retrieve modifiers.

**Returns:**
- `int`: An integer representing the modifiers of the constructor.

**Throws:**
- `NullPointerException`: If the constructor is null.

**Example Usage:**
```java
int modifiers = ReflectionUtils.getConstructorModifiers(myConstructor);
```

---

### getConstructors

**Description:**
Retrieves all public constructors of the specified class.

**Parameters:**
- `clazz` (Class<?>): The class from which to retrieve constructors.

**Returns:**
- `Constructor<?>[]`: An array of public constructors of the specified class.

**Throws:**
- `NullPointerException`: If the class is null.

**Example Usage:**
```java
Constructor<?>[] constructors = ReflectionUtils.getConstructors(MyClass.class);
```

---

### getDeclaredConstructors

**Description:**
Retrieves all declared constructors of the specified class, including public, protected, default (package), and private constructors.

**Parameters:**
- `clazz` (Class<?>): The class from which to retrieve declared constructors.

**Returns:**
- `Constructor<?>[]`: An array of declared constructors of the specified class.

**Throws:**
- `NullPointerException`: If the class is null.

**Example Usage:**
```java
Constructor<?>[] constructors = ReflectionUtils.getDeclaredConstructors(MyClass.class);
```

---

# Field Utils

## Methods

### getAllPrivateFields

**Description:**
Retrieves all private fields of a given class, including fields declared in its superclasses.

**Parameters:**
- `clazz` (Class<?>): The class from which to retrieve private fields.

**Returns:**
- `List<Field>`: A list of all private fields of the specified class.

**Throws:**
- `NullPointerException`: If the clazz is null.

**Example Usage:**
```java
List<Field> privateFields = ReflectionUtils.getAllPrivateFields(MyClass.class);
```

---

### getAllPrivateFieldsMap

**Description:**
Retrieves all private fields of a given class, including fields declared in its superclasses, and returns them as a map with field names as keys.

**Parameters:**
- `clazz` (Class<?>): The class from which to retrieve private fields.

**Returns:**
- `Map<String, Field>`: A map of all private fields of the specified class with field names as keys.

**Throws:**
- `NullPointerException`: If the clazz is null.

**Example Usage:**
```java
Map<String, Field> privateFieldsMap = ReflectionUtils.getAllPrivateFieldsMap(MyClass.class);
```

---

### getFieldsMap

**Description:**
Helper method to convert a list of fields to a map with field names as keys.

**Parameters:**
- `fields` (List<Field>): The list of fields to convert to a map.

**Returns:**
- `Map<String, Field>`: A map with field names as keys and Field objects as values.

**Example Usage:**
```java
Map<String, Field> fieldsMap = ReflectionUtils.getFieldsMap(fieldsList);
```

---

### getAllAnnotatedFields

**Description:**
Retrieves all fields annotated with a specific annotation from a given class, including fields declared in its superclasses.

**Parameters:**
- `type` (Class<?>): The class from which to retrieve annotated fields.
- `annotation` (Class<? extends Annotation>): The annotation class to search for.

**Returns:**
- `List<Field>`: A list of fields annotated with the specified annotation.

**Throws:**
- `NullPointerException`: If type or annotation is null.

**Example Usage:**
```java
List<Field> annotatedFields = ReflectionUtils.getAllAnnotatedFields(MyClass.class, MyAnnotation.class);
```

---

### getFieldType

**Description:**
Retrieves the type of a specified field in the given class.

**Parameters:**
- `clazz` (Class<?>): The class from which the field type is to be retrieved.
- `fieldName` (String): The name of the field whose type is to be retrieved.

**Returns:**
- `Class<?>`: The type of the specified field.

**Throws:**
- `NoSuchFieldException`: If the specified field does not exist.
- `NullPointerException`: If the clazz or fieldName is null.

**Example Usage:**
```java
Class<?> fieldType = ReflectionUtils.getFieldType(MyClass.class, "myField");
```

---

### getFieldModifiers

**Description:**
Retrieves the modifiers of a specified field in the given class.

**Parameters:**
- `clazz` (Class<?>): The class from which the field modifiers are to be retrieved.
- `fieldName` (String): The name of the field whose modifiers are to be retrieved.

**Returns:**
- `int`: The modifiers of the specified field.

**Throws:**
- `NoSuchFieldException`: If the specified field does not exist.
- `NullPointerException`: If the clazz or fieldName is null.

**Example Usage:**
```java
int modifiers = ReflectionUtils.getFieldModifiers(MyClass.class, "myField");
```

---

### isFieldFinal

**Description:**
Checks if a specified field in the given class is final.

**Parameters:**
- `clazz` (Class<?>): The class from which the field is to be checked.
- `fieldName` (String): The name of the field to be checked.

**Returns:**
- `boolean`: True if the specified field is final, false otherwise.

**Throws:**
- `NoSuchFieldException`: If the specified field does not exist.
- `NullPointerException`: If the clazz or fieldName is null.

**Example Usage:**
```java
boolean isFinal = ReflectionUtils.isFieldFinal(MyClass.class, "myField");
```

---

### isFieldStatic

**Description:**
Checks if a specified field in the given class is static.

**Parameters:**
- `clazz` (Class<?>): The class from which the field is to be checked.
- `fieldName` (String): The name of the field to be checked.

**Returns:**
- `boolean`: True if the specified field is static, false otherwise.

**Throws:**
- `NoSuchFieldException`: If the specified field does not exist.
- `NullPointerException`: If the clazz or fieldName is null.

**Example Usage:**
```java
boolean isStatic = ReflectionUtils.isFieldStatic(MyClass.class, "myField");
```

---

### setFieldAccessible

**Description:**
Sets a specified field in the given class to be accessible.

**Parameters:**
- `clazz` (Class<?>): The class containing the field.
- `fieldName` (String): The name of the field to be set accessible.

**Throws:**
- `NoSuchFieldException`: If the specified field does not exist.
- `NullPointerException`: If the clazz or fieldName is null.

**Example Usage:**
```java
ReflectionUtils.setFieldAccessible(MyClass.class, "myField");
```

---

### isFieldAnnotated

**Description:**
Checks if a field is annotated with a specific annotation.

**Parameters:**
- `field` (Field): The field to check.
- `annotationClass` (Class<T>): The annotation class to look for.

**Returns:**
- `boolean`: True if the field is annotated with the specified annotation, false otherwise.

**Throws:**
- `NullPointerException`: If the field or annotationClass is null.

**Example Usage:**
```java
boolean isAnnotated = ReflectionUtils.isFieldAnnotated(myField, MyAnnotation.class);
```

---

### isFieldExactAnnotated

**Description:**
Checks if a field is exactly annotated with a specific annotation.

**Parameters:**
- `field` (Field): The field to check.
- `annotationClass` (Class<T>): The annotation class to look for.

**Returns:**
- `boolean`: True if the field is exactly annotated with the specified annotation, false otherwise.

**Throws:**
- `NullPointerException`: If the field or annotationClass is null.

**Example Usage:**
```java
boolean isExactAnnotated = ReflectionUtils.isFieldExactAnnotated(myField, MyAnnotation.class);
```

---

### getAllFields

**Description:**
Retrieves all fields of a given class, including fields declared in its superclasses.

**Parameters:**
- `type` (Class<?>): The class from which to retrieve fields.

**Returns:**
- `List<Field>`: A list of all fields of the specified class.

**Throws:**
- `NullPointerException`: If the type is null.

**Example Usage:**
```java
List<Field> fields = ReflectionUtils.getAllFields(MyClass.class);
```

---

### getAllFieldsMap

**Description:**
Retrieves all fields of a given class, including fields declared in its superclasses, and returns them as a map with field names as keys.

**Parameters:**
- `clazz` (Class<?>): The class from which to retrieve fields.

**Returns:**
- `Map<String, Field>`: A map of all fields of the specified class with field names as keys.

**Throws:**
- `NullPointerException`: If the clazz is null.

**Example Usage:**
```java
Map<String, Field> fieldsMap = ReflectionUtils.getAllFieldsMap(MyClass.class);
```

---

### readField

**Description:**
Reads the value of a field from an object.

**Parameters:**
- `object` (Object): The object from which to read the field.
- `fieldName` (String): The name of the field to read.

**Returns:**
- `Object`: The value of the field in the object.

**Throws:**
- `FieldAccessException`: If the field cannot be accessed.

**Example Usage:**
```java
Object value = ReflectionUtils.readField(myObject, "myField");
```

---

### clearUnselectedFields

**Description:**
Clears the values of unselected fields of the given object. For each field of the object's class, if the field name is not present in the specified collection of selected fields, the field value is set to null.

**Parameters:**
- `object` (Object): The object whose fields are to be cleared.
- `selectedFields` (Collection<String>): A collection containing the names of the fields to keep.

**Throws:**
- `IllegalArgumentException`: If the object is null.

**Example Usage:**
```java
ReflectionUtils.clearUnselectedFields(myObject, selectedFields);
```

---

# General Utils

### isInterface

**Description:**
Checks if a given class is an interface.

**Parameters:**
- `clazz` (Class<?>): The class to check.

**Returns:**
- `boolean`: True if the class is an interface, false otherwise.

**Example Usage:**
```java
boolean isInterface = ReflectionUtils.isInterface(MyClass.class);
```

---

### isArray

**Description:**
Checks if a given class is an array.

**Parameters:**
- `clazz` (Class<?>): The class to check.

**Returns:**
- `boolean`: True if the class is an array, false otherwise.

**Example Usage:**
```java
boolean isArray = ReflectionUtils.isArray(MyClass.class);
```

---

### isEnum

**Description:**
Checks if a given class is an enum.

**Parameters:**
- `clazz` (Class<?>): The class to check.

**Returns:**
- `boolean`: True if the class is an enum, false otherwise.

**Example Usage:**
```java
boolean isEnum = ReflectionUtils.isEnum(MyEnum.class);
```

---

### isAnnotation

**Description:**
Checks if a given class is an annotation.

**Parameters:**
- `clazz` (Class<?>): The class to check.

**Returns:**
- `boolean`: True if the class is an annotation, false otherwise.

**Example Usage:**
```java
boolean isAnnotation = ReflectionUtils.isAnnotation(MyAnnotation.class);
```

---

### isAnonymousClass

**Description:**
Checks if a given class is anonymous.

**Parameters:**
- `clazz` (Class<?>): The class to check.

**Returns:**
- `boolean`: True if the class is anonymous, false otherwise.

**Example Usage:**
```java
boolean isAnonymous = ReflectionUtils.isAnonymousClass(MyClass.class);
```

---

### getInnerClasses

**Description:**
Retrieves the inner classes declared within a class.

**Parameters:**
- `clazz` (Class<?>): The class to check.

**Returns:**
- `Class<?>[]`: An array of inner classes declared within the class.

**Example Usage:**
```java
Class<?>[] innerClasses = ReflectionUtils.getInnerClasses(MyClass.class);
```

---

# Invocation Utils

### invokeMethod

**Description:**
Invokes a method on an object.

**Parameters:**
- `objectToInvokeOn` (Object): The object to invoke the method on.
- `methodName` (String): The name of the method to invoke.
- `parameterTypes` (Class<?>[]): The parameter types of the method.
- `args` (Object[]): The arguments to pass to the method.

**Returns:**
- `Object`: The result of the method invocation.

**Throws:**
- `MethodInvokeException`: If an error occurs during method invocation.

**Example Usage:**
```java
Object result = ReflectionUtils.invokeMethod(myObject, "myMethod", new Class<?>[]{String.class}, new Object[]{"argument"});
```

---

### invokeSingleMethod

**Description:**
Invokes a single-parameter method on an object.

**Parameters:**
- `objectToInvokeOn` (Object): The object to invoke the method on.
- `methodName` (String): The name of the method to invoke.
- `parameterType` (Class<?>): The type of the parameter of the method.
- `parameter` (Object): The parameter value to pass to the method.

**Returns:**
- `Object`: The result of the method invocation.

**Throws:**
- `MethodInvokeException`: If an error occurs during method invocation.

**Example Usage:**
```java
Object result = ReflectionUtils.invokeSingleMethod(myObject, "myMethod", String.class, "argument");
```

---

### invokeInstance

**Description:**
Instantiates a class without constructor arguments.

**Parameters:**
- `className` (String): The name of the class to instantiate.

**Returns:**
- `Object`: The new instance of the class.

**Throws:**
- `InstanceInvocationException`: If an error occurs during instance invocation.

**Example Usage:**
```java
Object instance = ReflectionUtils.invokeInstance("com.example.MyClass");
```

---

### invokeInstance (with arguments)

**Description:**
Instantiates a class with constructor arguments.

**Parameters:**
- `classFullName` (String): The fully qualified name of the class to instantiate.
- `args` (Object[]): The arguments to pass to the constructor.

**Returns:**
- `Object`: The new instance of the class.

**Throws:**
- `InstanceInvocationException`: If an error occurs during instance invocation.

**Example Usage:**
```java
Object instance = ReflectionUtils.invokeInstance("com.example.MyClass", "arg1", 42);
```

---

### invokeInstance (generic)

**Description:**
Instantiates a class with constructor arguments.

**Parameters:**
- `clazz` (Class<T>): The class to instantiate.
- `args` (Object[]): The arguments to pass to the constructor.
- `<T>`: The type of the class to instantiate.

**Returns:**
- `<T>`: The new instance of the class.

**Throws:**
- `InstanceInvocationException`: If an error occurs during instance invocation.

**Example Usage:**
```java
MyClass instance = ReflectionUtils.invokeInstance(MyClass.class, "arg1", 42);
```

---

### getArrayValuesTypesByArgs

**Description:**
Gets the types of the arguments.

**Parameters:**
- `args` (Object[]): The arguments.

**Returns:**
- `Class<?>[]`: An array of argument types.

**Example Usage:**
```java
Class<?>[] argTypes = ReflectionUtils.getArrayValuesTypesByArgs(new Object[]{"arg1", 42});
```

---

### getAccessibleConstructor

**Description:**
Gets a constructor with accessible flag set.

**Parameters:**
- `contTypes` (Class<?>[]): The types of the constructor parameters.
- `clazz` (Class<T>): The class.
- `<T>`: The type of the class.

**Returns:**
- `Constructor<T>`: The constructor.

**Throws:**
- `NoSuchMethodException`: If the constructor is not found.

**Example Usage:**
```java
Constructor<MyClass> constructor = ReflectionUtils.getAccessibleConstructor(new Class<?>[]{String.class, int.class}, MyClass.class);
```

---

# Method Enhancements Utils

### getAnnotatedMethods

**Description:**
Retrieves methods annotated with a specific annotation.

**Parameters:**
- `clazz` (Class<?>): The class from which to retrieve methods.
- `annotationClass` (Class<? extends Annotation>): The Class object corresponding to the annotation type.

**Returns:**
- `List<Method>`: A list of methods annotated with the specified annotation.

**Throws:**
- `NullPointerException`: If the clazz or annotationClass is null.

**Example Usage:**
```java
List<Method> annotatedMethods = ReflectionUtils.getAnnotatedMethods(MyClass.class, MyAnnotation.class);
```

---

### getAnnotatedConstructors

**Description:**
Retrieves constructors annotated with a specific annotation.

**Parameters:**
- `clazz` (Class<?>): The class from which to retrieve constructors.
- `annotationClass` (Class<? extends Annotation>): The Class object corresponding to the annotation type.

**Returns:**
- `List<Constructor<?>>`: A list of constructors annotated with the specified annotation.

**Throws:**
- `NullPointerException`: If the clazz or annotationClass is null.

**Example Usage:**
```java
List<Constructor<?>> annotatedConstructors = ReflectionUtils.getAnnotatedConstructors(MyClass.class, MyAnnotation.class);
```

# Methods Utils

### getParameterTypes

**Description:**
Retrieves the parameter types of the given method.

**Parameters:**
- `method` (Method): The method whose parameter types are to be retrieved.

**Returns:**
- `Class<?>[]`: An array of Classes representing the parameter types of the method.

**Throws:**
- `IllegalArgumentException`: If the provided method is null.

**Example Usage:**
```java
Class<?>[] paramTypes = ReflectionUtils.getParameterTypes(myMethod);
```

---

### getReturnType

**Description:**
Gets the return type of the given method.

**Parameters:**
- `method` (Method): The method whose return type is to be retrieved.

**Returns:**
- `Class<?>`: The Class representing the return type of the method.

**Throws:**
- `IllegalArgumentException`: If the provided method is null.

**Example Usage:**
```java
Class<?> returnType = ReflectionUtils.getReturnType(myMethod);
```

---

### getExceptionTypes

**Description:**
Gets the types of exceptions thrown by the given method.

**Parameters:**
- `method` (Method): The method whose exception types are to be retrieved.

**Returns:**
- `Class<?>[]`: An array of Classes representing the exception types thrown by the method.

**Throws:**
- `IllegalArgumentException`: If the provided method is null.

**Example Usage:**
```java
Class<?>[] exceptionTypes = ReflectionUtils.getExceptionTypes(myMethod);
```

---

### getMethodModifiers

**Description:**
Retrieves the modifiers of the given method.

**Parameters:**
- `method` (Method): The method whose modifiers are to be retrieved.

**Returns:**
- `int`: An int representing the modifiers of the method.

**Throws:**
- `IllegalArgumentException`: If the provided method is null.

**Example Usage:**
```java
int modifiers = ReflectionUtils.getMethodModifiers(myMethod);
```

---

### isMethodVarArgs

**Description:**
Checks if the given method takes a variable number of arguments.

**Parameters:**
- `method` (Method): The method to be checked.

**Returns:**
- `boolean`: True if the method takes a variable number of arguments, false otherwise.

**Throws:**
- `IllegalArgumentException`: If the provided method is null.

**Example Usage:**
```java
boolean isVarArgs = ReflectionUtils.isMethodVarArgs(myMethod);
```

---

### getDefaultValue

**Description:**
Gets the default value of the given method's annotation element.

**Parameters:**
- `method` (Method): The method whose annotation element's default value is to be retrieved.

**Returns:**
- `Object`: The default value of the annotation element, or null if none.

**Throws:**
- `IllegalArgumentException`: If the provided method is null.

**Example Usage:**
```java
Object defaultValue = ReflectionUtils.getDefaultValue(myMethod);
```

---

### getAllPrivateMethods

**Description:**
Retrieves all private methods of a class.

**Parameters:**
- `clazz` (Class<?>): The class from which to retrieve methods.

**Returns:**
- `List<Method>`: A list of private methods of the specified class.

**Throws:**
- `NullPointerException`: If the clazz is null.

**Example Usage:**
```java
List<Method> privateMethods = ReflectionUtils.getAllPrivateMethods(MyClass.class);
```

---

### getAllPublicProtectedMethods

**Description:**
Retrieves all public and protected methods of a class.

**Parameters:**
- `clazz` (Class<?>): The class from which to retrieve methods.

**Returns:**
- `List<Method>`: A list of public and protected methods of the specified class.

**Throws:**
- `NullPointerException`: If the clazz is null.

**Example Usage:**
```java
List<Method> publicProtectedMethods = ReflectionUtils.getAllPublicProtectedMethods(MyClass.class);
```

---

### getAllPublicMethods

**Description:**
Retrieves all public methods of a class.

**Parameters:**
- `clazz` (Class<?>): The class from which to retrieve methods.

**Returns:**
- `List<Method>`: A list of public methods of the specified class.

**Throws:**
- `NullPointerException`: If the clazz is null.

**Example Usage:**
```java
List<Method> publicMethods = ReflectionUtils.getAllPublicMethods(MyClass.class);
```

---

### getAllMethodsWithModifiers

**Description:**
Retrieves all methods of a class that match the given modifiers.

**Parameters:**
- `clazz` (Class<?>): The class from which to retrieve methods.
- `modifiers` (List<IntPredicate>): The list of predicates to match the method modifiers.

**Returns:**
- `List<Method>`: A list of methods that match the given modifiers.

**Throws:**
- `NullPointerException`: If the clazz or modifiers are null.

**Example Usage:**
```java
List<Method> methodsWithModifiers = ReflectionUtils.getAllMethodsWithModifiers(MyClass.class, modifiersList);
```

---

### getDefaultMethodsOfInterfaces

**Description:**
Retrieves all default methods from the interfaces implemented by the specified class.

**Parameters:**
- `clazz` (Class<?>): The class whose interfaces' default methods are to be retrieved.

**Returns:**
- `List<Method>`: A list of default methods from the interfaces implemented by the specified class.

**Throws:**
- `IllegalArgumentException`: If the class parameter is null.

**Example Usage:**
```java
List<Method> defaultMethods = ReflectionUtils.getDefaultMethodsOfInterfaces(MyClass.class);
```

---

### getDeclaredMethods

**Description:**
Retrieves all declared methods of the specified class, including default methods from its interfaces.

**Parameters:**
- `clazz` (Class<?>): The class whose declared methods and default interface methods are to be retrieved.

**Returns:**
- `Method[]`: An array of Method objects reflecting all declared methods of the class, including default methods from its interfaces.

**Throws:**
- `IllegalArgumentException`: If the class parameter is null.
- `IllegalStateException`: If an error occurs while retrieving the methods.

**Example Usage:**
```java
Method[] declaredMethods = ReflectionUtils.getDeclaredMethods(MyClass.class);
```

---

### getDeclaredMethodsList

**Description:**
Retrieves all declared methods of the specified class, including default methods from its interfaces, and returns them as a list.

**Parameters:**
- `clazz` (Class<?>): The class whose declared methods and default interface methods are to be retrieved.

**Returns:**
- `List<Method>`: A list of Method objects reflecting all declared methods of the class, including default methods from its interfaces.

**Throws:**
- `IllegalArgumentException`: If the class parameter is null.
- `IllegalStateException`: If an error occurs while retrieving the methods.

**Example Usage:**
```java
List<Method> declaredMethodsList = ReflectionUtils.getDeclaredMethodsList(MyClass.class);
```

---

### findMethodByName

**Description:**
Finds a method by name in the specified class or its superclasses and interfaces.

**Parameters:**
- `clazz` (Class<?>): The class in which to search for the method.
- `name` (String): The name of the method to search for.

**Returns:**
- `Method`: The Method object if a method with the specified name is found, or null if not found.

**Throws:**
- `IllegalArgumentException`: If the class or method name parameter is null.

**Example Usage:**
```java
Method method = ReflectionUtils.findMethodByName(MyClass.class, "methodName");
```

---

# Creation Instance

### newInstance

**Description:**
Creates a new instance of a class using its no-argument constructor.

**Parameters:**
- `clazz` (Class<T>): The class of which to create an instance.

**Returns:**
- `<T>`: A new instance of the specified class.

**Throws:**
- `NullPointerException`: If the class is null.
- `InstantiationException`: If the class represents an abstract class, an interface, an array class, a primitive type, or void; or if the class has no nullary constructor.
- `IllegalAccessException`: If the class or its nullary constructor is not accessible.
- `InvocationTargetException`: If the nullary constructor throws an exception.

**Example Usage:**
```java
MyClass instance = ReflectionUtils.newInstance(MyClass.class);
```

---

# Miscellaneous Methods

### getArrayComponentType

**Description:**
Retrieves the component type of an array class.

**Parameters:**
- `arrayClass` (Class<?>): The array class.

**Returns:**
- `Class<?>`: The component type of the array class.

**Throws:**
- `NullPointerException`: If the array class is null.

**Example Usage:**
```java
Class<?> componentType = ReflectionUtils.getArrayComponentType(MyArrayClass.class);
```

---

# Object utils

### isFieldPrimitiveType

**Description:**
Checks if the type of a field is a primitive type or a wrapper class.

**Parameters:**
- `field` (Field): The field to check.

**Returns:**
- `boolean`: True if the type of the field is a primitive type or a wrapper class, false otherwise.

**Example Usage:**
```java
boolean isPrimitive = ReflectionUtils.isFieldPrimitiveType(myField);
```

---

### copy

**Description:**
Creates a deep copy of the given object.

**Parameters:**
- `object` (Object): The object to be copied.

**Returns:**
- `Object`: The deep copy of the object.

**Throws:**
- `IllegalStateException`: If copying fails.



**Example Usage:**
```java
MyClass copiedObject = (MyClass) ReflectionUtils.copy(originalObject);
```

---

# Package Utils

### getClassesByPackage

**Description:**
Retrieves all classes within a package.

**Parameters:**
- `packageName` (String): The name of the package.

**Returns:**
- `List<Class<?>>`: A list of classes within the specified package.

**Throws:**
- `ClassNotFoundException`: If a class cannot be found.
- `IOException`: If an I/O error occurs.
- `URISyntaxException`: If a URI syntax error occurs.

**Example Usage:**
```java
List<Class<?>> classes = ReflectionUtils.getClassesByPackage("com.example.package");
```

---

### getClassesByDirectoryAndPackage

**Description:**
Retrieves all classes within a directory and its subdirectories.

**Parameters:**
- `directory` (File): The directory to search for classes.
- `packageName` (String): The name of the package.

**Returns:**
- `List<Class<?>>`: A list of classes within the specified directory and package.

**Throws:**
- `ClassNotFoundException`: If a class cannot be found.

**Example Usage:**
```java
List<Class<?>> classes = ReflectionUtils.getClassesByDirectoryAndPackage(new File("src/com/example/package"), "com.example.package");
```

---

### getAllAnnotatedClassesByPackage

**Description:**
Retrieves all classes within a package that are annotated with a specific annotation.

**Parameters:**
- `packageName` (String): The name of the package.
- `annotation` (Class): The annotation to filter classes by.

**Returns:**
- `List<Class<?>>`: A list of classes within the specified package that are annotated with the specified annotation.

**Throws:**
- `IOException`: If an I/O error occurs.
- `URISyntaxException`: If a URI syntax error occurs.
- `ClassNotFoundException`: If a class cannot be found.

**Example Usage:**
```java
List<Class<?>> annotatedClasses = ReflectionUtils.getAllAnnotatedClassesByPackage("com.example.package", MyAnnotation.class);
```

---

# Security Utils

### setMethodAccessible

**Description:**
Sets a method to be accessible.

**Parameters:**
- `method` (Method): The method to be set accessible.

**Throws:**
- `NullPointerException`: If the method is null.

**Example Usage:**
```java
ReflectionUtils.setMethodAccessible(myMethod);
```

---

### setConstructorAccessible

**Description:**
Sets a constructor to be accessible.

**Parameters:**
- `constructor` (Constructor<?>): The constructor to be set accessible.

**Throws:**
- `NullPointerException`: If the constructor is null.

**Example Usage:**
```java
ReflectionUtils.setConstructorAccessible(myConstructor);
```



### Changelog

#### 0.0.3:
- initial release

**Installation**
Include this library in your project by adding the respective files to your classpath.

**Requirements**
Java 8 or higher is required to use this library.

**License**
This library is licensed under the MIT License.
