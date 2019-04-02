package com.semgtech.api.simulation.anatomy;

import com.semgtech.api.utils.vector.Vector3d;

import java.util.ArrayList;
import java.util.List;

public class MotorUnit<T>
        extends AnatomicComposite<Fibre>
{

    private static final String DEFAULT_NAME = "Anatomic Motor Unit";

    private double startingTime;
    private double firingRate;

    private T actionPotential;

    public MotorUnit(final String name, final Vector3d position,
                     final double radius, final double startingTime,
                     final double firingRate, final T actionPotential)
    {
        super(name, position, radius);

        this.startingTime = startingTime;
        this.firingRate = firingRate;

        this.actionPotential = actionPotential;
    }

    public MotorUnit(final Vector3d position, final double radius,
                     final double startingTime, final double firingRate,
                     final T actionPotential)
    {
        this(DEFAULT_NAME, position, radius, startingTime,
                firingRate, actionPotential);
    }

    public double getStartingTime()
    {
        return startingTime;
    }

    public void setStartingTime(final double startingTime)
    {
        this.startingTime = startingTime;
    }

    public double getFiringRate()
    {
        return firingRate;
    }

    public void setFiringRate(final double firingRate)
    {
        this.firingRate = firingRate;
    }

    public T getActionPotential()
    {
        return actionPotential;
    }

    public void setActionPotential(final T actionPotential)
    {
        this.actionPotential = actionPotential;
    }
}
