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
import frc.robot.subsystems.Positioning;

@SuppressWarnings("unused")
public class RobotContainer {

	private final XboxController controller = new XboxController(0);

	private final Trigger rollerfloor = new Trigger(() -> rollercounter > 0);

	// private final JoystickButton l1 = new JoystickButton(controller,
	// XboxController.Button.kLeftBumper.value);
	private final Trigger l2 = new Trigger(() -> controller.getLeftTriggerAxis() > 0.5);
	private final JoystickButton r1 = new JoystickButton(controller, XboxController.Button.kRightBumper.value);
	private final Trigger r2 = new Trigger(() -> controller.getRightTriggerAxis() > 0.5);

	// private final JoystickButton button_y = new JoystickButton(controller,
	// XboxController.Button.kY.value);
	// private final JoystickButton button_b = new JoystickButton(controller, XboxController.Button.kB.value);
	private final JoystickButton button_a = new JoystickButton(controller, XboxController.Button.kA.value);
	private final JoystickButton button_x = new JoystickButton(controller, XboxController.Button.kX.value);

	private final Trigger dpad_up = new Trigger(() -> controller.getPOV() == 0);
	private final Trigger dpad_down = new Trigger(() -> controller.getPOV() == 180);
	// private final Trigger dpad_inline = new Trigger(() -> controller.getPOV() ==
	// 90 || controller.getPOV() == -90);

	private final JoystickButton back = new JoystickButton(controller, XboxController.Button.kBack.value);

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
		// REVERSING
		Command ReversePuker = new ReversePuker();
		Command ReverseCollector = new ReverseCollector();
		l2.whileTrue(ReversePuker);
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
		dpad_down.whileTrue(CollectorDown);

		// ROLLER
		Command RollerFloor = new RollerFloor();
		rollerfloor.whileTrue(RollerFloor);

		// ZEROING
		Command ZeroGyro = new ZeroGyro();
		back.onTrue(ZeroGyro);
	}
}
