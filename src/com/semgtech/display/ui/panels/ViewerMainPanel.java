package com.semgtech.display.ui.panels;

import com.semgtech.display.ui.panes.ViewerTabPane;

import javax.swing.*;
import java.awt.*;

public class ViewerMainPanel extends JPanel
{

    private ViewerConsolePanel viewerConsolePanel;
    private ViewerSidePanel viewerSidePanel;
    private ViewerTabPane viewerTabPane;

    public ViewerMainPanel()
    {
        // Create the tab pane responsible for displaying the tabs
        viewerConsolePanel = new ViewerConsolePanel();
        viewerSidePanel = new ViewerSidePanel();
        viewerTabPane = new ViewerTabPane(viewerSidePanel);

        // Split the viewer tab panel from the console panel
        final JSplitPane verticalSplit = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                viewerTabPane,
                viewerConsolePanel
        );
        verticalSplit.setResizeWeight(0.9);
        verticalSplit.setDividerSize(2);
        verticalSplit.setBorder(BorderFactory.createEmptyBorder());

        // Split the viewer panel
        final JSplitPane horizontalSplit = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                verticalSplit,
                viewerSidePanel
        );
        horizontalSplit.setResizeWeight(0.75);
        horizontalSplit.setDividerSize(2);
        horizontalSplit.setBorder(BorderFactory.createEmptyBorder());

        // Create the panel
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder());
        add(horizontalSplit, BorderLayout.CENTER);
    }

    public ViewerConsolePanel getViewerConsolePanel()
    {
        return viewerConsolePanel;
    }

    public ViewerSidePanel getViewerSidePanel()
    {
        return viewerSidePanel;
    }

    public ViewerTabPane getViewerTabPane()
    {
        return viewerTabPane;
    }

    public void displayObjectPanels(final Object tabObject, final boolean open)
    {
        displayObjectPanels(tabObject, null, open);
    }

    public void displayObjectPanels(final Object tabObject, final Object structureObject,
                                    final boolean open)
    {
        viewerTabPane.displayObjectTabPanel(tabObject, structureObject, open);
    }
}
