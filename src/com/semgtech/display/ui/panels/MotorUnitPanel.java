package com.semgtech.display.ui.panels;

import com.semgtech.api.simulation.anatomy.MotorUnit;

import javax.swing.*;

public class MotorUnitPanel<T> extends AnatomicPanel<MotorUnit<T>>
{

    // The name and tooltip for the starting time
    private static final String STARTING_TIME_FIELD_NAME = "Starting Time (ms):";
    private static final String STARTING_TIME_FIELD_TOOLTIP = "The time at which the motor unit begins firing";

    // The name and tooltip for the firing rate
    private static final String FIRING_RATE_FIELD_NAME = "Firing Rate (Hz):";
    private static final String FIRING_RATE_FIELD_TOOLTIP = "The rate at which the motor units fibres fire.";

    // Starting time and firing rate
    private JTextField startingTimeTextField;
    private JTextField firingRateTextField;

    public MotorUnitPanel(final MotorUnit<T> motorUnit)
    {
        super(motorUnit);
    }

    @Override
    public void initContentPanel()
    {
        super.initContentPanel();

        // Create the starting time field
        startingTimeTextField = new JTextField();
        addEditableComponent(STARTING_TIME_FIELD_NAME, STARTING_TIME_FIELD_TOOLTIP, startingTimeTextField);

        // Create the firing rate field
        firingRateTextField = new JTextField();
        addEditableComponent(FIRING_RATE_FIELD_NAME, FIRING_RATE_FIELD_TOOLTIP, firingRateTextField);
    }

    @Override
    public void makePanelEditable(final boolean editable)
    {
        super.makePanelEditable(editable);

        // Enable / disable the fields
        startingTimeTextField.setEnabled(editable);
        firingRateTextField.setEnabled(editable);
    }

    @Override
    public boolean canApplyEdits()
    {
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
        super.updateEditComponents();

        // Update the fields so as to match the values of the motor unit
        startingTimeTextField.setText(Double.toString(getAnatomicComponent().getStartingTime()));
        firingRateTextField.setText(Double.toString(getAnatomicComponent().getFiringRate()));
    }

}
