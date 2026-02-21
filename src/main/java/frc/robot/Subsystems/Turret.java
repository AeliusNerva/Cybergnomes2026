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
import frc.robot.helpers.KrakenServo;

public class Turret {
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

	private static final int pitch_motor_id = Constants.Turret.PITCH_MOTOR;
	private static final int yaw_motor_id = Constants.Turret.YAW_MOTOR;
	private static final int flywheel_motor_1_id = Constants.Turret.FLYWHEEL_MOTOR_1;
	private static final int flywheel_motor_2_id = Constants.Turret.FLYWHEEL_MOTOR_2;
	private static final int intake_motor_id = Constants.Turret.INTAKE_MOTOR;

	private static final double intake_speed = Constants.Turret.INTAKE_SPEED;
	private static final double flywheel_radius = Constants.Turret.FLYWHEEL_RADIUS;

	private static final TalonFX pitch_motor = new TalonFX(pitch_motor_id);
	private static final TalonFX yaw_motor = new TalonFX(yaw_motor_id);
	private static final TalonFX flywheel_motor_1 = new TalonFX(flywheel_motor_1_id);
	private static final TalonFX flywheel_motor_2 = new TalonFX(flywheel_motor_2_id);
	private static final TalonFX intake_motor = new TalonFX(intake_motor_id);

	private static final VelocityVoltage vv = new VelocityVoltage(0).withSlot(0);

	private static double last_speed_command = 0.0;

	public static void init() {
		var slot0Configs = new Slot0Configs();
		slot0Configs.kS = 0.1;
		slot0Configs.kP = 0.6;
		slot0Configs.kV = 0.12;
		slot0Configs.kI = 0.1;
		slot0Configs.kD = 0.0;
		flywheel_motor_1.getConfigurator().apply(slot0Configs);
		flywheel_motor_2.getConfigurator().apply(slot0Configs);
		yaw_motor.getConfigurator().apply(slot0Configs);
	}

	public static void lock_onto_hub() {
		Vector3 position = new Vector3(Positioning.position.x, 0, Positioning.position.y);
		Vector3 deltapos = position.sub(hub);

		Vector3 deltavel = new Vector3(0.0, 0.0, 0.0); // Zero velocity of the hub
		deltavel.sub(Positioning.velocity);

		Vector3 velocity = BallGuidance.get_required_velocity(deltapos, apogee, deltavel);

		Vector3 commands = BallGuidance.get_turret_instructions(velocity);

		KrakenServo.rotate_to(pitch_motor, commands.x);
		KrakenServo.rotate_to(yaw_motor, commands.y);

		last_speed_command = commands.x;
	}

	public static void rotate_yaw(double input) {
		yaw_motor.setControl(new DutyCycleOut(input / 10));
	}

	public static void spin_up_flywheel() {
		last_speed_command = 10; // m/s
		double required_rps = last_speed_command / (2 * Math.PI * flywheel_radius);

		System.out.println(flywheel_motor_1.getMotorVoltage().getValueAsDouble());

		System.out.println(required_rps);
		System.out.println(flywheel_motor_1.getVelocity().getValueAsDouble());
		System.out.println(flywheel_motor_2.getVelocity().getValueAsDouble());
		System.out.println();

		/*
		flywheel_motor_1.setControl(new DutyCycleOut(0.5));
		flywheel_motor_2.setControl(new DutyCycleOut(-0.5));
		*/

		flywheel_motor_1.setControl(vv.withVelocity(required_rps));
		flywheel_motor_2.setControl(vv.withVelocity(-required_rps));
	}

	public static void stop_flywheel() {
		flywheel_motor_1.setControl(new DutyCycleOut(0.0));
		flywheel_motor_2.setControl(new DutyCycleOut(0.0));
	}

	public static void fire() {
		intake_motor.setControl(new DutyCycleOut(-intake_speed));
	}

	public static void stop_firing() {
		intake_motor.setControl(new DutyCycleOut(0.0));
	}
}