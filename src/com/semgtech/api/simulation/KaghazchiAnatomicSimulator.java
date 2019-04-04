package com.semgtech.api.simulation;

import com.semgtech.api.simulation.actionpotential.KaghazchiActionPotential;
import com.semgtech.api.simulation.anatomy.Fibre;
import com.semgtech.api.simulation.anatomy.MotorUnit;
import com.semgtech.api.simulation.anatomy.Muscle;
import com.semgtech.api.utils.signals.Signal;

import javax.sound.sampled.AudioFormat;

public class KaghazchiAnatomicSimulator extends AnatomicSimulator<KaghazchiActionPotential>
{

    public static final String DEFAULT_NAME = "Basic Kaghazchi Anatomic Simulator";

    private static final float ACTION_POTENTIAL_DURATION_MODIFIER = 6f;

    public KaghazchiAnatomicSimulator(final String name, final Muscle<KaghazchiActionPotential> muscle,
                                      final AudioFormat format)
    {
        super(name, muscle, format);
    }

    public KaghazchiAnatomicSimulator(final Muscle<KaghazchiActionPotential> muscle, final AudioFormat format)
    {
        super(DEFAULT_NAME, muscle, format);
    }

    @Override
    protected Signal simulateSingleFibreAP(final float timeStep, final Electrode electrode,
                                           final MotorUnit<KaghazchiActionPotential> motorUnit,
                                           final Fibre fibre)
    {
        if (electrode == null)
            throw new IllegalArgumentException("Electrode is null");
        else if (motorUnit == null)
            throw new IllegalArgumentException("Kaghazchi MotorUnit is null");
        else if (fibre == null)
            throw new IllegalArgumentException("Kaghazchi MotorUnit Fibre is null");

        // Get the motor units action potential
        final KaghazchiActionPotential kap = motorUnit.getActionPotential();
        if (kap == null)
            throw new NullPointerException(String.format("MotorUnit (%s) Kaghazchi Action Potential is null", motorUnit));

        final float kapDuration = (float) (kap.getDuration() * ACTION_POTENTIAL_DURATION_MODIFIER);

        final double fibreToElectrodeDistance = getFibreToElectrodeDistance(fibre, electrode);

        // No need to compute the signals time related information - we're going to discard it
        final Signal actionPotential = new Signal(kapDuration, getFormat(), false);

        // Compute the kaghazchi action potential
        float time = 0, amplitude = 0;
        for (int samplesTaken = 0; samplesTaken < actionPotential.getSampledTimings().length; ++samplesTaken, time += timeStep) {
            amplitude = (float) (kap.getAmplitude() * Math.sin(time / kap.getRisingPhaseRate())
                    * Math.exp((-1 * time) / kap.getDuration()));

            // Reduce the amplitude based on the fibres distance from the electrode
            if (fibreToElectrodeDistance != 0)
                amplitude *= (1f / fibreToElectrodeDistance);

            actionPotential.setSampledAmplitudeAt(samplesTaken, amplitude);
        }

        return actionPotential;
    }
}
