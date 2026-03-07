package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.Collector;
import frc.robot.subsystems.Puker;
import frc.robot.subsystems.Turret;

public class Autos {
	public void BasicPukerAuto() {

		Timer autoTimer = new Timer();

		autoTimer.reset();
		autoTimer.start();

		Puker.spin_up_flywheel();
		Turret.spin_up_flywheel();

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
					Puker.fire();
					Turret.fire();
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
					Puker.stop_firing();
					Turret.stop_firing();
					Puker.stop_flywheel();
					Turret.stop_flywheel();
				}
			}
			Timer.delay(0.05);
		}
	}
}
