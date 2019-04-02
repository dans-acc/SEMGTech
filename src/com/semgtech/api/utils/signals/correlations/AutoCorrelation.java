package com.semgtech.api.utils.signals.correlations;

import com.semgtech.api.utils.StatisticsUtilities;
import com.semgtech.api.utils.signals.Signal;

public class AutoCorrelation extends SignalCorrelation
{

    private static final String DEFAULT_NAME = "Auto-Correlation";

    private Signal signal;

    public AutoCorrelation(final String name, final int maxLag,
                           final boolean circular, final Signal signal)
    {
        super(name, maxLag, circular);
        this.signal = signal;
        computeCoefficients();
    }

    public AutoCorrelation(final int maxLag, final boolean circular,
                           final Signal signal)
    {
        this(DEFAULT_NAME, maxLag, circular, signal);
    }

    public Signal getSignal()
    {
        return signal;
    }

    public void setSignal(final Signal signal)
    {
        this.signal = signal;
    }

    @Override
    protected void computeCoefficients()
    {
        // Get the signal related information
        final float[] amplitudes = signal.getSampledAmplitudes();
        final float mean = StatisticsUtilities.mean(amplitudes);
        final int length = amplitudes.length;

        // Calculate the auto correlations coefficients
        float xim = 0, numerator = 0, denominator = 0;
        for (int lag = 0, samples = 0; lag < super.maxLag; ++lag) {

            // Determine the number of elements used to compute the numerator and denominator
            samples = super.circular
                    ? length
                    : length - lag;

            // Compute the numerator and denominator
            numerator = denominator = 0;
            for (int i = 0; i < samples; ++i) {
                xim = amplitudes[i] - mean;
                numerator += xim * (amplitudes[i + lag % amplitudes.length] - mean);
                denominator += xim * xim;
            }

            // Finally, calculate the correlations coefficient
            super.coefficients[lag] = numerator / denominator;
        }
    }
}
