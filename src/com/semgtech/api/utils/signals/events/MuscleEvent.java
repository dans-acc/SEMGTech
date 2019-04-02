package com.semgtech.api.utils.signals.events;

public class MuscleEvent extends EventComposite<MotorUnitEvent>
{

    private static final String DEFAULT_NAME = "Muscle Event";

    public MuscleEvent(final String name, final float timeBegan,
                       final float timeEnded)
    {
        super(name, timeBegan, timeEnded);
    }

    public MuscleEvent(final float timeBegan, final float timeEnded)
    {
        super(DEFAULT_NAME, timeBegan, timeEnded);
    }
}
