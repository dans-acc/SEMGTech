package com.semgtech.api.utils.signals.spectrum;

import com.semgtech.api.utils.signals.Signal;

public class PhaseSpectrum extends Spectrum
{

    private static final String DEFAULT_NAME = "Phase Spectrum";

    private int resolution;

    public PhaseSpectrum(final String name, final Signal signal)
    {
        super(name, signal);
        this.resolution = resolution;
    }

    public PhaseSpectrum(final Signal signal)
    {
        super(DEFAULT_NAME, signal);
    }

    public int getResolution()
    {
        return resolution;
    }

    public void setResolution(final int resolution)
    {
        this.resolution = resolution;
    }

    @Override
    public void computeSpectrum()
    {
        // Compute the resolution
        final int threshold = 1 << resolution;

        // Compute the phase spectrum of the signal
        super.spectrum = new double[frequencyDomain.length >> 1];
        double re = 0, im = 0, amplitude = 0, phase = 0;
        for (int bin = 0; bin < spectrum.length - 1; ++bin) {
            re = frequencyDomain[2 * bin];
            im = frequencyDomain[2 * bin + 1];

            // TODO: Come back to this and implement a threshold!

            super.spectrum[bin] = Math.atan2(im, re) * 180 / Math.PI;
        }
    }

}
