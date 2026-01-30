package frc.robot.Helpers;

public class Vector3 {
    public double x;
    public double y;
    public double z;

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3 add(Vector3 v) {
        return new Vector3(x + v.x, y + v.y, z + v.z);
    }

    public Vector3 sub(Vector3 v) {
        return new Vector3(x - v.x, y - v.y, z - v.z);
    }

    public Vector3 scale(double s) {
        return new Vector3(x * s, y * s, z * s);
    }

    public Vector3 scalar_divide(double s) {
        return new Vector3(x / s, y / s, z / s);
    }

    public double dot(Vector3 v) {
        return x * v.x + y * v.y + z * v.z;
    }

    public double magnitude() {
        return Math.sqrt(x*x + y*y + z*z);
    }

    public Vector3 normalize() {
        double m = this.magnitude();
        if (m == 0) return new Vector3(0, 0, 0);
        return this.scalar_divide(m);
    }

    public Vector3 cross(Vector3 v) {
    return new Vector3(
            y * v.z - z * v.y,
            z * v.x - x * v.z,
            x * v.y - y * v.x
        );
    }

    public double distance(Vector3 v) {
        return this.sub(v).magnitude();
    }

    public Vector3 negate() {
        return new Vector3(-x, -y, -z);
    }

    public boolean equals(Vector3 v) {
        return x == v.x && y == v.y && z == v.z;
    }
}