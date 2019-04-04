package com.semgtech.api.simulation;

import com.semgtech.api.simulation.actionpotential.GaussianActionPotential;
import com.semgtech.api.simulation.anatomy.Fibre;
import com.semgtech.api.simulation.anatomy.MotorUnit;
import com.semgtech.api.simulation.anatomy.Muscle;
import com.semgtech.api.utils.signals.Signal;

import javax.sound.sampled.AudioFormat;

public class GaussianAnatomicSimulator extends AnatomicSimulator<GaussianActionPotential>
{

    public static final String DEFAULT_NAME = "Basic Gaussian Anatomic Simulator";

    public GaussianAnatomicSimulator(final String name, final Muscle<GaussianActionPotential> muscle,
                                     final AudioFormat format)
    {
        super(name, muscle, format);
    }

    public GaussianAnatomicSimulator(final Muscle<GaussianActionPotential> muscle, final AudioFormat format)
    {
        this(DEFAULT_NAME, muscle, format);
    }

    @Override
    protected Signal simulateSingleFibreAP(final float timeStep, final Electrode electrode,
                                           final MotorUnit<GaussianActionPotential> motorUnit,
                                           final Fibre fibre)
    {
        if (electrode == null)
            throw new IllegalArgumentException("Electrode is null");
        else if (motorUnit == null)
            throw new IllegalArgumentException("Gaussian MotorUnit is null");
        else if (fibre == null)
            throw new IllegalArgumentException("Gaussian MotorUnit Fibre is null");

        // Get the motor units action potential
        final GaussianActionPotential gap = motorUnit.getActionPotential();
        if (gap == null)
            throw new NullPointerException(String.format("MotorUnit (%s) Gaussian Action Potential is null", motorUnit));

        final float gapDuration = (float) (Math.sqrt(gap.getVariance()) * 8f),
                gapMean = gapDuration / 2f;

        final double fibreToElectrodeDistance = getFibreToElectrodeDistance(fibre, electrode);

        // Since time related information gets discarded, omit it!
        final Signal actionPotential = new Signal(gapDuration, getFormat(), false);

        // Compute the gaussian action potential
        float time = 0, amplitude = 0;
        for (int samplesTaken = 0; samplesTaken < actionPotential.getSampledTimings().length; ++samplesTaken, time += timeStep) {
            amplitude = (float) (gap.getAmplitude() * Math.exp(-1 * (Math.pow(time - gapMean, 2)
                    / (2 * Math.sqrt(gap.getVariance())))));

            // Reduce the amplitude based on the distance (but be sure not to divide by 0)
            if (fibreToElectrodeDistance != 0)
                amplitude *= (1f / fibreToElectrodeDistance);

            actionPotential.setSampledAmplitudeAt(samplesTaken, amplitude);
        }

        return actionPotential;
    }
}
