package com.semgtech.display.ui.panels;

import com.semgtech.display.ui.panes.ViewerTabPane;
import com.semgtech.display.ui.panes.ConsoleTextPane;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ViewerPanel_old extends JPanel
{

    private static final String CONSOLE_NAME = "Console (Output)";

    private ViewerTabPane viewerTabPane;
    private ConsolePanel consolePanel;

    public ViewerPanel_old()
    {
        initViewerPanel();
    }

    private void initViewerPanel()
    {
        // Create instances of the viewer tab pane and the console text pane
        viewerTabPane = new ViewerTabPane();
        consolePanel = new ConsolePanel();

        // Create a split pane
        final JSplitPane contents = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                viewerTabPane,
                consolePanel
        );
        contents.setResizeWeight(0.8);
        contents.setDividerSize(2);
        contents.setBorder(BorderFactory.createEmptyBorder());

        final JSplitPane contents2 = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                contents,
                new JPanel()
        );

        // Create the contents of the viewer panel
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder());
        add(contents2, BorderLayout.CENTER);
    }

    public ViewerTabPane getViewerTabPane()
    {
        return viewerTabPane;
    }

    public ConsolePanel getConsolePane()
    {
        return consolePanel;
    }

}
