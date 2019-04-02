package com.semgtech.display.controllers;

import com.semgtech.display.ui.buttons.TabCloseButton;
import com.semgtech.display.ui.panels.TabPanel;
import com.semgtech.display.ui.panes.ViewerTabPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewerTabPaneController implements ActionListener
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
            if (((TabPanel) viewerTabPane.getTabComponentAt(tab)).getTabCloseButton() == tabCloseButton) {
                viewerTabPane.removeTabAt(tab);
                viewerTabPane.repaint();
                break;
            }
        }
    }
}
