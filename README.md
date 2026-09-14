<h1 align="center">
  <img src="https://www.svgrepo.com/show/144446/mirror-horizontally.svg" height="72px" alt="" /><br/>
  Reflector
</h1>

<p align="center">
  <b>A small, dependency-light toolkit that makes the Java Reflection API pleasant to use.</b>
</p>

<p align="center">
  <a href="https://github.com/alxkm/reflector/actions/workflows/gradle.yml"><img src="https://github.com/alxkm/reflector/actions/workflows/gradle.yml/badge.svg" alt="Build"></a>
  <a href="https://opensource.org/licenses/MIT"><img src="https://img.shields.io/badge/License-MIT-yellow.svg" alt="License: MIT"></a>
  <img src="https://img.shields.io/badge/Java-8%2B-orange.svg" alt="Java 8+">
  <a href="https://jitpack.io/#alxkm/reflector"><img src="https://jitpack.io/v/alxkm/reflector.svg" alt="JitPack"></a>
  <a href="https://codecov.io/gh/alxkm/reflector"><img src="https://codecov.io/gh/alxkm/reflector/branch/master/graph/badge.svg" alt="Coverage"></a>
</p>

<p align="center">
  <a href="docs/README.md">Documentation</a> &middot;
  <a href="https://alxkm.github.io/reflector/">Javadoc</a> &middot;
  <a href="CHANGELOG.md">Changelog</a>
</p>

---

Collecting the private fields of a class with plain `java.lang.reflect` means walking the
hierarchy by hand and filtering modifiers yourself. Reflector collapses that into a single
call, without pulling in a framework.

```java
// java.lang.reflect
List<Field> fields = new ArrayList<>();
for (Class<?> current = Person.class; current != null; current = current.getSuperclass()) {
    for (Field field : current.getDeclaredFields()) {
        if (Modifier.isPrivate(field.getModifiers())) {
            fields.add(field);
        }
    }
}

// Reflector
List<Field> fields = ReflectionUtils.getAllPrivateFields(Person.class);
```

## Installation

Releases are published through [JitPack](https://jitpack.io/#alxkm/reflector).

**Gradle**

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.alxkm:reflector:release.v0.0.1.1'
}
```

**Maven**

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.alxkm</groupId>
    <artifactId>reflector</artifactId>
    <version>release.v0.0.1.1</version>
</dependency>
```

Requires **Java 8 or newer**. The only runtime dependency is `slf4j-api`.

## Quick start

```java
package com.example;

public class Account {
    private String id;
}

public class Person extends Account {
    private final String name;
    private Integer age;

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    private String greet(String greeting) {
        return greeting + ", " + name;
    }
}
```

```java
import org.reflector.ReflectionUtils;

Person person = new Person("Ada", 36);

// Read a private field, no accessibility juggling
Object name = ReflectionUtils.readField(person, "name");            // "Ada"

// Every private field, superclasses included - name, age, id
List<Field> fields = ReflectionUtils.getAllPrivateFields(Person.class);

// The same fields keyed by name
Map<String, Field> byName = ReflectionUtils.getAllPrivateFieldsMap(Person.class);

// Call a private method
Object greeting = ReflectionUtils.invokeMethod(
        person, "greet", new Class<?>[]{String.class}, new Object[]{"Hello"});   // "Hello, Ada"

// Build an instance without touching Constructor directly
Person grace = ReflectionUtils.invokeInstance(Person.class, "Grace", 45);

// Class metadata
String simpleName = ReflectionUtils.getClassSimpleName(person);     // "Person"
String pkg = ReflectionUtils.getPackage(person);                    // "com.example"
String parent = ReflectionUtils.getSuperClassNameByClass(Person.class); // "com.example.Account"
```

## Recipes

<details>
<summary><b>Collect every field annotated with your annotation</b></summary>

```java
List<Field> auditable = ReflectionUtils.getAllAnnotatedFields(Order.class, Auditable.class);

for (Field field : auditable) {
    System.out.println(field.getName() + " -> " + field.getType().getSimpleName());
}
```

The returned fields are already accessible, and fields inherited from superclasses are
included.
</details>

<details>
<summary><b>Find annotated methods and call them</b></summary>

```java
for (Method method : ReflectionUtils.getAnnotatedMethods(service.getClass(), Startup.class)) {
    ReflectionUtils.setMethodAccessible(method);
    method.invoke(service);
}
```
</details>

<details>
<summary><b>Filter methods by modifier</b></summary>

```java
List<Method> publicOrProtected = ReflectionUtils.getAllMethodsWithModifiers(
        MyClass.class, Arrays.asList(Modifier::isPublic, Modifier::isProtected));
```

Predicates are combined with OR, so a method is returned when it matches any of them.
`getAllPrivateMethods`, `getAllPublicMethods` and `getAllPublicProtectedMethods` cover the
common cases without writing predicates.

Only methods declared by the class itself are considered - inherited methods are not
returned. This differs from the field helpers, which do walk the hierarchy.
</details>

<details>
<summary><b>Scan a package for annotated classes</b></summary>

```java
List<Class<?>> entities = ReflectionUtils.getAllAnnotatedClassesByPackage(
        "com.example.model", Entity.class);
```

Scanning walks the file system classpath. Classes packaged inside a jar are not found.
</details>

<details>
<summary><b>Strip everything but a whitelist of fields</b></summary>

```java
// Null out every field except id and name - handy before logging or serializing
ReflectionUtils.clearUnselectedFields(order, Arrays.asList("id", "name"));
```

Reference fields are set to `null` and primitive fields to their default value, since a
primitive cannot hold `null`. Static and final fields are left alone.
</details>

<details>
<summary><b>Copy an object field by field</b></summary>

```java
Person duplicate = (Person) ReflectionUtils.copy(person);
```

Primitives, their wrappers and `String` are copied by value, other fields are copied
recursively, and `final` fields are skipped. The class needs a no-argument constructor of
any visibility - without one the call throws `InstanceInvocationException`.
</details>

## What is in the box

Every method is reachable from the `ReflectionUtils` facade, and also from the focused
utility class it belongs to - pick whichever reads better at the call site.

| Area | Utility class | Documentation |
|------|---------------|---------------|
| Annotations on classes, methods, fields and parameters | `AnnotationUtils` | [annotations.md](docs/annotations.md) |
| Reading, filtering and clearing fields | `FieldUtils`, `FieldsExtraUtils` | [fields.md](docs/fields.md) |
| Method lookup, signatures, modifier filtering | `MethodUtils`, `MethodEnhancementsUtils` | [methods.md](docs/methods.md) |
| Constructor lookup, parameters and modifiers | `ConstructorUtils` | [constructors.md](docs/constructors.md) |
| Invoking methods and creating instances | `InvokeUtils`, `MiscellaneousUtils` | [invocation.md](docs/invocation.md) |
| Class names, type checks, object copying | `ClassBasicUtils`, `GeneralUtils`, `ObjectUtils` | [classes.md](docs/classes.md) |
| Package and classpath scanning | `PackageUtils` | [packages.md](docs/packages.md) |
| Making members accessible | `SecurityUtils` | [security.md](docs/security.md) |

Full reference: **[docs/](docs/README.md)**.

## Error handling

Reflector fails fast instead of returning `null` to signal a problem.

| Situation | Behaviour |
|-----------|-----------|
| `null` argument | `NullPointerException` |
| Field read fails, or no such field | `FieldAccessException` |
| Method invocation fails | `MethodInvokeException` |
| Instantiation fails, including `copy` on a class without a no-arg constructor | `InstanceInvocationException` |
| Nothing matched a lookup | An empty collection, never `null` |

All three library exceptions are unchecked and carry the original reflective failure as
their cause, so a call site does not need a `try`/`catch` block to see what went wrong.
Helpers that mirror a single JDK call, such as `getFieldType`, still propagate the checked
exception the JDK throws.

## Known limitations

- `invokeSingleMethod` resolves public methods only. Use `invokeMethod` to reach a private
  one.
- Constructor resolution matches on the runtime types of the arguments. It handles
  primitive parameters and subtypes, but it does not pick between overloads the way `javac`
  would - the first applicable constructor wins. Use `getAccessibleConstructor` when a class
  has ambiguous overloads.
- The method helpers look at declared methods only, while the field helpers walk the
  superclass chain. `getAnnotatedMethods` on a subclass does not return an annotated method
  inherited from its parent.
- Package scanning reads directories, not jar entries.

## Building from source

```bash
git clone https://github.com/alxkm/reflector.git
cd reflector
./gradlew build
```

The build compiles with `--release 8`, so the produced bytecode stays usable on Java 8 no
matter which JDK runs the build. Tests run on JUnit 5, and a JaCoCo coverage report is
written to `build/reports/jacoco/test/html/index.html`.

## Contributing

Issues and pull requests are welcome - see [CONTRIBUTING.md](CONTRIBUTING.md) for the
workflow and coding conventions.

## License

MIT - see [LICENSE.md](LICENSE.md).
