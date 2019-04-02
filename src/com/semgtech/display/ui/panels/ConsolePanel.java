package com.semgtech.display.ui.panels;

import com.semgtech.display.ui.panes.ConsoleTextPane;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ConsolePanel extends JPanel
{

    private static final String CONSOLE_PANEL_NAME = "Console";

    private ConsoleTextPane consoleTextPane;

    public ConsolePanel()
    {
        initConsolePanel();
    }

    private void initConsolePanel()
    {

        // Create the console text panel
        consoleTextPane = new ConsoleTextPane();

        // Create a scroll pane for the console
        final JScrollPane consoleScrollPane = new JScrollPane(consoleTextPane);
        consoleScrollPane.setBorder(BorderFactory.createLoweredBevelBorder());

        // Create the console pane
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(),
                CONSOLE_PANEL_NAME,
                TitledBorder.LEFT,
                TitledBorder.TOP
        ));
        setLayout(new BorderLayout());
        add(consoleScrollPane, BorderLayout.CENTER);
    }

    public ConsoleTextPane getConsoleTextPane()
    {
        return consoleTextPane;
    }
}
