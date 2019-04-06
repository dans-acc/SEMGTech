package com.semgtech.display.ui.trees;

import com.semgtech.api.simulation.AnatomicSimulator;
import com.semgtech.api.simulation.anatomy.AnatomicComponent;
import com.semgtech.api.simulation.anatomy.AnatomicComposite;
import com.semgtech.display.controllers.AnatomicTreeController;
import com.semgtech.display.models.AnatomicTreeModel;
import com.semgtech.display.ui.popups.AnatomicTreePopup;

import javax.swing.*;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class AnatomicTree extends Tree<AnatomicTreeModel, AnatomicTreePopup>
{

    // The anatomic trees tooltip popup message
    private static final String ANATOMIC_TREE_TOOLTIP = "Anatomic structure. Right-click for more options.";

    private AnatomicSimulator anatomicSimulator;

    public AnatomicTree(final AnatomicSimulator anatomicSimulator)
    {
        this.anatomicSimulator = anatomicSimulator;
        initTree();
    }

    @Override
    protected void initTree()
    {
        // Set the tree related properties
        setToolTipText(ANATOMIC_TREE_TOOLTIP);
        setRootVisible(true);
        getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);

        // Create and set the tree model
        model = new AnatomicTreeModel(anatomicSimulator.getMuscle());
        setModel(model);

        // Create and set the anatomic tree controller
        controller = new AnatomicTreeController(this);
        addMouseListener(controller);

        // Create and set the trees popup menu
        popup = new AnatomicTreePopup(controller);
        add(popup);
    }

    public AnatomicSimulator getAnatomicSimulator()
    {
        return anatomicSimulator;
    }

    @Override
    public void addChild(final Object parent, final Object child,
                         final boolean children)
    {
        if (!(parent instanceof AnatomicComposite))
            return;
        else if (!(child instanceof AnatomicComponent))
            return;
        final AnatomicComposite composite = (AnatomicComposite) parent;
        composite.getComponents().add(child);
    }

    @Override
    public void removeChild(final TreePath path)
    {

    }

}
