package frc.robot;

import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.generated.TunerConstants;
import frc.robot.helpers.Vector3;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Positioning;
import frc.robot.subsystems.Pukers;
import frc.robot.subsystems.RollerFloor;

import org.littletonrobotics.junction.networktables.LoggedNetworkBoolean;
import org.littletonrobotics.junction.networktables.LoggedNetworkNumber;

import com.ctre.phoenix6.swerve.SwerveModule;
import com.ctre.phoenix6.swerve.SwerveRequest;

public class Robot extends TimedRobot {
	@SuppressWarnings("unused")
	private RobotContainer rc;

	public Robot() {
	}

	private final LoggedNetworkNumber puker_speed_input = new LoggedNetworkNumber("/SmartDashboard/PukerSpeed",
			Constants.Pukers.DEFAULT_SPEED_COMMAND);
	private final LoggedNetworkBoolean puker_left_enable_input = new LoggedNetworkBoolean(
			"/SmartDashboard/PukerLeftEnable", true);
	private final LoggedNetworkBoolean puker_right_enable_input = new LoggedNetworkBoolean(
			"/SmartDashboard/PukerRightEnable", true);

	PowerDistribution pd = new PowerDistribution(1, ModuleType.kRev);

	@Override
	public void robotInit() {

		pd.clearStickyFaults();
		rc = new RobotContainer();

		Collector.init();
		Pukers.init();
		RollerFloor.init();

		DataLogManager.start();

		pd.setSwitchableChannel(true);
	}

	@Override
	public void robotPeriodic() {
		CommandScheduler.getInstance().run();
		Positioning.position();

		SmartDashboard.putNumber("puker_1", Pukers.get_velocity_puker_1());
		SmartDashboard.putNumber("puker_2", Pukers.get_velocity_puker_2());
	}

	@Override
	public void disabledInit() {
	}

	@Override
	public void disabledPeriodic() {
	}

	private Command autonomousCommand;
	private Autos autos = new Autos();

	@Override
	public void autonomousInit() {
		/*
		 * autonomousCommand = rc.getAutonomousCommand();
		 * 
		 * if (autonomousCommand != null) {
		 * CommandScheduler.getInstance().schedule(autonomousCommand);
		 * }
		 */
		autos.BasicPukerAuto();
	}

	@Override
	public void autonomousPeriodic() {
	}

	@Override
	public void autonomousExit() {
		/*
		 * Stops firing the turret after auto period is over. This is here because
		 * pathplanner kills all running commands after all of the paths are over,
		 * but that doesn't neccessarily mean that we should stop firing. This truly
		 * stops firing after auto is over, and cleans up after itself.
		 */
		/*
		 * Turret.stop_firing();
		 * RobotContainer.rollercounter -= 1;
		 */

		autos.BasicPukerAutoStopEverythingAnyway();
	}

	@Override
	public void teleopInit() {
		if (autonomousCommand != null) {
			autonomousCommand.cancel();
		}
	}

	private final XboxController controller = new XboxController(0);
	public static final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

	private final double max_speed = Constants.Drive.MAX_SPEED;
	private final double max_angular_speed = Constants.Drive.MAX_ANGULAR_SPEED;

	public static final SwerveRequest.FieldCentric driveReq = new SwerveRequest.FieldCentric()
			.withDriveRequestType(SwerveModule.DriveRequestType.OpenLoopVoltage)
			.withSteerRequestType(SwerveModule.SteerRequestType.Position);

	private double stick_deadband(double input, double deadband) {
		if (Math.abs(input) > Math.abs(deadband)) {
			return (input - deadband) / (1.0 - deadband);
		} else {
			return 0.0;
		}
	}

	@Override
	public void teleopPeriodic() {
		puker_speed_input.periodic();
		puker_left_enable_input.periodic();
		puker_right_enable_input.periodic();

		Pukers.speed_command = puker_speed_input.get();
		Pukers.left_puker_enable = puker_left_enable_input.get();
		Pukers.right_puker_enable = puker_right_enable_input.get();

		if (controller.getBButton()) {

			System.out.println("B");
			Vector3.println(Positioning.delta_pos, 2);
			// Ease to hub
			drivetrain.setDefaultCommand(
					drivetrain.applyRequest(() -> driveReq.withVelocityX(
							stick_deadband(-1 * Math.min(1.0, Positioning.delta_pos.copy().y), 0.1)
									* max_speed / 2.0)
							.withVelocityY(stick_deadband(
									-1 * Math.min(1.0, Positioning.delta_pos.copy().x), 0.1)
									* max_speed / 2.0)
							.withRotationalRate(stick_deadband(
									-1 * Math.min(1.0, Positioning.delta_pos.copy().x), 0.1)
									* max_angular_speed / 2.0)));
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
	}

	@Override
	public void testInit() {
	}

	@Override
	public void testPeriodic() {
	}

	@Override
	public void simulationInit() {
	}

	@Override
	public void simulationPeriodic() {
	}
}
