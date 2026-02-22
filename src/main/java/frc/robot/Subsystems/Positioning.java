package frc.robot.subsystems;

import com.ctre.phoenix6.configs.MountPoseConfigs;
import com.ctre.phoenix6.configs.Pigeon2Configuration;
import com.ctre.phoenix6.hardware.Pigeon2;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;
import frc.robot.helpers.Limelight;
import frc.robot.helpers.Vector3;

public class Positioning {
	// Z is used for yaw position/velocity
	public static Vector3 position = new Vector3(0.0, 0.0, 0.0);
	public static Vector3 velocity = new Vector3(0.0, 0.0, 0.0);
	public static boolean locked = false;

	private static Vector3 grounded_position = new Vector3(0.0, 0.0, 0.0);
	private static Vector3 relative_position = new Vector3(0.0, 0.0, 0.0);

	private static Vector3 last_position = new Vector3(0.0, 0.0, 0.0);
	private static double time = Timer.getFPGATimestamp();
	private static double last_time = Timer.getFPGATimestamp();

	private static int pigeon_id = Constants.Positioning.PIGEON_ID;
	private static final int turret_yaw_motor_id = Constants.Turret.YAW_MOTOR;
	private static final double turret_yaw_rotations_per_degrees = Constants.Turret.ROTATIONS_PER_DEGREE;

	private static Pigeon2 pigeon2 = new Pigeon2(pigeon_id);
	private static final TalonFX turret_yaw_motor = new TalonFX(turret_yaw_motor_id);

	public static void pigeon_init() {
		// Clear any sticky faults
		pigeon2.clearStickyFaults();

		// Configure Pigeon settings
		Pigeon2Configuration pigeonConfig = new Pigeon2Configuration();
		MountPoseConfigs mountPose = new MountPoseConfigs();
		mountPose.MountPosePitch = 0;
		mountPose.MountPoseYaw = 0;
		mountPose.MountPoseRoll = 0;
		pigeonConfig.withMountPose(mountPose);
	}

	public static void position() {
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
			Turret.lock_onto_hub();
			Puker.get_acceleration_command();

			/*
			double turret_position = KrakenServo.get_position(turret_yaw_motor, turret_yaw_rotations_per_degrees);
			Vector3 robot_relative_position = new Vector3(0.0, 0.0, 0.0);
			robot_relative_position.x = limelight_data.x * Math.cos(turret_position)
					- limelight_data.y * Math.sin(turret_position);
			robot_relative_position.y = limelight_data.x * Math.sin(turret_position)
					+ limelight_data.y * Math.cos(turret_position);

			grounded_position.x = robot_relative_position.x * Math.cos(position.z)
					- robot_relative_position.y * Math.sin(position.z);
			grounded_position.y = robot_relative_position.x * Math.sin(position.z)
					+ robot_relative_position.y * Math.cos(position.z);
			*/
			
			grounded_position = limelight_data;
			grounded_position.z = 0.0;

			relative_position = new Vector3(0.0, 0.0, 0.0);
		}

		// Get the relative position of the robot
		relative_position.x += pigeon2.getAccelerationX().getValueAsDouble();
		relative_position.y += pigeon2.getAccelerationY().getValueAsDouble();

		// Get the real position
		position = grounded_position.add(relative_position);
		position.z = pigeon2.getYaw().getValueAsDouble();

		velocity = (position.sub(last_position)).scalar_divide(dt);
	}
}
