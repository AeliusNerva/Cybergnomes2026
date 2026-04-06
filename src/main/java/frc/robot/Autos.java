package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Pukers;
import frc.robot.subsystems.RollerFloor;

public class Autos {
	public void BasicPukerAuto() {

		Timer autoTimer = new Timer();

		autoTimer.reset();
		autoTimer.start();

		Pukers.spin_up_flywheel();
		RollerFloor.start_roller_floor();
		Collector.raise_collector();

		boolean movedone = false;
		boolean shootdone = false;
		boolean done_time_to_start_collector = false;
		boolean done_time_to_stop_collector = false;
		boolean done_stop_everything = false;

		final double max_speed = Constants.Drive.MAX_SPEED;

		RobotContainer.driveReq
				.withVelocityX(0.5 * max_speed);

		RobotContainer.drivetrain.setControl(RobotContainer.driveReq);

		while (!done_time_to_stop_collector) {
			if (!movedone) {
				if (autoTimer.get() > 0.5) {
					movedone = true;
					RobotContainer.driveReq
							.withVelocityX(0.0 * max_speed);

					RobotContainer.drivetrain.setControl(RobotContainer.driveReq);
				}
			}
			if (!shootdone) {
				if (autoTimer.get() > 2.0) {
					shootdone = true;
					Pukers.fire();
					Collector.stop_arm();
				}
			}
			if (!done_time_to_start_collector) {
				if (autoTimer.get() > 6.0) {
					done_time_to_start_collector = true;
					Collector.start_driver();
				}
			}
			if (!done_time_to_stop_collector) {
				if (autoTimer.get() > 7.0) {
					done_time_to_stop_collector = true;
					Collector.stop_driver();
				}
			}
			if (!done_stop_everything) {
				if (autoTimer.get() > 10.0) {
					done_stop_everything = true;
					Pukers.stop_firing();
					Pukers.stop_flywheel();
					RollerFloor.stop_roller_floor();
				}
			}
			Timer.delay(0.05);
		}
	}

	public void BasicPukerAutoStopEverythingAnyway() {
		Pukers.stop_firing();
		Pukers.stop_flywheel();
		RollerFloor.stop_roller_floor();
	}
}
