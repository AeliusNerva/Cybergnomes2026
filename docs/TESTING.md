# Testing
**THIS IS OUTDATED BUT STILL WORKS PROBABLY**

Unit tests are necessary for quality code. Here's how you can run and create them in this repo.

---

**These commands assume your working directory is the root of this repo!**

## Compiling tests
To compile all tests, you can run:

**Linux:** `javac -d src/main/java/frc/robot/bin $(find src/main/java/frc/robot/src -name "*.java")`
**Windows:** `javac -d src/main/java/frc/robot/bin (Get-ChildItem -Recurse src/main/java/frc/robot/src -Filter *.java | ForEach-Object { $_.FullName })`

## Running tests
To run all tests with the test manager, you can run:

**Linux & Windows:** `java -cp src/main/java/frc/robot/bin main`

## Creating a new test
If your test requires an entirely new class (i.e., it has a completely different scope from all the other tests) simply create a new `.java` file in `src/main/java/frc/robot/src` and start writing methods. Otherwise, simply append a new method to an existing class and the test manager will pick it up automatically.

One unit test per method you wish to test, please.

## Test creation requirements
* Each test MUST take zero arguments
* Each test MUST return a string; "" = passed, "anything but a empty string" = failed

Example:

`return "";` -> the test manager sees this as success

`return "unexpected motor speed returned";` -> test manager sees this as a fail, and will pass this on to the test user.

* No need to worry about showing where errors came from, the test manager will handle that.

## Test creation suggestions
Here are some tips for creating tests:

* Each test should only verify one method.
* Name your test methods clearly, e.g., `test_motor_speed()` or `test_sensor_reading()`.
* Keep tests small and deterministic (no random behavior unless mocked).
* If a test depends on hardware or external resources, isolate it as much as possible.
