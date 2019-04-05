package com.semgtech.display.ui.panels;

import com.semgtech.display.controllers.EditablePanelController;
import com.semgtech.display.utils.SpringUtilities;

import javax.swing.*;
import java.awt.*;

public abstract class EditablePanel extends JPanel
{

    // The positioning of the editable components
    private static final int EDITABLE_COMPONENT_INITIAL_X = 5;
    private static final int EDITABLE_COMPONENT_INITIAL_Y = 5;
    private static final int EDITABLE_COMPONENT_INITIAL_X_PADDING = 10;
    private static final int EDITABLE_COMPONENT_INITIAL_Y_PADDING = 10;

    // Make edits button name and tooltip
    private static final String MAKE_EDITS_BUTTON_NAME = "Edit";
    private static final String MAKE_EDITS_BUTTON_TOOLTIP = "Click to enable edit mode.";

    // Cancel edits button name and tooltip
    private static final String CANCEL_EDITS_BUTTON_NAME = "Cancel";
    private static final String CANCEL_EDITS_BUTTON_TOOLTIP = "Click to cancel the editing and revert changes.";

    // Apply edits button name and tooltip
    private static final String APPLY_EDITS_BUTTON_NAME = "Apply";
    private static final String APPLY_EDITS_BUTTON_TOOLTIP = "Click to apply the changes made.";

    // Whether or not the panel is being edited
    private boolean beingEdited;

    // Controller responsible for enabling / disabling the edit mode
    private EditablePanelController editablePanelController;

    // The sub panels displaying and controlling the edit mode, respectively
    private JPanel contentsPanel;
    private JPanel editButtonsPanel;

    // Edit control buttons
    private JButton makeEditsButton;
    private JButton cancelEditsButton;
    private JButton applyEditsButton;

    public EditablePanel()
    {
        // Init the editable panel
        beingEdited = false;

        // Create the editable panel controller
        editablePanelController = new EditablePanelController(this);

        // Create the sub-panels
        contentsPanel = new JPanel(new SpringLayout());
        editButtonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        // Add components to the panels
        initContentPanel();
        initEditButtonsPanel();

        // Apply the spring layout formatting
        SpringUtilities.makeCompactGrid(
                contentsPanel,
                contentsPanel.getComponents().length >> 1,
                2,
                EDITABLE_COMPONENT_INITIAL_X,
                EDITABLE_COMPONENT_INITIAL_Y,
                EDITABLE_COMPONENT_INITIAL_X_PADDING,
                EDITABLE_COMPONENT_INITIAL_Y_PADDING
        );

        makePanelEditable(false);

        // apply various options to the
        setLayout(new BorderLayout());
        add(contentsPanel, BorderLayout.PAGE_START);
        add(editButtonsPanel, BorderLayout.PAGE_END);
    }

    public boolean isBeingEdited()
    {
        return beingEdited;
    }

    public void setBeingEdited(final boolean beingEdited)
    {
        this.beingEdited = beingEdited;
    }

    public EditablePanelController getEditablePanelController()
    {
        return editablePanelController;
    }

    public JPanel getContentsPanel()
    {
        return contentsPanel;
    }

    public JPanel getEditButtonsPanel()
    {
        return editButtonsPanel;
    }

    public JButton getMakeEditsButton()
    {
        return makeEditsButton;
    }

    public JButton getCancelEditsButton()
    {
        return cancelEditsButton;
    }

    public JButton getApplyEditsButton()
    {
        return applyEditsButton;
    }

    protected abstract void initContentPanel();

    private void initEditButtonsPanel()
    {
        // Create the make edits button
        makeEditsButton = new JButton(MAKE_EDITS_BUTTON_NAME);
        makeEditsButton.setToolTipText(MAKE_EDITS_BUTTON_TOOLTIP);
        makeEditsButton.setEnabled(true);
        makeEditsButton.addActionListener(editablePanelController);

        // Create the cancel edits button
        cancelEditsButton = new JButton(CANCEL_EDITS_BUTTON_NAME);
        cancelEditsButton.setToolTipText(CANCEL_EDITS_BUTTON_TOOLTIP);
        cancelEditsButton.setEnabled(false);
        cancelEditsButton.addActionListener(editablePanelController);

        // Create the apply edits button
        applyEditsButton = new JButton(APPLY_EDITS_BUTTON_NAME);
        applyEditsButton.setToolTipText(APPLY_EDITS_BUTTON_TOOLTIP);
        applyEditsButton.setEnabled(false);
        applyEditsButton.addActionListener(editablePanelController);

        // Create the edit buttons panel
        editButtonsPanel.add(makeEditsButton);
        editButtonsPanel.add(cancelEditsButton);
        editButtonsPanel.add(applyEditsButton);
    }

    public abstract void makePanelEditable(final boolean editable);

    public abstract boolean canApplyEdits();

    public abstract void applyEdits();

    public abstract void updateEditComponents();

    protected void addEditableComponent(final String name, final String tooltip,
                                        final JComponent component)
    {
        // Create the label for the component
        final JLabel componentLabel = new JLabel(name, JLabel.LEADING);
        componentLabel.setToolTipText(tooltip);
        componentLabel.setLabelFor(component);

        // Set the component tooltip
        component.setToolTipText(tooltip);

        // Add the components to the contents panel
        contentsPanel.add(componentLabel);
        contentsPanel.add(component);
    }

}
