# Fields

Read, filter and modify fields, including private and inherited ones.

[← Back to documentation index](README.md)

## Contents

- [`getAllPrivateFields`](#getallprivatefields)
- [`getAllPrivateFieldsMap`](#getallprivatefieldsmap)
- [`getFieldsMap`](#getfieldsmap)
- [`getAllAnnotatedFields`](#getallannotatedfields)
- [`getFieldType`](#getfieldtype)
- [`getFieldModifiers`](#getfieldmodifiers)
- [`isFieldFinal`](#isfieldfinal)
- [`isFieldStatic`](#isfieldstatic)
- [`setFieldAccessible`](#setfieldaccessible)
- [`isFieldAnnotated`](#isfieldannotated)
- [`isFieldExactAnnotated`](#isfieldexactannotated)
- [`getAllFields`](#getallfields)
- [`getAllFieldsMap`](#getallfieldsmap)
- [`readField`](#readfield)
- [`clearUnselectedFields`](#clearunselectedfields)


## getAllPrivateFields

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

## getAllPrivateFieldsMap

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

## getFieldsMap

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

## getAllAnnotatedFields

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

## getFieldType

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

## getFieldModifiers

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

## isFieldFinal

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

## isFieldStatic

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

## setFieldAccessible

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

## isFieldAnnotated

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

## isFieldExactAnnotated

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

## getAllFields

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

## getAllFieldsMap

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

## readField

**Description:**
Reads the value of a field from an object. The field is looked up on the runtime class of
the object only - a field declared by a superclass is not found. Use `getAllFieldsMap` when
you need inherited fields.

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

## clearUnselectedFields

**Note:** only reference fields are cleared. A primitive field cannot be set to `null` and
keeps its value.

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
