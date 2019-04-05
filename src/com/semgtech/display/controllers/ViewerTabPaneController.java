package com.semgtech.display.controllers;

import com.semgtech.display.ui.buttons.TabCloseButton;
import com.semgtech.display.ui.panels.TabPanel;
import com.semgtech.display.ui.panes.ViewerTabPane;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewerTabPaneController implements ActionListener, ChangeListener
{

    private ViewerTabPane viewerTabPane;

    public ViewerTabPaneController(final ViewerTabPane viewerTabPane)
    {
        this.viewerTabPane = viewerTabPane;
    }

    @Override
    public void actionPerformed(final ActionEvent actionEvent)
    {
        // Check if the close button has been pressed
        if (!(actionEvent.getSource() instanceof TabCloseButton))
            return;

        // Find the tab to which the button belongs, and close it.
        final TabCloseButton tabCloseButton = (TabCloseButton) actionEvent.getSource();
        for (int tab = 0; tab < viewerTabPane.getTabCount(); ++tab) {
            if (((TabPanel) viewerTabPane.getTabComponentAt(tab)).getCloseButton() == tabCloseButton) {
                viewerTabPane.closeObjectTabPanel(tab);
                break;
            }
        }
    }

    @Override
    public void stateChanged(final ChangeEvent changeEvent)
    {
        final int selectedIndex = viewerTabPane.getSelectedIndex();
        if (selectedIndex == -1)
            return;
        else if (!(viewerTabPane.getTabComponentAt(selectedIndex) instanceof TabPanel)) {
            return;
        }
        // Get the tab panel, and display it in the structure view
        final TabPanel tabPanel = (TabPanel) viewerTabPane.getTabComponentAt(selectedIndex);
        viewerTabPane.getViewerSidePanel().displayObjectSidePanel(tabPanel.getSideObject(), true);
    }
}
