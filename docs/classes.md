# Classes and objects

Class names and packages, type checks, inner classes and object copying.

[← Back to documentation index](README.md)

## Contents

- [`getClassFullName`](#getclassfullname)
- [`getClassCanonicalName`](#getclasscanonicalname)
- [`getClassSimpleName`](#getclasssimplename)
- [`getPackage`](#getpackage)
- [`getClassFullNameByClass`](#getclassfullnamebyclass)
- [`getClassCanonicalNameByClass`](#getclasscanonicalnamebyclass)
- [`getClassSimpleNameByClass`](#getclasssimplenamebyclass)
- [`getPackageByClass`](#getpackagebyclass)
- [`getSuperClassNameForObject`](#getsuperclassnameforobject)
- [`getSuperClassNameByClass`](#getsuperclassnamebyclass)
- [`getSuperClass`](#getsuperclass)
- [`getEnclosingClass`](#getenclosingclass)
- [`getInterfaces`](#getinterfaces)
- [`isInterface`](#isinterface)
- [`isArray`](#isarray)
- [`isEnum`](#isenum)
- [`isAnnotation`](#isannotation)
- [`isAnonymousClass`](#isanonymousclass)
- [`getInnerClasses`](#getinnerclasses)
- [`getArrayComponentType`](#getarraycomponenttype)
- [`isFieldPrimitiveType`](#isfieldprimitivetype)
- [`copy`](#copy)

## getClassFullName

**Description:**
Gets the full name (including the package name) of the class of the given object.

**Parameters:**
- `obj` (Object): The object whose class full name is to be retrieved.

**Returns:**
- `String`: The full name of the class of the object.

**Throws:**
- `NullPointerException`: If the object is null.

**Example Usage:**
```java
String name = ReflectionUtils.getClassFullName(person); // com.example.Person
```

## getClassCanonicalName

**Description:**
Gets the canonical name of the class of the given object.

**Parameters:**
- `obj` (Object): The object whose class canonical name is to be retrieved.

**Returns:**
- `String`: The canonical name of the class of the object.

**Throws:**
- `NullPointerException`: If the object is null.

**Example Usage:**
```java
String name = ReflectionUtils.getClassCanonicalName(person);
```

## getClassSimpleName

**Description:**
Gets the simple name of the class of the given object.

**Parameters:**
- `obj` (Object): The object whose class simple name is to be retrieved.

**Returns:**
- `String`: The simple name of the class of the object.

**Throws:**
- `NullPointerException`: If the object is null.

**Example Usage:**
```java
String name = ReflectionUtils.getClassSimpleName(person); // Person
```

## getPackage

**Description:**
Gets the package name of the class of the given object.

**Parameters:**
- `obj` (Object): The object whose package name is to be retrieved.

**Returns:**
- `String`: The package name, or null if the class has no package.

**Throws:**
- `NullPointerException`: If the object is null.

**Example Usage:**
```java
String pkg = ReflectionUtils.getPackage(person); // com.example
```

## getClassFullNameByClass

**Description:**
Gets the full name (including the package name) of the given class.

**Parameters:**
- `clazz` (Class<?>): The class whose full name is to be retrieved.

**Returns:**
- `String`: The full name of the class, or an empty string if the class is null.

**Example Usage:**
```java
String name = ReflectionUtils.getClassFullNameByClass(Person.class);
```

## getClassCanonicalNameByClass

**Description:**
Gets the canonical name of the given class.

**Parameters:**
- `clazz` (Class<?>): The class whose canonical name is to be retrieved.

**Returns:**
- `String`: The canonical name of the class, or null if the class is null.

**Example Usage:**
```java
String name = ReflectionUtils.getClassCanonicalNameByClass(Person.class);
```

## getClassSimpleNameByClass

**Description:**
Gets the simple name of the given class.

**Parameters:**
- `clazz` (Class<?>): The class whose simple name is to be retrieved.

**Returns:**
- `String`: The simple name of the class.

**Throws:**
- `NullPointerException`: If the class is null.

**Example Usage:**
```java
String name = ReflectionUtils.getClassSimpleNameByClass(Person.class); // Person
```

## getPackageByClass

**Description:**
Gets the package name of the given class.

**Parameters:**
- `clazz` (Class<?>): The class whose package name is to be retrieved.

**Returns:**
- `String`: The package name, or null if the class has no package.

**Throws:**
- `NullPointerException`: If the class is null.

**Example Usage:**
```java
String pkg = ReflectionUtils.getPackageByClass(Person.class);
```

## getSuperClassNameForObject

**Description:**
Gets the name of the superclass of the given object's class.

**Parameters:**
- `obj` (Object): The object whose superclass name is to be retrieved.

**Returns:**
- `String`: The name of the superclass, or null if the class has no superclass.

**Throws:**
- `NullPointerException`: If the object is null.

**Example Usage:**
```java
String parent = ReflectionUtils.getSuperClassNameForObject(person);
```

## getSuperClassNameByClass

**Description:**
Retrieves the name of the superclass of the given class. Returns null when the class has no
superclass, which is the case for interfaces, primitive types and `java.lang.Object`.

**Parameters:**
- `clazz` (Class<?>): The class whose superclass name is to be retrieved.

**Returns:**
- `String`: The name of the superclass, or null if the class has no superclass.

**Throws:**
- `NullPointerException`: If the class is null.

**Example Usage:**
```java
String parent = ReflectionUtils.getSuperClassNameByClass(Person.class); // java.lang.Object
```

## getSuperClass

**Description:**
Retrieves the superclass of the given object's class.

**Parameters:**
- `obj` (Object): The object whose superclass is to be retrieved.

**Returns:**
- `Class<?>`: The superclass of the object's class.

**Throws:**
- `NullPointerException`: If the object is null.

**Example Usage:**
```java
Class<?> parent = ReflectionUtils.getSuperClass(person);
```

## getEnclosingClass

**Description:**
Retrieves the enclosing class of the given class if it is an inner class.

**Parameters:**
- `clazz` (Class<?>): The class whose enclosing class is to be retrieved.

**Returns:**
- `Class<?>`: The enclosing class, or null if the class is not an inner class.

**Throws:**
- `NullPointerException`: If the class is null.

**Example Usage:**
```java
Class<?> outer = ReflectionUtils.getEnclosingClass(Map.Entry.class); // java.util.Map
```

## getInterfaces

**Description:**
Retrieves the interfaces directly implemented by the given class.

**Parameters:**
- `clazz` (Class<?>): The class whose implemented interfaces are to be retrieved.

**Returns:**
- `List<Class<?>>`: The interfaces implemented by the class.

**Throws:**
- `NullPointerException`: If the class is null.

**Example Usage:**
```java
List<Class<?>> interfaces = ReflectionUtils.getInterfaces(ArrayList.class);
```

## isInterface

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

## isArray

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

## isEnum

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

## isAnnotation

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

## isAnonymousClass

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

## getInnerClasses

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

## getArrayComponentType

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

## isFieldPrimitiveType

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

## copy

**Note:** the class needs a no-argument constructor. Without one the call logs the failure
and returns `null`.

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
