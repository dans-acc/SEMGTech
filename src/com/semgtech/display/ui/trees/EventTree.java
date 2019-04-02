package com.semgtech.display.ui.trees;

import com.semgtech.api.utils.signals.LoggedSignal;
import com.semgtech.display.models.EventTreeModel;

import javax.swing.*;
import javax.swing.tree.TreeSelectionModel;

public class EventTree extends JTree
{

    private static final String EVENT_TREE_TOOLTIP = "Signal Events. Right-Click for more options.";

    private LoggedSignal loggedSignal;

    private EventTreeModel eventTreeModel;

    public EventTree(final LoggedSignal loggedSignal)
    {
        this.loggedSignal = loggedSignal;
        initLoggedEventTree();
    }

    private void initLoggedEventTree()
    {
        // Apply various properties to the tree structure
        setToolTipText(EVENT_TREE_TOOLTIP);
        setRootVisible(true);
        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    }

    public LoggedSignal getLoggedSignal()
    {
        return loggedSignal;
    }

    public EventTreeModel getEventTreeModel()
    {
        return eventTreeModel;
    }

}
