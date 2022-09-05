# Reflector <img src="https://www.svgrepo.com/show/144446/mirror-horizontally.svg" height="32px" alt="Reflector" />

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

[![Build Status](https://app.travis-ci.com/alxkm/reflector.svg?branch=master)](https://app.travis-ci.com/alxkm/reflector)

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


### Changelog

#### 0.0.1:
- initial release