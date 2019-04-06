package com.semgtech.display.ui.panels;

import com.semgtech.display.ui.trees.ExplorerTree;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ExplorerPanel extends JPanel
{

    private static final String EXPLORER_PANEL_TITLE = "Project Explorer";

    private ExplorerTree explorerTree;

    public ExplorerPanel()
    {
        super(new BorderLayout());

        // Create and add the explorer tree to the explorer panel
        this.explorerTree = new ExplorerTree();

        // Wrap the tree in a scroll pane, and JPanel to suppress scroll pane behaviour
        final JScrollPane explorerScrollPane = new JScrollPane(explorerTree);
        explorerScrollPane.setBorder(BorderFactory.createLoweredBevelBorder());

        // Wrap the scroll pane to suppress behaviour
        final JPanel explorerScrollPanel = new JPanel(new BorderLayout());
        explorerScrollPanel.add(explorerScrollPane);

        // Set the explorer panel title and contents
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(),
                EXPLORER_PANEL_TITLE,
                TitledBorder.LEFT,
                TitledBorder.TOP
        ));
        add(explorerScrollPanel, BorderLayout.CENTER);

    }

    public ExplorerTree getExplorerTree()
    {
        return explorerTree;
    }
}
