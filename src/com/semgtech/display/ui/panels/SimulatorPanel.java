package com.semgtech.display.ui.panels;

import com.semgtech.api.simulation.AnatomicSimulator;

import javax.sound.sampled.AudioFormat;
import javax.swing.*;
import java.util.Arrays;

public class SimulatorPanel extends EditablePanel
{

    // Name and tooltip of the simulator name tooltip
    private static final String NAME_TEXT_FIELD_NAME = "Name:";
    private static final String NAME_TEXT_FIELD_TOOLTIP = "The name of the simulator.";

    // Name and tooltip for the simulator type
    private static final String TYPE_NAME_FIELD_NAME = "Simulator Type:";
    private static final String TYPE_NAME_FILED_TOOLTIP = "The type of simulator being used defines the action potential generated.";

    // Name and tooltip for the encoding combobox
    private static final String ENCODING_FIELD_NAME = "Encoding:";
    private static final String ENCODING_FIELD_TOOLTIP = "The format used for encoding the signal.";
    private static final AudioFormat.Encoding[] ENCODING_FIELD_VALUES = new AudioFormat.Encoding[] {
            AudioFormat.Encoding.ALAW,
            AudioFormat.Encoding.PCM_FLOAT,
            AudioFormat.Encoding.PCM_SIGNED,
            AudioFormat.Encoding.PCM_UNSIGNED,
            AudioFormat.Encoding.ULAW
    };

    // The name and tooltip for the audio format sampling frequency
    private static final String SAMPLING_RATE_FIELD_NAME = "Sampling Rate (sec):";
    private static final String SAMPLING_RATE_FIELD_TOOLTIP = "The rate at which the signal is sampled (per second).";

    // The name and tooltip for the audio format sampling size in bits
    private static final String SAMPLING_SIZE_IN_BITS_FIELD_NAME = "Sampling Size (bits/sample):";
    private static final String SAMPLING_SIZE_IN_BITS_FIELD_TOOLTIP = "The number of bits in each sample.";

    // The name and tooltip for the endianness name and tooltip
    private static final String ENDIANNESS_FIELD_NAME = "Endianness:";
    private static final String ENDIANNESS_FIELD_TOOLTIP = "Whether or not the signals are in big- or little-endian.";
    private static final String ENDIANNESS_FIELD_BIG_ENDIAN_NAME = "Big-Endian Format";
    private static final String ENDIANNESS_FIELD_LITTLE_ENDIAN_NAME = "Little-Endian Format";
    private static final String[] ENDIANNESS_FIELD_VALUES = new String[] {
            ENDIANNESS_FIELD_BIG_ENDIAN_NAME,
            ENDIANNESS_FIELD_LITTLE_ENDIAN_NAME
    };

    // The simulator for which the panel exists
    private AnatomicSimulator anatomicSimulator;

    // The name of the simulator
    private JTextField nameTextField;

    private JLabel typeNameLabel;

    // Audio format related options
    private JComboBox<AudioFormat.Encoding> encodingComboBox;
    private JComboBox<String> endiannessComboBox;
    private JTextField samplingRateTextField;
    private JTextField samplingSizeInBitsTextField;

    public SimulatorPanel(final AnatomicSimulator anatomicSimulator)
    {
        super();
        this.anatomicSimulator = anatomicSimulator;
        updateEditComponents();
    }

    public AnatomicSimulator getAnatomicSimulator()
    {
        return anatomicSimulator;
    }

    @Override
    public void initContentPanel()
    {
        // Create an editable component for the name
        nameTextField = new JTextField();
        addEditableComponent(
                NAME_TEXT_FIELD_NAME,
                NAME_TEXT_FIELD_TOOLTIP,
                nameTextField
        );

        // Create a label for displaying the type of simulator being used
        typeNameLabel = new JLabel();
        addEditableComponent(
                TYPE_NAME_FIELD_NAME,
                TYPE_NAME_FILED_TOOLTIP,
                typeNameLabel
        );

        // Create the encoding combobox
        encodingComboBox = new JComboBox<AudioFormat.Encoding>(ENCODING_FIELD_VALUES);
        addEditableComponent(
                ENCODING_FIELD_NAME,
                ENCODING_FIELD_TOOLTIP,
                encodingComboBox
        );

        // Create the endianness combobox
        endiannessComboBox = new JComboBox<String>(ENDIANNESS_FIELD_VALUES);
        addEditableComponent(
                ENDIANNESS_FIELD_NAME,
                ENDIANNESS_FIELD_TOOLTIP,
                endiannessComboBox
        );

        // Create an editable component for the sampling rate
        samplingRateTextField = new JTextField();
        addEditableComponent(
                SAMPLING_RATE_FIELD_NAME,
                SAMPLING_RATE_FIELD_TOOLTIP,
                samplingRateTextField
        );

        // Create the editable component for the sampling size in bits
        samplingSizeInBitsTextField = new JTextField();
        addEditableComponent(
                SAMPLING_SIZE_IN_BITS_FIELD_NAME,
                SAMPLING_SIZE_IN_BITS_FIELD_TOOLTIP,
                samplingSizeInBitsTextField
        );
    }

    @Override
    public void makePanelEditable(final boolean editable)
    {
        // Enable / disable editable components of the simulator panel
        nameTextField.setEnabled(editable);
        typeNameLabel.setEnabled(editable);
        encodingComboBox.setEnabled(editable);
        samplingRateTextField.setEnabled(editable);
        samplingSizeInBitsTextField.setEnabled(editable);
        endiannessComboBox.setEnabled(editable);
    }

    @Override
    public boolean canApplyEdits()
    {
        return true;
    }

    @Override
    public void applyEdits()
    {

    }

    @Override
    public void updateEditComponents()
    {
        // Update the fields based on the anatomic values
        nameTextField.setText(anatomicSimulator.getName());
        typeNameLabel.setText(anatomicSimulator.getClass().getSimpleName());
        encodingComboBox.setSelectedIndex(Arrays.asList(ENCODING_FIELD_VALUES).indexOf(anatomicSimulator.getFormat().getEncoding()));
        endiannessComboBox.setSelectedIndex(anatomicSimulator.getFormat().isBigEndian() ? 0 : 1);
        samplingRateTextField.setText(Float.toString(anatomicSimulator.getFormat().getSampleRate()));
        samplingSizeInBitsTextField.setText(Integer.toString(anatomicSimulator.getFormat().getSampleSizeInBits()));
    }


}
