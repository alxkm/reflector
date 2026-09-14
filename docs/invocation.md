# Invocation and instantiation

Invoke methods and create instances reflectively.

[← Back to documentation index](README.md)

## Contents

- [`invokeMethod`](#invokemethod)
- [`invokeSingleMethod`](#invokesinglemethod)
- [`invokeInstance`](#invokeinstance)
- [`invokeInstance (with arguments)`](#invokeinstance-with-arguments)
- [`invokeInstance (generic)`](#invokeinstance-generic)
- [`getArrayValuesTypesByArgs`](#getarrayvaluestypesbyargs)
- [`getAccessibleConstructor`](#getaccessibleconstructor)
- [`newInstance`](#newinstance)


## invokeMethod

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

## invokeSingleMethod

**Note:** resolves public methods only. Use `invokeMethod` to reach a private one.

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

## invokeInstance

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

## invokeInstance (with arguments)

**Note:** the constructor is resolved from the runtime types of the arguments. An exact
match on the declared parameter types is preferred; failing that the declared constructors
are scanned for one that can accept the arguments, which covers primitive parameters and
subtypes. The first applicable constructor wins, so use `getAccessibleConstructor` directly
when a class has ambiguous overloads.

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

## invokeInstance (generic)

**Note:** the constructor is resolved from the runtime types of the arguments. An exact
match on the declared parameter types is preferred; failing that the declared constructors
are scanned for one that can accept the arguments, which covers primitive parameters and
subtypes. The first applicable constructor wins, so use `getAccessibleConstructor` directly
when a class has ambiguous overloads.

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

## getArrayValuesTypesByArgs

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

## getAccessibleConstructor

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

## newInstance

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
