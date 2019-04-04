package com.semgtech.display.ui.panels;

import com.semgtech.display.ui.panes.ViewerConsoleTextPane;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ViewerConsolePanel extends JPanel
{

    private static final String PANEL_TITLE = "Console";

    private ViewerConsoleTextPane viewerConsoleTextPane;

    protected ViewerConsolePanel()
    {
        // Create the console text are for displaying logged messages
        viewerConsoleTextPane = new ViewerConsoleTextPane();

        // Create a scroll pane for the console text panel
        final JScrollPane consoleScrollPane = new JScrollPane(viewerConsoleTextPane);
        consoleScrollPane.setBorder(BorderFactory.createLoweredBevelBorder());

        // Suppress the scroll pane behaviour
        final JPanel consoleScrollPanel = new JPanel(new BorderLayout());
        consoleScrollPanel.add(consoleScrollPane, BorderLayout.CENTER);

        // Create the console panel
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(),
                PANEL_TITLE,
                TitledBorder.LEFT,
                TitledBorder.TOP
        ));
        add(consoleScrollPanel, BorderLayout.CENTER);
    }

    public ViewerConsoleTextPane getViewerConsoleTextPane()
    {
        return viewerConsoleTextPane;
    }

    public synchronized void displayMessage(final String message)
    {
        viewerConsoleTextPane.displayMessage(message);
    }

}
