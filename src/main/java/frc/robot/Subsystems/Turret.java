package frc.robot.Subsystems;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import frc.robot.Constants;
import frc.robot.Helpers.BallGuidance;
import frc.robot.Helpers.Vector3;
import frc.robot.Helpers.KrakenServo;

public class Turret {
    private static final Vector3 hub = Constants.Arena.HUB;
    private static final double apogee = hub.y + 1; // Hub's height plus 1 meter for optimal arc

    private static final int pitch_motor_id = Constants.Turret.PITCH_MOTOR;
    private static final int yaw_motor_id = Constants.Turret.YAW_MOTOR;
    private static final int flywheel_motor_id = Constants.Turret.FLYWHEEL_MOTOR;
    private static final int loader_motor_id = Constants.Turret.LOADER_MOTOR;

    private static final double loader_speed = Constants.Turret.LOADER_SPEED;
    private static final double flywheel_radius = Constants.Turret.FLYWHEEL_RADIUS;

    private static final TalonFX pitch_motor = new TalonFX(pitch_motor_id);
    private static final TalonFX yaw_motor = new TalonFX(yaw_motor_id);
    private static final TalonFX flywheel_motor = new TalonFX(flywheel_motor_id);
    private static final TalonFX loader_motor = new TalonFX(loader_motor_id);



    private static double last_speed_command = 0.0;



    public static void lock_onto_hub() {
        if (Positioning.locked) {
            Vector3 position = new Vector3(Positioning.position.x, 0, Positioning.position.y);
            Vector3 deltapos = position.sub(hub);
    
            Vector3 deltavel = new Vector3(0.0, 0.0, 0.0); // Zero velocity of the hub
            deltavel.sub(Positioning.velocity);
    
            Vector3 velocity = BallGuidance.get_required_velocity(deltapos, apogee, deltavel);
    
            Vector3 commands = BallGuidance.get_turret_instructions(velocity);
    
            KrakenServo.rotate_to(pitch_motor, commands.x);
            KrakenServo.rotate_to(yaw_motor, commands.y);
            last_speed_command = commands.x;
        }
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