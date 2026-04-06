package frc.robot;

import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import frc.robot.helpers.Vector3;

public final class Constants {
	public static final class Positioning {
		public static final int PIGEON_ID = 0;
	}	

	public static final class Pukers {
		// CAN IDs of each motor
		public static final int PUKER_1_FLYWHEEL_MOTOR = 22;
		public static final int PUKER_1_LOADER_MOTOR = 21;
		public static final int PUKER_2_FLYWHEEL_MOTOR = 25;
		public static final int PUKER_2_LOADER_MOTOR = 27;

		// Motor velocity of the loader in rotations per second
		public static final double LOADER_SPEED = 30;

		// Default motor velocity of the flywheel in rotations per second
		public static final double DEFAULT_SPEED_COMMAND = 70;

		// Radius of the flywheel in meters
		public static final double FLYWHEEL_RADIUS = 0.054;

		// The angle of the puker's ball-shooting "barrel"/guides in degrees.
		public static final double PUKER_ANGLE = 70.0;
	}

	public static final class BallGuidance {
		// Minimum speed command in m/s
		public static final double MINIMUM_SPEED = 3.0;	
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
		public static final double MAX_SPEED = 5.23;

		// Max angular speed of the robot in radians/s (1 radian = ~57 degrees)
		public static final double MAX_ANGULAR_SPEED = RotationsPerSecond.of(0.75).in(RadiansPerSecond);
	}

	public static final class Collector {
		// CAN IDs of each motor
		public static final int LEFT_COLLECTOR_PIVOT_MOTOR = 29;
		public static final int RIGHT_COLLECTOR_PIVOT_MOTOR = 19;
		public static final int COLLECTOR_MOTOR = 16;

		// Motor speed of the loader, 0.0 -> 0% max, 1.0 -> 100% max
		public static final double COLLECTOR_SPEED = 0.9;

		public static final double ROTATIONS_PER_DEGREE = ((27.0 / 1.0) * (8.0 / 5.0)) / 360.0;

		// Collector positions in degrees
		public static final double RAISED_DEG = -10.0;
		public static final double LOWERED_DEG = -115.0;
	}

	public static final class Arena {
		// Units in meters, measured from top right corner of arena specs diagram

		// ----- BOUNDARIES -----
		// X: 444.800" -> 11.298m
		public static double LEFT_CENTER_BOUNDARY = 11.298;

		// X: 205.320" -> 5.215m
		public static double RIGHT_CENTER_BOUNDARY = 5.215;

		// ----- POINTS OF INTEREST -----

		// X: 157.790" -> 4.008m Y: 72.000" -> 1.828m Z: 158.845" -> 4.034m
		public static Vector3 BLUE_HUB = new Vector3(4.008, 1.828, 4.034);

		// X: 468.560" -> 11.901m Y: 72.000" -> 1.828m Z: 158.845" -> 4.034m
		public static Vector3 RED_HUB = new Vector3(11.901, 1.828, 4.034);

		// X: 0.540" -> 0.013m Y: 0.000" -> 0.000m Z: 25.620" -> 0.6507m
		public static Vector3 BLUE_COLLECTION_ZONE = new Vector3(0.013, 0.000, 0.6507);

		// X: 649.580" -> 16.499m Y: 0.000" -> 0.000m Z: 291.020" -> 7.392m
		public static Vector3 RED_COLLECTION_ZONE = new Vector3(16.499, 0.000, 7.392);
	}

	public static final class RollerFloor {
		// CAN IDs of each motor
		public static final int ROLLER_FLOOR_MOTOR = 23;

		// Gear ratio
		public static final double GEAR_RATIO = (1.0 / 1.0);

		// Motor speed of the roller floor in rotations per second
		public static final double ROLLER_FLOOR_SPEED = 2.0;
	}

	public static final class Filters {
		// Maximum number of samples for the low-pass filters
		public static final int LOW_PASS_MAX_SAMPLES = 5;
	}
}
