package frc.robot.subsystems;

import java.util.Optional;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.Constants;
import frc.robot.helpers.BallGuidance;
import frc.robot.helpers.Vector3;

public class Puker {
	private static Vector3 hub;
	public static Optional<Alliance> ally = DriverStation.getAlliance();
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

	private static final double apogee = hub.y + 1; // Hub's height plus 1 meter for optimal arc

	private static final int flywheel_motor_id = Constants.Puker.FLYWHEEL_MOTOR;
	private static final int loader_motor_id = Constants.Puker.LOADER_MOTOR;

	private static final double loader_speed = Constants.Puker.LOADER_SPEED;
	private static final double flywheel_radius = Constants.Puker.FLYWHEEL_RADIUS;

	private static final TalonFX flywheel_motor = new TalonFX(flywheel_motor_id);
	private static final TalonFX loader_motor = new TalonFX(loader_motor_id);

	private static double last_speed_command = 0.0;

	public static void init() {
		var slot0Configs = new Slot0Configs();
		slot0Configs.kP = 5.0;
		slot0Configs.kI = 0.0;
		slot0Configs.kD = 0.0;
		flywheel_motor.getConfigurator().apply(slot0Configs);
	}

	public static void get_acceleration_command() {
		Vector3 position = hub;
		Vector3 offset = new Vector3(-1.0, 0, 0); // 1 meter away from the hub
		position.sub(offset); // Apply
		position.y = 0; // Set y to zero cause
		Vector3 deltapos = position.sub(hub);

		Vector3 deltavel = new Vector3(0.0, 0.0, 0.0); // Zero velocity of the hub
		deltavel.sub(Positioning.velocity);

		Vector3 velocity = BallGuidance.get_required_velocity(deltapos, apogee, deltavel);

		Vector3 commands = BallGuidance.get_turret_instructions(velocity);

		last_speed_command = commands.x;
	}

	public static void spin_up_flywheel() {
		last_speed_command = 5;
		double required_rps = last_speed_command / (2 * Math.PI * flywheel_radius);
		flywheel_motor.setControl(new DutyCycleOut(-0.4));
		// flywheel_motor.setControl(new VelocityVoltage(0).withSlot(0).withVelocity(-required_rps).withFeedForward(0.5));
	}

	public static void stop_flywheel() {
		flywheel_motor.setControl(new DutyCycleOut(0.0));
	}

	public static void fire() {
		loader_motor.setControl(new DutyCycleOut(loader_speed));
	}

	public static void stop_firing() {
		loader_motor.setControl(new DutyCycleOut(0.0));
	}
}