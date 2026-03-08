package frc.robot.commands.Collector;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Collector;

public class RunCollector extends Command {
	public RunCollector() {
	}

	@Override
	public void initialize() {
		RobotContainer.rollercounter += 1;
		Collector.start_driver();
	}

	@Override
	public void execute() {
	}

	@Override
	public void end(boolean interrupted) {
		RobotContainer.rollercounter -= 1;
		Collector.stop_driver();
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
