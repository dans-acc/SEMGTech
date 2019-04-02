package com.semgtech.api.simulation.anatomy;

import com.semgtech.api.utils.vector.Vector3d;

public abstract class AnatomicComponent
{

    protected String name;
    protected Vector3d position;
    protected double radius;

    protected AnatomicComponent(final String name, final Vector3d position,
                                final double radius)
    {
        this.name = name;
        this.position = position;
        this.radius = radius;
    }

    public String getName()
    {
        return name;
    }

    public Vector3d getPosition()
    {
        return position;
    }

    public void setPosition(final Vector3d position)
    {
        this.position = position;
    }

    public double getRadius()
    {
        return radius;
    }

    public void setRadius(final double radius)
    {
        this.radius = radius;
    }

    public boolean isWithin(final AnatomicComponent component)
    {
        return isWithin(position, radius,
                component.getPosition(), component.getRadius());
    }

    public boolean isWithin(final Vector3d position, final double radius)
    {
        return isWithin(this.position, this.radius,
                position, radius);
    }

    public static boolean isWithin(final Vector3d aPosition, final double aRadius,
                                   final Vector3d bPosition, final double bRadius)
    {
        final double distance = Math.sqrt(Math.pow(bPosition.getX() - aPosition.getX(), 2)
                + Math.pow(bPosition.getY() - aPosition.getY(), 2));
        return aRadius > distance + bRadius;
    }

}
