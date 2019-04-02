package com.semgtech.api.utils.signals.events;

public class MotorUnitEvent
    extends EventComposite<FibreEvent>
{

    private static final String DEFAULT_NAME = "Motor Unit Event";

    public MotorUnitEvent(final String name, final float timeBegan,
                          final float timeEnded)
    {
        super(name, timeBegan, timeEnded);
    }

    public MotorUnitEvent(final float timeBegan, final float timeEnded)
    {
        super(DEFAULT_NAME, timeBegan, timeEnded);
    }
}
