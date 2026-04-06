package frc.robot.helpers;

import frc.robot.Constants;

public class BallGuidance {
	private static final double gravity = 9.80665; // Gravity in m/s^2

	private static final double minimum_speed = Constants.BallGuidance.MINIMUM_SPEED;

	private static final double puker_angle = Constants.Pukers.PUKER_ANGLE;

	public static final double get_velocity_command(Vector3 delta_pos) {
		double velocity_command = 0.0;

		/*
		 * We can effectively ignore a third dimension as the robot will already be
		 * pointing towards the hub. In this scenario where the angle of fire is fixed,
		 * the only thing we can modulate is the speed of the ball.
		 * 
		 * I'm not gonna lie, I had to use a LLM for this. This is much harder than the
		 * old guidance implimentation you'll find in the git history pre-"1.2.4". I do
		 * understand each indiviual part, but all put together it's ever so slightly
		 * out of my grasp. Here's the formula we're now using:
		 * 
		 * v0 = sqrt( (gravity * delta_x ^ 2) / (2 * cos ^ 2(theta) * (delta_x *
		 * tan(theta) - delta_y ) ) )
		 */

		// Collect our terms
		double delta_x = new Vector3(delta_pos.x, 0.0, delta_pos.z).magnitude();
		double delta_y = delta_pos.y;

		double numerator = gravity * delta_x * delta_x;
		double denominator = 2 * (Math.cos(puker_angle) * Math.cos(puker_angle))
				* (delta_x * Math.tan(puker_angle) - delta_y);

		velocity_command = Math.sqrt(numerator / denominator);

		if (velocity_command < minimum_speed)
			velocity_command = minimum_speed;

		return velocity_command;
	}
}