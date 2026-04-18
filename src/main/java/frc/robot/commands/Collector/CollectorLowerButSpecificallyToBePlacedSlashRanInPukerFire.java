package frc.robot.commands.Collector;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Collector;

public class CollectorLowerButSpecificallyToBePlacedSlashRanInPukerFire extends Command {
	Timer timer = new Timer();

	public CollectorLowerButSpecificallyToBePlacedSlashRanInPukerFire() {
	}

	@Override
	public void initialize() {
		timer.reset();
		timer.start();
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
		if (timer.get() > 1.0) {
			return true;
		}
		return false;
	}
}
