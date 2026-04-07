package frc.robot;

import com.ctre.phoenix6.swerve.SwerveModule;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.Reversing.ReverseCollector;
import frc.robot.commands.Reversing.ReversePuker;
import frc.robot.commands.RollerFloor.RollerFloor;
import frc.robot.commands.Collector.RunCollector;
import frc.robot.commands.Collector.CollectorUp;
import frc.robot.commands.ZeroGyro;
import frc.robot.commands.Collector.CollectorDown;
import frc.robot.commands.Puker.PukerFire;
import frc.robot.commands.Puker.PukerFlywheel;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;

public class RobotContainer {

	private final XboxController controller = new XboxController(0);
	public static final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

	public static final SwerveRequest.FieldCentric driveReq = new SwerveRequest.FieldCentric()
			.withDriveRequestType(SwerveModule.DriveRequestType.OpenLoopVoltage)
			.withSteerRequestType(SwerveModule.SteerRequestType.Position);

	private final double max_speed = Constants.Drive.MAX_SPEED;
	private final double max_angular_speed = Constants.Drive.MAX_ANGULAR_SPEED;

	private final Trigger rollerfloor = new Trigger(() -> rollercounter > 0);

	// private final JoystickButton l1 = new JoystickButton(controller,
	// XboxController.Button.kLeftBumper.value);
	private final Trigger l2 = new Trigger(() -> controller.getLeftTriggerAxis() > 0.5);
	private final JoystickButton r1 = new JoystickButton(controller, XboxController.Button.kRightBumper.value);
	private final Trigger r2 = new Trigger(() -> controller.getRightTriggerAxis() > 0.5);

	private final JoystickButton button_y = new JoystickButton(controller, XboxController.Button.kY.value);
	private final JoystickButton button_b = new JoystickButton(controller, XboxController.Button.kB.value);
	private final JoystickButton button_a = new JoystickButton(controller, XboxController.Button.kA.value);
	private final JoystickButton button_x = new JoystickButton(controller, XboxController.Button.kX.value);

	private final Trigger dpad_up = new Trigger(() -> controller.getPOV() == 0);

	private final JoystickButton back = new JoystickButton(controller, XboxController.Button.kBack.value);

	public static int rollercounter = 0;

	private final SendableChooser<Command> autoChooser;

	private double stick_deadband(double input, double deadband) {
		if (Math.abs(input) > Math.abs(deadband)) {
			return input;
		} else {
			return 0.0;
		}
	}

	public Command getAutonomousCommand() {
		// This returns the command for the auto you picked on the Dashboard
		return autoChooser.getSelected();
	}

	public RobotContainer() {
		NamedCommands.registerCommand("Collector Down", new CollectorDown());
		// NamedCommands.registerCommand("Collector Up", new CollectorUp());

		autoChooser = AutoBuilder.buildAutoChooser();

		configureBindings();

		SmartDashboard.putData("Auto Chooser", autoChooser);
	}

	private void configureBindings() {
		// REVERSING
		Command ReversePuker = new ReversePuker();
		Command ReverseCollector = new ReverseCollector();
		button_y.whileTrue(ReversePuker);
		button_x.whileTrue(ReverseCollector);

		// PUKER
		Command PukerFlywheel = new PukerFlywheel();
		Command PukerFire = new PukerFire();
		r1.whileTrue(PukerFlywheel);
		r2.whileTrue(PukerFire);

		// COLLECTOR
		Command RunCollector = new RunCollector();
		Command CollectorUp = new CollectorUp();
		Command CollectorDown = new CollectorDown();
		button_a.whileTrue(RunCollector);
		dpad_up.whileTrue(CollectorUp);
		button_b.whileTrue(CollectorDown);

		// SWERVES
		/*
		 * on this date (2026-03-06) as per Andrew's request I hereby decree
		 * that Xbox controllers can be directly mapped to our swerve drives
		 * with no inverting, just Y goes to X and vice versa
		 */

		if (l2.getAsBoolean()) {
			// Slow down the robot
			drivetrain.setDefaultCommand(
					drivetrain.applyRequest(() -> driveReq
							.withVelocityX(stick_deadband(controller.getLeftY(), 0.1)
									* max_speed / 2.0)
							.withVelocityY(stick_deadband(controller.getLeftX(), 0.1)
									* max_speed / 2.0)
							.withRotationalRate(
									-controller.getRightX() * max_angular_speed
											/ 2.0)));
		} else {
			// Normal
			drivetrain.setDefaultCommand(
					drivetrain.applyRequest(() -> driveReq
							.withVelocityX(stick_deadband(controller.getLeftY(), 0.1)
									* max_speed)
							.withVelocityY(stick_deadband(controller.getLeftX(), 0.1)
									* max_speed)
							.withRotationalRate(stick_deadband(-controller.getRightX(), 0.1)
									* max_angular_speed)));
		}

		final var idle = new SwerveRequest.Idle();
		RobotModeTriggers.disabled().whileTrue(
				drivetrain.applyRequest(() -> idle).ignoringDisable(true));

		// ROLLER
		Command RollerFloor = new RollerFloor();
		rollerfloor.whileTrue(RollerFloor);

		// ZEROING
		Command ZeroGyro = new ZeroGyro();
		back.onTrue(ZeroGyro);
	}
}
