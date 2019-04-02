package com.semgtech.api.simulation.anatomy;

import com.semgtech.api.utils.vector.Vector3d;

import java.util.List;

public class Muscle<T>
        extends AnatomicComposite<MotorUnit<T>>
{

    private static final String DEFAULT_NAME = "Anatomic Muscle";

    public Muscle(final String name, final Vector3d position,
                  final double radius)
    {
        super(name, position, radius);
    }

    public Muscle(final Vector3d position, final double radius)
    {
        super(DEFAULT_NAME, position, radius);
    }
}
