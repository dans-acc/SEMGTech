package com.semgtech.display.ui.trees;

import com.semgtech.api.simulation.AnatomicSimulator;
import com.semgtech.display.models.AnatomicTreeModel;

import javax.swing.*;
import javax.swing.tree.TreeSelectionModel;

public class AnatomicTree extends JTree
{

    private static final String ANATOMIC_TREE_TOOLTIP = "Anatomic structure. Right-click for more options.";

    private AnatomicSimulator anatomicSimulator;
    private AnatomicTreeModel anatomicTreeModel;

    public AnatomicTree(final AnatomicSimulator anatomicSimulator)
    {
        this.anatomicSimulator = anatomicSimulator;

        // Create and set the model
        anatomicTreeModel = new AnatomicTreeModel(anatomicSimulator.getMuscle());
        setModel(anatomicTreeModel);

        // Set the tree related properties
        setToolTipText(ANATOMIC_TREE_TOOLTIP);
        setRootVisible(true);
        getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
    }

    public AnatomicSimulator getAnatomicSimulator()
    {
        return anatomicSimulator;
    }

    public AnatomicTreeModel getAnatomicTreeModel()
    {
        return anatomicTreeModel;
    }
}
