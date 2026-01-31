package frc.robot.Subsystems;

import frc.robot.Helpers.Vector3;

public class Positioning {
    // Z is used for yaw position/velocity
    public static Vector3 position = new Vector3(0.0, 0.0, 0.0);
    public static Vector3 velocity = new Vector3(0.0, 0.0, 0.0);
    public static boolean locked = false;
}
