package frc.robot.helpers;

import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import frc.robot.Constants;

public class KrakenServo {
    private static double rotations_per_degree = Constants.Drive.ROTATIONS_PER_DEGREE;
    public static void rotate_to(TalonFX motor, double position) {
        motor.setControl(new PositionVoltage(0).withPosition(rotations_per_degree));
    }

    public static double get_position(TalonFX motor, double gear_ratio) {
        return motor.getPosition().getValueAsDouble() / gear_ratio;
    }
}
