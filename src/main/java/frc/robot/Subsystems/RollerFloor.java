package frc.robot.subsystems;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;

import frc.robot.Constants;

public class RollerFloor {
    private static final int roller_floor_motor_id = Constants.RollerFloor.ROLLER_FLOOR_MOTOR;

    private static final TalonFX roller_floor_motor = new TalonFX(roller_floor_motor_id);

    private static final double roller_floor_speed = Constants.RollerFloor.ROLLER_FLOOR_SPEED;

    public static void start_roller_floor() {
        roller_floor_motor.setControl(new DutyCycleOut(roller_floor_speed));
    }

    public static void stop_roller_floor() {
        roller_floor_motor.setControl(new DutyCycleOut(0.0));
    }
}
