# Accessibility

Make methods and constructors accessible before reflective calls.

[← Back to documentation index](README.md)

## Contents

- [`setMethodAccessible`](#setmethodaccessible)
- [`setConstructorAccessible`](#setconstructoraccessible)


## setMethodAccessible

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

## setConstructorAccessible

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
