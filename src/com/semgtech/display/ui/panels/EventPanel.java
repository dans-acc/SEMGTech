package com.semgtech.display.ui.panels;

import com.semgtech.api.utils.signals.events.EventComponent;

import javax.swing.*;

public class EventPanel extends EditablePanel
{

    // Name and tooltip for the events duration
    private static final String DURATION_FIELD_NAME = "Duration (ms):";
    private static final String DURATION_FIELD_TOOLTIP = "The duration of the event.";

    // Name and tooltip for the event starting time
    private static final String STARTING_TIME_FILED_NAME = "Started (ms):";
    private static final String STARTING_TIME_FIELD_TOOLTIP = "The time at which the event begun.";

    // Name and tooltip for the event ending time
    private static final String ENDING_TIME_FIELD_NAME = "Ended (ms):";
    private static final String ENDING_TIME_FIELD_TOOLTIP = "The time at which the event ended";

    // The logged signal for which the event exists
    private EventComponent eventComponent;

    // The duration of the event
    private JLabel durationLabel;

    // Starting and ending time fields
    private JTextField startingTimeTextField;
    private JTextField endingTimeTextField;

    public EventPanel(final EventComponent eventComponent)
    {
        super();
        this.eventComponent = eventComponent;
        updateEditComponents();
    }

    public EventComponent getEventComponent()
    {
        return eventComponent;
    }

    @Override
    public void initContentPanel()
    {
        // Create the duration label
        durationLabel = new JLabel();
        addEditableComponent(DURATION_FIELD_NAME, DURATION_FIELD_TOOLTIP, durationLabel);

        // Create the starting time field
        startingTimeTextField = new JTextField();
        addEditableComponent(STARTING_TIME_FILED_NAME, STARTING_TIME_FIELD_TOOLTIP, startingTimeTextField);

        // Create the ending time component
        endingTimeTextField = new JTextField();
        addEditableComponent(ENDING_TIME_FIELD_NAME, ENDING_TIME_FIELD_TOOLTIP, endingTimeTextField);
    }

    @Override
    public void makePanelEditable(final boolean editable)
    {
        // Enable / disable the fields
        durationLabel.setEnabled(editable);
        startingTimeTextField.setEnabled(editable);
        endingTimeTextField.setEnabled(editable);
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
        durationLabel.setText(Float.toString(eventComponent.getDuration()));
        startingTimeTextField.setText(Float.toString(eventComponent.getTimeBegan()));
        endingTimeTextField.setText(Float.toString(eventComponent.getTimeEnded()));
    }

}
