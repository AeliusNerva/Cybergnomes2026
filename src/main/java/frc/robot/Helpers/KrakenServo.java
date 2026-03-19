package frc.robot.helpers;

import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

public class KrakenServo {
	private static final PositionVoltage pv = new PositionVoltage(0).withSlot(0);
	private static final MotionMagicVoltage mmv = new MotionMagicVoltage(0).withSlot(0);

	public static void rotate_to(TalonFX motor, double position, double gear_ratio) {
		motor.setControl(pv.withPosition(position * gear_ratio));
	}

	public static void rotate_to_with_mm(TalonFX motor, double position, double gear_ratio, int slot) {
		motor.setControl(mmv.withPosition(position * gear_ratio).withSlot(slot));
	}

	public static double get_position(TalonFX motor, double gear_ratio) {
		return motor.getPosition().getValueAsDouble() / gear_ratio;
	}
}