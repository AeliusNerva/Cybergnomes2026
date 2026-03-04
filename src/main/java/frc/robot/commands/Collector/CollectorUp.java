package frc.robot.commands.Collector;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Collector;
import frc.robot.RobotContainer;

public class CollectorUp extends Command {
	public CollectorUp() {}

	@Override
	public void initialize() {
		Collector.raise_collector();
		RobotContainer.rollercounter -= 1;
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
