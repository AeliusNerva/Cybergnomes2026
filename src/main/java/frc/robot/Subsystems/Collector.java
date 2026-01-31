package frc.robot.Subsystems;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;

import frc.robot.Constants;
import frc.robot.Helpers.KrakenServo;

public class Collector {
    private static final int collector_motor_id = Constants.Collector.COLLECTOR_MOTOR;
    private static final int collector_pivot_motor_id = Constants.Collector.COLLECTOR_PIVOT_MOTOR;

    private static final TalonFX collector_motor = new TalonFX(collector_motor_id);
    private static final TalonFX collector_pivot_motor = new TalonFX(collector_pivot_motor_id);

    private static final double collector_speed = Constants.Collector.COLLECTOR_SPEED;

    private static final double lowered_deg = Constants.Collector.LOWERED_DEG;
    private static final double raised_deg = Constants.Collector.RAISED_DEG;

    public static void lower_collector() {
        collector_motor.setControl(new DutyCycleOut(collector_speed));
        KrakenServo.rotate_to(collector_pivot_motor, lowered_deg);
    }

    public static void raise_collector() {
        collector_motor.setControl(new DutyCycleOut(0.0));
        KrakenServo.rotate_to(collector_pivot_motor, raised_deg);
    }
}
