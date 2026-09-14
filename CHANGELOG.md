# Changelog

All notable changes to this project are documented here. The format follows
[Keep a Changelog](https://keepachangelog.com/en/1.1.0/).

## [Unreleased]

### Changed - behaviour

These change what existing calls do. Worth a look before cutting a release.

- `readField` now walks the superclass chain instead of looking only at the runtime class.
  A call that used to fail for an inherited field now returns its value. Nothing that
  worked before behaves differently.
- `clearUnselectedFields` now resets primitive fields to their default value instead of
  logging a failure and leaving them untouched. Static and final fields are now skipped
  explicitly rather than failing on each attempt.
- `copy` now throws `InstanceInvocationException` when the class has no usable no-argument
  constructor, instead of logging and returning `null`. Code that checked the result for
  `null` needs to catch instead. It also finds non-public no-argument constructors now.
- `getAccessibleConstructor`, and the `invokeInstance` overloads built on it, fall back to
  an assignability scan when no constructor matches the argument types exactly. A
  constructor taking `int` is now reachable with an `Integer` argument, and non-public
  constructors are considered by the fallback.
- `invokeSingleMethod` now marks the resolved method accessible, so a public method on a
  package-private class is callable. The lookup is still public-only.
- Null arguments throw `NullPointerException` everywhere. `AnnotationUtils`, `FieldUtils`
  and `MethodUtils` previously threw `IllegalArgumentException` in 13 places while the rest
  of the library threw `NullPointerException`.
- `getMethodsDeclaredAnnotations` keeps the first entry when a method appears twice in the
  array, instead of failing with the `IllegalStateException` that `Collectors.toMap` raises
  on a duplicate key.
- `getAllAnnotatedClassesByPackage` takes `Class<? extends Annotation>` instead of a raw
  `Class`. Callers already passing an annotation type are unaffected.

### Deprecated

- `ReflectionUtilsLegacy`. Use `ReflectionUtils` or a focused utility class.
- `isFieldExactAnnotated`, which is identical to `isFieldAnnotated`. `isAnnotationPresent`
  is defined by the JDK as `getAnnotation(x) != null`, and a field annotation is never
  inherited, so there is nothing for "exact" to distinguish.
- `isFieldPrimitiveType`, replaced by `isSimpleValueType`. The old name says primitive but
  the check also answers true for the wrapper classes and for `String`.

### Added

- `isSimpleValueType`, the honestly named replacement for `isFieldPrimitiveType`.
- `ReflectionUtils` now exposes the `ClassBasicUtils` methods - `getClassFullName`,
  `getClassSimpleName`, `getPackage`, `getSuperClass`, `getInterfaces`, `getEnclosingClass`
  and their by-class variants. They were previously reachable only through
  `ClassBasicUtils`.
- Library exceptions accept a cause, so the original reflective failure is no longer lost.
- A delegation test covering every `ReflectionUtils` method against the utility it forwards
  to. It fails when a facade method has no case, so a new delegate cannot ship untested.
  Method coverage went from 16.5% to 100% on the facade, and from 64% to 93% overall.
- JaCoCo coverage reporting, Javadoc and sources jars, and a `maven-publish` configuration
  with a Maven Central ready POM. Signing and OSSRH upload activate when the credentials
  are present.
- Javadoc published to GitHub Pages on every push to `master`.
- Split API reference under [`docs/`](docs/README.md), plus `CONTRIBUTING.md` and issue
  and pull request templates.

### Fixed

- `invokeMethod` could not call private methods. It resolves the method with
  `getDeclaredMethod` but never made it accessible.
- `invokeMethod`, `invokeSingleMethod` and `invokeInstance` discarded the underlying
  exception and threw a message-only failure. The cause is now attached.
- `getArrayValuesTypesByArgs` threw `NullPointerException` on a null argument instead of
  reporting a null type for it.
- `PackageUtils` cached the class loader in a static field resolved at class initialisation.
  In a container the context class loader of whichever thread touched the class first is not
  necessarily the one that can see the caller's classes. It is now resolved per call.
- The README claimed `getAllMethodsWithModifiers` combines its predicates with AND. The
  implementation uses `anyMatch`, so it is OR.
- A test used `List.of`, which is not available on Java 8, while the project advertises
  Java 8 support. The build now enforces the target with `--release 8`.
- `gradlew.bat` and the Gradle wrapper were excluded by `.gitignore`, so a fresh clone had
  no working wrapper on Windows. `gradlew` was also committed without its executable bit.

### Changed - build and internals

- `ReflectionConstant` fields are `static final` instead of mutable public statics.
- `Class.newInstance`, deprecated since Java 9 and known for laundering constructor
  exceptions, replaced with `getDeclaredConstructor().newInstance()`.
- `ConstructorUtils` no longer catches an exception only to log it and rethrow the same one.
- Build upgraded to `java-library`, JUnit 5.10.2 and slf4j 2.0.13.
- CI builds on JDK 8, 11, 17 and 21; coverage is uploaded from the 17 job.
- Removed the unused Travis CI configuration; GitHub Actions is the only CI.

## [0.0.1]

- Initial release.

[Unreleased]: https://github.com/alxkm/reflector/compare/release.v0.0.1.1...HEAD
[0.0.1]: https://github.com/alxkm/reflector/releases/tag/release.v0.0.1.1
