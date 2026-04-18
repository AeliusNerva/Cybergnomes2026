package frc.robot.commands.Puker;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Pukers;
import frc.robot.commands.Collector.CollectorLowerButSpecificallyToBePlacedSlashRanInPukerFire;

public class PukerFire extends Command {
	Timer timer = new Timer();
	Boolean raised = false;

	public PukerFire() {
	}

	@Override
	public void initialize() {
		timer.reset();
		timer.start();
		Pukers.fire();
		RobotContainer.rollercounter += 1;
	}

	@Override
	public void execute() {
		if (timer.get() > 0.5) {
			raised = true;
			Collector.raise_collector();
		}
		if (timer.get() > 1.0) {
			raised = true;
			Collector.start_driver();
		}
	}

	@SuppressWarnings("removal")
	@Override
	public void end(boolean interrupted) {
		Pukers.stop_firing();
		RobotContainer.rollercounter -= 1;
		if (raised) {
			Collector.stop_arm();
			Collector.stop_driver();
			new CollectorLowerButSpecificallyToBePlacedSlashRanInPukerFire().schedule();
		}
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
