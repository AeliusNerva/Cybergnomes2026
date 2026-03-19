package frc.robot.commands.Collector;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Collector;

public class CollectorUp extends Command {
	public CollectorUp() {
	}

	@Override
	public void initialize() {
		Collector.raise_collector();
	}

	@Override
	public void execute() {
	}

	@Override
	public void end(boolean interrupted) {
		Collector.stop_arm();
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
