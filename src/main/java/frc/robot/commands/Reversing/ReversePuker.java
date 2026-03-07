package frc.robot.commands.Reversing;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Puker;

public class ReversePuker extends Command {
	public ReversePuker() {
	}

	@Override
	public void initialize() {
		Puker.reverse_everything();
		RobotContainer.rollercounter += 1;
	}

	@Override
	public void execute() {
	}

	@Override
	public void end(boolean interrupted) {
		Puker.stop_everything();
		RobotContainer.rollercounter -= 1;
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
