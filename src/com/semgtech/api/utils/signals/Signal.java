package com.semgtech.api.utils.signals;

import com.semgtech.api.utils.StatisticsUtilities;

import javax.sound.sampled.AudioFormat;

public class Signal
{

    private static final String DEFAULT_NAME = "Signal";

    protected String name;

    protected float duration;
    protected AudioFormat format;

    protected float[] sampledTimings;
    protected float[] sampledAmplitudes;

    public Signal(final String name, final float duration, final AudioFormat format, final boolean computeTimings)
    {
        this.name = name;
        this.duration = duration;
        this.format = format;

        // The float arrays into which the signal is to be stored
        final int samples = (int) ((duration / 1000f) * format.getSampleRate());
        this.sampledTimings = new float[samples];
        this.sampledAmplitudes = new float[samples];

        // If desired, the time related information can be pre-computed.
        if (computeTimings) {
            final float timeStep = duration / (float) (samples);
            float time = 0;
            for (int timeIndex = 0; timeIndex < samples; ++timeIndex, time += timeStep)
                sampledTimings[timeIndex] = time;
        }
    }

    public Signal(final float duration, final AudioFormat format, final boolean computeTimings)
    {
        this(DEFAULT_NAME, duration, format, computeTimings);
    }

    public Signal(final String name, final float duration, final AudioFormat format)
    {
        this(DEFAULT_NAME, duration, format, false);
    }

    public Signal(final float duration, final AudioFormat format)
    {
        this(duration, format, false);
    }

    public String getName()
    {
        return name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    public float getDuration()
    {
        return duration;
    }

    public float getTimeStep()
    {
        return duration / sampledTimings.length;
    }

    public AudioFormat getFormat()
    {
        return format;
    }

    public float[] getSampledTimings()
    {
        return sampledTimings;
    }

    public float getSampledTimeAt(final int index)
    {
        return sampledTimings[index];
    }

    public void setSampledTimeAt(final int index, final float time)
    {
        sampledTimings[index] = time;
    }

    public float[] getSampledAmplitudes()
    {
        return sampledAmplitudes;
    }

    public float getSampledAmplitudeAt(final int index)
    {
        return sampledAmplitudes[index];
    }

    public void setSampledAmplitudeAt(final int index, final float amplitude)
    {
        sampledAmplitudes[index] = amplitude;
    }

    public void sumAmplitudes(final Signal signal)
    {
        sumAmplitudes(signal, 0, 0, signal.sampledAmplitudes.length);
    }

    public void sumAmplitudes(final Signal signal, int fromSrcSignalIndex,
                              int fromDestSignalIndex, final int samples)
    {
        for (int summed = 0; summed < samples;
             ++summed, ++fromSrcSignalIndex, ++fromDestSignalIndex) {
            sampledAmplitudes[fromDestSignalIndex] += signal.sampledAmplitudes[fromSrcSignalIndex];
        }
    }

    public Signal normalise()
    {
        final Signal signal = new Signal(
                String.format("Normalised (%s) %s", DEFAULT_NAME,  name),
                duration,
                format,
                true
        );
        signal.sampledAmplitudes = StatisticsUtilities.normalise(this.sampledAmplitudes);
        return signal;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
