package com.semgtech.display.windows;

import com.semgtech.display.ui.panels.EditablePanel;
import org.jfree.ui.ApplicationFrame;

import javax.swing.*;
import java.awt.*;

public class PropertiesWindow<T extends EditablePanel> extends JFrame
{

    // The name of the properties window
    private static final String PROPERTIES_WINDOW_TITLE = "Properties";

    // The editable panel for which the window exists
    private T editablePanel;

    public PropertiesWindow(final T editablePanel)
    {
        super(PROPERTIES_WINDOW_TITLE);
        this.editablePanel = editablePanel;
        initPropertiesWindowFrame();
    }

    public EditablePanel getEditablePanel()
    {
        return editablePanel;
    }

    private void initPropertiesWindowFrame()
    {
        // We want to close the window, not exit the program
        setDefaultCloseOperation(ApplicationFrame.DISPOSE_ON_CLOSE);

        // Set the size of the main window
        final Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();
        super.setMinimumSize(new Dimension(
                (int) (screenDimensions.getWidth() / 3),
                (int) (screenDimensions.getHeight() / 3)
        ));
        super.setPreferredSize(new Dimension(
                (int) (screenDimensions.getWidth() * 0.3),
                (int) (screenDimensions.getHeight() * 0.3)
        ));

        // Add the panel to the screen
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(editablePanel, BorderLayout.CENTER);

        // Display the properties window
        pack();
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        requestFocus();
        setVisible(true);
    }
}
