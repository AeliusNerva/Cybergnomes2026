package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import frc.robot.Constants;

public class RollerFloor {
	private static final int roller_floor_motor_id = Constants.RollerFloor.ROLLER_FLOOR_MOTOR;

	private static final TalonFX roller_floor_motor = new TalonFX(roller_floor_motor_id);

	private static final double gear_ratio = Constants.RollerFloor.GEAR_RATIO;
	private static final double roller_floor_speed = Constants.RollerFloor.ROLLER_FLOOR_SPEED;

	private static final VelocityVoltage vv = new VelocityVoltage(0).withSlot(0);

	public static void init() {
		TalonFXConfiguration config = new TalonFXConfiguration();
		config.Slot0.kP = 0.5;
		config.Slot0.kI = 0.0;
		config.Slot0.kD = 0.0;

		roller_floor_motor.getConfigurator().apply(config);
	}

	public static void start_roller_floor() {
		roller_floor_motor.setControl(vv.withVelocity(-roller_floor_speed * gear_ratio));
	}

	public static void stop_roller_floor() {
		roller_floor_motor.setControl(new DutyCycleOut(0.0));
	}
}