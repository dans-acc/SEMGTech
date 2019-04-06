package com.semgtech.display.controllers;

import com.semgtech.api.utils.signals.events.EventComponent;
import com.semgtech.display.models.EventTreeModel;
import com.semgtech.display.ui.panels.EventPanel;
import com.semgtech.display.ui.popups.EventTreePopup;
import com.semgtech.display.ui.trees.EventTree;
import com.semgtech.display.windows.MainWindow;
import com.semgtech.display.windows.PropertiesWindow;

import javax.swing.*;
import javax.swing.tree.TreePath;

public class EventTreeController
        extends TreeController<EventTreeModel, EventTreePopup, EventTree>
{

    // Refactor event panel title and message
    private static final String REFACTOR_RENAME_PANEL_TITLE = "Rename Event";
    private static final String REFACTOR_RENAME_PANEL_MESSAGE = "Rename the selected event to:";

    // Deletion option panel title and message
    private static final String DELETE_PANEL_TITLE = "Delete Event(s)";
    private static final String DELETE_PANEL_MESSAGE = "Delete the selected event(s).";

    public EventTreeController(final EventTree eventTree)
    {
        super(eventTree);
    }

    /**
     * Handles a double click event. Method deals with EventComponents only.
     *
     * @param object - the object that was double clicked.
     */
    @Override
    public void handleClick(final Object object)
    { }

    /**
     * Take the appropriate action based on the selected objects, paths etc.
     *
     * @param source - the source JComponent that triggered the event.
     * @param popup - the popup menu with all the items / options
     * @param paths - an array of selected tree paths.
     * @param objects - an array of selected objects; corresponding to the selected paths.
     * @return - true of false based on whether or not the action modified the tree
     */
    @Override
    public boolean handleAction(final Object source, final EventTreePopup popup,
                                final TreePath[] paths, final Object[] objects)
    {
        if (source == popup.getPropertiesItem())
            return handleProperties(objects);
        else if (source == popup.getNewMuscleEventItem())
            return handleNewMuscleEvent(paths, objects);
        else if (source == popup.getNewMotorUnitEventItem())
            return handleNewMotorUnitEvent(paths, objects);
        else if (source == popup.getNewFibreEventItem())
            return handleNewFibreEvent(paths, objects);
        else if (source == popup.getRefactorRenameItem())
            return handleRefactorRename(objects);
        else if (source == popup.getDeleteItem())
            return handleDeletion(paths);
        return false;
    }

    private boolean handleProperties(final Object[] objects)
    {
        final EventComponent eventComponent = (EventComponent) objects[0];
        final EventPanel eventPanel = new EventPanel(eventComponent);
        final PropertiesWindow<EventPanel> propertiesWindow = new PropertiesWindow<>(eventPanel);
        return true;
    }

    private boolean handleNewMuscleEvent(final TreePath[] paths, final Object[] objects)
    {
        return false;
    }

    private boolean handleNewMotorUnitEvent(final TreePath[] paths, final Object[] objects)
    {
        return false;
    }

    private boolean handleNewFibreEvent(final TreePath[] paths, final Object[] objects)
    {
        return false;
    }

    @SuppressWarnings("Duplicates")
    private boolean handleRefactorRename(final Object[] objects)
    {
        final EventComponent event = (EventComponent) objects[0];
        String name = null;
        do {
            name = JOptionPane.showInputDialog(
                    MainWindow.getInstance(),
                    REFACTOR_RENAME_PANEL_MESSAGE,
                    REFACTOR_RENAME_PANEL_TITLE,
                    JOptionPane.QUESTION_MESSAGE
            );
        } while (name != null && name.isEmpty());
        if (name == null)
            return false;
        event.setName(name);
        return true;
    }

    @SuppressWarnings("Duplicates")
    private boolean handleDeletion(final TreePath[] paths)
    {
        // Get the user fo confirm the deletion of the selected objects
        final int confirmed = JOptionPane.showConfirmDialog(
                MainWindow.getInstance(),
                DELETE_PANEL_MESSAGE,
                DELETE_PANEL_TITLE,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        // If we did not confirm, return
        if (confirmed == JOptionPane.NO_OPTION)
            return false;

        // Loop through all of the selected events, deleting them
        for (final TreePath path : paths)
            tree.removeChild(path);

        return true;
    }
}
