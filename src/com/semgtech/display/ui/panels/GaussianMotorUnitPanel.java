package com.semgtech.display.ui.panels;

import com.semgtech.api.simulation.actionpotential.GaussianActionPotential;
import com.semgtech.api.simulation.anatomy.MotorUnit;

import javax.swing.*;

public class GaussianMotorUnitPanel extends MotorUnitPanel<GaussianActionPotential>
{

    // Amplitude field name and tooltip
    private static final String AMPLITUDE_FIELD_NAME = "SFAP Amplitude:";
    private static final String AMPLITUDE_FIELD_TOOLTIP = "The amplitude of the fibre action potentials.";

    // Variance field name and tooltip
    private static final String VARIANCE_FIELD_NAME = "SFAP Variance:";
    private static final String VARIANCE_FIELD_TOOLTIP = "The variance of the Gaussian pulse.";

    // Fields for the amplitude and variance
    private JTextField amplitudeTextField;
    private JTextField varianceTextField;

    public GaussianMotorUnitPanel(final MotorUnit<GaussianActionPotential> motorUnit)
    {
        super(motorUnit);
    }

    @Override
    public void initContentPanel()
    {
        super.initContentPanel();

        // Create the amplitude field
        amplitudeTextField = new JTextField();
        addEditableComponent(AMPLITUDE_FIELD_NAME, AMPLITUDE_FIELD_TOOLTIP, amplitudeTextField);

        // Create the variance field
        varianceTextField = new JTextField();
        addEditableComponent(VARIANCE_FIELD_NAME, VARIANCE_FIELD_TOOLTIP, varianceTextField);
    }

    @Override
    public void makePanelEditable(final boolean editable)
    {
        // Enable / disable anatomic fields
        super.makePanelEditable(editable);

        // Enable / disable amplitude and variance fields
        amplitudeTextField.setEnabled(editable);
        varianceTextField.setEnabled(editable);
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

        // Update the amplitude and variance fields to match the mu action potentials
        final GaussianActionPotential gaussianActionPotential = getAnatomicComponent().getActionPotential();
        amplitudeTextField.setText(Double.toString(gaussianActionPotential.getAmplitude()));
        varianceTextField.setText(Double.toString(gaussianActionPotential.getVariance()));
    }

}
