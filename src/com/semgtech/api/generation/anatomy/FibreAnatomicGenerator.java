package com.semgtech.api.generation.anatomy;

import com.semgtech.api.simulation.anatomy.Fibre;
import com.semgtech.api.utils.GenerationUtilities;
import com.semgtech.api.utils.StatisticsUtilities;
import com.semgtech.api.utils.vector.Vector3d;

public class FibreAnatomicGenerator
        implements AnatomicGenerator<Fibre> {

    private double meanLength;
    private double lengthStd;

    private double radius;

    public FibreAnatomicGenerator(final double meanLength, final double lengthStd,
                                  final double radius)
    {
        this.meanLength = meanLength;
        this.lengthStd = lengthStd;

        this.radius = radius;
    }

    public double getMeanLength()
    {
        return meanLength;
    }

    public void setMeanLength(final double meanLength)
    {
        this.meanLength = meanLength;
    }

    public double getLengthStd()
    {
        return lengthStd;
    }

    public void setLengthStd(final double lengthStd)
    {
        this.lengthStd = lengthStd;
    }

    public double getRadius()
    {
        return radius;
    }

    public void setRadius(final double radius)
    {
        this.radius = radius;
    }

    @Override
    public Fibre nextAnatomicComponent(final Vector3d areaCentre, final double areaRadius)
    {
        final Vector3d position = GenerationUtilities.nextPosition(
                areaCentre,
                areaRadius,
                radius,
                areaCentre.getZ()
        );

        final double length = StatisticsUtilities.nextGaussian(meanLength, lengthStd);

        return new Fibre(position, radius, length);
    }
}
