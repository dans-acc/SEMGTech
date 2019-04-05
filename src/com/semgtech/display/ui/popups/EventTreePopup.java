package com.semgtech.display.ui.popups;

import com.semgtech.display.controllers.EventTreeController;

import javax.swing.*;

public class EventTreePopup extends PopupMenu<EventTreeController>
{

    // Names and tooltips for the refactor menu and items
    private static final String NEW_MENU_NAME = "New";
    private static final String NEW_MUSCLE_EVENT_NAME = "Muscle Event";
    private static final String NEW_MUSCLE_EVENT_TOOLTIP = "Create a new muscle event.";
    private static final String NEW_MOTOR_UNIT_EVENT_NAME = "Motor-Unit Event";
    private static final String NEW_MOTOR_UNIT_EVENT_TOOLTIP = "Create a new motor-unit event.";
    private static final String NEW_FIBRE_EVENT_NAME = "Fibre Event";
    private static final String NEW_FIBRE_EVENT_TOOLTIP = "Creat a new fibre event";

    // Refactor menu and item names and tooltips
    private static final String REFACTOR_MENU_NAME = "Refactor";
    private static final String REFACTOR_RENAME_ITEM_NAME = "Rename";
    private static final String REFACTOR_RENAME_ITEM_TOOLTIP = "Click to rename the selected event.";

    // Delete item name and tooltip
    private static final String DELETE_ITEM_NAME = "Delete";
    private static final String DELETE_ITEM_TOOLTIP = "Delete the selected event";

    // New menu used to create single components
    private JMenu newMenu;
    private JMenuItem newMuscleEventItem;
    private JMenuItem newMotorUnitEventItem;
    private JMenuItem newFibreEventItem;

    // Refactoring options
    private JMenu refactorMenu;
    private JMenuItem refactorRenameItem;

    // Deletion item
    private JMenuItem deleteItem;

    public EventTreePopup(final EventTreeController eventTreeController)
    {
        super(eventTreeController);
    }

    @SuppressWarnings("Duplicates")
    @Override
    protected void initMenuItems()
    {
        // Create the new menu
        newMuscleEventItem = createMenuItem(NEW_MUSCLE_EVENT_NAME, NEW_MUSCLE_EVENT_TOOLTIP);
        newMotorUnitEventItem = createMenuItem(NEW_MOTOR_UNIT_EVENT_NAME, NEW_MOTOR_UNIT_EVENT_TOOLTIP);
        newFibreEventItem = createMenuItem(NEW_FIBRE_EVENT_NAME, NEW_FIBRE_EVENT_TOOLTIP);
        newMenu = createMenu(NEW_MENU_NAME, newMuscleEventItem, newMotorUnitEventItem, newFibreEventItem);
        add(newMenu);

        // Create the refactor menu
        refactorRenameItem = createMenuItem(REFACTOR_RENAME_ITEM_NAME, REFACTOR_RENAME_ITEM_TOOLTIP);
        refactorMenu = createMenu(REFACTOR_MENU_NAME, refactorRenameItem);
        add(refactorMenu);

        // Create the deletion item
        deleteItem = createMenuItem(DELETE_ITEM_NAME, DELETE_ITEM_TOOLTIP);
        add(deleteItem);
    }

    @Override
    public void enableMenuItemsFor(final Object[] selectedObjects)
    {

    }

}
