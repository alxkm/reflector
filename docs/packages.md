# Packages

Scan packages on the classpath and find annotated classes.

[← Back to documentation index](README.md)

## Contents

- [`getClassesByPackage`](#getclassesbypackage)
- [`getClassesByDirectoryAndPackage`](#getclassesbydirectoryandpackage)
- [`getAllAnnotatedClassesByPackage`](#getallannotatedclassesbypackage)


## getClassesByPackage

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

## getClassesByDirectoryAndPackage

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

## getAllAnnotatedClassesByPackage

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
