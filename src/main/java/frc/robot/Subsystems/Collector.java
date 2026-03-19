package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
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

	private static final double raised_deg = Constants.Collector.RAISED_DEG;
	private static final double lowered_deg = Constants.Collector.LOWERED_DEG;

	public static void init() {
		TalonFXConfiguration config = new TalonFXConfiguration();
		config.MotionMagic.MotionMagicAcceleration = 90;
		config.MotionMagic.MotionMagicCruiseVelocity = 45;
		config.Slot0.kP = 0.25;
		config.Slot0.kI = 0.0;
		config.Slot0.kD = 0.0;

		config.Slot1.kP = 0.5;
		config.Slot1.kI = 0.0;
		config.Slot1.kD = 0.0;

		collector_pivot_motor.getConfigurator().apply(config);
	}

	public static void raise_collector() {
		KrakenServo.rotate_to_with_mm(collector_pivot_motor, raised_deg, collector_rotations_per_degree, 1);
	}

	public static void lower_collector() {
		KrakenServo.rotate_to_with_mm(collector_pivot_motor, lowered_deg, collector_rotations_per_degree, 0);
	}

	public static void start_driver() {
		collector_motor.setControl(new DutyCycleOut(collector_speed));
	}

	public static void stop_driver() {
		collector_motor.setControl(new DutyCycleOut(0.0));
	}

	public static void stop_arm() {
		collector_pivot_motor.setControl(new DutyCycleOut(0.0));
	}

	public static void reverse_driver() {
		collector_motor.setControl(new DutyCycleOut(-collector_speed));
	}
}
