package com.semgtech.api.utils.signals.correlations;

import com.semgtech.api.utils.StatisticsUtilities;
import com.semgtech.api.utils.signals.Signal;

public class CrossCorrelation extends SignalCorrelation
{

    private static final String DEFAULT_NAME = "Signal Cross-Correlation";

    private Signal signalX;
    private Signal signalY;

    public CrossCorrelation(final String name, final int maxLag,
                            final boolean circular, final Signal signalX,
                            final Signal signalY)
    {
        super(name, maxLag, circular);

        this.signalX = signalX;
        this.signalY = signalY;
        computeCoefficients();
    }

    public CrossCorrelation(final int maxLag, final boolean circular,
                            final Signal signalX, final Signal signalY)
    {
        this(DEFAULT_NAME, maxLag, circular, signalX, signalY);
    }

    public Signal getSignalX()
    {
        return signalX;
    }

    public void setSignalX(final Signal signalX)
    {
        this.signalX = signalX;
    }

    public Signal getSignalY()
    {
        return signalY;
    }

    public void setSignalY(final Signal signalY)
    {
        this.signalY = signalY;
    }

    @Override
    protected void computeCoefficients()
    {
        // Get the amplitudes for both of the signals
        final float[] amplitudesX = signalX.getSampledAmplitudes(),
                amplitudesY = signalY.getSampledAmplitudes();

        // Calculate the means for both signals
        final float meanX = StatisticsUtilities.mean(amplitudesX),
                meanY = StatisticsUtilities.mean(amplitudesY);

        // Calculate the denominator
        float sx = 0, sy = 0, denominator = 0;
        for (int index = 0; index < amplitudesX.length; ++index) {
            sx += (amplitudesX[index] - meanX) * (amplitudesX[index] - meanX);
            sy += (amplitudesY[index] - meanY) * (amplitudesY[index] - meanY);
        }
        denominator = (float) Math.sqrt(sx * sy);

        // Calculate the cross correlations coefficients
        float sxy = 0;
        for (int lag = -super.maxLag; lag < super.maxLag; ++lag, sxy = 0) {
            for (int i = 0, j = lag; i < amplitudesX.length; ++i, j = i + lag) {
                if (!super.circular) {
                    if (j < 0 || j >= amplitudesX.length)
                        continue;
                } else {
                    while (j < 0)
                        j += amplitudesX.length;
                    j %= amplitudesX.length;
                }
                sxy += (amplitudesX[i] - meanX) * (amplitudesY[j] - meanY);
            }

            // Finally, compute the cross correlations coefficient and store it
            super.coefficients[lag + amplitudesX.length] = sxy / denominator;
        }
    }
}
