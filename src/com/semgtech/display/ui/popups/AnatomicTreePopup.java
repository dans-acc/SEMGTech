package com.semgtech.display.ui.popups;

import com.semgtech.display.controllers.AnatomicTreeController;

import javax.swing.*;

public class AnatomicTreePopup extends PopupMenu<AnatomicTreeController>
{

    // Names and tooltip for the 'new' menu items
    private static final String NEW_MENU_NAME = "New";
    private static final String NEW_MUSCLE_ITEM_NAME = "Muscle";
    private static final String NEW_MUSCLE_ITEM_TOOLTIP = "Create a new (single) muscle component.";
    private static final String NEW_MOTOR_UNIT_ITEM_NAME = "Motor-Unit";
    private static final String NEW_MOTOR_UNIT_ITEM_TOOLTIP = "Create a new (single) motor unit component.";
    private static final String NEW_FIBRE_ITEM_NAME = "Fibre";
    private static final String NEW_FIBRE_ITEM_TOOLTIP = "Create a new (single) fibre component";

    // Names and tooltips for the generate sub-menu
    private static final String GENERATE_MENU_NAME = "Generate";
    private static final String GENERATE_MUSCLE_ITEM_NAME = "Muscle";
    private static final String GENERATE_MUSCLE_ITEM_TOOLTIP = "Generate an entire muscle component";
    private static final String GENERATE_MOTOR_UNITS_ITEM_NAME = "Motor Unit(s)";
    private static final String GENERATE_MOTOR_UNITS_ITEM_TOOLTIP = "Generate motor unit(s) for the selected muscle";
    private static final String GENERATE_FIBRES_ITEM_NAME = "Fibre(s)";
    private static final String GENERATE_FIBRES_ITEM_TOOLTIP = "Generate fibre(s) for the selected motor units.";

    // Names and tooltips for the refactor menu and items
    private static final String REFACTOR_MENU_NAME = "Refactor";
    private static final String REFACTOR_RENAME_ITEM_NAME = "Rename";
    private static final String REFACTOR_RENAME_ITEM_TOOLTIP = "Click to rename the selected anatomic component.";

    // Name and tooltip for the deletion item
    private static final String DELETE_ITEM_NAME = "Delete";
    private static final String DELETE_ITEM_TOOLTIP = "Click to delete the currently selected anatomic components";

    // Controller used for handling the popup menu presses
    private AnatomicTreeController anatomicTreeController;

    // New menu for creating single components
    private JMenu newMenu;
    private JMenuItem newMuscleItem;
    private JMenuItem newMotorUnitItem;
    private JMenuItem newFibreItem;

    // Menu for generating multiple components
    private JMenu generateMenu;
    private JMenuItem generateMuscleItem;
    private JMenuItem generateMotorUnitItem;
    private JMenuItem generateFibreItem;

    // Refactoring menu
    private JMenu refactorMenu;
    private JMenuItem refactorRenameItem;

    // Deletion item
    private JMenuItem deleteItem;

    public AnatomicTreePopup(final AnatomicTreeController anatomicTreeController)
    {
        super(anatomicTreeController);
    }

    @SuppressWarnings("Duplicates")
    @Override
    protected void initMenuItems()
    {
        // Create the new menu
        newMuscleItem = createMenuItem(NEW_MUSCLE_ITEM_NAME, NEW_MUSCLE_ITEM_TOOLTIP);
        newMotorUnitItem = createMenuItem(NEW_MOTOR_UNIT_ITEM_NAME, NEW_MOTOR_UNIT_ITEM_TOOLTIP);
        newFibreItem = createMenuItem(NEW_FIBRE_ITEM_NAME, NEW_FIBRE_ITEM_TOOLTIP);
        newMenu = createMenu(NEW_MENU_NAME, newMuscleItem, newMotorUnitItem, newFibreItem);
        add(newMenu);

        // Create the generation menu
        generateMuscleItem = createMenuItem(GENERATE_MUSCLE_ITEM_NAME, GENERATE_MUSCLE_ITEM_TOOLTIP);
        generateMotorUnitItem = createMenuItem(GENERATE_MOTOR_UNITS_ITEM_NAME, GENERATE_MOTOR_UNITS_ITEM_TOOLTIP);
        generateFibreItem = createMenuItem(GENERATE_FIBRES_ITEM_NAME, GENERATE_FIBRES_ITEM_TOOLTIP);
        generateMenu = createMenu(GENERATE_MENU_NAME, generateMuscleItem, generateMotorUnitItem, generateFibreItem);
        add(generateMenu);

        // Create the refactor menu
        refactorRenameItem = createMenuItem(REFACTOR_RENAME_ITEM_NAME, REFACTOR_RENAME_ITEM_TOOLTIP);
        refactorMenu = createMenu(REFACTOR_MENU_NAME, refactorRenameItem);
        add(refactorMenu);

        // Create the deletion menu
        deleteItem = createMenuItem(DELETE_ITEM_NAME, DELETE_ITEM_TOOLTIP);
        add(deleteItem);
    }

    public JMenu getNewMenu()
    {
        return newMenu;
    }

    public JMenuItem getNewMuscleItem()
    {
        return newMuscleItem;
    }

    public JMenuItem getNewMotorUnitItem()
    {
        return newMotorUnitItem;
    }

    public JMenuItem getNewFibreItem()
    {
        return newFibreItem;
    }

    public JMenu getGenerateMenu()
    {
        return generateMenu;
    }

    public JMenuItem getGenerateMuscleItem()
    {
        return generateMuscleItem;
    }

    public JMenuItem getGenerateMotorUnitItem()
    {
        return generateMotorUnitItem;
    }

    public JMenuItem getGenerateFibreItem()
    {
        return generateFibreItem;
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

    @Override
    public void enableMenuItemsFor(final Object[] selectedObjects)
    {

    }

}
