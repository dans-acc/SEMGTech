package com.semgtech.api.utils.signals.spectrum;

import com.semgtech.api.utils.signals.Signal;

public class MagnitudeSpectrum extends Spectrum
{

    private static final String DEFAULT_NAME = "Magnitude Spectrum";

    public MagnitudeSpectrum(final String name, final Signal signal)
    {
        super(name, signal);
    }

    public MagnitudeSpectrum(final Signal signal)
    {
        super(DEFAULT_NAME, signal);
    }

    @Override
    public void computeSpectrum()
    {
        super.spectrum = new double[signal.getSampledAmplitudes().length >> 1];
        double re = 0, im = 0;
        for (int bin = 0; bin < spectrum.length - 1; ++bin) {
            re = super.frequencyDomain[2 * bin];
            im = super.frequencyDomain[2 * bin + 1];
            super.spectrum[bin] = Math.sqrt(re * re + im * im);
        }
    }
}
