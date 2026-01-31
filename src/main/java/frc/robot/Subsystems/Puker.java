package frc.robot.Subsystems;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import frc.robot.Constants;
import frc.robot.Helpers.BallGuidance;
import frc.robot.Helpers.Vector3;

public class Puker {
    private static final Vector3 hub = Constants.Arena.HUB;
    private static final double apogee = hub.y + 1; // Hub's height plus 1 meter for optimal arc

    private static final int flywheel_motor_id = Constants.Puker.FLYWHEEL_MOTOR;
    private static final int loader_motor_id = Constants.Puker.LOADER_MOTOR;

    private static final double loader_speed = Constants.Puker.LOADER_SPEED;
    private static final double flywheel_radius = Constants.Puker.FLYWHEEL_RADIUS;

    private static final TalonFX flywheel_motor = new TalonFX(flywheel_motor_id);
    private static final TalonFX loader_motor = new TalonFX(loader_motor_id);



    private static double last_speed_command = 0.0;



    public static void get_force_command() {
        Vector3 position = hub;
        Vector3 offset = new Vector3(-1.0, 0, 0); // 1 meter away from the hub
        position.sub(offset); // Apply
        position.y = 0; // Set y to zero cause
        Vector3 deltapos = position.sub(hub);

        Vector3 deltavel = new Vector3(0.0, 0.0, 0.0); // Zero velocity of the hub
        deltavel.sub(Positioning.velocity);

        Vector3 velocity = BallGuidance.get_required_velocity(deltapos, apogee, deltavel);

        Vector3 commands = BallGuidance.get_turret_instructions(velocity);

        last_speed_command = commands.x;
    }



    public static void spin_up_flywheel() {
        double required_rps = last_speed_command / (2 * Math.PI * flywheel_radius);
        flywheel_motor.setControl(new VelocityVoltage(required_rps));
    }

    public static void stop_flywheel() {
        flywheel_motor.setControl(new DutyCycleOut(0.0));
    }



    public static void fire() {
        loader_motor.setControl(new DutyCycleOut(loader_speed));
    }

    public static void stop_firing() {
        loader_motor.setControl(new DutyCycleOut(0.0));
    }
}