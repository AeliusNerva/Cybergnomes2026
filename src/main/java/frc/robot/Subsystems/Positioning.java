package frc.robot.Subsystems;

import frc.robot.Helpers.Vector3;

public class Positioning {
    // Z is used for yaw position/velocity
    public Vector3 position = new Vector3(0.0, 0.0, 0.0);
    public Vector3 velocity = new Vector3(0.0, 0.0, 0.0);
    public boolean locked = false;
}
