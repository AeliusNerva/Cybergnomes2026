package frc.robot.helpers;

import edu.wpi.first.wpilibj.DigitalOutput;

public class LinearActuator {
	public DigitalOutput init_pwm(int channel) {
		DigitalOutput pwm = new DigitalOutput(channel);
		pwm.setPWMRate(1000.0);
		pwm.enablePWM(0.0);
		return pwm;
	}

	public void disable_pwm(DigitalOutput pwm) {
		pwm.disablePWM();
		pwm.close();
	}

	public void actuate_to(DigitalOutput pwm, double meters, double fullstrokemeters) {
		double percent = meters / fullstrokemeters;
		if (percent < 0.0)
			percent = 0.0;
		if (percent > 1.0)
			percent = 1.0;

		pwm.updateDutyCycle(percent);
	}

	/*
	 * Finds the actuation distance in meters from two given side lengths and an
	 * angle using the law of cosines. The turret can be simplified into a
	 * triangle, where side A is the length of the hood modulation arm, B is
	 * once again the length of the hood modulation arm, and C is the length of
	 * the linear actuator. My ball guidance functions return pitch and yaw
	 * angles for the ball to be shot, so the desired pitch of the ball's velocity
	 * vector can be angle A. To find side C, we do:
	 * 
	 * captials are angles, sides are lowercase.
	 * 
	 * c^2 = a^2 + b^2 - 2ab*cos(A)
	 * 
	 * This is what this function does.
	 */
	public double get_actuation_distance_from_angle(double a, double b, double theta) {
		return Math.sqrt(a * a + b * b - 2 * a * b * Math.cos(theta));
	}
}