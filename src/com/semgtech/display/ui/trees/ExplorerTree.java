package com.semgtech.display.ui.trees;

import com.semgtech.api.simulation.Electrode;
import com.semgtech.api.simulation.GaussianAnatomicSimulator;
import com.semgtech.api.simulation.actionpotential.GaussianActionPotential;
import com.semgtech.api.simulation.anatomy.Fibre;
import com.semgtech.api.simulation.anatomy.MotorUnit;
import com.semgtech.api.simulation.anatomy.Muscle;
import com.semgtech.api.utils.signals.LoggedSignal;
import com.semgtech.api.utils.signals.Signal;
import com.semgtech.api.utils.signals.events.MuscleEvent;
import com.semgtech.api.utils.vector.Vector3d;
import com.semgtech.display.controllers.ExplorerTreeController;
import com.semgtech.display.ui.panels.ExplorerPanel;
import com.semgtech.display.ui.popups.ExplorerTreePopup;

import javax.sound.sampled.AudioFormat;
import javax.swing.*;
import javax.swing.tree.*;

public class ExplorerTree extends JTree
{

    private static final String EXPLORER_TREE_TOOLTIP = "Right-Click for more options.";

    private ExplorerPanel explorerPanel;

    // The explorer tree controller and popup menu
    private ExplorerTreeController explorerTreeController;

    // The tree model
    private DefaultMutableTreeNode defaultTreeNodeRoot;
    private DefaultTreeModel defaultTreeModel;

    // The tree selection model
    private DefaultTreeSelectionModel defaultTreeSelectionModel;

    // The trees popup menu
    private ExplorerTreePopup explorerTreePopup;

    public ExplorerTree(final ExplorerPanel explorerPanel)
    {
        this.explorerPanel = explorerPanel;
        initExplorerTree();
    }

    private void initExplorerTree()
    {
        // Create the default tree model
        defaultTreeNodeRoot = new DefaultMutableTreeNode();
        defaultTreeModel = new DefaultTreeModel(defaultTreeNodeRoot);
        setModel(defaultTreeModel);

        // Init various tree related properties
        setToolTipText(EXPLORER_TREE_TOOLTIP);
        setRootVisible(false);
        getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);

        // Create the explorer controller and set it
        explorerTreeController = new ExplorerTreeController(this);
        addMouseListener(explorerTreeController);

        // Create an instance of the explorer popup menu, and add it
        explorerTreePopup = new ExplorerTreePopup(explorerTreeController);
        add(explorerTreePopup);
    }

    public ExplorerPanel getExplorerPanel()
    {
        return explorerPanel;
    }

    public ExplorerTreeController getExplorerTreeController()
    {
        return explorerTreeController;
    }

    public ExplorerTreePopup getExplorerTreePopup()
    {
        return explorerTreePopup;
    }

    public void addChildObject(final TreePath path, final Object object,
                               final boolean allowChildren, final boolean expandNode)
    {
        addChildObject(path.getLastPathComponent(), object, allowChildren);
        if (expandNode)
            expandPath(path);
    }

    public void addChildObject(final Object parent, final Object object,
                               final boolean allowChildren)
    {
        // Check that we're dealing with a valid node
        if (!(parent instanceof DefaultMutableTreeNode))
            return;
        DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) parent;

        // Create a child node and add it
        final DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(object, allowChildren);
        parentNode.add(childNode);
        defaultTreeModel.reload(parentNode);
    }
}
