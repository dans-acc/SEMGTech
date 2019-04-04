package com.semgtech.display.windows;

import com.semgtech.display.ui.panels.ExplorerPanel;
import com.semgtech.display.ui.panels.ViewerMainPanel;
import org.jfree.ui.ApplicationFrame;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends ApplicationFrame
{

    private static final String APPLICATION_FRAME_TITLE = "SEMGTech";

    private static MainWindow instance;

    // Explorer and viewer instance variables
    private ExplorerPanel explorerPanel;
    private ViewerMainPanel viewerMainPanel;

    private MainWindow()
    {
        super(APPLICATION_FRAME_TITLE);
        initMainWindowFrame();
    }

    private void initMainWindowFrame()
    {
        // Set the main windows core properties
        super.setDefaultCloseOperation(ApplicationFrame.EXIT_ON_CLOSE);

        // Set the size of the main window
        final Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();
        super.setMinimumSize(new Dimension(
                (int) (screenDimensions.getWidth() / 2),
                (int) (screenDimensions.getHeight() / 2)
        ));
        super.setPreferredSize(new Dimension(
                (int) (screenDimensions.getWidth() * 0.85),
                (int) (screenDimensions.getHeight() * 0.85)
        ));

        // Init the main windows content pane
        initMainWindowContents();

        // Display the main window
        super.pack();
        super.setLocationRelativeTo(null);
        super.requestFocus();
        super.setVisible(true);
    }

    private void initMainWindowContents()
    {
        // Create the explorer and viewer panels, respectively
        this.explorerPanel = new ExplorerPanel();
        this.viewerMainPanel = new ViewerMainPanel();

        // Create the main windows content pane
        final JSplitPane contents = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                this.explorerPanel,
                this.viewerMainPanel
        );
        contents.setResizeWeight(0.15);

        // Display the contents on the main window
        super.setContentPane(contents);
    }

    public static MainWindow getInstance()
    {
        if (instance == null)
            instance = new MainWindow();
        return instance;
    }

    public ExplorerPanel getExplorerPanel()
    {
        return explorerPanel;
    }

    public ViewerMainPanel getViewerMainPanel()
    {
        return viewerMainPanel;
    }
}
