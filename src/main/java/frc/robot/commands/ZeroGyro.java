package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Positioning;

public class ZeroGyro extends Command {
  public ZeroGyro() {
  }

  @Override
  public void initialize() {
      Positioning.pigeon.setYaw(0);
  }

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
