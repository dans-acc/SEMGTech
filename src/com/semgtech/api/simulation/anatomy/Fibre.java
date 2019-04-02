package com.semgtech.api.simulation.anatomy;

import com.semgtech.api.utils.vector.Vector3d;

public class Fibre extends AnatomicComponent
{

    private static final String DEFAULT_NAME = "Anatomic Muscle Fibre";

    private double length;

    public Fibre(final String name, final Vector3d position,
                 final double radius, final double length)
    {
        super(name, position, radius);
        this.length = length;
    }

    public Fibre(final Vector3d position, final double radius,
                 final double length)
    {
        this(DEFAULT_NAME, position, radius, length);
    }

    public double getLength()
    {
        return length;
    }

    public void setLength(final double length)
    {
        this.length = length;
    }
}
