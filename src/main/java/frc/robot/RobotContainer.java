package frc.robot;

import com.ctre.phoenix6.swerve.SwerveModule;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.Collector.CollectorDown;
import frc.robot.commands.Collector.CollectorUp;
import frc.robot.commands.Puker.PukerFire;
import frc.robot.commands.Puker.PukerFlywheel;
import frc.robot.commands.RollerFloor.RollerFloor;
import frc.robot.commands.Turret.TurretFire;
import frc.robot.commands.Turret.TurretFlywheel;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;

public class RobotContainer {

    private final XboxController controller = new XboxController(0);
    private final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

    private static final SwerveRequest.FieldCentric driveReq = new SwerveRequest.FieldCentric()
            .withDriveRequestType(SwerveModule.DriveRequestType.OpenLoopVoltage)
            .withSteerRequestType(SwerveModule.SteerRequestType.Position);

    private final double max_speed = Constants.Drive.MAX_SPEED;
    private final double max_angular_speed = Constants.Drive.MAX_ANGULAR_SPEED;

    private final Trigger rollerfloor = new Trigger(() -> rollercounter > 0);

    private final JoystickButton l1 = new JoystickButton(controller, PS4Controller.Button.kL1.value);
    private final Trigger l2 = new Trigger(() -> controller.getLeftTriggerAxis() > 0.5);
    private final JoystickButton r1 = new JoystickButton(controller, PS4Controller.Button.kR1.value);
    private final Trigger r2 = new Trigger(() -> controller.getRightTriggerAxis() > 0.5);

    private final JoystickButton triangle = new JoystickButton(controller, PS4Controller.Button.kTriangle.value);
    private final JoystickButton circle = new JoystickButton(controller, XboxController.Button.kB.value);
    /*
    private final JoystickButton cross = new JoystickButton(controller, PS4Controller.Button.kCross.value);
    private final JoystickButton square = new JoystickButton(controller, PS4Controller.Button.kSquare.value);
    */
    
    public static int rollercounter = 0;

    private final SendableChooser<Command> autoChooser;

public Command getAutonomousCommand() {
    // This returns the command for the auto you picked on the Dashboard
    return autoChooser.getSelected();
}

    public RobotContainer() {
	NamedCommands.registerCommand("Collector Down", new CollectorDown());
	NamedCommands.registerCommand("Collector Up", new CollectorUp());

	autoChooser = AutoBuilder.buildAutoChooser();

        configureBindings();

        SmartDashboard.putData("Auto Chooser", autoChooser);
    }

    private void configureBindings() {
        // TURRET
        Command TurretFlywheel = new TurretFlywheel();
        r1.whileTrue(TurretFlywheel);
        Command TurretFire = new TurretFire();
        r2.whileTrue(TurretFire);

        // PUKER
        Command PukerFlywheel = new PukerFlywheel();
        l1.whileTrue(PukerFlywheel);
        Command PukerFire = new PukerFire();
        l2.whileTrue(PukerFire);

        // COLLECTOR
        Command CollectorUp = new CollectorUp();
        triangle.whileTrue(CollectorUp);
        Command CollectorDown = new CollectorDown();
        circle.whileTrue(CollectorDown);

        // SWERVES
        drivetrain.setDefaultCommand(
                drivetrain.applyRequest(()
                        -> driveReq
                        .withVelocityX(controller.getLeftY() * max_speed)
                        .withVelocityY(-controller.getLeftX() * max_speed)
                        .withRotationalRate(-controller.getRightX() * max_angular_speed)
                )
        );

        final var idle = new SwerveRequest.Idle();
        RobotModeTriggers.disabled().whileTrue(
                drivetrain.applyRequest(() -> idle).ignoringDisable(true)
        );

        // ROLLER 
        /*
        Command RollerFloor = new RollerFloor();
        rollerfloor.whileTrue(RollerFloor);
        */
    }
}
