package com.semgtech.api.generation.actionpotential;

import com.semgtech.api.simulation.actionpotential.KaghazchiActionPotential;
import com.semgtech.api.utils.GenerationUtilities;
import com.semgtech.api.utils.StatisticsUtilities;

public class KaghazchiActionPotentialGenerator
    implements ActionPotentialGenerator<KaghazchiActionPotential>
{

    private double meanAmplitude;
    private double stdAmplitude;

    private double meanRisingPhaseRate;
    private double stdRisingPhaseRate;

    private double meanDuration;
    private double stdDuration;

    public KaghazchiActionPotentialGenerator(final double meanAmplitude, final double stdAmplitude,
                                             final double meanRisingPhaseRate, final double stdRisingPhaseRate,
                                             final double meanDuration, final double stdDuration)
    {
        this.meanAmplitude = meanAmplitude;
        this.stdAmplitude = stdAmplitude;

        this.meanRisingPhaseRate = meanRisingPhaseRate;
        this.stdRisingPhaseRate = stdRisingPhaseRate;

        this.meanDuration = meanDuration;
        this.stdDuration = stdDuration;
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

    public double getMeanRisingPhaseRate()
    {
        return meanRisingPhaseRate;
    }

    public void setMeanRisingPhaseRate(final double meanRisingPhaseRate)
    {
        this.meanRisingPhaseRate = meanRisingPhaseRate;
    }

    public double getStdRisingPhaseRate()
    {
        return stdRisingPhaseRate;
    }

    public void setStdRisingPhaseRate(final double stdRisingPhaseRate)
    {
        this.stdRisingPhaseRate = stdRisingPhaseRate;
    }

    public double getMeanDuration()
    {
        return meanDuration;
    }

    public void setMeanDuration(final double meanDuration)
    {
        this.meanDuration = meanDuration;
    }

    public double getStdDuration()
    {
        return stdDuration;
    }

    public void setStdDuration(final double stdDuration)
    {
        this.stdDuration = stdDuration;
    }

    @Override
    public KaghazchiActionPotential nextActionPotential()
    {
        final double amplitude = StatisticsUtilities.nextGaussian(meanAmplitude, stdAmplitude),
                risingPhaseRate = StatisticsUtilities.nextGaussian(meanRisingPhaseRate, stdRisingPhaseRate),
                duration = StatisticsUtilities.nextGaussian(meanDuration, stdRisingPhaseRate);

        return new KaghazchiActionPotential(amplitude, risingPhaseRate, duration);
    }
}
