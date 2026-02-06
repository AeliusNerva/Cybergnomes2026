package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.generated.TunerConstants;
import frc.robot.helpers.Debug;

import com.ctre.phoenix6.swerve.SwerveModule;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.wpilibj.XboxController;

public class Controller {
    private static final XboxController controller = new XboxController(0);
    private static final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

    private static final SwerveRequest.FieldCentric driveReq = new SwerveRequest.FieldCentric()
        .withDriveRequestType(SwerveModule.DriveRequestType.OpenLoopVoltage)
        .withSteerRequestType(SwerveModule.SteerRequestType.Position);

    private static final double stick_minimum = Constants.Controller.STICK_MINIMUM;
    private static final double trigger_minimum = Constants.Controller.TRIGGER_MINIMUM;

    private static final double max_speed = Constants.Drive.MAX_SPEED;
    private static final double max_angular_speed = Constants.Drive.MAX_ANGULAR_SPEED;


    
    private static boolean releasing_l1 = false;
    private static boolean releasing_l2 = false;
    private static boolean releasing_r1 = false;
    private static boolean releasing_r2 = false;



    public static void input_and_output() {
        double raw_left_stick_x = controller.getLeftX();
        double raw_left_stick_y = controller.getLeftY();
        double raw_right_stick_x = controller.getRightX();
        double raw_right_stick_y = controller.getRightY();

        double raw_l2 = controller.getLeftTriggerAxis();
        double raw_r2 = controller.getRightTriggerAxis();

        

        double left_stick_x = (Math.abs(raw_left_stick_x) > stick_minimum) ? raw_left_stick_x : 0.0;
        double left_stick_y = (Math.abs(raw_left_stick_y) > stick_minimum) ? raw_left_stick_y : 0.0;
        double right_stick_x = (Math.abs(raw_right_stick_x) > stick_minimum) ? raw_right_stick_x : 0.0;
        double right_stick_y = (Math.abs(raw_right_stick_y) > stick_minimum) ? raw_right_stick_y : 0.0;
        right_stick_x *= -1;

        boolean triangle = controller.getYButton();
        boolean circle = controller.getBButton();

        boolean l1 = controller.getLeftBumperButton();
        boolean l2 = (raw_l2 > trigger_minimum);
        boolean r1 = controller.getRightBumperButton();
        boolean r2 = (raw_r2 > trigger_minimum);



        // -----     TURRET     -----
            // Spin up turret flywheel if R1 is pressed
            if (r1) {
                Debug.debug("Spinning up turret flywheel...");
                Turret.spin_up_flywheel();
                releasing_r1 = true;
            } else if (releasing_r1) {
                Debug.debug("Stopping turret flywheel...");
                Turret.stop_flywheel();
                releasing_r1 = false;
            }

            // Shoot the turret if R2 is pressed
            if (r2) {
                Debug.debug("Shooting turret...");
                Turret.fire();
                releasing_r2 = true;
            } else if (releasing_r2) {
                Debug.debug("Stopping shooting turret...");
                Turret.stop_firing();
                releasing_r2 = false;
            }



        // -----     PUKER     -----
            // Spin up puker flywheel if L1 is pressed
            if (l1) {
                Debug.debug("Spinning up puker flywheel...");
                Puker.spin_up_flywheel();
                releasing_l1 = true;
            } else if (releasing_l1) {
                Debug.debug("Stopping puker flywheel...");
                Puker.stop_flywheel();
                releasing_l1 = false;
            }

            // Shoot the puker if L2 is pressed
            if (l2) {
                Debug.debug("Shooting puker...");
                Puker.fire();
                releasing_l2 = true;
            } else if (releasing_l2) {
                Debug.debug("Stopping shooting puker...");
                Puker.stop_firing();
                releasing_l2 = false;
            }



        // -----     DRIVING     -----
            driveReq
                .withVelocityX(-right_stick_y * max_speed)
                .withVelocityY(right_stick_x * max_speed)
                .withRotationalRate(-left_stick_x * max_angular_speed);

            drivetrain.setControl(driveReq);



        // -----     COLLECTOR     -----
            // Raise the collector if the triangle button is pressed
            if (triangle) {
                Debug.debug("Raising the collector...");
                Collector.raise_collector();
            }

            // Lower the collector if the circle button is pressed
            if (circle) {
                Debug.debug("Lowering the collector...");
                Collector.lower_collector();
            } 
    }
}
