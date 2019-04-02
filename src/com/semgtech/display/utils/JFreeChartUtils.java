package com.semgtech.display.utils;

import com.semgtech.api.utils.signals.Signal;
import com.semgtech.api.utils.signals.correlations.SignalCorrelation;
import com.semgtech.api.utils.signals.spectrum.MagnitudeSpectrum;
import com.semgtech.api.utils.signals.spectrum.PhaseSpectrum;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class JFreeChartUtils
{


    public static XYSeriesCollection createSignalXYSeriesCollection(final Signal signal,
                                                                    final String seriesName)
    {
        // Get the signal time and amplitudes
        final float[] times = signal.getSampledTimings();
        final float[] amplitudes = signal.getSampledAmplitudes();

        // Create the XY series using the signals samples
        final XYSeries series = new XYSeries(seriesName);
        for (int sample = 0; sample < times.length; ++sample)
            series.add(times[sample], amplitudes[sample]);

        return new XYSeriesCollection(series);
    }

    public static XYSeriesCollection createCorrelationXYSeriesCollection(final SignalCorrelation signalCorrelation,
                                                                         final String seriesName)
    {
        final float[] correlationCoefficients = signalCorrelation.getCoefficients();

        // Create the XY series using the signals correlation coefficients
        final XYSeries series = new XYSeries(seriesName);
        final int half = correlationCoefficients.length >> 1;
        for (int coefficient = 0; coefficient < correlationCoefficients.length; ++coefficient)
            series.add(-half + coefficient, correlationCoefficients[coefficient]);

        return new XYSeriesCollection(series);
    }

    public static HistogramDataset createMagnitudeSpectrumHistogramDataset(final MagnitudeSpectrum magnitudeSpectrum,
                                                                           final String seriesName)
    {
        final HistogramDataset magnitudeHistogram = new HistogramDataset();
        magnitudeHistogram.setType(HistogramType.RELATIVE_FREQUENCY);
        magnitudeHistogram.addSeries(
                seriesName,
                magnitudeSpectrum.getSpectrum(),
                magnitudeSpectrum.getSpectrum().length
        );
        return magnitudeHistogram;
    }

    public static XYSeriesCollection createPhaseSpectrumXYSeriesCollection(final PhaseSpectrum phaseSpectrum,
                                                                           final String seriesName,
                                                                           final int tolerance)
    {
        // Based on the tolerance, accept or zero the phase
        final XYSeries series = new XYSeries(seriesName);
        return new XYSeriesCollection(series);
    }

}
