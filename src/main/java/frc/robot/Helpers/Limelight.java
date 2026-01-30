package frc.robot.Helpers;

import frc.robot.Constants;


import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

/*
t2d {
    targetValid = index 0
    tx = index 4
    ty = index 5
    tid (target ID) = index 9
    targetShortSidePixels = index 13
    targetHorizontalExtentPixels = index 14
    targetVerticalExtentPixels = index 15
}
*/

public class Limelight {
    private int misscounter = 0;

    private double[] t2d = {};

    private boolean tv = false;
    private double tx = 0;
    private double ty = 0;
    private int tid = 0;
    private double tshort = 0;
    private double thor = 0;
    private double tvert = 0;
    private double tyaw = 0;

    private double distance = 0;

    public Vector3 robot_world_pos = new Vector3(0, 0, 0); // Z is used for yaw

    private static double normalizeangle(double angle) {
        angle = (angle % 360 + 360) % 360;
        return (angle > 180) ? angle - 360 : angle;
    }

    private void getvalues() {
        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        t2d = table.getEntry("t2d").getDoubleArray(new double[0]);
        tv = t2d.length > 0 && t2d[0] == 1;
        if (tv) {
            misscounter = 0;
            tx = t2d.length > 4 ? t2d[4] : 0;
            ty = t2d.length > 5 ? t2d[5] : 0;
            tid = t2d.length > 9 ? (int) Math.round(t2d[9]) : 0;
            tshort = t2d.length > 13 ? t2d[13] : 0;
            thor = t2d.length > 14 ? t2d[14] : 0;
            tvert = t2d.length > 15 ? t2d[15] : 0;

            double[] pose = table.getEntry("targetpose_cameraspace").getDoubleArray(new double[0]);
            tyaw = pose.length > 5 ? pose[5] : 0;
        } else {
            misscounter += 1;
        }

        if (misscounter > 10) {tv = false; tx = 0; ty = 0; tid = 0; tshort = 0; thor = 0; tvert = 0; distance = 0;}
    }

    public void periodic() {
        getvalues();

        if (tv) {
            double p = Math.log(Constants.Limelight.MEASUREMENT_A[1] / Constants.Limelight.MEASUREMENT_B[1]) / Math.log(Constants.Limelight.MEASUREMENT_A[0] / Constants.Limelight.MEASUREMENT_B[0]);
            double k = Constants.Limelight.MEASUREMENT_A[1] / Math.pow(Constants.Limelight.MEASUREMENT_A[0] , p);
            distance = Math.pow(tvert / k, 1 / p);

            double aspect = thor / tvert;
            double unsigned_tyaw = Math.acos((aspect + 1/aspect) / 2);
            tyaw = unsigned_tyaw;
        }

        if (Constants.Arena.APRIL_TAGS.containsKey(tid)) {
            Vector3 april_position = Constants.Arena.APRIL_TAGS.get(tid); // Get the position of the apriltag
            double theta = Math.toRadians(normalizeangle(april_position.z)); // Calculate theta (apriltag yaw - cameraspace apriltag yaw)

            Vector3 robot_relative_pos = new Vector3(0, 0, 0); // This must end up being the relative position of the robot and the apriltag
            robot_relative_pos.x = distance;
            robot_relative_pos.y = distance * Math.sin(Math.toRadians(tx));

            Vector3 robot_translation = new Vector3(0, 0, 0);
            robot_translation.x = robot_relative_pos.x * Math.cos(theta) - robot_relative_pos.y * Math.sin(theta); // Apply the distance
            robot_translation.y = robot_relative_pos.x * Math.sin(theta) + robot_relative_pos.y * Math.cos(theta);

            robot_world_pos = april_position.sub(robot_translation); // April tag position - robot relative position = robot world position
        }
    }

    public void PrintData() {
        System.out.println("tx: " + tx);
        System.out.println("ty: " + ty);
        System.out.println("tid: " + tid);
        System.out.println("tshort: " + tshort);
        System.out.println("thor: " + thor);
        System.out.println("tvert: " + tvert);
        System.out.println("tv: " + tv);
        System.out.println("tyaw: " + tyaw);
        System.out.println();
    }

    public Vector3 GetLimelightResults() {return new Vector3(tx, ty, distance);}
    public Vector3 GetRawLimelightResults() {return new Vector3(tx, ty, tvert);}
}