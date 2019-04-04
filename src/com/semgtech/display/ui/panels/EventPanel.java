package com.semgtech.display.ui.panels;

import com.semgtech.api.utils.signals.LoggedSignal;
import com.semgtech.display.ui.trees.EventTree;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class EventPanel extends SideView<LoggedSignal>
{

    private static final String EVENT_PANEL_NAME = "Signal Events";

    private EventTree eventTree;

    public EventPanel(final LoggedSignal loggedSignal)
    {
        super(loggedSignal);

        // Create the event tree
        eventTree = new EventTree(loggedSignal);
        final JScrollPane eventTreeScrollPane = new JScrollPane(eventTree);
        eventTreeScrollPane.setBorder(BorderFactory.createLoweredBevelBorder());

        // Create the event tree panel
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(),
                EVENT_PANEL_NAME,
                TitledBorder.LEFT,
                TitledBorder.TOP
        ));
        setLayout(new BorderLayout());
        add(eventTreeScrollPane, BorderLayout.CENTER);
    }

    public EventTree getEventTree()
    {
        return eventTree;
    }
}
