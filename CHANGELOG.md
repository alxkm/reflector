# Changelog

All notable changes to this project are documented here. The format follows
[Keep a Changelog](https://keepachangelog.com/en/1.1.0/).

## [Unreleased]

### Added
- `ReflectionUtils` now exposes the `ClassBasicUtils` methods - `getClassFullName`,
  `getClassSimpleName`, `getPackage`, `getSuperClass`, `getInterfaces`,
  `getEnclosingClass` and their by-class variants. They were previously reachable only
  through `ClassBasicUtils`.
- Library exceptions accept a cause, so the original reflective failure is no longer lost.
- JaCoCo coverage reporting, Javadoc and sources jars, and a `maven-publish` configuration.
- Split API reference under [`docs/`](docs/README.md), plus `CONTRIBUTING.md` and issue
  and pull request templates.

### Fixed
- `invokeMethod` could not call private methods. It resolves the method with
  `getDeclaredMethod` but never made it accessible.
- `invokeMethod`, `invokeSingleMethod` and `invokeInstance` discarded the underlying
  exception and threw a message-only failure. The cause is now attached.
- A test used `List.of`, which is not available on Java 8, while the project advertises
  Java 8 support. The build now enforces the target with `--release 8`.
- `gradlew.bat` and the Gradle wrapper were excluded by `.gitignore`, so a fresh clone had
  no working wrapper on Windows.

### Changed
- `ReflectionConstant` fields are `static final` instead of mutable public statics.
- Build upgraded to `java-library`, JUnit 5.10.2 and slf4j 2.0.13.
- Removed the unused Travis CI configuration; GitHub Actions is the only CI.

## [0.0.1]

- Initial release.

[Unreleased]: https://github.com/alxkm/reflector/compare/release.v0.0.1.1...HEAD
[0.0.1]: https://github.com/alxkm/reflector/releases/tag/release.v0.0.1.1
