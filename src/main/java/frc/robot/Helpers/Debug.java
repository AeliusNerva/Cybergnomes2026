package frc.robot.Helpers;

import frc.robot.Constants;

public class Debug {
    private static final boolean DEBUG = Constants.DEBUG;
    public static void debug(String str) {
        if (DEBUG) {
            System.out.println(str);
        }
    }
}
