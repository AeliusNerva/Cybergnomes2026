package frc.robot;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Positioning;
import frc.robot.subsystems.Pukers;
import frc.robot.subsystems.RollerFloor;

public class Robot extends TimedRobot {
	@SuppressWarnings("unused")
	private RobotContainer rc;

	public Robot() {
	}

	PowerDistribution pd = new PowerDistribution(1, ModuleType.kRev);
	@Override
	public void robotInit() {
		rc = new RobotContainer();
		Collector.init();
		Pukers.init();
		RollerFloor.init();
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
