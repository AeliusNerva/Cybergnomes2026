package frc.robot.main;

import edu.wpi.first.wpilibj.TimedRobot;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.controls.DutyCycleOut;
import frc.robot.ball_guidance.ball_guidance;
import frc.robot.vector3.vector3;

public class main extends TimedRobot {
    // Ball Guidance
    public static final double required_apogee = 3.0; // Ball apogee (highest point of tragectory) in meters 
    TalonFX falcon = new TalonFX(1);

    @Override
    public void teleopPeriodic() {
        falcon.setControl(new DutyCycleOut(0.5));
    }
}
