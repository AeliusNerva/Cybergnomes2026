package frc.robot;

import frc.robot.Subsystems.Controller;
import frc.robot.Subsystems.Drive;

import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {

  @Override
  public void robotInit() {
    Drive.init_swerve_drive();
  }

  @Override
  public void robotPeriodic() {}



  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void disabledExit() {}



  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void autonomousExit() {}



  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {
    Controller.input_and_output();
  }

  @Override
  public void teleopExit() {}



  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  @Override
  public void testExit() {}
}
