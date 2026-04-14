package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Amps;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

import frc.robot.Constants;

public class Collector {
	private static final int collector_motor_id = Constants.Collector.COLLECTOR_MOTOR;
	private static final int left_collector_pivot_motor_id = Constants.Collector.LEFT_COLLECTOR_PIVOT_MOTOR;
	private static final int right_collector_pivot_motor_id = Constants.Collector.RIGHT_COLLECTOR_PIVOT_MOTOR;

	private static final TalonFX collector_motor = new TalonFX(collector_motor_id);
	private static final TalonFX left_collector_pivot_motor = new TalonFX(left_collector_pivot_motor_id);
	private static final TalonFX right_collector_pivot_motor = new TalonFX(right_collector_pivot_motor_id);

	private static final double collector_speed = Constants.Collector.COLLECTOR_SPEED;

	private static final double pivot_speed = Constants.Collector.PIVOT_SPEED;

	private static final VelocityVoltage vv = new VelocityVoltage(0).withSlot(0);

	public static void init() {
		TalonFXConfiguration config = new TalonFXConfiguration();
		config.Slot0.kP = 0.4;
		config.Slot0.kI = 0.05;
		config.Slot0.kD = 0.05;

		config.withCurrentLimits(new CurrentLimitsConfigs().withSupplyCurrentLimit(Amps.of(10)));

		left_collector_pivot_motor.getConfigurator().apply(config);

		right_collector_pivot_motor.setControl(new Follower(left_collector_pivot_motor_id, MotorAlignmentValue.Opposed));
	}

	public static void raise_collector() {
		left_collector_pivot_motor.setControl(vv.withVelocity(-pivot_speed));
	}

	public static void lower_collector() {
		left_collector_pivot_motor.setControl(vv.withVelocity(pivot_speed));
	}

	public static void start_driver() {
		collector_motor.setControl(new DutyCycleOut(-collector_speed));
	}

	public static void stop_driver() {
		collector_motor.setControl(new DutyCycleOut(0.0));
	}

	public static void stop_arm() {
		left_collector_pivot_motor.setControl(new DutyCycleOut(0.0));
	}

	public static void reverse_driver() {
		collector_motor.setControl(new DutyCycleOut(collector_speed));
	}
}
