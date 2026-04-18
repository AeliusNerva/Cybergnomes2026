package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import frc.robot.Constants;

public class Pukers {
	private static final int puker_1_flywheel_motor_id = Constants.Pukers.PUKER_1_FLYWHEEL_MOTOR;
	private static final int puker_1_loader_motor_id = Constants.Pukers.PUKER_1_LOADER_MOTOR;
	private static final int puker_2_flywheel_motor_id = Constants.Pukers.PUKER_2_FLYWHEEL_MOTOR;
	private static final int puker_2_loader_motor_id = Constants.Pukers.PUKER_2_LOADER_MOTOR;

	private static final TalonFX puker_1_flywheel_motor = new TalonFX(puker_1_flywheel_motor_id);
	private static final TalonFX puker_1_loader_motor = new TalonFX(puker_1_loader_motor_id);
	private static final TalonFX puker_2_flywheel_motor = new TalonFX(puker_2_flywheel_motor_id);
	private static final TalonFX puker_2_loader_motor = new TalonFX(puker_2_loader_motor_id);

	private static final double default_speed_command = Constants.Pukers.DEFAULT_SPEED_COMMAND;
	public static double speed_command = default_speed_command;
	private static boolean shooting = false;
	private static int not_shooting_counter = 0;

	public static boolean left_puker_enable = true;
	public static boolean right_puker_enable = true;

	private static final VelocityVoltage vv = new VelocityVoltage(0).withSlot(0);

	public static void init() {
		TalonFXConfiguration config = new TalonFXConfiguration();
		config.Slot0.kS = 0.1;
		config.Slot0.kV = 0.12;
		config.Slot0.kP = 1.0;
		config.Slot0.kI = 0.1;
		config.Slot0.kD = 0.0;

		puker_1_flywheel_motor.getConfigurator().apply(config);
		puker_1_loader_motor.getConfigurator().apply(config);

		config.Slot0.kS = 0.1;
		config.Slot0.kV = 0.12;
		config.Slot0.kP = 1.0;
		config.Slot0.kI = 0.05;
		config.Slot0.kD = 0.0;

		puker_2_flywheel_motor.getConfigurator().apply(config);
		puker_2_loader_motor.getConfigurator().apply(config);
	}

	public static double get_velocity_puker_1() {
		return puker_1_flywheel_motor.getVelocity().getValueAsDouble();
	}

	public static double get_velocity_puker_2() {
		return puker_2_flywheel_motor.getVelocity().getValueAsDouble();
	}

	public static void spin_up_flywheel() {
		if (left_puker_enable) {
			puker_1_flywheel_motor.setControl(vv.withVelocity(speed_command));
		} else {
			puker_1_flywheel_motor.setControl(new DutyCycleOut(0.0));
		}
		if (left_puker_enable) {
			puker_2_flywheel_motor.setControl(vv.withVelocity(speed_command));
		} else {
			puker_2_flywheel_motor.setControl(new DutyCycleOut(0.0));
		}
	}

	public static void stop_flywheel() {
		puker_1_flywheel_motor.setControl(new DutyCycleOut(0.0));
		puker_2_flywheel_motor.setControl(new DutyCycleOut(0.0));
	}

	public static void fire() {
		shooting = true;
		if (left_puker_enable) {
			puker_1_loader_motor.setControl(vv.withVelocity(-speed_command / 3.0));
		} else {
			puker_1_loader_motor.setControl(new DutyCycleOut(0.0));
		}
		if (right_puker_enable) {
			puker_2_loader_motor.setControl(vv.withVelocity(-speed_command / 3.0));
		} else {
			puker_2_loader_motor.setControl(new DutyCycleOut(0.0));
		}
	}

	public static void stop_firing() {
		shooting = false;
		puker_1_loader_motor.setControl(new DutyCycleOut(0.0));
		puker_2_loader_motor.setControl(new DutyCycleOut(0.0));
	}

	public static void reverse_everything() {
		if (!shooting) {
			puker_1_flywheel_motor.setControl(vv.withVelocity(-default_speed_command));
			puker_1_loader_motor.setControl(vv.withVelocity(speed_command / 3.0));
			puker_2_flywheel_motor.setControl(vv.withVelocity(-default_speed_command));
			puker_2_loader_motor.setControl(vv.withVelocity(speed_command / 3.0));
		}
	}

	public static void reverse_against_the_floor() {
		if (!shooting) {
			not_shooting_counter++;
			puker_1_loader_motor.setControl(vv.withVelocity(speed_command / 6.0));
			puker_2_loader_motor.setControl(vv.withVelocity(speed_command / 6.0));
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