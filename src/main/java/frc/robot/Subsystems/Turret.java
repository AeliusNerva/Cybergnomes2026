package frc.robot.subsystems;

import java.util.Optional;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.Constants;
import frc.robot.helpers.BallGuidance;
import frc.robot.helpers.KrakenServo;
import frc.robot.helpers.LinearActuator;
import frc.robot.helpers.LowPassFilter;
import frc.robot.helpers.Vector3;

public class Turret {
	private static Vector3 hub;
	private static Vector3 human_collection_zone;
	private static double offset;

	private static final double left_center_boundary = Constants.Arena.LEFT_CENTER_BOUNDARY;
	private static final double right_center_boundary = Constants.Arena.RIGHT_CENTER_BOUNDARY;

	private static final double apogee = Constants.Strategy.NORMAL_APOGEE;

	private static final int yaw_motor_id = Constants.Turret.YAW_MOTOR;
	private static final int flywheel_motor_1_id = Constants.Turret.FLYWHEEL_MOTOR_1;
	private static final int flywheel_motor_2_id = Constants.Turret.FLYWHEEL_MOTOR_2;
	private static final int intake_motor_id = Constants.Turret.INTAKE_MOTOR;
	private static final int west_actuator_channel = Constants.Turret.WEST_ACTUATOR;
	private static final int east_actuator_channel = Constants.Turret.EAST_ACTUATOR;

	private static final double loader_speed = Constants.Turret.LOADER_SPEED;
	private static final double flywheel_radius = Constants.Turret.FLYWHEEL_RADIUS;
	private static final double yaw_rotations_per_degree = Constants.Turret.ROTATIONS_PER_DEGREE;
	private static final double yaw_degrees_of_freedom = Constants.Turret.TURRET_DEGREES_OF_FREEDOM;
	private static final double hood_arm_length = Constants.Turret.HOOD_ARM_LENGTH;
	private static final double rotation_to_actuator_length = Constants.Turret.ROTATION_TO_ACTUATOR_LENGTH;
	private static final double actuator_full_stroke = Constants.Turret.ACTUATOR_FULL_STROKE;
	private static final double actuator_max = Constants.Turret.ACTUATOR_MAX;
	private static final double actuator_min = Constants.Turret.ACTUATOR_MIN;
	private static final double actuator_start = Constants.Turret.ACTUATOR_START;

	private static final TalonFX yaw_motor = new TalonFX(yaw_motor_id);
	private static final TalonFX flywheel_motor_1 = new TalonFX(flywheel_motor_1_id);
	private static final TalonFX flywheel_motor_2 = new TalonFX(flywheel_motor_2_id);
	private static final TalonFX intake_motor = new TalonFX(intake_motor_id);
	private static DigitalOutput west_actuator;
	private static DigitalOutput east_actuator;

	private static final VelocityVoltage vv = new VelocityVoltage(0).withSlot(0);

	private static double last_speed_command = Constants.Turret.TURRET_DEFAULT_SPEED_COMMAND;
	private static boolean shooting = false;
	private static int not_shooting_counter = 0;

	private static final double flywheel_speed_scalar = Constants.Turret.FLYWHEEL_SPEED_SCALAR;

	private static final double scan_divisor = Constants.Turret.TURRET_SCAN_DIVISOR;

	private static final LowPassFilter yaw_filter = new LowPassFilter();

	private static final boolean snowblowing = Constants.Strategy.SNOWBLOWING;

	public static double wrap_to_180_and_clamp(double degrees, double max) {
		degrees = degrees % 360.0;

		if (degrees > 180.0) {
			degrees -= 360.0;
		} else if (degrees <= -180.0) {
			degrees += 360.0;
		}

		if (degrees > max) {
			degrees = max;
		} else if (degrees < -max) {
			degrees = -max;
		}

		return degrees;
	}

	public static void init() {
		var slot0Configs = new Slot0Configs();
		slot0Configs.kS = 0.1;
		slot0Configs.kP = 0.6;
		slot0Configs.kV = 0.12;
		slot0Configs.kI = 0.1;
		slot0Configs.kD = 0.0;
		flywheel_motor_1.getConfigurator().apply(slot0Configs);
		flywheel_motor_2.getConfigurator().apply(slot0Configs);
		intake_motor.getConfigurator().apply(slot0Configs);

		slot0Configs.kS = 0.0;
		slot0Configs.kP = 0.5;
		slot0Configs.kV = 0.0;
		slot0Configs.kI = 0.0;
		slot0Configs.kD = 0.0;
		yaw_motor.getConfigurator().apply(slot0Configs);

		west_actuator = LinearActuator.init_pwm(west_actuator_channel);
		east_actuator = LinearActuator.init_pwm(east_actuator_channel);
	}

	public static void actuate(double input) {
		LinearActuator.actuate_to(west_actuator, input / 10,
				actuator_full_stroke);
		LinearActuator.actuate_to(east_actuator, input / 10,
				actuator_full_stroke);
	}

	public static void lock_onto_hub() {
		Optional<DriverStation.Alliance> alliance = DriverStation.getAlliance();
		if (alliance.isPresent()) {
			if (alliance.get() == Alliance.Red) {
				hub = Constants.Arena.RED_HUB.copy();
				human_collection_zone = Constants.Arena.RED_COLLECTION_ZONE.copy();
				offset = 90.0;
			} else if (alliance.get() == Alliance.Blue) {
				hub = Constants.Arena.BLUE_HUB.copy();
				human_collection_zone = Constants.Arena.BLUE_COLLECTION_ZONE.copy();
				offset = -90.0;
			} else {
				hub = Constants.Arena.RED_HUB.copy();
				human_collection_zone = Constants.Arena.RED_COLLECTION_ZONE.copy();
				offset = 90.0;
			}
		}

		Vector3 position = new Vector3(Positioning.position.x, 0, Positioning.position.y);

		// Vector3 velocity = new Vector3(Positioning.velocity.x, 0,
		// Positioning.velocity.y);
		Vector3 deltavel = new Vector3(0.0, 0.0, 0.0); // .sub(velocity);

		Vector3 ball_velocity;
		Vector3 commands;
		if (snowblowing) {
			if (position.x < left_center_boundary && position.x > right_center_boundary) {
				// -----  SNOWBLOW  -----

				// Collect required positions
				Vector3 deltapos = human_collection_zone.sub(position);

				// Get velocity
				ball_velocity = BallGuidance.get_required_snowblowing_velocity(deltapos, apogee,
						deltavel);
			} else {
				// -----  HUB  -----

				// Collect required positions
				Vector3 deltapos = hub.sub(position);

				// Get turret commands
				ball_velocity = BallGuidance.get_required_velocity(deltapos, apogee, deltavel);
			}
		} else {
			// -----  HUB  -----

			// Collect required positions
			Vector3 deltapos = hub.sub(position);

			// Get turret commands
			ball_velocity = BallGuidance.get_required_velocity(deltapos, apogee, deltavel);
		}

		commands = BallGuidance.get_turret_instructions(ball_velocity, offset);

		/*
		 * Offset from robot position to become world position and wrap and clamp for
		 * the turret
		 */

		commands.y += Positioning.position.z;
		commands.y = yaw_filter.low_pass(commands.y);
		commands.y = wrap_to_180_and_clamp(commands.y, yaw_degrees_of_freedom);

		// Rotate the turret
		KrakenServo.rotate_to(yaw_motor, commands.y, yaw_rotations_per_degree);

		// Get actuator actuation distance
		double actuator_distance = LinearActuator.get_actuation_distance_from_angle(hood_arm_length,
				rotation_to_actuator_length, commands.x);
		actuator_distance -= actuator_start;
		actuator_distance = (actuator_distance > actuator_max) ? actuator_max : actuator_distance;
		actuator_distance = (actuator_distance < actuator_min) ? actuator_min : actuator_distance;

		// Actuate the actuators
		// LinearActuator.actuate_to(west_actuator, 0.1,
		// actuator_full_stroke);
		// LinearActuator.actuate_to(east_actuator, 0.1,
		// actuator_full_stroke);

		/*
		 * Save the speed command for spin_up_flywheel()
		 */
		last_speed_command = commands.z;
	}

	public static double start_time = Timer.getFPGATimestamp();
	public static double last_time = start_time;
	public static double time = start_time;

	public static void search_for_target() {
		last_time = time;
		time = Timer.getFPGATimestamp();
		if (time - last_time > 0.1)
			start_time = time;

		double elapsed_time = time - start_time;
		elapsed_time += 157.0 / 300.0; // Makes sure the sine wave starts at 0.5 (middle)
		elapsed_time /= scan_divisor;
		// System.out.println("elapsed_time: " + elapsed_time);

		double command = Math.sin(elapsed_time);
		command -= 0.5; // Center it so 0.5 is 0
		command *= yaw_degrees_of_freedom; // Make the scan go for the full range of motion
		command *= 0.4; // Necessary because sine sucks here for some reason
		// System.out.println("command: " + command);

		// KrakenServo.rotate_to(yaw_motor, command, yaw_rotations_per_degree);
	}

	public static void spin_up_flywheel() {
		double required_rps = last_speed_command / (2 * Math.PI * flywheel_radius);
		required_rps *= flywheel_speed_scalar;

		flywheel_motor_1.setControl(vv.withVelocity(required_rps));
		flywheel_motor_2.setControl(vv.withVelocity(-required_rps));
	}

	public static void stop_flywheel() {
		flywheel_motor_1.setControl(new DutyCycleOut(0.0));
		flywheel_motor_2.setControl(new DutyCycleOut(0.0));
	}

	public static void fire() {
		shooting = true;
		intake_motor.setControl(vv.withVelocity(-loader_speed));
	}

	public static void stop_firing() {
		shooting = false;
		intake_motor.setControl(new DutyCycleOut(0.0));
	}

	public static void reverse_everything() {
		flywheel_motor_1.setControl(vv.withVelocity(-55));
		flywheel_motor_2.setControl(vv.withVelocity(55));
		intake_motor.setControl(vv.withVelocity(loader_speed));
	}

	public static void reverse_against_the_floor() {
		if (!shooting) {
			not_shooting_counter++;
			intake_motor.setControl(vv.withVelocity(loader_speed));
		} else {
			not_shooting_counter = 0;
		}
	}

	public static void stop_floor() {
		if (not_shooting_counter > 0) {
			stop_everything();
		}
	}

	public static void stop_everything() {
		stop_firing();
		stop_flywheel();
	}
}