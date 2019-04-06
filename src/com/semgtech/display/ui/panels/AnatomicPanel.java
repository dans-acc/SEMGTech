package com.semgtech.display.ui.panels;

import com.semgtech.api.simulation.anatomy.AnatomicComponent;
import com.semgtech.api.utils.signals.events.EventComponent;
import com.semgtech.api.utils.vector.Vector3d;

import javax.swing.*;

public class AnatomicPanel<T extends AnatomicComponent> extends EditablePanel
{

    // The name and tooltip for the name of the anatomic component
    private static final String NAME_FIELD_NAME = "Name:";
    private static final String NAME_FIELD_TOOLTIP = "The name of the anatomic component";

    // The name and tooltip for the position component
    private static final String POSITION_FIELD_NAME = "Position:";
    private static final String POSITION_FIELD_TOOLTIP = "The position (x, y, z) of the anatomic component.";
    private static final String POSITION_FIELD_FORMAT = "%f, %f, %f";

    // The name and tooltip for the radius
    private static final String RADIUS_NAME = "Radius:";
    private static final String RADIUS_TOOLTIP = "The radius of the anatomic component";

    // The anatomic component
    private T anatomicComponent;

    // The name, position and radius of the anatomic component
    private JTextField nameTextField;
    private JTextField positionTextField;
    private JTextField radiusTextField;

    public AnatomicPanel(final T anatomicComponent)
    {
        this.anatomicComponent = anatomicComponent;
    }

    public T getAnatomicComponent()
    {
        return anatomicComponent;
    }

    @Override
    public void initContentPanel()
    {
        // Create the anatomic component name field
        nameTextField = new JTextField();
        addEditableComponent(NAME_FIELD_NAME, NAME_FIELD_TOOLTIP, nameTextField);

        // Create the position fields
        positionTextField = new JTextField();
        addEditableComponent(POSITION_FIELD_NAME, POSITION_FIELD_TOOLTIP, positionTextField);

        // Create the radius fields
        radiusTextField = new JTextField();
        addEditableComponent(RADIUS_NAME, RADIUS_TOOLTIP, radiusTextField);
    }

    @Override
    public void makePanelEditable(final boolean editable)
    {
        nameTextField.setEnabled(editable);
        positionTextField.setEnabled(editable);
        radiusTextField.setEnabled(editable);
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
        nameTextField.setText(anatomicComponent.getName());

        // Set the position of the anatomic component
        final Vector3d position = anatomicComponent.getPosition();
        positionTextField.setText(String.format(
                POSITION_FIELD_FORMAT,
                position.getX(),
                position.getY(),
                position.getZ()
                )
        );

        // Set the radius of the anatomic component
        radiusTextField.setText(Double.toString(anatomicComponent.getRadius()));
    }

}
