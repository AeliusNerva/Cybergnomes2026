package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.Puker;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Controller;
import frc.robot.subsystems.Positioning;

public class Robot extends TimedRobot {
	public Robot() {
	}

	@Override
	public void robotInit() {
		Collector.init();
		Turret.init();
		Puker.init();
	}

	@Override
	public void robotPeriodic() {
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
		Controller.input_and_output();
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
