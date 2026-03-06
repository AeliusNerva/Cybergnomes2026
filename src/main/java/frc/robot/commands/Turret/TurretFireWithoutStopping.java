package frc.robot.commands.Turret;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Turret;

public class TurretFireWithoutStopping extends Command {
	public TurretFireWithoutStopping() {
	}

	@Override
	public void initialize() {
		Turret.fire();
		RobotContainer.rollercounter += 1;
	}

	@Override
	public void execute() {
	}

	@Override
	public void end(boolean interrupted) {
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
