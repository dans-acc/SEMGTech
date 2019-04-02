package com.semgtech.api.generation.anatomy;

import com.semgtech.api.simulation.anatomy.MotorUnit;
import com.semgtech.api.simulation.anatomy.Muscle;
import com.semgtech.api.utils.vector.Vector3d;

public class MuscleAnatomicGenerator<T>
        implements AnatomicGenerator<Muscle<T>>
{

    private AnatomicGenerator<MotorUnit<T>> motorUnitAnatomicGenerator;
    private int numMotorUnits;

    public MuscleAnatomicGenerator(final AnatomicGenerator<MotorUnit<T>> motorUnitAnatomicGenerator,
                                   final int numMotorUnits)
    {
        this.motorUnitAnatomicGenerator = motorUnitAnatomicGenerator;
        this.numMotorUnits = numMotorUnits;
    }

    public AnatomicGenerator<MotorUnit<T>> getMotorUnitAnatomicGenerator()
    {
        return motorUnitAnatomicGenerator;
    }

    public void setMotorUnitAnatomicGenerator(final AnatomicGenerator<MotorUnit<T>> motorUnitAnatomicGenerator)
    {
        this.motorUnitAnatomicGenerator = motorUnitAnatomicGenerator;
    }

    public int getNumMotorUnits()
    {
        return numMotorUnits;
    }

    public void setNumMotorUnits(final int numMotorUnits)
    {
        this.numMotorUnits = numMotorUnits;
    }

    @Override
    public Muscle<T> nextAnatomicComponent(Vector3d position, double radius)
    {
        final Muscle<T> muscle = new Muscle<>(position, radius);
        if (motorUnitAnatomicGenerator == null || numMotorUnits <= 0)
            return muscle;

        for (int generatedMotorUnits = 0; generatedMotorUnits < numMotorUnits; ++generatedMotorUnits) {
            final MotorUnit<T> motorUnit = motorUnitAnatomicGenerator.nextAnatomicComponent(position, radius);
            muscle.getComponents().add(motorUnit);
        }

        return muscle;
    }
}
