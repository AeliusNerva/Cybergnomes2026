package frc.robot.commands.Reversing;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Turret;

public class ReverseTurret extends Command {
	public ReverseTurret() {
	}

	@Override
	public void initialize() {
		Turret.reverse_everything();
		RobotContainer.rollercounter += 1;
	}

	@Override
	public void execute() {
	}

	@Override
	public void end(boolean interrupted) {
		Turret.stop_everything();
		RobotContainer.rollercounter -= 1;
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
