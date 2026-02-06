package frc.robot;

import java.util.HashMap;
import java.util.Map;

import frc.robot.helpers.Vector3;

public final class Constants {
  // Enables/disables all debug prints
  public static boolean DEBUG = true;

  public static final class Positioning {
    public static final int PIGEON_ID = 29;
  }

  public static final class Turret {
      // Highest point of the ball's trajectory in meters 3
      public static final double apogee = 3;

      // CAN IDs of each axis's motor
      public static final int PITCH_MOTOR = 9;
      public static final int YAW_MOTOR = 10;
      public static final int FLYWHEEL_MOTOR = 11;
      public static final int LOADER_MOTOR = 12;

      // Motor speed of the loader, 0.0 -> 0% max, 1.0 -> 100% max
      public static final double LOADER_SPEED = 0.5;

      // Radius of the flywheel in meters
      public static final double FLYWHEEL_RADIUS = 0.050;
  }

  public static final class Puker {
      // Highest point of the ball's trajectory in meters
      public static final double apogee = 3;

      // CAN IDs of each motor
      public static final int FLYWHEEL_MOTOR = 13;
      public static final int LOADER_MOTOR = 14;

      // Motor speed of the loader, 0.0 -> 0% max, 1.0 -> 100% max
      public static final double LOADER_SPEED = 0.5;

      // Radius of the flywheel in meters
      public static final double FLYWHEEL_RADIUS = 0.050;
  }

  public static final class Controller {
      /*  
          Any stick value under this will be resolved to zero.
          0.0-1.0, recomended: 0.1
      */
      public static double STICK_MINIMUM = 0.1;

      /*  
          Any trigger value under this will be resolved to false, anything over will resolve to true.
          0.0-1.0, recomended: 0.5
      */
      public static double TRIGGER_MINIMUM = 0.5;
  }

  public static final class Drive {
      // The rotations per degree of the Kraken X60s motor, if this isn't right I geniunely don't know what to do.
      public static final double ROTATIONS_PER_DEGREE = (150.0/7.0) / 360.0;

    // Max speed of the robot in m/s
      public static final double MAX_SPEED = 2;

      // Max angular speed of the robot in radians/s (1 radian = ~57 degrees)
      public static final double MAX_ANGULAR_SPEED = 3;
  }

  public static final class Collector {
      // CAN IDs of each motor
      public static final int COLLECTOR_PIVOT_MOTOR = 15;
      public static final int COLLECTOR_MOTOR = 16;

      // Motor speed of the loader, 0.0 -> 0% max, 1.0 -> 100% max
      public static final double COLLECTOR_SPEED = 0.5;

      // Collector positions in degrees
      public static final int LOWERED_DEG = 0;
      public static final int RAISED_DEG = 0;
  }

  public static final class Arena {
      // Units in meters, measured from bottom left corner of arena specs diagram

      // -----     POINTS OF INTEREST     -----

      // X: 182.110" -> 4.625m        Y: 158.845" -> 4.034m        Z: 72.000" -> 1.828m
      public static Vector3 HUB = new Vector3(4.625, 4.034, 1.828);



      // -----     APRIL TAGS     -----
      // Z axis will be used for yaw rotation
      // 0 degrees = straight up the Y axis = facing up
      // 90 degrees = straight on the X axis = facing right
      public static Map<Integer, Vector3> APRIL_TAGS = new HashMap<Integer, Vector3>();
      static {
          // X: 182.110" -> 4.625m        Y: 135.090" -> 3.431m        Z: Facing down -> - -180 degrees
          APRIL_TAGS.put(2, new Vector3(4.625, 3.431, -180.0));

          // X: 205.870" -> 5.229m        Y: 144.840" -> 3.678m        Z: Facing right -> - 90 degrees
          APRIL_TAGS.put(3, new Vector3(5.229, 3.678, 90.0));

          // X: 205.870" -> 5.229m        Y: 158.840" -> 4.034m        Z: Facing right -> - 90 degrees
          APRIL_TAGS.put(4, new Vector3(5.229, 4.034, 90.0));

          // X: 182.110" -> 4.625m        Y: 182.600" -> 4.638m        Z: Facing up -> - 0 degrees
          APRIL_TAGS.put(5, new Vector3(4.625, 4.638, 0.0));

          // X: 168.110" -> 4.269m        Y: 182.600" -> 4.638m        Z: Facing up -> 0 degrees
          APRIL_TAGS.put(8, new Vector3(4.269, 4.638, 0.0));

          // X: 158.340" -> 4.021m        Y: 172.840" -> 4.390m        Z: Facing left -> -90 degrees
          APRIL_TAGS.put(9, new Vector3(4.021, 4.390, -90.0));

          // X: 158.340" -> 4.021m        Y: 158.840" -> 4.034m        Z: Facing left -> -90 degrees
          APRIL_TAGS.put(10, new Vector3(4.021, 4.034, -90.0));

          // X: 168.110" -> 4.269m        Y: 135.090" -> 3.431m        Z: Facing down -> -180 degrees
          APRIL_TAGS.put(11, new Vector3(4.269, 3.431, -180.0));


          // 2 -> 18
          APRIL_TAGS.put(18, APRIL_TAGS.get(2));

          // 3 -> 19
          APRIL_TAGS.put(19, APRIL_TAGS.get(3));

          // 4 -> 20
          APRIL_TAGS.put(20, APRIL_TAGS.get(4));

          // 5 -> 21
          APRIL_TAGS.put(21, APRIL_TAGS.get(5));

          // 8 -> 24
          APRIL_TAGS.put(24, APRIL_TAGS.get(8));

          // 9 -> 25
          APRIL_TAGS.put(25, APRIL_TAGS.get(9));

          // 10 -> 26
          APRIL_TAGS.put(26, APRIL_TAGS.get(10));

          // 11 -> 27
          APRIL_TAGS.put(27, APRIL_TAGS.get(11));
      }
  }

  public static final class Limelight {
      public static final double APRIL_TAG_HEIGHT = 0.164; // Width in meters
      public static final double[] MEASUREMENT_A = {0.5, 100}; // Distance in meters, target height in pixels
      public static final double[] MEASUREMENT_B = {1, 47}; // Distance in meters, target height in pixels
  }

  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }
}
