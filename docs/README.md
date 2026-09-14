# Reflector documentation

Full API reference for [Reflector](../README.md). Every method listed here is available
through the `ReflectionUtils` facade and through the focused utility class it belongs to.

## Reference

| Page | What it covers | Utility classes |
|------|----------------|-----------------|
| [Annotations](annotations.md) | Annotations on classes, methods, fields and parameters | `AnnotationUtils`, `MethodEnhancementsUtils` |
| [Fields](fields.md) | Reading, filtering and modifying fields | `FieldUtils`, `FieldsExtraUtils` |
| [Methods](methods.md) | Method lookup, signatures, modifier filtering | `MethodUtils`, `MethodEnhancementsUtils` |
| [Constructors](constructors.md) | Constructor lookup, parameters and modifiers | `ConstructorUtils` |
| [Invocation and instantiation](invocation.md) | Calling methods and creating instances | `InvokeUtils`, `MiscellaneousUtils` |
| [Classes and objects](classes.md) | Type checks, inner classes, object copying | `GeneralUtils`, `ClassBasicUtils`, `ObjectUtils` |
| [Packages](packages.md) | Classpath and package scanning | `PackageUtils` |
| [Accessibility](security.md) | Making members accessible | `SecurityUtils` |

## Two ways to call the same code

`ReflectionUtils` is a facade that delegates to the focused utility classes. Use whichever
reads better in your code - the behaviour is identical.

```java
// Through the facade
List<Field> fields = ReflectionUtils.getAllPrivateFields(Person.class);

// Through the focused utility
List<Field> fields = FieldUtils.getAllPrivateFields(Person.class);
```

`ReflectionUtilsLegacy` keeps the pre-split API of early versions. It is `@Deprecated` and
still shipped so existing code keeps compiling, but it gets no fixes and no new methods.

## Deprecated methods

| Deprecated | Use instead | Why |
|------------|-------------|-----|
| `isFieldExactAnnotated` | `isFieldAnnotated` | Identical behaviour. `isAnnotationPresent(x)` is defined by the JDK as `getAnnotation(x) != null`, and a field annotation is never inherited, so there is nothing for "exact" to distinguish. |
| `isFieldPrimitiveType` | `isSimpleValueType` | The name says primitive but the check also answers true for the wrapper classes and for `String`. |
| `ReflectionUtilsLegacy` | `ReflectionUtils` or a focused utility | Superseded by the split API. |

## Conventions

- All utility classes are `final` with a private constructor. Everything is `static`.
- A `null` argument fails fast with `NullPointerException` instead of returning `null`.
- Invocation and instantiation helpers wrap reflective failures in the library's own
  unchecked exceptions - `FieldAccessException`, `MethodInvokeException` and
  `InstanceInvocationException` - so a call site does not need a `try`/`catch` block.
- Lookup helpers that mirror a single JDK call (`getFieldType`, `isFieldFinal`, ...)
  propagate the checked exception the JDK throws, such as `NoSuchFieldException`.
- Methods that return a collection return an empty one, never `null`.
