package frc.robot.subsystems;

import com.ctre.phoenix6.configs.MountPoseConfigs;
import com.ctre.phoenix6.configs.Pigeon2Configuration;
import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;
import frc.robot.helpers.Limelight;
import frc.robot.helpers.Vector3;

public class Positioning {
	private static double gravity = Constants.Positioning.GRAVITY;

	// Z is used for yaw position/velocity
	public static Vector3 position = new Vector3(0.0, 0.0, 0.0);
	public static Vector3 velocity = new Vector3(0.0, 0.0, 0.0);
	private static boolean first_lock = false;

	private static Vector3 grounded_position = new Vector3(0.0, 0.0, 0.0);
	private static Vector3 relative_velocity = new Vector3(0.0, 0.0, 0.0);
	private static Vector3 relative_position = new Vector3(0.0, 0.0, 0.0);

	private static double pigeon_accelerometer_x_sample_error = 0.0;
	private static double pigeon_accelerometer_y_sample_error = 0.0;
	private static final double pigeon_accelerometer_x_scalar = Constants.Positioning.PIGEON_ACCELEROMETER_X_SCALAR;
	private static final double pigeon_accelerometer_y_scalar = Constants.Positioning.PIGEON_ACCELEROMETER_Y_SCALAR;

	private static Vector3 last_position = new Vector3(0.0, 0.0, 0.0);
	private static double time = Timer.getFPGATimestamp();
	private static double last_time = Timer.getFPGATimestamp();
	private static double start_time = Timer.getFPGATimestamp();
	private static final double calibration_time = Constants.Positioning.CALIBRATION_TIME;
	private static boolean calibration_done = false;

	private static int pigeon_id = Constants.Positioning.PIGEON_ID;
	private static Pigeon2 pigeon = new Pigeon2(pigeon_id);

	public static void pigeon_init() {
		// Clear any sticky faults
		pigeon.clearStickyFaults();

		// Configure Pigeon settings
		Pigeon2Configuration pigeonConfig = new Pigeon2Configuration();
		MountPoseConfigs mountPose = new MountPoseConfigs();
		mountPose.MountPosePitch = 0;
		mountPose.MountPoseYaw = 0;
		mountPose.MountPoseRoll = 0;
		pigeonConfig.withMountPose(mountPose);

		// Start time for pigeon calibration
		start_time = Timer.getFPGATimestamp();
	}

	private static Vector3 samples_sum = new Vector3(0.0, 0.0, 0.0);
	private static Vector3 average_sample = new Vector3(0.0, 0.0, 0.0);
	private static int samples = 0;

	public static void collect_pigeon_sample() {
		Vector3 sample = new Vector3(pigeon.getAccelerationX().getValueAsDouble(),
				pigeon.getAccelerationY().getValueAsDouble(), 0.0);
		samples_sum = samples_sum.add(sample);
		samples += 1;
		average_sample = samples_sum.scalar_divide(samples);
	}

	public static void position() {
		// Pigeon calibration
		if (!calibration_done) {
			if (time - start_time < calibration_time) {
				collect_pigeon_sample();
			} else {
				pigeon_accelerometer_x_sample_error = average_sample.x;
				pigeon_accelerometer_y_sample_error = average_sample.y;
				relative_velocity = new Vector3(0.0, 0.0, 0.0);
				relative_position = new Vector3(0.0, 0.0, 0.0);
				calibration_done = true;
			}
		}

		// Finding and responsibly managing delta time
		last_time = time;
		time = Timer.getFPGATimestamp();
		double dt = time - last_time;
		if (dt > 0.05) {
			last_time = time;
			return;
		} // Throw away bad delta times and exit
		last_position = position;

		// Get the grounded position of the robot
		Limelight.periodic();
		Vector3 limelight_data = Limelight.robot_limelight_position;

		if (limelight_data.z > 0.0) {
			first_lock = true;
			grounded_position = new Vector3(
					limelight_data.x,
					limelight_data.y,
					0.0);

			relative_velocity = new Vector3(0.0, 0.0, 0.0);
			relative_position = new Vector3(0.0, 0.0, 0.0);
		}

		if (first_lock) {
			Turret.lock_onto_hub();
		}

		// Get the relative position of the robot
		relative_velocity.x += ((pigeon.getAccelerationX().getValueAsDouble()
				- pigeon_accelerometer_x_sample_error) / gravity) * dt * pigeon_accelerometer_x_scalar;
		relative_velocity.y += ((pigeon.getAccelerationY().getValueAsDouble()
				- pigeon_accelerometer_y_sample_error) / gravity) * dt * pigeon_accelerometer_y_scalar;

		relative_position.x += relative_velocity.x;
		relative_position.y += relative_velocity.y;

		// Get the real position
		/*
		 * position.x = relative_position.x + grounded_position.x;
		 * position.y = relative_position.y + grounded_position.y;
		 */

		position.x = relative_position.x + grounded_position.x;
		position.y = relative_position.y + grounded_position.y;
		position.z = pigeon.getYaw().getValueAsDouble();

		Vector3 difference = position.sub(last_position);
		velocity = difference.scalar_divide(dt);
		Vector3.println(difference, 4);
		System.out.println("\n");
	}
}
