package frc.robot;

import frc.robot.helpers.Vector3;

public final class Constants {
	// Enables/disables all debug prints
	public static boolean DEBUG = true;

	public static final class Positioning {
		public static final int PIGEON_ID = 0;
	}

	public static final class Turret {
		// CAN IDs of each axis's motor
		public static final int YAW_MOTOR = 23;
		public static final int INTAKE_MOTOR = 24;
		public static final int FLYWHEEL_MOTOR_1 = 22;
		public static final int FLYWHEEL_MOTOR_2 = 21;

		// Linear actuator channels
		public static final int WEST_ACTUATOR = 0;
		public static final int EAST_ACTUATOR = 0;

		// The rotations per degree of the yaw motor
		public static final double ROTATIONS_PER_DEGREE = ((27.0 / 1.0) * (10.0 / 1.0)) / 360.0;

		// Motor speed of the intake, 0.0 -> 0% max, 1.0 -> 100% max
		public static final double LOADER_SPEED = 0.5;

		// Radius of the flywheel in meters
		public static final double FLYWHEEL_RADIUS = 0.054;

		// Hood modulation arm length in meters
		public static final double HOOD_ARM_LENGTH = 0.24;

		// Length from the end of the actuator to the rotation point of the hood arm
		public static final double ROTATION_TO_ACTUATOR_LENGTH = 0.16;

		// Actuator full stroke length in meters
		public static final double ACTUATOR_FULL_STROKE = 0.1;

		// Actuator software max and minimum actuation in meters
		public static final double ACTUATOR_MAX = 0.8;
		public static final double ACTUATOR_MIN = 0.2;

		/*
		 * Maximum absolute rotation of the turret in degrees, 10 degrees means
		 * it can only rotate 10 degrees to the left and 10 degrees to the right,
		 * and 180 degrees means it can only do a full 180 degree turn before
		 * having to go around the way it came.
		 */
		public static final double TURRET_DEGREES_OF_FREEDOM = 90.0;
	}

	public static final class Puker {
		// CAN IDs of each motor
		public static final int FLYWHEEL_MOTOR = 26;
		public static final int LOADER_MOTOR = 25;

		// Motor speed of the loader, 0.0 -> 0% max, 1.0 -> 100% max
		public static final double LOADER_SPEED = 0.25;

		// Radius of the flywheel in meters
		public static final double FLYWHEEL_RADIUS = 0.054;
	}

	public static final class Controller {
		/*
		 * Any stick value under this will be resolved to zero.
		 * 0.0-1.0, recomended: 0.1
		 */
		public static double STICK_MINIMUM = 0.1;

		/*
		 * Any trigger value under this will be resolved to false, anything over will
		 * resolve to true.
		 * 0.0-1.0, recomended: 0.5
		 */
		public static double TRIGGER_MINIMUM = 0.5;
	}

	public static final class Drive {
		// Max speed of the robot in m/s
		public static final double MAX_SPEED = 2;

		// Max angular speed of the robot in radians/s (1 radian = ~57 degrees)
		public static final double MAX_ANGULAR_SPEED = 3;
	}

	public static final class Collector {
		// CAN IDs of each motor
		public static final int COLLECTOR_PIVOT_MOTOR = 19;
		public static final int COLLECTOR_MOTOR = 16;

		// Motor speed of the loader, 0.0 -> 0% max, 1.0 -> 100% max
		public static final double COLLECTOR_SPEED = 1.0;

		public static final double ROTATIONS_PER_DEGREE = 1.0 / 360.0;

		// Collector positions in degrees
		public static final double LOWERED_DEG = 0.0;
		public static final double RAISED_DEG = 90.0;
	}

	public static final class Arena {
		// Units in meters, measured from bottom left corner of arena specs diagram

		// ----- POINTS OF INTEREST -----

		// X: 182.110" -> 4.625m Y: 158.845" -> 4.034m Z: 72.000" -> 1.828m
		public static Vector3 BLUE_HUB = new Vector3(4.034, 1.828, 4.625);

		// X: 182.110" -> 4.625m Y: 445.845" -> 11.324m Z: 72.000" -> 1.828m
		public static Vector3 RED_HUB = new Vector3(11.324, 1.828, 4.625);
	}

	public static final class Limelight {

	}

	public static final class RollerFloor {
		// CAN IDs of each motor
		public static final int ROLLER_FLOOR_MOTOR = 20;

		// Motor speed of the loader, 0.0 -> 0% max, 1.0 -> 100% max
		public static final double ROLLER_FLOOR_SPEED = 1.0;
	}
}
