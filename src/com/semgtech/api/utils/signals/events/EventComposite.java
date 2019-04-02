package com.semgtech.api.utils.signals.events;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class EventComposite<T extends EventComponent>
        extends EventComponent
{

    protected List<T> components;

    protected EventComposite(final String name, final float timeBegan,
                             final float timeEnded)
    {
        super(name, timeBegan, timeEnded);
        this.components = new ArrayList<>();
    }

    public List<T> getComponents()
    {
        return components;
    }

    public T getFirstEvent()
    {
        return Collections.min(components, (x, y) -> Float.compare(x.timeBegan, y.timeEnded));
    }

    public T getLastEvent()
    {
        return Collections.max(components, (x, y) -> Float.compare(x.timeBegan, y.timeEnded));
    }
}
