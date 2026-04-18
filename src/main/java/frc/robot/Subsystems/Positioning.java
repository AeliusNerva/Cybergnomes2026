package frc.robot.subsystems;

import java.util.Optional;

import com.ctre.phoenix6.configs.MountPoseConfigs;
import com.ctre.phoenix6.configs.Pigeon2Configuration;
import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.Constants;
import frc.robot.helpers.Limelight;
import frc.robot.helpers.LowPassFilter;
import frc.robot.helpers.Vector3;

public class Positioning {
	// Z is used for yaw position/velocity
	public static Vector3 position = new Vector3(0.0, 0.0, 0.0);

	private static int pigeon_id = Constants.Positioning.PIGEON_ID;
	public static Pigeon2 pigeon = new Pigeon2(pigeon_id);

	private static final LowPassFilter position_x = new LowPassFilter();
	private static final LowPassFilter position_y = new LowPassFilter();

	// Hub stuff
	private static Vector3 hub;

	private static Optional<Alliance> ally = DriverStation.getAlliance();

	static {
		if (ally.isPresent()) {
			if (ally.get() == Alliance.Red) {
				hub = Constants.Arena.RED_HUB;
			} else if (ally.get() == Alliance.Blue) {
				hub = Constants.Arena.BLUE_HUB;
			}
		} else {
			hub = Constants.Arena.RED_HUB;
		}
	}

	public static Vector3 delta_pos = new Vector3(0.0, 0.0, 0.0);

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
		if (ally.isPresent()) {
			if (ally.get() == Alliance.Red) {
				hub = Constants.Arena.RED_HUB.copy();
				hub.x += 2.0;
			} else if (ally.get() == Alliance.Blue) {
				hub = Constants.Arena.BLUE_HUB.copy();
				hub.x -= 2.0;
			}
		} else {
			hub = Constants.Arena.RED_HUB.copy();
		}

		// Get the grounded position of the robot
		Limelight.periodic();
		Vector3 limelight_data = Limelight.robot_limelight_position.copy();

		if (limelight_data.z > 0.0) {
			if (limelight_data.x != 0.0 && limelight_data.y != 0.0) {
				position.x = limelight_data.x;
				position.y = limelight_data.y;
				position.x = position_x.low_pass(position.x);
				position.y = position_y.low_pass(position.y);

				delta_pos = hub.sub(position);
				delta_pos.z = Math.toRadians(-position.z);
			}
		}

		position.z = pigeon.getYaw().getValueAsDouble();
	}
}
