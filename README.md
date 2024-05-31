# Reflector <img src="https://www.svgrepo.com/show/144446/mirror-horizontally.svg" height="32px" alt="Reflector" />

[![Build Status](https://app.travis-ci.com/alxkm/reflector.svg?branch=master)](https://app.travis-ci.com/alxkm/reflector)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

Reflector is small and simple reflection Java library.

Library main usage is:
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

Required java version is java 8

Specially not used java 8 features to keep more obvious code.
And formatting also not according to official java code style for such reason.

### Quick start


Usage example:

Get all private fields:

```java

List<Field> fields = ReflectionUtils.getAllPrivateFields(List.class);

```
Get all public and protected methods:

```java

List<Method> allPublicProtectedMethods = ReflectionUtils.getAllPublicProtectedMethods(List.class);

```
Get all private methods from java.util.List:

```java

List<Method> allPublicProtectedMethods = ReflectionUtils.getAllPrivateMethods(List.class);

```

Get all annotated fields with @Autowired annotation:

```java

List<Field> fields = ReflectionUtils.getAllAnnotatedFields(DataService.class, Autowired.class);

```

Get all annotated fields:

```java

String fullClassNameWithPackage = "org.data.model.User";

Object[] obj = {"Name", "Surname"};

User instance = (User) ReflectionUtils.invokeInstance(fullClassNameWithPackage, obj);

```

Get method by name:

```java

ReflectionUtil.findMethod(Person.class, "getId")

```
Get declared methods by class:

```java

List<Method> methods = ReflectionUtil.getDeclaredMethodsList(Person.class)

```

Get declared methods by class:

```java

Method[] methods = ReflectionUtil.getDeclaredMethods(Person.class)

```

Get declared methods by class:

```java

List<Method> methods = ReflectionUtil.findDefaultMethodsOnInterfaces(Person.class)

```

### Changelog

#### 0.0.1:
- initial release
