package frc.robot.subsystems;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;

import frc.robot.Constants;
import frc.robot.helpers.KrakenServo;

public class Collector {
	private static final int collector_motor_id = Constants.Collector.COLLECTOR_MOTOR;
	private static final int collector_pivot_motor_id = Constants.Collector.COLLECTOR_PIVOT_MOTOR;

	private static final TalonFX collector_motor = new TalonFX(collector_motor_id);
	private static final TalonFX collector_pivot_motor = new TalonFX(collector_pivot_motor_id);

	private static final double collector_speed = Constants.Collector.COLLECTOR_SPEED;
	private static final double collector_rotations_per_degree = Constants.Collector.ROTATIONS_PER_DEGREE;

	private static final double lowered_deg = Constants.Collector.LOWERED_DEG;

	public static void init() {
		var slot0Configs = new Slot0Configs();
		slot0Configs.kP = 1.0;
		slot0Configs.kI = 0.2;
		slot0Configs.kD = 0.0;
		collector_pivot_motor.getConfigurator().apply(slot0Configs);
	}

	public static void lower_collector() {
		KrakenServo.rotate_to(collector_pivot_motor, lowered_deg, collector_rotations_per_degree);
	}

	public static void start_driver() {
		collector_motor.setControl(new DutyCycleOut(-collector_speed));
	}

	public static void stop_driver() {
		collector_motor.setControl(new DutyCycleOut(0.0));
	}

	public static void stop_arm() {
		collector_pivot_motor.setControl(new DutyCycleOut(0.0));
	}
}
