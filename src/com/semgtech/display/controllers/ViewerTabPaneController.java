package com.semgtech.display.controllers;

import com.semgtech.display.ui.buttons.TabCloseButton;
import com.semgtech.display.ui.panels.EditablePanel;
import com.semgtech.display.ui.panels.TabPanel;
import com.semgtech.display.ui.panes.ViewerTabPane;
import javafx.scene.control.Tab;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ViewerTabPaneController
        implements ActionListener, ChangeListener, MouseListener
{

    // The TabPane for which this controller exists
    private ViewerTabPane viewerTabPane;

    public ViewerTabPaneController(final ViewerTabPane viewerTabPane)
    {
        this.viewerTabPane = viewerTabPane;
    }

    /**
     * Handles the closure of tabs and certain views. Given that each tab possesses an
     * associated view, clicking the button will result in the closure of all tabs.
     *
     * @param actionEvent - I/O provided by the JVM in the form of a button click.
     */
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

    /**
     * Handles the switching of views based on the tab to which we changed.
     *
     * @param changeEvent - I/O event provided by the JVM upon a tab being switched.
     */
    @Override
    public void stateChanged(final ChangeEvent changeEvent)
    {
        // Get the selected tab index
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

    @Override
    public void mouseClicked(final MouseEvent mouseEvent) { }
    @Override
    public void mousePressed(final MouseEvent mouseEvent)
    {
        // If we are dealing with editable panels, then get the editable panel
        final int currentIndex = viewerTabPane.getSelectedIndex();
        if (!(mouseEvent.getSource() instanceof TabPanel))
            return;
        if (!(viewerTabPane.getComponentAt(currentIndex) instanceof EditablePanel))
            return;

        // Get the tab component to which we are switching
        final TabPanel tabPanel = (TabPanel) mouseEvent.getSource();

        // Get the editable panel and check that we are not switching away from it
        final EditablePanel editablePanel = (EditablePanel) viewerTabPane.getComponentAt(currentIndex);
        if (!editablePanel.isBeingEdited())
            return;

        // If we are not switching away from it, then stay on the current panel
        if (!viewerTabPane.switchFromEditablePanel(currentIndex)) {
            viewerTabPane.updateUI();
            return;
        }
        viewerTabPane.getExistingTabObjectIndex(tabPanel);
    }
    @Override
    public void mouseReleased(final MouseEvent mouseEvent) { }
    @Override
    public void mouseEntered(final MouseEvent mouseEvent) { }
    @Override
    public void mouseExited(final MouseEvent mouseEvent) { }
}
