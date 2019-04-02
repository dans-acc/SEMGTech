package com.semgtech.api.simulation;

import com.semgtech.api.simulation.anatomy.Fibre;
import com.semgtech.api.simulation.anatomy.MotorUnit;
import com.semgtech.api.simulation.anatomy.Muscle;
import com.semgtech.api.utils.signals.LoggedSignal;
import com.semgtech.api.utils.signals.Signal;
import com.semgtech.api.utils.signals.events.FibreEvent;
import com.semgtech.api.utils.signals.events.MotorUnitEvent;
import com.semgtech.api.utils.signals.events.MuscleEvent;
import com.semgtech.api.utils.vector.Vector2d;
import com.semgtech.api.utils.vector.Vector3d;

import javax.sound.sampled.AudioFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AnatomicSimulator<T>
{

    private String name;

    private Muscle<T> muscle;

    private AudioFormat format;

    private List<Electrode> electrodes;

    protected AnatomicSimulator(final String name, final Muscle<T> muscle,
                                final AudioFormat format)
    {
        this.name = name;
        this.muscle = muscle;
        this.format = format;
        this.electrodes = new ArrayList<>();
    }

    public String getName()
    {
        return name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    public Muscle<T> getMuscle()
    {
        return muscle;
    }

    public void setMuscle(final Muscle<T> muscle)
    {
        this.muscle = muscle;
    }

    public AudioFormat getFormat()
    {
        return format;
    }

    public void setFormat(final AudioFormat format)
    {
        this.format = format;
    }

    public List<Electrode> getElectrodes()
    {
        return electrodes;
    }

    public void setElectrodes(final List<Electrode> electrodes)
    {
        this.electrodes = electrodes;
    }

    public List<LoggedSignal<MuscleEvent>> simulateMuscleSEMGs(final float duration)
    {
        if (duration <= 0)
            throw new IllegalArgumentException(String.format("Simulation duration (%f) <= 0", duration));

        final List<LoggedSignal<MuscleEvent>> semgs = new ArrayList<>(electrodes.size());
        if (electrodes.isEmpty())
            return semgs;

        // Compute an semg signal for each of the electrodes
        for (final Electrode electrode : electrodes) {
            final LoggedSignal<MuscleEvent> semg = simulateMuscleSEMG(duration, electrode);
            semgs.add(semg);
        }

        return semgs;
    }

    public LoggedSignal<MuscleEvent> simulateMuscleSEMG(final float duration, final Electrode electrode)
    {
        if (duration <= 0)
            throw new IllegalArgumentException(String.format("Simulation duration (%f) <= 0", duration));

        final List<LoggedSignal<MotorUnitEvent>> trains = simulateMotorUnitAPTs(duration, electrode);

        // Create the semg signal
        final LoggedSignal<MuscleEvent> semg = new LoggedSignal<>(duration, format, true);
        if (trains.isEmpty())
            return semg;

        final MuscleEvent muscleEvent = new MuscleEvent(0, duration);

        // Sum all the trains onto the semg signal
        for (final LoggedSignal<MotorUnitEvent> train : trains) {
            semg.sumAmplitudes(train);
            muscleEvent.getComponents().addAll(train.getEvents());
        }

        semg.getEvents().add(muscleEvent);

        return semg;
    }

    public List<LoggedSignal<MotorUnitEvent>> simulateMotorUnitAPTs(final float duration, final Electrode electrode)
    {
        if (duration <= 0)
            throw new IllegalArgumentException(String.format("Simulation duration (%f) <= 0", duration));

        final List<LoggedSignal<MotorUnitEvent>> trains = new ArrayList<>(muscle.getComponents().size());
        if (muscle.getComponents().isEmpty())
            return trains;

        // Compute a motor unit train for with the given electrode for each of the motor unit(s)
        for (final MotorUnit<T> motorUnit : muscle.getComponents()) {
            final LoggedSignal<MotorUnitEvent> train = simulateMotorUnitAPT(duration, electrode, motorUnit);
            trains.add(train);
        }

        return trains;
    }

    public LoggedSignal<MotorUnitEvent> simulateMotorUnitAPT(final float duration, final Electrode electrode,
                                                             final MotorUnit<T> motorUnit)
    {
        if (duration <= 0)
            throw new IllegalArgumentException(String.format("Simulation duration (%f) <= 0", duration));
        else if (motorUnit == null)
            throw new NullPointerException("MotorUnit instance is null");

        final LoggedSignal<MotorUnitEvent> train = new LoggedSignal<>(duration, format, true);
        if (motorUnit.getComponents().isEmpty())
            return train;

        final float trainTimeStep = train.getTimeStep();

        // Compute motor unit time related information
        final int motorUnitStartingIndex = (int) (motorUnit.getStartingTime() / trainTimeStep),
                motorUnitFiringInterval = (int) (1000 / trainTimeStep / motorUnit.getFiringRate()),
                motorUnitFiringFor = (int) (duration / (motorUnitFiringInterval * trainTimeStep)),
                motorUnitTrainSamples = train.getSampledTimings().length;

        // Find the longest fibre
        final Fibre longestFibre = Collections.max(
                motorUnit.getComponents(),
                (x, y) -> Double.compare(
                        x.getPosition().getZ() + x.getLength(),
                        x.getPosition().getZ() + y.getLength()
                )
        );

        // Using the longest fibre, compute the end of the motor unit event
        final float timeMotorUnitBegunFiring = motorUnitStartingIndex * trainTimeStep,
                timeMotorUnitEndedFiring = timeMotorUnitBegunFiring + (float) (longestFibre.getLength() / (longestFibre.getRadius() * 2f));

        final MotorUnitEvent motorUnitEvent = new MotorUnitEvent(
                timeMotorUnitBegunFiring,
                timeMotorUnitEndedFiring < duration ? timeMotorUnitEndedFiring : duration
        );

        // Fire each of the motor units fibres
        for (final Fibre fibre : motorUnit.getComponents()) {

            // Cache a single fibre action potential
            final Signal actionPotential = simulateSingleFibreAP(trainTimeStep, electrode, motorUnit, fibre);
            if (actionPotential == null)
                throw new NullPointerException("Cached action potential is null");

            // Calculate fibre velocity and timings
            final double fibreZCoordinateOffset = fibre.getPosition().getZ() - motorUnit.getPosition().getZ(),
                    fibreDiameter = fibre.getRadius() * 2.0f,
                    fibreLocationTimeOffset = fibreZCoordinateOffset / fibreDiameter;

            // Offset and propagation of the motor unit
            final int fibreStartingIndexOffset = (int) (fibreLocationTimeOffset / trainTimeStep),
                    fibreFiringInterval = (int) (motorUnitFiringInterval * fibreDiameter);

            // 'Paste' the cached fibre action potential onto the train
            int timesFibreFired = 0, fireFromIndex = motorUnitStartingIndex + fibreStartingIndexOffset;
            for (; timesFibreFired < motorUnitFiringFor; ++timesFibreFired, fireFromIndex += fibreFiringInterval) {

                // Check that we're still propagating on the fibres length
                final double distancePropagated = (fireFromIndex * trainTimeStep) / fibreDiameter;
                if (fireFromIndex >= motorUnitTrainSamples || distancePropagated >= fibre.getLength())
                    break;

                // Sum the action potential to the motor unit train
                int sumNumFibreSamples = actionPotential.getSampledTimings().length;
                if (fireFromIndex + sumNumFibreSamples > motorUnitTrainSamples)
                    sumNumFibreSamples = motorUnitTrainSamples - fireFromIndex;

                train.sumAmplitudes(actionPotential, 0,
                        fireFromIndex, sumNumFibreSamples);

                // Log the fibre event
                final float timeFibreBegunFiring = (float) (fireFromIndex) * trainTimeStep,
                        timeFibreEndedFiring = timeFibreBegunFiring + (sumNumFibreSamples * trainTimeStep);

                final FibreEvent fibreEvent = new FibreEvent(timeFibreBegunFiring, timeFibreEndedFiring);
                motorUnitEvent.getComponents().add(fibreEvent);
            }
        }

        // Log the motor unit event
        train.getEvents().add(motorUnitEvent);

        return train;
    }

    protected abstract Signal simulateSingleFibreAP(final float timeStep, final Electrode electrode,
                                                    final MotorUnit<T> motorUnit, final Fibre fibre);

    protected double getFibreToElectrodeDistance(final Fibre fibre, final Electrode electrode)
    {
        final Vector3d fibrePosition = fibre.getPosition(),
                electrodePosition = electrode.getPosition();

        // Calculate the fibre-to-electrode distance
        return Vector2d.euclideanDistance(
                fibrePosition.getX(),
                fibrePosition.getY(),
                electrodePosition.getX(),
                electrodePosition.getY()
        );
    }
}
