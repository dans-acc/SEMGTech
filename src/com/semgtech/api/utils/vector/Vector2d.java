package com.semgtech.api.utils.vector;

public class Vector2d
{

    private double x;
    private double y;

    public Vector2d(final double x, final double y)
    {
        this.x = x;
        this.y = y;
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

    public static double euclideanDistance(final Vector2d vector2da,
                                           final Vector2d vector2db)
    {
        return euclideanDistance(vector2da.x, vector2da.y,
                vector2db.x, vector2db.y);
    }

    public static double euclideanDistance(final double ax, final double ay,
                                           final double bx, final double by)
    {
        return Math.sqrt(Math.pow(bx - ax, 2) + Math.pow(by - bx, 2));
    }

    public static Vector2d add(final Vector2d vector2da, final Vector2d vector2db)
    {
        return new Vector2d(
                vector2da.x + vector2db.x,
                vector2da.y + vector2db.y
        );
    }

    public static Vector2d add(final double ax, final double ay,
                               final double bx, final double by)
    {
        return new Vector2d(ax + bx, ay + by);
    }

    @Override
    public String toString()
    {
        return String.format("x: %f, y: %f", x, y);
    }
}
