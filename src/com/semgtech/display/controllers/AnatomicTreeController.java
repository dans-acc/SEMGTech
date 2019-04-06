package com.semgtech.display.controllers;

import com.semgtech.api.simulation.anatomy.AnatomicComponent;
import com.semgtech.display.models.AnatomicTreeModel;
import com.semgtech.display.ui.popups.AnatomicTreePopup;
import com.semgtech.display.ui.trees.AnatomicTree;
import com.semgtech.display.windows.MainWindow;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.MouseEvent;

public class AnatomicTreeController
        extends TreeController<AnatomicTreeModel, AnatomicTreePopup, AnatomicTree>
{

    public AnatomicTreeController(final AnatomicTree anatomicTree)
    {
        super(anatomicTree);
    }

    /**
     * Handles a double click event. Method deals with AnatomicComponents only.
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
    public boolean handleAction(final Object source, final AnatomicTreePopup popup,
                                final TreePath[] paths, final Object[] objects)
    {
        if (source == popup.getNewMuscleItem())
            return handleNewMuscle(paths, objects);
        else if (source == popup.getNewMotorUnitItem())
            return handleNewMotorUnit(paths, objects);
        else if (source == popup.getNewFibreItem())
            return handleNewFibre(paths, objects);
        else if (source == popup.getGenerateMuscleItem())
            return handleGenerateMuscle(paths, objects);
        else if (source == popup.getGenerateMotorUnitItem())
            return handleGenerateMotorUnit(paths, objects);
        else if (source == popup.getGenerateFibreItem())
            return handleGenerateFibre(paths, objects);
        else if (source == popup.getRefactorRenameItem())
            return handleRefactorRename(paths, objects);
        else if (source == popup.getDeleteItem())
            return handleDeletion(paths, objects);
        return false;
    }

    private boolean handleNewMuscle(final TreePath[] paths, final Object[] objects)
    {
        return false;
    }

    private boolean handleNewMotorUnit(final TreePath[] paths, final Object[] objects)
    {
        return false;
    }

    private boolean handleNewFibre(final TreePath[] paths, final Object[] objects)
    {
        return false;
    }

    private boolean handleGenerateMuscle(final TreePath[] paths, final Object[] objects)
    {
        return false;
    }

    private boolean handleGenerateMotorUnit(final TreePath[] paths, final Object[] objects)
    {
        return false;
    }

    private boolean handleGenerateFibre(final TreePath[] paths, final Object[] objects)
    {
        return false;
    }

    private boolean handleRefactorRename(final TreePath[] paths, final Object[] objects)
    {
        return false;
    }

    private boolean handleDeletion(final TreePath[] paths, final Object[] objects)
    {
        return false;
    }
}
