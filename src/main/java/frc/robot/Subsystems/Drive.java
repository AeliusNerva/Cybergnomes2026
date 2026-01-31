package frc.robot.Subsystems;

import frc.robot.Constants;
import frc.robot.Helpers.KrakenServo;

import com.ctre.phoenix6.hardware.TalonFX;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;

public class Drive {
    private static double max_speed = Constants.Drive.MAX_SPEED;
    private static double robot_width = Constants.Drive.ROBOT_WIDTH;
    private static double robot_length = Constants.Drive.ROBOT_LENGTH;
    private static double wheel_inset = Constants.Drive.WHEEL_INSET;

    // MUST follow this exact order
    private static TalonFX[] motors = {
        new TalonFX(Constants.Drive.MOTOR_1),
        new TalonFX(Constants.Drive.MOTOR_2),
        new TalonFX(Constants.Drive.MOTOR_3),
        new TalonFX(Constants.Drive.MOTOR_4),

        new TalonFX(Constants.Drive.SWERVE_MOTOR_1),
        new TalonFX(Constants.Drive.SWERVE_MOTOR_2),
        new TalonFX(Constants.Drive.SWERVE_MOTOR_3),
        new TalonFX(Constants.Drive.SWERVE_MOTOR_4)
    };



    public static void init_swerve_drive() {
        for (int i = 0; i > motors.length; i++) {
            TalonFXConfiguration config = new TalonFXConfiguration();
            config.Slot0.kP = 0.1;
            config.Slot0.kI = 0.0;
            config.Slot0.kD = 0.01;
            motors[i].getConfigurator().apply(config);
        }
    }

    /*
        Takes in the robots yaw relative to world, the X and Y you want the robot to drive,
        and the amount to modulate the speeds of each motor in a way that will turn the robot
        in the desired direction. In an ideal world, a desired_yaw_modulation of -1.0 would
        turn the robot left at 1.0 degrees per second, but that's just not true. Guess and
        test with small values until you get it right.

        Last tested acceptable value: NOT TESTED
    */
    public static void translate(double robotYaw, double xToDrive, double yToDrive, double desiredYawRate) {
        // Robot dimensions: distance from center to each wheel
        double half_width  = robot_width  / 2.0;
        double half_length = robot_length / 2.0;
        half_width -= wheel_inset;
        half_length -= wheel_inset;

    
        // Wheel positions relative to robot center
        double[][] wheel_positions = {
            {-half_width,  half_length}, // Front left
            { half_width,  half_length}, // Front right
            {-half_width, -half_length}, // Back left
            { half_width, -half_length}  // Back right
        };
    
        for (int i = 0; i < 4; i++) {
            double wheel_x = wheel_positions[i][0];
            double wheel_y = wheel_positions[i][1];
    
            // Rotation vector for this wheel
            double rotation_vector_x = -desiredYawRate * wheel_y;
            double rotation_vector_y =  desiredYawRate * wheel_x;
    
            // Combine translation + rotation
            double combined_x = xToDrive + rotation_vector_x;
            double combined_y = yToDrive + rotation_vector_y;
    
            // Compute wheel angle and speed
            double wheel_angle = Math.atan2(combined_y, combined_x) - robotYaw;
            double wheel_speed = Math.sqrt(combined_x * combined_x + combined_y * combined_y) / Math.sqrt(2) * max_speed;
    
            // Rotate the module to the desired angle
            KrakenServo.rotate_to(motors[4 + i], wheel_angle);
    
            // Drive the module at the calculated speed
            motors[i].setControl(new DutyCycleOut(wheel_speed));
        }
    }
}