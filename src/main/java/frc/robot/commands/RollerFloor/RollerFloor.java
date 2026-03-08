package frc.robot.commands.RollerFloor;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Puker;
import frc.robot.subsystems.Turret;

public class RollerFloor extends Command {
	public RollerFloor() {
	}

	@Override
	public void initialize() {
		frc.robot.subsystems.RollerFloor.start_roller_floor();
	}

	@Override
	public void execute() {
		Turret.reverse_against_the_floor();
		Puker.reverse_against_the_floor();
	}

	@Override
	public void end(boolean interrupted) {
		frc.robot.subsystems.RollerFloor.stop_roller_floor();
		Turret.stop_floor();
		Puker.stop_floor();
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
