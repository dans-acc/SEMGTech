package com.semgtech.display.ui.panels;

import com.semgtech.api.simulation.AnatomicSimulator;
import com.semgtech.display.ui.trees.AnatomicTree;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class AnatomicPanel extends SideView<AnatomicSimulator>
{

    private static final String ANATOMIC_PANEL_NAME = "Anatomic Structure";

    private AnatomicTree anatomicTree;

    public AnatomicPanel(final AnatomicSimulator anatomicSimulator)
    {
        super(anatomicSimulator);

        // Create the anatomic tree
        anatomicTree = new AnatomicTree(anatomicSimulator);
        final JScrollPane anatomicTreeScrollPane = new JScrollPane(anatomicTree);
        anatomicTreeScrollPane.setBorder(BorderFactory.createLoweredBevelBorder());

        // Create the anatomic tree panel
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(),
                ANATOMIC_PANEL_NAME,
                TitledBorder.LEFT,
                TitledBorder.TOP
        ));
        setLayout(new BorderLayout());
        add(anatomicTreeScrollPane, BorderLayout.CENTER);
    }

    public AnatomicTree getAnatomicTree()
    {
        return anatomicTree;
    }

}
