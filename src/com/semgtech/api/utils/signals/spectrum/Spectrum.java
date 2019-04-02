package com.semgtech.api.utils.signals.spectrum;

import com.semgtech.api.utils.StatisticsUtilities;
import com.semgtech.api.utils.signals.Signal;
import jwave.Transform;
import jwave.tools.MathToolKit;
import jwave.transforms.AncientEgyptianDecomposition;
import jwave.transforms.DiscreteFourierTransform;


public abstract class Spectrum
{

    private String name;

    protected Signal signal;

    protected Transform transform;

    protected double[] frequencyDomain;
    protected double[] spectrum;

    protected Spectrum(final String name, final Signal signal)
    {
        this.name = name;
        this.signal = signal;

        this.transform = MathToolKit.isBinary(signal.getSampledAmplitudes().length)
                ? new Transform(new DiscreteFourierTransform())
                : new Transform(new AncientEgyptianDecomposition(new DiscreteFourierTransform()));

        this.frequencyDomain = transform.forward(StatisticsUtilities.promoteToDouble(signal.getSampledAmplitudes()));

        computeSpectrum();
    }

    public String getName()
    {
        return name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    public Signal getSignal()
    {
        return signal;
    }

    public Transform getTransform()
    {
        return transform;
    }

    public double[] getFrequencyDomain()
    {
        return frequencyDomain;
    }

    public double[] getSpectrum()
    {
        return spectrum;
    }

    protected abstract void computeSpectrum();

    @Override
    public String toString()
    {
        return name;
    }
}
