package com.semgtech.api.utils.signals.events;

public abstract class EventComponent
{

    private String name;

    protected float timeBegan;
    protected float timeEnded;

    protected EventComponent(final String name, final float timeBegan,
                             final float timeEnded)
    {
        this.name = name;
        this.timeBegan = timeBegan;
        this.timeEnded = timeEnded;
    }

    public String getName()
    {
        return name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    public float getTimeBegan()
    {
        return timeBegan;
    }

    public void setTimeBegan(final float timeBegan)
    {
        this.timeBegan = timeBegan;
    }

    public float getTimeEnded()
    {
        return timeEnded;
    }

    public void setTimeEnded(final float timeEnded)
    {
        this.timeEnded = timeEnded;
    }

    public float getDuration()
    {
        return timeEnded - timeBegan;
    }

}
