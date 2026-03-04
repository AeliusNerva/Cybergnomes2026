package frc.robot.commands.RollerFloor;

import edu.wpi.first.wpilibj2.command.Command;

public class RollerFloor extends Command {
	public RollerFloor() {}

	@Override
	public void initialize() {
		frc.robot.subsystems.RollerFloor.start_roller_floor();
	}

	@Override
	public void execute() {}

	@Override
	public void end(boolean interrupted) {
		frc.robot.subsystems.RollerFloor.stop_roller_floor();
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
