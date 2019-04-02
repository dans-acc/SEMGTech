package com.semgtech.api.simulation.actionpotential;

public class KaghazchiActionPotential
{

    private double amplitude;
    private double risingPhaseRate;
    private double duration;

    public KaghazchiActionPotential(final double amplitude, final double risingPhaseRate,
                                    final double duration)
    {
        this.amplitude = amplitude;
        this.risingPhaseRate = risingPhaseRate;
        this.duration = duration;
    }

    public double getAmplitude()
    {
        return amplitude;
    }

    public void setAmplitude(final double amplitude)
    {
        this.amplitude = amplitude;
    }

    public double getRisingPhaseRate()
    {
        return risingPhaseRate;
    }

    public void setRisingPhaseRate(final double risingPhaseRate)
    {
        this.risingPhaseRate = risingPhaseRate;
    }

    public double getDuration()
    {
        return duration;
    }

    public void setDuration(final double duration)
    {
        this.duration = duration;
    }

}
