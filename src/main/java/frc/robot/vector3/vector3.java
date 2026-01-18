package frc.robot.vector3;

public class vector3 {
    public double x;
    public double y;
    public double z;

    public vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public vector3 add(vector3 v) {
        return new vector3(x + v.x, y + v.y, z + v.z);
    }

    public vector3 sub(vector3 v) {
        return new vector3(x - v.x, y - v.y, z - v.z);
    }

    public vector3 scale(double s) {
        return new vector3(x * s, y * s, z * s);
    }

    public vector3 scalar_divide(double s) {
        return new vector3(x / s, y / s, z / s);
    }

    public double dot(vector3 v) {
        return x * v.x + y * v.y + z * v.z;
    }

    public double magnitude() {
        return Math.sqrt(x*x + y*y + z*z);
    }

    public vector3 normalize() {
        double m = this.magnitude();
        if (m == 0) return new vector3(0, 0, 0);
        return this.scalar_divide(m);
    }

    public vector3 cross(vector3 v) {
    return new vector3(
            y * v.z - z * v.y,
            z * v.x - x * v.z,
            x * v.y - y * v.x
        );
    }

    public double distance(vector3 v) {
        return this.sub(v).magnitude();
    }

    public vector3 negate() {
        return new vector3(-x, -y, -z);
    }

    public boolean equals(vector3 v) {
        return x == v.x && y == v.y && z == v.z;
    }
}