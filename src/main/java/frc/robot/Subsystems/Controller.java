package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.generated.TunerConstants;
import edu.wpi.first.wpilibj2.command.button.*;
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

    public static final JoystickButton bumper = new JoystickButton(controller, XboxController.Button.kRightBumper.value);

    private static int roller_floor_requests = 0;

    public static void input_and_output() {

        double raw_left_stick_x = controller.getLeftX();
        double raw_left_stick_y = controller.getLeftY();
        double raw_right_stick_x = controller.getRightX();
    //  double raw_right_stick_y = controller.getRightY();

        double raw_l2 = controller.getLeftTriggerAxis();
        double raw_r2 = controller.getRightTriggerAxis();

        double left_stick_x = (Math.abs(raw_left_stick_x) > stick_minimum) ? raw_left_stick_x : 0.0;
        double left_stick_y = (Math.abs(raw_left_stick_y) > stick_minimum) ? raw_left_stick_y : 0.0;
        double right_stick_x = (Math.abs(raw_right_stick_x) > stick_minimum) ? raw_right_stick_x : 0.0;
    //  double right_stick_y = (Math.abs(raw_right_stick_y) > stick_minimum) ? raw_right_stick_y : 0.0;
        right_stick_x *= -1;

        boolean triangle = controller.getYButton();
        boolean circle = controller.getBButton();

        boolean l1 = controller.getLeftBumperButton();
        boolean l2 = (raw_l2 > trigger_minimum);
        boolean r1 = controller.getRightBumperButton();
        boolean r2 = (raw_r2 > trigger_minimum);
        // ----- TURRET -----
        // Spin up turret flywheel if R1 is pressed
        if (r1) {
            Turret.spin_up_flywheel();
            releasing_r1 = true;
        } else if (releasing_r1) {
            Turret.stop_flywheel();
            releasing_r1 = false;
        }

        // Shoot the turret if R2 is pressed
        if (r2) {
            Turret.fire();
            roller_floor_requests += 1;
            releasing_r2 = true;
        } else if (releasing_r2) {
            Turret.stop_firing();
            roller_floor_requests += -1;
            releasing_r2 = false;
        }

        // ----- PUKER -----
        // Spin up puker flywheel if L1 is pressed
        if (l1) {
            Puker.spin_up_flywheel();
            releasing_l1 = true;
        } else if (releasing_l1) {
            Puker.stop_flywheel();
            releasing_l1 = false;
        }

        // Shoot the puker if L2 is pressed
        if (l2) {
            Puker.fire();
            roller_floor_requests += 1;
            releasing_l2 = true;
        } else if (releasing_l2) {
            Puker.stop_firing();
            roller_floor_requests += -1;
            releasing_l2 = false;
        }

        // ----- DRIVING -----
        driveReq
                .withVelocityX(-left_stick_y * max_speed)
                .withVelocityY(left_stick_x * max_speed)
                .withRotationalRate(-right_stick_x * max_angular_speed);

        drivetrain.setControl(driveReq);

        // ----- COLLECTOR -----
        // Raise the collector if the triangle button is pressed
        if (triangle) {
            Collector.raise_collector();
            roller_floor_requests += 1;
        }

        // Lower the collector if the circle button is pressed
        if (circle) {
            Collector.lower_collector();
            roller_floor_requests += -1;
        }

        if (roller_floor_requests != 0) {
            RollerFloor.start_roller_floor();
        } else {
            RollerFloor.stop_roller_floor();
        }
    }
}
