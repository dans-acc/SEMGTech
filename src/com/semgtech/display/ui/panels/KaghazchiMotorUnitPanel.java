package com.semgtech.display.ui.panels;

import com.semgtech.api.simulation.actionpotential.GaussianActionPotential;
import com.semgtech.api.simulation.actionpotential.KaghazchiActionPotential;
import com.semgtech.api.simulation.anatomy.MotorUnit;

import javax.swing.*;

public class KaghazchiMotorUnitPanel extends MotorUnitPanel<KaghazchiActionPotential>
{

    // The name and tooltip for the amplitude field
    private static final String AMPLITUDE_FIELD_NAME = "SFAP Amplitude:";
    private static final String AMPLITUDE_FIELD_TOOLTIP = "The amplitude of the fibre action potentials.";

    // The name and tooltips for the rising phase rate field
    private static final String RISING_PHASE_RATE_NAME = "SFAP Rising Phase Rate:";
    private static final String RISING_PHASE_RATE_TOOLTIP = "The rate at which the amplitude rises.";

    // The name and tooltip for the duration field
    private static final String DURATION_NAME = "SFAP Duration:";
    private static final String DURATION_TOOLTIP = "The duration of the action potential.";

    // Fields
    private JTextField amplitudeTextField;
    private JTextField risingPhaseRateTextField;
    private JTextField durationTextField;

    public KaghazchiMotorUnitPanel(final MotorUnit<KaghazchiActionPotential> motorUnit)
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
        risingPhaseRateTextField = new JTextField();
        addEditableComponent(RISING_PHASE_RATE_NAME, RISING_PHASE_RATE_TOOLTIP, risingPhaseRateTextField);

        // Create the duration field
        durationTextField = new JTextField();
        addEditableComponent(DURATION_NAME, DURATION_TOOLTIP, durationTextField);
    }

    @Override
    public void makePanelEditable(final boolean editable)
    {
        // Enable / disable anatomic fields
        super.makePanelEditable(editable);

        // Enable / disable amplitude and variance fields
        amplitudeTextField.setEnabled(editable);
        risingPhaseRateTextField.setEnabled(editable);
        durationTextField.setEnabled(editable);
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
        final KaghazchiActionPotential kaghazchiActionPotential = getAnatomicComponent().getActionPotential();
        amplitudeTextField.setText(Double.toString(kaghazchiActionPotential.getAmplitude()));
        risingPhaseRateTextField.setText(Double.toString(kaghazchiActionPotential.getRisingPhaseRate()));
        durationTextField.setText(Double.toString(kaghazchiActionPotential.getDuration()));
    }


}
