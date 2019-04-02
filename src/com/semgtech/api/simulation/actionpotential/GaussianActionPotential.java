package com.semgtech.api.simulation.actionpotential;

public class GaussianActionPotential
{

    private double amplitude;
    private double variance;

    public GaussianActionPotential(final double amplitude, final double variance)
    {
        this.amplitude = amplitude;
        this.variance = variance;
    }

    public double getAmplitude()
    {
        return amplitude;
    }

    public void setAmplitude(final double amplitude)
    {
        this.amplitude = amplitude;
    }

    public double getVariance()
    {
        return variance;
    }

    public void setVariance(final double variance)
    {
        this.variance = variance;
    }

}
