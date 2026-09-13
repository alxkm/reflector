# Contributing to Reflector

Thanks for taking the time to contribute. Bug reports, documentation fixes and pull
requests are all welcome.

## Getting started

```bash
git clone https://github.com/alxkm/reflector.git
cd reflector
./gradlew build
```

Any JDK 8 or newer works. The build compiles with `--release 8`, so code that uses a newer
API fails at compile time rather than at a user's runtime.

Useful tasks:

| Task | What it does |
|------|--------------|
| `./gradlew build` | Compile, run tests, produce jars |
| `./gradlew test` | Run the test suite |
| `./gradlew javadoc` | Generate Javadoc into `build/docs/javadoc` |
| `./gradlew jacocoTestReport` | Coverage report in `build/reports/jacoco/test/html` |

## Reporting a bug

Open an issue with the Java version, the Reflector version and a snippet that reproduces
the problem. A failing test is the fastest way to get a fix.

## Pull requests

1. Branch off `master`.
2. Keep the change focused - one topic per pull request.
3. Add tests for anything you fix or add. A behaviour change without a test will be asked
   for one.
4. Run `./gradlew build` before pushing.
5. Describe what changed and why in the pull request body.

## Coding conventions

The codebase follows a few simple rules; match the surrounding code and you will be fine.

- Utility classes are `final`, have a private constructor and expose only `static` methods.
- Parameters are `final` where practical.
- Validate arguments at the top of the method and fail fast on `null`.
- Wrap reflective failures in `FieldAccessException`, `MethodInvokeException` or
  `InstanceInvocationException`, and always pass the original exception as the cause.
- Return an empty collection rather than `null`.
- Every public method gets Javadoc with `@param`, `@return` and `@throws`.
- Four spaces for indentation, no tabs.

## Adding a method

A new utility method usually touches four places:

1. The focused utility class, for example `FieldUtils`.
2. A delegating method on the `ReflectionUtils` facade, so both entry points stay in sync.
3. A test in `src/test/java/org/common/reflector/utils`.
4. The matching page under [`docs/`](docs/README.md).

`ReflectionUtilsLegacy` is frozen for backwards compatibility - please do not add to it.

## Commit messages

Short, imperative subject line, optionally prefixed with the area:

```
fix: reach private methods from invokeMethod
docs: split the API reference into docs/
```

## License

By contributing you agree that your work is licensed under the
[MIT License](LICENSE.md).
