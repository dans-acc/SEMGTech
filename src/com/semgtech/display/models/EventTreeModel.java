package com.semgtech.display.models;

import com.semgtech.api.utils.signals.events.EventComponent;
import com.semgtech.api.utils.signals.events.EventComposite;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class EventTreeModel implements TreeModel
{

    private EventComponent root;

    public EventTreeModel(final EventComponent root)
    {
        this.root = root;
    }

    @Override
    public Object getRoot()
    {
        return root;
    }

    @Override
    public boolean isLeaf(final Object eventComponent)
    {
        if (eventComponent instanceof EventComposite)
            return ((EventComposite) eventComponent).getComponents().isEmpty();
        return eventComponent instanceof EventComponent;
    }

    @Override
    public Object getChild(final Object parentEventComposite,
                           final int index)
    {
        if (!(parentEventComposite instanceof EventComposite))
            return null;
        final EventComposite eventComposite = (EventComposite) parentEventComposite;
        if (eventComposite.getComponents().isEmpty())
            return null;
        return eventComposite.getComponents().get(index);
    }

    @Override
    public int getChildCount(final Object parentEventComposite)
    {
        if (!(parentEventComposite instanceof EventComposite))
            return -1;
        final EventComposite eventComposite = (EventComposite) parentEventComposite;
        return eventComposite.getComponents().size();
    }

    @Override
    public int getIndexOfChild(final Object parentEventComposite,
                               final Object childEventComponent)
    {
        if (parentEventComposite == null || childEventComponent == null)
            return -1;
        else if (!(parentEventComposite instanceof EventComposite && childEventComponent instanceof EventComponent))
            return -1;
        final EventComposite eventComposite = (EventComposite) parentEventComposite;
        final EventComponent eventComponent = (EventComponent) childEventComponent;
        return eventComposite.getComponents().indexOf(eventComponent);
    }

    @Override
    public void valueForPathChanged(final TreePath path, final Object value) { }
    @Override
    public void addTreeModelListener(final TreeModelListener l) {}
    @Override
    public void removeTreeModelListener(final TreeModelListener l) {}

}
