package com.semgtech.api.utils.signals.events;

public class FibreEvent
    extends EventComponent
{

    private static final String DEFAULT_NAME = "Fibre Event";

    public FibreEvent(final String name, final float timeBegan,
                      final float timeEnded)
    {
        super(name, timeBegan, timeEnded);
    }

    public FibreEvent(final float timeBegan, final float timeEnded)
    {
        super(DEFAULT_NAME, timeBegan, timeEnded);
    }

}
