/*      
        Project:        cybergnomes-2026
        File Purpose:   Test Manager
        Author:         Nicholas Fortune
        Contributors:   --
        Created:        10-01-2026
        First Release:  10-01-2026
        Updated:        --

        Description:    A simple test manager for compiling individual unit tests into one, manually runable tests.

        Notes:          --
*/

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class main {
    // Text styles
    public static final String BOLD = "\u001B[1m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String RED = "\u001B[31m";
    public static final String RESET = "\u001B[0m";

    // Tags
    public static final String TEST_MANAGER = BOLD + "test_manager: " + RESET;
    public static final String ERROR = BOLD + RED + "error: " + RESET;
    public static final String WARNING = BOLD + YELLOW + "warning: " + RESET;

    // Test results
    public static final String PASSED = GREEN + "[PASSED] " + RESET;
    public static final String FAILED = RED + "[FAILED] " + RESET;
    public static final String NOTICE = YELLOW + "[NOTICE] " + RESET;

    public static void test_manager(String[] args) throws Exception {
        int failed = 0;
        int skipped = 0;
        System.out.println("\n" + TEST_MANAGER + "cybergnomes test manager started");

        String testsDirectory = "src/tests/java/frc/robot/bin";
        File dir = new File(testsDirectory);

        URL[] urls = {dir.toURI().toURL()};
        URLClassLoader loader = new URLClassLoader(urls);

        System.out.println(TEST_MANAGER + "finding classes to test...");
        File[] files = dir.listFiles((d, name) -> name.endsWith(".class") && !name.equalsIgnoreCase("main.class"));

        if (files == null || files.length == 0) {
            System.out.println(ERROR + "no classes found in \"" + testsDirectory + "\"; exiting. \n");
            loader.close();
            return;
        }

        int numFiles = files.length;
        if (numFiles == 1) {
            System.out.println(TEST_MANAGER + "1 test found! starting test...");
        } else {
            System.out.println(TEST_MANAGER + numFiles + " tests found! starting tests...");
        }

        for (int i = 0; i < numFiles; i++) {
            String className = files[i].getName().replace(".class", "");
            Class<?> cls = loader.loadClass(className);
            Object instance = cls.getDeclaredConstructor().newInstance();

            System.out.println("\n" + TEST_MANAGER + "running tests in \"" + BOLD + className + RESET + "\". (" + (i + 1) + "/" + numFiles + ")");

            Method[] methods = cls.getDeclaredMethods();
            for (int v = 0; v < methods.length; v++) {
                Method method = methods[v];

                if (method.getParameterCount() == 0) {
                    if (method.getReturnType() == String.class) {
                        String result = (String) method.invoke(instance);

                        if (result.equals("")) {
                            System.out.println(PASSED + "(" + (v+1) + "/" + methods.length + ") " + className + ": " + method.getName());
                        } else {
                            System.out.println(FAILED + "(" + (v+1) + "/" + methods.length + ") " + className + ": " + method.getName() + ": " + result);
                            failed++;
                        }
                    } else {
                        System.out.println(WARNING + "skipping over test \"" + method.getName() + "\", method must return string.");
                        skipped++;
                    }
                } else {
                    System.out.println(WARNING + "skipping over test \"" + method.getName() + "\", arguments are not supported.");
                    skipped++;
                }
            }
        }
        String failedstr = failed + " failed, ";
        if (failed != 0) {failedstr = BOLD + RED + failedstr + RESET;}
        String skippedstr = skipped + " skipped.\n";
        if (skipped != 0) {skippedstr = BOLD + YELLOW + skippedstr + RESET;}
        System.out.println("\n" + TEST_MANAGER + "all tests completed. " + failedstr + skippedstr);
        loader.close();
    }
}