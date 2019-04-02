package com.semgtech.api.utils.vector;

public class Vector3d
{

    private double x;
    private double y;
    private double z;

    public Vector3d(final double x, final double y,
                    final double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX()
    {
        return x;
    }

    public void setX(final double x)
    {
        this.x = x;
    }

    public double getY()
    {
        return y;
    }

    public void setY(final double y)
    {
        this.y = y;
    }


    public double getZ()
    {
        return z;
    }

    public void setZ(final double z)
    {
        this.z = z;
    }

    public static double euclideanDistance(final Vector3d vector3da, final Vector3d vector3db)
    {
        return euclideanDistance(vector3da.x, vector3da.y, vector3da.z,
                vector3db.x, vector3db.y, vector3db.z);
    }

    public static double euclideanDistance(final double ax, final double ay,
                                           final double az, final double bx,
                                           final double by, final double bz)
    {
        return Math.sqrt(Math.pow(bx - ax, 2) + Math.pow(by - ay, 2) + Math.pow(bz - az, 2));
    }

    public static Vector3d add(final Vector3d vector3da, final Vector3d vector3db)
    {
        return new Vector3d(
                vector3da.x + vector3db.x,
                vector3da.y + vector3db.y,
                vector3da.z + vector3db.z
        );
    }

    public static Vector3d add(final double ax, final double ay,
                               final double az, final double bx,
                               final double by, final double bz)
    {
        return new Vector3d(ax + bx, ay + by, az + bz);
    }

    @Override
    public String toString()
    {
        return String.format("x: %f, y: %f, z: %f", x, y, z);
    }

}
