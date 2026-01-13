// Put me into src/tests/java/frc/robot/src and compile me to see how I work!

public class example_test {
    public static String example() {
        // This is the expected results of the test
        int expected = 3;

        // Program you want to test, usually a function but it's not here.
        int a = 1;
        int b = 2;
        int c = a + b;

        int result = c;

        // Some sort of simple, easily verifiable logic to see if we got the results we expected
        if (expected == result) {
            return ""; // Yay! It worked!
        } else {
            return "c returned an unexpected value."; // Aw man
        }
    }
}