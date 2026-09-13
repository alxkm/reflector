# Annotations

Read and inspect annotations on classes, methods, fields and method parameters.

[← Back to documentation index](README.md)

## Contents

- [`getClassAnnotations`](#getclassannotations)
- [`getAnnotationsByType`](#getannotationsbytype)
- [`getDeclaredAnnotations`](#getdeclaredannotations)
- [`getMethodDeclaredAnnotations`](#getmethoddeclaredannotations)
- [`getMethodsDeclaredAnnotations`](#getmethodsdeclaredannotations)
- [`isAnnotationOnClassPresent`](#isannotationonclasspresent)
- [`isMethodParameterAnnotated`](#ismethodparameterannotated)
- [`getFieldAnnotations`](#getfieldannotations)
- [`isMethodAnnotated`](#ismethodannotated)


## getClassAnnotations

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

## getAnnotationsByType

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

## getDeclaredAnnotations

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

## getMethodDeclaredAnnotations

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

## getMethodsDeclaredAnnotations

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

## isAnnotationOnClassPresent

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

## isMethodParameterAnnotated

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

## getFieldAnnotations

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

## isMethodAnnotated

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
