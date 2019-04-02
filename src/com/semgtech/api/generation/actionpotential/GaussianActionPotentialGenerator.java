package com.semgtech.api.generation.actionpotential;

import com.semgtech.api.simulation.actionpotential.GaussianActionPotential;
import com.semgtech.api.utils.GenerationUtilities;
import com.semgtech.api.utils.StatisticsUtilities;

public class GaussianActionPotentialGenerator
    implements ActionPotentialGenerator<GaussianActionPotential>
{

    private double meanAmplitude;
    private double stdAmplitude;

    private double meanVariance;
    private double stdVariance;

    public GaussianActionPotentialGenerator(final double meanAmplitude, final double stdAmplitude,
                                            final double meanVariance, final double stdVariance)
    {
        this.meanAmplitude = meanAmplitude;
        this.stdAmplitude = stdAmplitude;

        this.meanVariance = meanVariance;
        this.stdVariance = stdVariance;
    }

    public double getMeanAmplitude()
    {
        return meanAmplitude;
    }

    public void setMeanAmplitude(final double meanAmplitude)
    {
        this.meanAmplitude = meanAmplitude;
    }

    public double getStdAmplitude()
    {
        return stdAmplitude;
    }

    public void setStdAmplitude(final double stdAmplitude)
    {
        this.stdAmplitude = stdAmplitude;
    }

    public double getMeanVariance()
    {
        return meanVariance;
    }

    public void setMeanVariance(final double meanVariance)
    {
        this.meanVariance = meanVariance;
    }

    public double getStdVariance()
    {
        return stdVariance;
    }

    public void setStdVariance(final double stdVariance)
    {
        this.stdVariance = stdVariance;
    }

    @Override
    public GaussianActionPotential nextActionPotential()
    {
        final double amplitude = StatisticsUtilities.nextGaussian(meanAmplitude, stdAmplitude),
                variance = StatisticsUtilities.nextGaussian(meanVariance, stdVariance);

        return new GaussianActionPotential(amplitude, variance);
    }

}
