package frc.robot.commands.Collector;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Collector;

public class StopPivot extends Command {
	public StopPivot() {
	}

	@Override
	public void initialize() {
		Collector.stop_arm();
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
