package frc.robot.commands.Reversing;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Collector;

public class ReverseCollector extends Command {
	public ReverseCollector() {
	}

	@Override
	public void initialize() {
		Collector.reverse_driver();
		RobotContainer.rollercounter += 1;
	}

	@Override
	public void execute() {
	}

	@Override
	public void end(boolean interrupted) {
		Collector.stop_driver();
		RobotContainer.rollercounter -= 1;
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
