package com.semgtech.api.utils.signals.correlations;

public abstract class SignalCorrelation
{

    protected String name;
    protected int maxLag;
    protected boolean circular;

    protected float[] coefficients;

    protected SignalCorrelation(final String name, final int maxLag, final boolean circular)
    {
        this.name = name;
        this.maxLag = maxLag;
        this.circular = circular;
        this.coefficients = new float[maxLag];
    }

    public String getName()
    {
        return name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    public int getMaxLag()
    {
        return maxLag;
    }

    public void setMaxLag(final int maxLag)
    {
        this.maxLag = maxLag;
    }

    public boolean isCircular()
    {
        return circular;
    }

    public void setCircular(final boolean circular)
    {
        this.circular = circular;
    }

    public float[] getCoefficients()
    {
        return coefficients;
    }

    public int getHighestCoefficientIndex()
    {
        int index = 0, highestIndex = 0;
        for (; index < coefficients.length; ++index) {
            if (coefficients[index] > coefficients[highestIndex])
                highestIndex = index;
        }
        return highestIndex;
    }

    public float getHighestCoefficient()
    {
        final int highestIndex = getHighestCoefficientIndex();
        if (highestIndex == -1)
            return -1;
        return coefficients[highestIndex];
    }

    protected abstract void computeCoefficients();

    @Override
    public String toString()
    {
        return name;
    }
}
