package com.semgtech.display.controllers;

import com.semgtech.display.ui.panels.EditablePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditablePanelController
        implements ActionListener
{

    // The panel for which the controller exists i.e. manages the panels I/O
    private EditablePanel editablePanel;

    public EditablePanelController(final EditablePanel editablePanel)
    {
        this.editablePanel = editablePanel;
    }

    /**
     * @return the editable panel
     */
    public EditablePanel getEditablePanel()
    {
        return editablePanel;
    }

    /**
     * Method handles the changing of state; clicking any of the control buttons
     * either makes the panel editable or not.
     *
     * @param actionEvent - I/0 from the JVM in the form of a button click.
     */
    @Override
    public void actionPerformed(final ActionEvent actionEvent)
    {
        // We only handle input buttons
        if (!(actionEvent.getSource() instanceof JButton))
            return;

        // Get the button, and take appropriate action i.e. make, cancel or apply changes
        final JButton button = (JButton) actionEvent.getSource();
        final boolean editButtonPressed = button == editablePanel.getMakeEditsButton();

        // Enable / disable buttons based on the press
        editablePanel.getMakeEditsButton().setEnabled(!editButtonPressed);
        editablePanel.getCancelEditsButton().setEnabled(editButtonPressed);
        editablePanel.getApplyEditsButton().setEnabled(editButtonPressed);

        // Call the appropriate methods
        editablePanel.makePanelEditable(editButtonPressed);

        // If the apply button was pressed, apply all the changes
        if (button == editablePanel.getApplyEditsButton() && editablePanel.canApplyEdits())
            editablePanel.applyEdits();

        // Finally, update the editable panel
        editablePanel.updateEditComponents();
        editablePanel.setBeingEdited(editButtonPressed);
    }
}
