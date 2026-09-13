# Constructors

Inspect constructors, their parameters and modifiers.

[← Back to documentation index](README.md)

## Contents

- [`getConstructorParameters`](#getconstructorparameters)
- [`getConstructorModifiers`](#getconstructormodifiers)
- [`getConstructors`](#getconstructors)
- [`getDeclaredConstructors`](#getdeclaredconstructors)


## getConstructorParameters

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

## getConstructorModifiers

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

## getConstructors

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

## getDeclaredConstructors

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
