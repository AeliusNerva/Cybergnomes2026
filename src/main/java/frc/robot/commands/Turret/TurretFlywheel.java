package frc.robot.commands.Turret;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Turret;

public class TurretFlywheel extends Command {
	public TurretFlywheel() {}

	@Override
	public void initialize() {
		Turret.spin_up_flywheel();
	}

	@Override
	public void execute() {}

	@Override
	public void end(boolean interrupted) {
		Turret.stop_flywheel();
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
