package com.semgtech.api.utils.signals;

import com.semgtech.api.utils.StatisticsUtilities;
import com.semgtech.api.utils.signals.events.EventComponent;

import javax.sound.sampled.AudioFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LoggedSignal<T extends EventComponent>
    extends Signal
{

    private static final String DEFAULT_NAME = "Logged Signal";

    private List<T> events;

    public LoggedSignal(final String name, final float duration, final AudioFormat format,
                        final boolean computeTiming)
    {
        super(name, duration, format, computeTiming);
        events = new ArrayList<>();
    }

    public LoggedSignal(final float duration, final AudioFormat format,
                        final boolean computeTiming)
    {
        this(DEFAULT_NAME, duration, format, computeTiming);
    }

    public LoggedSignal(final float duration, final AudioFormat format)
    {
        this(DEFAULT_NAME, duration, format, false);
    }

    public List<T> getEvents()
    {
        return events;
    }

    public T getFirstEvent()
    {
        return Collections.min(
                events,
                (x, y) -> Float.compare(
                        x.getTimeBegan(),
                        y.getTimeBegan()
                )
        );
    }

    public T getLastEvent()
    {
        return Collections.max(
                events,
                (x, y) -> Float.compare(
                        x.getTimeBegan(),
                        y.getTimeBegan()
                )
        );
    }

    @Override
    public LoggedSignal<T> normalise()
    {
        final LoggedSignal<T> loggedSignal = new LoggedSignal<T>(
                String.format("Normalised (%s) %s", DEFAULT_NAME, name),
                duration,
                format,
                true
        );
        loggedSignal.sampledAmplitudes = StatisticsUtilities.normalise(sampledAmplitudes);
        loggedSignal.events = events;
        return loggedSignal;
    }
}
