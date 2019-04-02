package com.semgtech.api.simulation;

import com.semgtech.api.utils.vector.Vector3d;

public class Electrode
{

    private static final String DEFAULT_NAME = "Sensor Electrode";

    private String name;
    private Vector3d position;

    public Electrode(final String name, final Vector3d position)
    {
        this.name = name;
        this.position = position;
    }

    public Electrode(final Vector3d position)
    {
        this(DEFAULT_NAME, position);
    }

    public String getName()
    {
        return name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    public Vector3d getPosition()
    {
        return position;
    }

    public void setPosition(final Vector3d position)
    {
        this.position = position;
    }
}
