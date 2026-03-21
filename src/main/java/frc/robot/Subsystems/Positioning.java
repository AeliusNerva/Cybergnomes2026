package frc.robot.subsystems;

import com.ctre.phoenix6.configs.MountPoseConfigs;
import com.ctre.phoenix6.configs.Pigeon2Configuration;
import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;
import frc.robot.helpers.Limelight;
import frc.robot.helpers.LowPassFilter;
import frc.robot.helpers.Vector3;

public class Positioning {
	// Z is used for yaw position/velocity
	public static Vector3 position = new Vector3(0.0, 0.0, 0.0);
	public static Vector3 velocity = new Vector3(0.0, 0.0, 0.0);
	private static boolean first_lock = false;

	private static Vector3 last_position = new Vector3(0.0, 0.0, 0.0);
	private static double dt = 0.02; // Should be 20ms (0.020s) so we're gonna start with that
	private static double time = Timer.getFPGATimestamp();
	private static double last_time = Timer.getFPGATimestamp();

	private static int pigeon_id = Constants.Positioning.PIGEON_ID;
	private static Pigeon2 pigeon = new Pigeon2(pigeon_id);

	private static final LowPassFilter position_x = new LowPassFilter();
	private static final LowPassFilter position_y = new LowPassFilter();

	private static final LowPassFilter difference_x = new LowPassFilter();
	private static final LowPassFilter difference_y = new LowPassFilter();

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
	}

	public static void position() {

		// Get the grounded position of the robot
		Limelight.periodic();
		Vector3 limelight_data = Limelight.robot_limelight_position;

		if (limelight_data.z > 0.0) {
			if (limelight_data.x != 0.0 && limelight_data.y != 0.0) {
				// Finding and responsibly managing delta time
				last_time = time;
				time = Timer.getFPGATimestamp();
				dt = time - last_time;
				if (dt > 0.1) {
					// Throw away bad delta times and exit
					last_time = time;
					return;
				}
				last_position = position.copy();

				first_lock = true;
				position.x = limelight_data.x;
				position.y = limelight_data.y;
				position.x = position_x.low_pass(position.x);
				position.y = position_y.low_pass(position.y);

				Vector3 difference = position.sub(last_position);
				difference.x = difference_x.low_pass(difference.x);
				difference.y = difference_y.low_pass(difference.y);
				velocity = difference.scalar_divide(dt);
			}
		}

		position.z = pigeon.getYaw().getValueAsDouble();

		velocity = velocity.scalar_divide(1.2);

		if (first_lock) {
			Turret.lock_onto_hub();
		}
	}
}
