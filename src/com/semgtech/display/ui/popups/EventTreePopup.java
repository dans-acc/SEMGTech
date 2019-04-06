package com.semgtech.display.ui.popups;

import com.semgtech.api.utils.signals.events.MotorUnitEvent;
import com.semgtech.api.utils.signals.events.MuscleEvent;
import com.semgtech.display.controllers.TreeController;

import javax.swing.*;

public class EventTreePopup extends PopupMenu
{

    // name and tooltip for the event
    private static final String PROPERTIES_ITEM_NAME = "Properties";
    private static final String PROPERTIES_ITEM_TOOLTIP = "Edit the propertiesItem of the selected event.";

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

    // Events propertiesItem menu item
    private JMenuItem propertiesItem;

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

    public EventTreePopup(final TreeController controller)
    {
        super(controller);
    }

    @SuppressWarnings("Duplicates")
    @Override
    protected void initMenuItems()
    {
        // Create the propertiesItem item
        propertiesItem = createMenuItem(PROPERTIES_ITEM_NAME, PROPERTIES_ITEM_TOOLTIP);
        add(propertiesItem);
        add(new JSeparator(JSeparator.HORIZONTAL));

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

    public JMenuItem getPropertiesItem()
    {
        return propertiesItem;
    }

    public JMenu getNewMenu()
    {
        return newMenu;
    }

    public JMenuItem getNewMuscleEventItem()
    {
        return newMuscleEventItem;
    }

    public JMenuItem getNewMotorUnitEventItem()
    {
        return newMotorUnitEventItem;
    }

    public JMenuItem getNewFibreEventItem()
    {
        return newFibreEventItem;
    }

    public JMenu getRefactorMenu()
    {
        return refactorMenu;
    }

    public JMenuItem getRefactorRenameItem()
    {
        return refactorRenameItem;
    }

    public JMenuItem getDeleteItem()
    {
        return deleteItem;
    }

    /**
     * Disables / enables options (JMenuItems) within the popup based
     * on the currently selected nodes and therefore objects in the tree (JTree).
     *
     * The ability to create a new muscle event is always enabled. We can simply
     * add the event to the root node.
     *
     * @param objects - the 'list' (array) of selected objects in the tree.
     */
    @Override
    public void enableMenuItemsFor(final Object[] objects)
    {
        // Enable / disable the properties option
        final boolean enablePropertiesItem = objects != null && objects.length == 1;
        propertiesItem.setEnabled(enablePropertiesItem);

        // Enable / disable the ability to create / add certain events
        final boolean enableNewMotorUnitEventItem = objects != null && objects.length == 1 && areOfSameInstance(MuscleEvent.class, objects),
                enableNewFibreEventItem = objects != null && objects.length == 1 && areOfSameInstance(MotorUnitEvent.class, objects);
        newMotorUnitEventItem.setEnabled(enableNewMotorUnitEventItem);
        newFibreEventItem.setEnabled(enableNewFibreEventItem);

        // Enable / disable the entire menu because there is only one selectable item
        final boolean enableRefactorMenu = objects != null && objects.length == 1;
        refactorMenu.setEnabled(enableRefactorMenu);

        // Enable / disable the deletion item
        final boolean enableDeleteItem = objects != null && objects.length > 0;
        deleteItem.setEnabled(enableDeleteItem);
    }

}
