package frc.robot.commands.Puker;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Pukers;

public class PukerFlywheel extends Command {
	public PukerFlywheel() {
	}

	@Override
	public void initialize() {
		Pukers.spin_up_flywheel();
	}

	@Override
	public void execute() {
	}

	@Override
	public void end(boolean interrupted) {
		Pukers.stop_flywheel();
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
