package frc.robot;

import frc.robot.helpers.Vector3;

public final class Constants {
	public static final class Strategy {
		// The apogee of the ball within shooting areas
		public static final double NORMAL_APOGEE = 3.0;

		/*
		 * Our snowblowing strategy will be to shoot the ball in the direction of the
		 * human collection zone, so that the balls will first touch the ground exactly
		 * 3 meters away from it, and will then have the distance to slowly lose their
		 * bounce and start rolling into the ball recepticle. Best case scenario: they
		 * land in the human collection zone and they can be loaded into our teammates
		 * or our ball shooters. Worst case scenario: the balls are in a good position
		 * to be collected and shot manually by a robot.
		 */

		// The apogee of the ball outside of shooting areas; while snowblowing.
		public static final double CENTER_APOGEE = 4.0;

		public static final boolean SNOWBLOWING = false;
	}

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
		public static final int EAST_ACTUATOR = 1;

		// The rotations per degree of the yaw motor
		public static final double ROTATIONS_PER_DEGREE = ((9.0 / 1.0) * (10.0 / 1.0)) / 360.0;

		// Motor velocity of the loader in rotations per second
		public static final double LOADER_SPEED = 30;

		// Radius of the flywheel in meters
		public static final double FLYWHEEL_RADIUS = 0.054;

		// Hood modulation arm length in meters
		public static final double HOOD_ARM_LENGTH = 0.24;

		// Length from the end of the actuator to the rotation point of the hood arm
		public static final double ROTATION_TO_ACTUATOR_LENGTH = 0.16;

		// Actuator full stroke length in meters
		public static final double ACTUATOR_FULL_STROKE = 0.1;

		// Actuator software max and minimum actuation in meters
		public static final double ACTUATOR_MAX = 0.07;
		public static final double ACTUATOR_MIN = 0.00;

		// The length of the actuator that should be subtracted from the command
		public static final double ACTUATOR_START = 0.09;

		/*
		 * Maximum absolute rotation of the turret in degrees, 10 degrees means
		 * it can only rotate 10 degrees to the left and 10 degrees to the right,
		 * and 180 degrees means it can only do a full 180 degree turn before
		 * having to go around the way it came.
		 */
		public static final double TURRET_DEGREES_OF_FREEDOM = 105.0;

		// Unitless scalar for the turret's flywheel speed
		public static final double FLYWHEEL_SPEED_SCALAR = 2.0;

		/*
		 * Unitless divisor for the turret's scan function to slow down the scan's sine
		 * wave.
		 */
		public static final double TURRET_SCAN_DIVISOR = 1.0;

		public static final double TURRET_DEFAULT_SPEED_COMMAND = 50;
	}

	public static final class Puker {
		// CAN IDs of each motor
		public static final int FLYWHEEL_MOTOR = 25;
		public static final int LOADER_MOTOR = 27;

		// Motor velocity of the loader in rotations per second
		public static final double LOADER_SPEED = 30;

		// Motor velocity of the flywheel in rotations per second
		public static final double FLYWHEEL_SPEED = 65;
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
		public static final double MAX_SPEED = 4;

		// Max angular speed of the robot in radians/s (1 radian = ~57 degrees)
		public static final double MAX_ANGULAR_SPEED = 4.5;
	}

	public static final class Collector {
		// CAN IDs of each motor
		public static final int COLLECTOR_PIVOT_MOTOR = 19;
		public static final int COLLECTOR_MOTOR = 16;

		// Motor speed of the loader, 0.0 -> 0% max, 1.0 -> 100% max
		public static final double COLLECTOR_SPEED = 0.75;

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
		public static final int ROLLER_FLOOR_MOTOR = 26;

		// Motor speed of the loader, 0.0 -> 0% max, 1.0 -> 100% max
		public static final double ROLLER_FLOOR_SPEED = 0.5;
	}

	public static final class Filters {
		// Maximum number of samples for the low-pass filters
		public static final int LOW_PASS_MAX_SAMPLES = 5;
	}
}
