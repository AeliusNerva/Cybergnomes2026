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

public class Pukers {
	private static Vector3 hub;
	private static double offset;

	private static final int puker_1_flywheel_motor_id = Constants.Pukers.PUKER_1_FLYWHEEL_MOTOR;
	private static final int puker_1_loader_motor_id = Constants.Pukers.PUKER_1_LOADER_MOTOR;
	private static final int puker_2_flywheel_motor_id = Constants.Pukers.PUKER_2_FLYWHEEL_MOTOR;
	private static final int puker_2_loader_motor_id = Constants.Pukers.PUKER_2_LOADER_MOTOR;

	private static final TalonFX puker_1_flywheel_motor = new TalonFX(puker_1_flywheel_motor_id);
	private static final TalonFX puker_1_loader_motor = new TalonFX(puker_1_loader_motor_id);
	private static final TalonFX puker_2_flywheel_motor = new TalonFX(puker_2_flywheel_motor_id);
	private static final TalonFX puker_2_loader_motor = new TalonFX(puker_2_loader_motor_id);

	private static final double loader_speed = Constants.Pukers.LOADER_SPEED;

	private static final double flywheel_radius = Constants.Pukers.FLYWHEEL_RADIUS;

	private static final double default_speed_command = Constants.Pukers.DEFAULT_SPEED_COMMAND;
	private static double last_speed_command = default_speed_command;
	private static boolean shooting = false;
	private static int not_shooting_counter = 0;

	private static final VelocityVoltage vv = new VelocityVoltage(0).withSlot(0);

	public static double swerve_command = 0.0;

	public static void init() {
		var slot0Configs = new Slot0Configs();
		slot0Configs.kS = 0.1;
		slot0Configs.kP = 0.6;
		slot0Configs.kV = 0.12;
		slot0Configs.kI = 0.1;
		slot0Configs.kD = 0.0;
		puker_1_flywheel_motor.getConfigurator().apply(slot0Configs);
		puker_1_loader_motor.getConfigurator().apply(slot0Configs);
		puker_2_flywheel_motor.getConfigurator().apply(slot0Configs);
		puker_2_loader_motor.getConfigurator().apply(slot0Configs);
	}

	public static void get_commands() {
		Optional<DriverStation.Alliance> alliance = DriverStation.getAlliance();
		if (alliance.isPresent()) {
			if (alliance.get() == Alliance.Red) {
				hub = Constants.Arena.RED_HUB.copy();
				offset = 90.0;
			} else if (alliance.get() == Alliance.Blue) {
				hub = Constants.Arena.BLUE_HUB.copy();
				offset = -90.0;
			} else {
				hub = Constants.Arena.RED_HUB.copy();
				offset = 90.0;
			}
		}

		Vector3 position = new Vector3(Positioning.position.x, 0, Positioning.position.y);
		Vector3 deltapos = hub.sub(position);

		double velocity_command = BallGuidance.get_velocity_command(deltapos);
		double last_speed_command = velocity_command / (2 * Math.PI * flywheel_radius); // may need to multiply
												// by 2

		if (shooting)
			puker_1_flywheel_motor.setControl(vv.withVelocity(last_speed_command));
		puker_2_flywheel_motor.setControl(vv.withVelocity(last_speed_command));

		double target_orientation = Math.atan2(deltapos.x, deltapos.z) + offset;

		swerve_command = target_orientation - Positioning.position.z;
		swerve_command /= 4;
	}

	public static void spin_up_flywheel() {
		puker_1_flywheel_motor.setControl(vv.withVelocity(last_speed_command));
		puker_2_flywheel_motor.setControl(vv.withVelocity(last_speed_command));
	}

	public static void stop_flywheel() {
		puker_1_flywheel_motor.setControl(new DutyCycleOut(0.0));
		puker_2_flywheel_motor.setControl(new DutyCycleOut(0.0));
	}

	public static void fire() {
		shooting = true;
		puker_1_loader_motor.setControl(vv.withVelocity(-loader_speed));
		puker_2_loader_motor.setControl(vv.withVelocity(-loader_speed));
	}

	public static void stop_firing() {
		shooting = false;
		puker_1_loader_motor.setControl(new DutyCycleOut(0.0));
		puker_2_loader_motor.setControl(new DutyCycleOut(0.0));
	}

	public static void reverse_everything() {
		if (!shooting) {
			puker_1_flywheel_motor.setControl(vv.withVelocity(-default_speed_command));
			puker_1_loader_motor.setControl(vv.withVelocity(loader_speed));
			puker_2_flywheel_motor.setControl(vv.withVelocity(-default_speed_command));
			puker_2_loader_motor.setControl(vv.withVelocity(loader_speed));
		}
	}

	public static void reverse_against_the_floor() {
		if (!shooting) {
			not_shooting_counter++;
			puker_1_loader_motor.setControl(vv.withVelocity(-loader_speed));
			puker_2_loader_motor.setControl(vv.withVelocity(-loader_speed));
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