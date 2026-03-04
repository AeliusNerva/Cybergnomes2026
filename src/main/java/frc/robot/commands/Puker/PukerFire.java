package frc.robot.commands.Puker;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Puker;

public class PukerFire extends Command {
	public PukerFire() {}

	@Override
	public void initialize() {
		Puker.fire();
		RobotContainer.rollercounter += 1;
	}

	@Override
	public void execute() {}

	@Override
	public void end(boolean interrupted) {
		Puker.stop_firing();
		RobotContainer.rollercounter -= 1;
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
