package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Positioning;
import frc.robot.subsystems.Puker;
import frc.robot.subsystems.Turret;

public class Robot extends TimedRobot {
	private RobotContainer rc;

	public Robot() {
	}

	@Override
	public void robotInit() {
		rc = new RobotContainer();
		Collector.init();
		Turret.init();
		Puker.init();
		Puker.get_acceleration_command();
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

	@Override
	public void autonomousInit() {
		autonomousCommand = rc.getAutonomousCommand();

		if (autonomousCommand != null) {
			CommandScheduler.getInstance().schedule(autonomousCommand);
		}
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
		Turret.stop_firing();
		RobotContainer.rollercounter -= 1;
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
