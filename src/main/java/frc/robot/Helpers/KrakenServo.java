package frc.robot.Helpers;

import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import frc.robot.Constants;

public class KrakenServo {
    private static double rotations_per_degree = Constants.Drive.ROTATIONS_PER_DEGREE;
    public static void rotate_to(TalonFX motor, double position) {
        motor.setControl(new PositionVoltage(0).withPosition(rotations_per_degree));
    }
}
