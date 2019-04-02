package com.semgtech.api.generation.anatomy;

import com.semgtech.api.generation.actionpotential.ActionPotentialGenerator;
import com.semgtech.api.simulation.anatomy.Fibre;
import com.semgtech.api.simulation.anatomy.MotorUnit;
import com.semgtech.api.utils.GenerationUtilities;
import com.semgtech.api.utils.StatisticsUtilities;
import com.semgtech.api.utils.vector.Vector3d;

public class MotorUnitAnatomicGenerator<T>
        implements AnatomicGenerator<MotorUnit<T>>
{

    private double minRadius;
    private double maxRadius;
    private double radiusLambda;

    private double minStartingTime;
    private double maxStartingTime;
    private double meanStartingTime;

    private double minFiringRate;
    private double maxFiringRate;
    private double firingRateLambda;

    private ActionPotentialGenerator<T> actionPotentialGenerator;

    private AnatomicGenerator<Fibre> fibreAnatomicGenerator;
    private int numFibres;

    public MotorUnitAnatomicGenerator(final double minRadius, final double maxRadius,
                                      final double radiusLambda, final double minStartingTime,
                                      final double maxStartingTime, final double meanStartingTime,
                                      final double minFiringRate, final double maxFiringRate,
                                      final double firingRateLambda,
                                      final ActionPotentialGenerator<T> actionPotentialGenerator,
                                      final AnatomicGenerator<Fibre> fibreAnatomicGenerator, final int numFibres)
    {
        this.minRadius = minRadius;
        this.maxRadius = maxRadius;
        this.radiusLambda = radiusLambda;

        this.minStartingTime = minStartingTime;
        this.maxStartingTime = maxStartingTime;
        this.meanStartingTime = meanStartingTime;

        this.minFiringRate = minFiringRate;
        this.maxFiringRate = maxFiringRate;
        this.firingRateLambda = firingRateLambda;

        this.actionPotentialGenerator = actionPotentialGenerator;

        this.fibreAnatomicGenerator = fibreAnatomicGenerator;
        this.numFibres = numFibres;
    }

    public MotorUnitAnatomicGenerator(final double radius, final double startingTime,
                                      final double firingRate,
                                      final ActionPotentialGenerator<T> actionPotentialGenerator,
                                      final AnatomicGenerator<Fibre> fibreAnatomicGenerator, final int numFibres)
    {
        this(
                radius, radius, radius,
                startingTime, startingTime, startingTime,
                firingRate, firingRate, firingRate,
                actionPotentialGenerator,
                fibreAnatomicGenerator, numFibres
        );
    }

    public double getMinRadius()
    {
        return minRadius;
    }

    public void setMinRadius(final double minRadius)
    {
        this.minRadius = minRadius;
    }

    public double getMaxRadius()
    {
        return maxRadius;
    }

    public void setMaxRadius(final double maxRadius)
    {
        this.maxRadius = maxRadius;
    }

    public double getRadiusLambda()
    {
        return radiusLambda;
    }

    public void setRadiusLambda(final double radiusLambda)
    {
        this.radiusLambda = radiusLambda;
    }

    public double getMinStartingTime()
    {
        return minStartingTime;
    }

    public void setMinStartingTime(final double minStartingTime)
    {
        this.minStartingTime = minStartingTime;
    }

    public double getMaxStartingTime()
    {
        return maxStartingTime;
    }

    public void setMaxStartingTime(final double maxStartingTime)
    {
        this.maxStartingTime = maxStartingTime;
    }

    public double getMeanStartingTime()
    {
        return meanStartingTime;
    }

    public void setMeanStartingTime(final double meanStartingTime)
    {
        this.meanStartingTime = meanStartingTime;
    }

    public double getMinFiringRate()
    {
        return minFiringRate;
    }

    public void setMinFiringRate(final double minFiringRate)
    {
        this.minFiringRate = minFiringRate;
    }

    public double getMaxFiringRate()
    {
        return maxFiringRate;
    }

    public void setMaxFiringRate(final double maxFiringRate)
    {
        this.maxFiringRate = maxFiringRate;
    }

    public double getFiringRateLambda()
    {
        return firingRateLambda;
    }

    public void setFiringRateLambda(final double firingRateLambda)
    {
        this.firingRateLambda = firingRateLambda;
    }

    public ActionPotentialGenerator<T> getActionPotentialGenerator()
    {
        return actionPotentialGenerator;
    }

    public void setActionPotentialGenerator(final ActionPotentialGenerator<T> actionPotentialGenerator)
    {
        this.actionPotentialGenerator = actionPotentialGenerator;
    }

    public AnatomicGenerator<Fibre> getFibreAnatomicGenerator()
    {
        return fibreAnatomicGenerator;
    }

    public void setFibreAnatomicGenerator(final AnatomicGenerator<Fibre> fibreAnatomicGenerator)
    {
        this.fibreAnatomicGenerator = fibreAnatomicGenerator;
    }

    public int getNumFibres()
    {
        return numFibres;
    }

    public void setNumFibres(final int numFibres)
    {
        this.numFibres = numFibres;
    }

    @Override
    public MotorUnit<T> nextAnatomicComponent(final Vector3d areaCentre, final double areaRadius)
    {
        final double radius = minRadius == maxRadius
                ? minRadius
                : StatisticsUtilities.nextPoisson(minRadius, maxRadius, radiusLambda);

        final Vector3d position = GenerationUtilities.nextPosition(areaCentre, areaRadius,
                radius, areaCentre.getZ());

        final T actionPotential = actionPotentialGenerator.nextActionPotential();

        final double startingTime = minStartingTime == maxStartingTime
                ? minStartingTime
                : GenerationUtilities.nextStartingTime(minStartingTime, maxStartingTime, meanStartingTime);

        final double firingRate = StatisticsUtilities.nextPoisson(minFiringRate, maxFiringRate,
                firingRateLambda);

        final MotorUnit<T> motorUnit = new MotorUnit<>(
                position,
                radius,
                startingTime,
                firingRate,
                actionPotential
        );

        if (fibreAnatomicGenerator == null || numFibres <= 0)
            return motorUnit;

        for (int generatedFibres = 0; generatedFibres < numFibres; ++generatedFibres) {
            final Fibre fibre = fibreAnatomicGenerator.nextAnatomicComponent(position, radius);
            motorUnit.getComponents().add(fibre);
        }

        return motorUnit;
    }
}
