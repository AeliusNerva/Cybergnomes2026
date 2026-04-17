package frc.robot;

import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Positioning;
import frc.robot.subsystems.Pukers;
import frc.robot.subsystems.RollerFloor;

import org.littletonrobotics.junction.networktables.LoggedNetworkBoolean;
import org.littletonrobotics.junction.networktables.LoggedNetworkNumber;


public class Robot extends TimedRobot {
	@SuppressWarnings("unused")
	private RobotContainer rc;

	public Robot() {
	}

	private final LoggedNetworkNumber puker_speed_input = new LoggedNetworkNumber("/SmartDashboard/PukerSpeed", Constants.Pukers.DEFAULT_SPEED_COMMAND);
	private final LoggedNetworkBoolean puker_left_enable_input = new LoggedNetworkBoolean("/SmartDashboard/PukerLeftEnable", true);
	private final LoggedNetworkBoolean puker_right_enable_input = new LoggedNetworkBoolean("/SmartDashboard/PukerRightEnable", true);

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

	@Override
	public void teleopPeriodic() {
		puker_speed_input.periodic();
		puker_left_enable_input.periodic();
		puker_right_enable_input.periodic();

		Pukers.speed_command = puker_speed_input.get();
		Pukers.left_puker_enable = puker_left_enable_input.get();
		Pukers.right_puker_enable = puker_right_enable_input.get();
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
