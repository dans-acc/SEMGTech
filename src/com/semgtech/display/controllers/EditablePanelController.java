package com.semgtech.display.controllers;

import com.semgtech.display.ui.panels.EditablePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditablePanelController implements ActionListener
{

    private EditablePanel editablePanel;

    public EditablePanelController(final EditablePanel editablePanel)
    {
        this.editablePanel = editablePanel;
    }

    public EditablePanel getEditablePanel()
    {
        return editablePanel;
    }

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
