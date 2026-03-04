package frc.robot.commands.Turret;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Turret;

public class TurretFire extends Command {
	public TurretFire() {}

	@Override
	public void initialize() {
		Turret.fire();
		RobotContainer.rollercounter += 1;
	}

	@Override
	public void execute() {}

	@Override
	public void end(boolean interrupted) {
		Turret.stop_firing();
		RobotContainer.rollercounter -= 1;
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
