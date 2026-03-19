package frc.robot.subsystems;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import frc.robot.Constants;

public class Puker {
	private static final int flywheel_motor_id = Constants.Puker.FLYWHEEL_MOTOR;
	private static final int loader_motor_id = Constants.Puker.LOADER_MOTOR;

	private static final double loader_speed = Constants.Puker.LOADER_SPEED;
	private static final double flywheel_speed = Constants.Puker.FLYWHEEL_SPEED;

	private static final TalonFX flywheel_motor = new TalonFX(flywheel_motor_id);
	private static final TalonFX loader_motor = new TalonFX(loader_motor_id);

	private static boolean shooting = false;
	private static int not_shooting_counter = 0;

	private static final VelocityVoltage vv = new VelocityVoltage(0).withSlot(0);

	public static void init() {
		var slot0Configs = new Slot0Configs();
		slot0Configs.kS = 0.1;
		slot0Configs.kP = 0.6;
		slot0Configs.kV = 0.12;
		slot0Configs.kI = 0.1;
		slot0Configs.kD = 0.0;
		flywheel_motor.getConfigurator().apply(slot0Configs);
		loader_motor.getConfigurator().apply(slot0Configs);
	}

	public static void spin_up_flywheel() {
		flywheel_motor.setControl(vv.withVelocity(flywheel_speed));
	}

	public static void stop_flywheel() {
		flywheel_motor.setControl(new DutyCycleOut(0.0));
	}

	public static void fire() {
		shooting = true;
		loader_motor.setControl(vv.withVelocity(-loader_speed));
	}

	public static void stop_firing() {
		shooting = false;
		loader_motor.setControl(new DutyCycleOut(0.0));
	}

	public static void reverse_everything() {
		if (!shooting) {
			flywheel_motor.setControl(vv.withVelocity(-flywheel_speed));
			loader_motor.setControl(vv.withVelocity(loader_speed));
		}
	}

	public static void reverse_against_the_floor() {
		if (!shooting) {
			not_shooting_counter++;
			loader_motor.setControl(vv.withVelocity(loader_speed));
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