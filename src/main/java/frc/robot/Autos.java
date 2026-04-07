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

		boolean firstshootdone = false;
		boolean firstshootstopdone = false;
		boolean shootdone = false;
		boolean done_time_to_start_collector = false;
		boolean done_time_to_stop_collector = false;
		boolean done_stop_everything = false;

		while (!done_time_to_stop_collector) {
			if (!firstshootdone) {
				if (autoTimer.get() > 2.0) {
					firstshootdone = true;
					Pukers.fire();
					Collector.stop_arm();
				}
			}
			if (!firstshootstopdone) {
				if (autoTimer.get() > 2.1) {
					firstshootstopdone = true;
					Pukers.stop_firing();
				}
			}
			if (!shootdone) {
				if (autoTimer.get() > 3.0) {
					shootdone = true;
					Pukers.fire();
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
