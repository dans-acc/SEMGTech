package com.semgtech.display.ui.trees;

import com.semgtech.api.simulation.AnatomicSimulator;
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

public class ExplorerTree extends Tree<DefaultTreeModel, ExplorerTreePopup>
{

    // The explorer trees tooltip popup message
    private static final String EXPLORER_TREE_TOOLTIP = "Right-Click for more options.";

    public ExplorerTree()
    {
        initTree();
    }

    @Override
    protected void initTree()
    {
        // Init various tree related properties
        setToolTipText(EXPLORER_TREE_TOOLTIP);
        setRootVisible(false);
        getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);

        // Create and set the trees model
        model = new DefaultTreeModel(new DefaultMutableTreeNode());
        setModel(model);

        // Create and set the trees controller
        controller = new ExplorerTreeController(this);
        addMouseListener(controller);

        // Create and set the popup menu
        popup = new ExplorerTreePopup(controller);
        add(popup);

        // These are temp
        createSignals();
        createSignals();
    }

    private void createSignals()
    {
        Fibre fibre = new Fibre(new Vector3d(0, 0, 0), 0.5 , 100);

        GaussianActionPotential gap = new GaussianActionPotential(1, 5);

        MotorUnit<GaussianActionPotential> mu = new MotorUnit<>(new Vector3d(0, 0, 0), 4,
                0, 100, gap);
        mu.getComponents().add(fibre);

        Muscle<GaussianActionPotential> muscle = new Muscle<GaussianActionPotential>(new Vector3d(0, 0, 0), 10);
        muscle.getComponents().add(mu);

        Electrode electrode = new Electrode(new Vector3d(0, 0, 0));

        AudioFormat format = new AudioFormat(1000, 16, 1, false, true);

        GaussianAnatomicSimulator simulator = new GaussianAnatomicSimulator(muscle, format);

        LoggedSignal signal = simulator.simulateMuscleSEMG(100, electrode);

        addChild(getModel().getRoot(), signal, true);
    }

    @Override
    public void addChild(final Object parent, final Object child,
                         final boolean children)
    {
        if (!(parent instanceof DefaultMutableTreeNode))
            return;

        final DefaultMutableTreeNode component = new DefaultMutableTreeNode(child, children);

        // Add the new child object to the composite
        final DefaultMutableTreeNode composite = (DefaultMutableTreeNode) parent;
        composite.add(component);
        getModel().reload(composite);
    }

    @Override
    public void removeChild(final TreePath path)
    {
        if (path == null || !(path.getLastPathComponent() instanceof DefaultMutableTreeNode))
            return;
        final DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
        getModel().removeNodeFromParent(node);
    }
}
