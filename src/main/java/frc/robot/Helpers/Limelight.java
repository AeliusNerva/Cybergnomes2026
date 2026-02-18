package frc.robot.helpers;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

/*
t2d {
    targetValid = index 0
    tx = index 4
    ty = index 5
    tid (target ID) = index 9
    targetShortSidePixels = index 13
    targetHorizontalExtentPixels = index 14
    targetVerticalExtentPixels = index 15
}
*/

public class Limelight {

	public static Vector3 robot_limelight_position = new Vector3(0, 0, 0); // Z is used for tv

	public static void periodic() {
		NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
		double[] pose = table.getEntry("botpose_wpiblue").getDoubleArray(new double[11]);
		robot_limelight_position.x = pose[0];
		robot_limelight_position.y = pose[1];
	}
}