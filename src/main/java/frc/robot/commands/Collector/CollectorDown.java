package frc.robot.commands.Collector;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Collector;

public class CollectorDown extends Command {
	public CollectorDown() {
	}

	@Override
	public void initialize() {
		Collector.lower_collector();
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
