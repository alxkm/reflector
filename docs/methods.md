# Methods

Look up methods, read their signatures and filter them by modifiers or annotations.

[← Back to documentation index](README.md)

## Contents

- [`getAnnotatedMethods`](#getannotatedmethods)
- [`getAnnotatedConstructors`](#getannotatedconstructors)
- [`getParameterTypes`](#getparametertypes)
- [`getReturnType`](#getreturntype)
- [`getExceptionTypes`](#getexceptiontypes)
- [`getMethodModifiers`](#getmethodmodifiers)
- [`isMethodVarArgs`](#ismethodvarargs)
- [`getDefaultValue`](#getdefaultvalue)
- [`getAllPrivateMethods`](#getallprivatemethods)
- [`getAllPublicProtectedMethods`](#getallpublicprotectedmethods)
- [`getAllPublicMethods`](#getallpublicmethods)
- [`getAllMethodsWithModifiers`](#getallmethodswithmodifiers)
- [`getDefaultMethodsOfInterfaces`](#getdefaultmethodsofinterfaces)
- [`getDeclaredMethods`](#getdeclaredmethods)
- [`getDeclaredMethodsList`](#getdeclaredmethodslist)
- [`findMethodByName`](#findmethodbyname)


## getAnnotatedMethods

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

## getAnnotatedConstructors

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

## getParameterTypes

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

## getReturnType

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

## getExceptionTypes

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

## getMethodModifiers

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

## isMethodVarArgs

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

## getDefaultValue

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

## getAllPrivateMethods

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

## getAllPublicProtectedMethods

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

## getAllPublicMethods

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

## getAllMethodsWithModifiers

**Description:**
Retrieves all methods of a class that match the given modifiers. A method is returned when
it matches any of the predicates, so the list is combined with OR, not AND.

Only methods declared by the class itself are considered - inherited methods are not
returned.

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

## getDefaultMethodsOfInterfaces

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

## getDeclaredMethods

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

## getDeclaredMethodsList

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

## findMethodByName

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
