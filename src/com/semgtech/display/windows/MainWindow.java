package com.semgtech.display.windows;

import com.semgtech.display.ui.panels.ExplorerPanel;
import com.semgtech.display.ui.panels.ViewerPanel_old;
import org.jfree.ui.ApplicationFrame;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends ApplicationFrame
{

    private static final String APPLICATION_TITLE = "SEMGTech";

    private static MainWindow instance;

    // Explorer and viewer instance variables
    private ExplorerPanel explorerPanel;
    private ViewerPanel_old viewerPanelOld;

    private MainWindow()
    {
        super(APPLICATION_TITLE);
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
                (int) (screenDimensions.getWidth() * 0.75),
                (int) (screenDimensions.getHeight() * 0.75)
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
        this.viewerPanelOld = new ViewerPanel_old();

        // Create the main windows content pane
        final JSplitPane contents = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                this.explorerPanel,
                this.viewerPanelOld
        );
        contents.setResizeWeight(0.15);

        // Display the contents on the main window
        super.setContentPane(contents);
    }

    /*
    TODO: Remove low amplitudes from the phase spectrum by discarding low amplitudes
    TODO: Create a multi-input gui for selecting the simulation model.
    TODO: Create a multi-selection model option for auto-correlation, and provide the circular feature
    TODO: Display the information of the signal at import times, create times, etc.
     */

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

    public ViewerPanel_old getViewerPanelOld()
    {
        return viewerPanelOld;
    }

    public void displayObjectPanel(final Object object, final boolean open)
    {
        viewerPanelOld.getViewerTabPane().displayObjectPanel(object, open);
    }

    public void closeObjectPanel(final Object object)
    {
        viewerPanelOld.getViewerTabPane().closeObject(object);
    }

    public void displayObjectInformation(final Object object)
    {
        viewerPanelOld.getConsolePane().getConsoleTextPane().displayObjectInformation(object);
    }
}
