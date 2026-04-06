package frc.robot.commands.Reversing;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Pukers;

public class ReversePuker extends Command {
	public ReversePuker() {
	}

	@Override
	public void initialize() {
		Pukers.reverse_everything();
		RobotContainer.rollercounter += 1;
	}

	@Override
	public void execute() {
	}

	@Override
	public void end(boolean interrupted) {
		Pukers.stop_everything();
		RobotContainer.rollercounter -= 1;
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
