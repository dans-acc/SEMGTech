package com.semgtech.display.models;

import com.semgtech.api.utils.signals.events.EventComponent;
import com.semgtech.api.utils.signals.events.EventComposite;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.util.List;

public class EventTreeModel implements TreeModel
{

    private List<?> events;

    public EventTreeModel(final List<EventComponent> events)
    {
        this.events = events;
    }

    @Override
    public Object getRoot()
    {
        return events;
    }

    @Override
    public boolean isLeaf(final Object eventNode)
    {
        if (eventNode instanceof List && eventNode == events)
            return events.isEmpty();
        else if (eventNode instanceof EventComposite)
            return ((EventComposite) eventNode).getComponents().isEmpty();
        else
            return eventNode instanceof EventComponent;
    }

    @Override
    public Object getChild(final Object eventNode, final int index)
    {
        if (eventNode == null || index == -1)
            return null;
        else if (eventNode instanceof EventComposite && !((EventComposite) eventNode).getComponents().isEmpty())
            return ((EventComposite) eventNode).getComponents().get(index);
        else if (eventNode instanceof List && eventNode == events)
            return events.get(index);
        return null;
    }

    @Override
    public int getChildCount(final Object eventNode)
    {
        if (eventNode == null)
            return -1;
        else if (eventNode instanceof EventComposite)
            return ((EventComposite) eventNode).getComponents().size();
        else if (eventNode instanceof List && eventNode == events)
            return events.size();
        return -1;
    }

    @Override
    public int getIndexOfChild(final Object parentEventNode, final Object childEventNode)
    {
        if (parentEventNode == null || childEventNode == null)
            return -1;

        // Check the appropriate list
        List<?> eventComponents = null;
        if (parentEventNode instanceof EventComposite)
            eventComponents = ((EventComposite) parentEventNode).getComponents();
        else if (parentEventNode instanceof List && parentEventNode == events)
            eventComponents = events;

        // Check whether the event is present in the list
        if (!(childEventNode instanceof EventComponent) || !eventComponents.contains(childEventNode))
            return -1;

        return eventComponents.indexOf(childEventNode);
    }

    @Override
    public void valueForPathChanged(final TreePath path, final Object value) { }
    @Override
    public void addTreeModelListener(final TreeModelListener l) {}
    @Override
    public void removeTreeModelListener(final TreeModelListener l) {}

}
