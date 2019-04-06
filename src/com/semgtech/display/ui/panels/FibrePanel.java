package com.semgtech.display.ui.panels;

import com.semgtech.api.simulation.anatomy.Fibre;
import com.semgtech.api.utils.vector.Vector3d;

import javax.swing.*;

public class FibrePanel extends AnatomicPanel<Fibre>
{

    // Name and tool tip for the fibre length
    private static final String LENGTH_FIELD_NAME = "Length (mm):";
    private static final String LENGTH_TOOLTIP = "The length of the fibre";

    // Used to display the length of the fibre
    private JTextField lengthTextField;

    public FibrePanel(final Fibre fibre)
    {
        super(fibre);
        updateEditComponents();
    }

    @Override
    public void initContentPanel()
    {
        // Create the length text field
        lengthTextField = new JTextField();
        addEditableComponent(LENGTH_FIELD_NAME, LENGTH_TOOLTIP, lengthTextField);
    }

    @Override
    public void makePanelEditable(final boolean editable)
    {
        // Enable / disable anatomic fields
        super.makePanelEditable(editable);

        // Enable / disable the length text field
        lengthTextField.setEnabled(editable);
    }

    @Override
    public boolean canApplyEdits()
    {
        super.canApplyEdits();
        return true;
    }

    @Override
    public void applyEdits()
    {
        super.applyEdits();
    }

    @Override
    public void updateEditComponents()
    {
        // Update the fields of the anatomic component panel
        super.updateEditComponents();

        // Update the length text field
        lengthTextField.setText(Double.toString(getAnatomicComponent().getLength()));
    }

}
