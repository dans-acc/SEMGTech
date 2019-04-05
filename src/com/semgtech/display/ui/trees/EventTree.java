package com.semgtech.display.ui.trees;

import com.semgtech.api.utils.signals.LoggedSignal;
import com.semgtech.display.controllers.EventTreeController;
import com.semgtech.display.models.EventTreeModel;
import com.semgtech.display.ui.popups.EventTreePopup;

import javax.swing.*;
import javax.swing.tree.TreeSelectionModel;

public class EventTree extends JTree
{

    private static final String EVENT_TREE_TOOLTIP = "Logged signal events. Right-click for more options.";

    private LoggedSignal loggedSignal;
    private EventTreeModel eventTreeModel;

    // Event tree controller and popup menu
    private EventTreeController eventTreeController;
    private EventTreePopup eventTreePopup;

    public EventTree(final LoggedSignal loggedSignal)
    {
        this.loggedSignal = loggedSignal;

        // Create the model
        eventTreeModel = new EventTreeModel(loggedSignal.getEvents());
        setModel(eventTreeModel);

        // Init the tree related properties
        setToolTipText(EVENT_TREE_TOOLTIP);
        setRootVisible(false);
        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        // Create the event tree controller and add it
        eventTreeController = new EventTreeController(this);
        addMouseListener(eventTreeController);

        // Create the popup menu and add it
        eventTreePopup = new EventTreePopup(eventTreeController);
        add(eventTreePopup);
    }

    public LoggedSignal getLoggedSignal()
    {
        return loggedSignal;
    }

    public EventTreeModel getEventTreeModel()
    {
        return eventTreeModel;
    }

    public EventTreePopup getEventTreePopup()
    {
        return eventTreePopup;
    }


}
