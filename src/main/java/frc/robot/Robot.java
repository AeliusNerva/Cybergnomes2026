package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
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

	@Override
	public void autonomousInit() {
	}

	@Override
	public void autonomousPeriodic() {
	}

	@Override
	public void teleopInit() {
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
