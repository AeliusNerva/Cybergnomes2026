package frc.robot.Subsystems;

import frc.robot.Constants;
import frc.robot.Helpers.Debug;

import edu.wpi.first.wpilibj.XboxController;

public class Controller {
    private static final XboxController controller = new XboxController(0);

    private static final double stick_minimum = Constants.Controller.STICK_MINIMUM;
    private static final double trigger_minimum = Constants.Controller.TRIGGER_MINIMUM;

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

            }

            // Shoot the turret if R2 is pressed
            if (r2) {
                Debug.debug("Shooting turret...");

            }



        // -----     PUKER     -----
            // Spin up puker flywheel if L1 is pressed
            if (l1) {
                Debug.debug("Spinning up puker flywheel...");

            }

            // Shoot the puker if L2 is pressed
            if (l2) {
                Debug.debug("Shooting puker...");

            }



        // -----     DRIVING     -----
            // Translate the robot if any of the stick inputs are not zero
            if ((Math.abs(left_stick_x) + Math.abs(left_stick_y) + Math.abs(right_stick_x) + Math.abs(right_stick_y)) > 0) {
                Debug.debug("Translating...");
                Drive.translate(0, right_stick_x, right_stick_y, left_stick_x);
            } else {
                // Stop the motors
                Drive.translate(0, 0, 0, 0);
            }



        // -----     COLLECTOR     -----
            // Raise the collector if the triangle button is pressed
            if (triangle) {
                Debug.debug("Raising the collector...");
            }

            // Lower the collector if the circle button is pressed
            if (circle) {
                Debug.debug("Lowering the collector...");
            } 
    }
}
