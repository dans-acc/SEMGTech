package com.semgtech.display.ui.panels;

import com.semgtech.api.simulation.anatomy.Muscle;

import javax.swing.*;

public class MusclePanel extends AnatomicPanel<Muscle>
{

    // Number of motor units name and tooltip
    private static final String NUMBER_OF_MOTOR_UNITS_FIELD_NAME = "Number of Motor-Units:";
    private static final String NUMBER_OF_MOTOR_UNITS_FIELD_TOOLTIP = "The number of motor-units present in the muscle.";

    // Number of fibres present name and tooltip
    private static final String NUMBER_OF_FIBRES_NAME = "Number of Fibres:";
    private static final String NUMBER_OF_FIBRES_TOOLTIP = "The number of fibres present in the muscle.";

    // The number of motor units and fibres labels
    private JLabel numberOfMotorUnitsLabel;
    private JLabel numberOfFibresLabel;

    public MusclePanel(final Muscle muscle)
    {
        super(muscle);
        updateEditComponents();
    }

    @Override
    public void initContentPanel()
    {
        // Create the number of motor units field
        numberOfMotorUnitsLabel = new JLabel();
        addEditableComponent(NUMBER_OF_MOTOR_UNITS_FIELD_NAME, NUMBER_OF_MOTOR_UNITS_FIELD_TOOLTIP, numberOfMotorUnitsLabel);

        // Create the number of fibres label
        numberOfFibresLabel = new JLabel();
        addEditableComponent(NUMBER_OF_FIBRES_NAME, NUMBER_OF_FIBRES_TOOLTIP, numberOfFibresLabel);
    }

    @Override
    public void makePanelEditable(final boolean editable)
    {
        // Enable / disable anatomic fields
        super.makePanelEditable(editable);

        // Enable / disable the muscle components
        numberOfMotorUnitsLabel.setEnabled(editable);
        numberOfFibresLabel.setEnabled(editable);
    }


    @Override
    public void updateEditComponents()
    {
        // Update the fields of the anatomic component panel
        super.updateEditComponents();

        // Update the number of motor units and fibres label
        numberOfMotorUnitsLabel.setText(Integer.toString(getAnatomicComponent().getNumComponents()));
        numberOfFibresLabel.setText(Integer.toString(getAnatomicComponent().getTotalNumComponents()));
    }

}
