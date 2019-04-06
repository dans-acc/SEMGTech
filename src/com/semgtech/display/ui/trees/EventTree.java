package com.semgtech.display.ui.trees;

import com.semgtech.api.utils.signals.LoggedSignal;
import com.semgtech.api.utils.signals.events.EventComponent;
import com.semgtech.api.utils.signals.events.EventComposite;
import com.semgtech.display.controllers.EventTreeController;
import com.semgtech.display.models.EventTreeModel;
import com.semgtech.display.ui.popups.EventTreePopup;

import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class EventTree extends Tree<EventTreeModel, EventTreePopup>
{

    // The event trees tooltip popup message
    private static final String EVENT_TREE_TOOLTIP = "Logged signal events. Right-click for more options.";

    private LoggedSignal loggedSignal;


    public EventTree(final LoggedSignal loggedSignal)
    {
        this.loggedSignal = loggedSignal;
        initTree();
    }

    @Override
    protected void initTree()
    {
        // Init the tree related properties
        setToolTipText(EVENT_TREE_TOOLTIP);
        setRootVisible(false);
        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        // Create the model
        model = new EventTreeModel(loggedSignal.getEvents());
        setModel(model);

        // Create the event tree controller and add it
        controller = new EventTreeController(this);
        addMouseListener(controller);

        // Create the popup menu and add it
        popup = new EventTreePopup(controller);
        add(popup);
    }

    public LoggedSignal getLoggedSignal()
    {
        return loggedSignal;
    }

    @Override
    public void addChild(final Object parent, final Object child,
                         final boolean children)
    {
        if (!(parent instanceof EventComposite))
            return;
        else if (!(child instanceof EventComponent))
            return;

        // Add the component to the composite
        final EventComposite composite = (EventComposite) parent;
        composite.getComponents().add(child);
    }

    @Override
    public void removeChild(final TreePath path)
    {
        final TreePath parentPath = path.getParentPath();
        if (parentPath == null || !(parentPath.getLastPathComponent() instanceof EventComposite))
            return;
        else if (!(path.getLastPathComponent() instanceof EventComponent))
            return;

        // Get the parent and child components
        final EventComposite parentComposite = (EventComposite) parentPath.getLastPathComponent();
        final EventComponent childComponent = (EventComponent) path.getLastPathComponent();

        // Remove the child component from the composite
        if (parentComposite.getComponents().contains(childComponent))
            parentComposite.getComponents().remove(childComponent);
    }
}
