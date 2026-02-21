package frc.robot;

import frc.robot.helpers.Vector3;

public final class Constants {
	// Enables/disables all debug prints
	public static boolean DEBUG = true;

	public static final class Positioning {
		public static final int PIGEON_ID = 0;
	}

	public static final class Turret {
		// Highest point of the ball's trajectory in meters 3
		public static final double apogee = 3;

		// CAN IDs of each axis's motor
		public static final int PITCH_MOTOR = 9;
		public static final int YAW_MOTOR = 23;
		public static final int INTAKE_MOTOR = 24;
		public static final int FLYWHEEL_MOTOR_1 = 22;
		public static final int FLYWHEEL_MOTOR_2 = 21;

		// Motor speed of the intake, 0.0 -> 0% max, 1.0 -> 100% max
		public static final double INTAKE_SPEED = 0.5;

		// Radius of the flywheel in meters
		public static final double FLYWHEEL_RADIUS = 0.054;
	}

	public static final class Puker {
		// Highest point of the ball's trajectory in meters
		public static final double apogee = 3;

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
		// The rotations per degree of the Kraken X60s motor, if this isn't right I
		// geniunely don't know what to do.
		public static final double ROTATIONS_PER_DEGREE = (150.0 / 7.0) / 360.0;

		// Max speed of the robot in m/s
		public static final double MAX_SPEED = 2;

		// Max angular speed of the robot in radians/s (1 radian = ~57 degrees)
		public static final double MAX_ANGULAR_SPEED = 3;
	}

	public static final class Collector {
		// CAN IDs of each motor
		public static final int COLLECTOR_PIVOT_MOTOR = 16;
		public static final int COLLECTOR_MOTOR = 19;

		// Motor speed of the loader, 0.0 -> 0% max, 1.0 -> 100% max
		public static final double COLLECTOR_SPEED = 0.5;

		// Collector positions in degrees
		public static final int LOWERED_DEG = 0;
		public static final int RAISED_DEG = 90;
	}

	public static final class Arena {
		// Units in meters, measured from bottom left corner of arena specs diagram

		// ----- POINTS OF INTEREST -----

		// X: 182.110" -> 4.625m Y: 158.845" -> 4.034m Z: 72.000" -> 1.828m
		public static Vector3 BLUE_HUB = new Vector3(4.625, 4.034, 1.828);

		// X: 182.110" -> 4.625m Y: 445.845" -> 4.034m Z: 72.000" -> 1.828m
		public static Vector3 RED_HUB = new Vector3(4.625, 11.324, 1.828);
	}

	public static final class Limelight {

	}

	public static final class RollerFloor {
		// CAN IDs of each motor
		public static final int ROLLER_FLOOR_MOTOR = 20;

		// Motor speed of the loader, 0.0 -> 0% max, 1.0 -> 100% max
		public static final double ROLLER_FLOOR_SPEED = 1.0;
	}

	public static class OperatorConstants {
		public static final int kDriverControllerPort = 0;
	}
}
