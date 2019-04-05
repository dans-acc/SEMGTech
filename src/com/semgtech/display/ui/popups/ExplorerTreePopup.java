package com.semgtech.display.ui.popups;

import com.semgtech.api.simulation.AnatomicSimulator;
import com.semgtech.api.utils.signals.Signal;
import com.semgtech.display.controllers.ExplorerTreeController;

import javax.swing.*;


public class ExplorerTreePopup extends PopupMenu<ExplorerTreeController>
{

    // New menu names
    private static final String NEW_MENU_NAME = "New";
    private static final String NEW_SIMULATION_ITEM_NAME = "Simulation";
    private static final String NEW_SIMULATION_ITEM_TOOLTIP = "Create a new simulation model";

    // Importing and exporting options
    private static final String IMPORT_ITEM_NAME = "Import";
    private static final String IMPORT_ITEM_TOOLTIP = "Import a signal into SEMGTech.";
    private static final String EXPORT_ITEM_NAME = "Export";
    private static final String EXPORT_ITEM_TOOLTIP = "Export the selected items.";

    // Refactoring names / tooltips
    private static final String REFACTOR_MENU_NAME = "Refactor";
    private static final String REFACTOR_RENAME_ITEM_NAME = "Rename";
    private static final String REFACTOR_RENAME_ITEM_TOOLTIP = "Rename the currently selected item.";

    // Deleting option name / tooltips
    private static final String DELETE_ITEM_NAME = "Delete";
    private static final String DELETE_ITEM_TOOLTIP = "Delete the currently selected item(s).";

    // Simulation analysis item names / tooltips
    private static final String SIMULATE_MENU_NAME = "Simulate";
    private static final String SIMULATE_SEMG_ITEM_NAME = "SEMG Signal";
    private static final String SIMULATE_SEMG_ITEM_TOOLTIP = "Simulate an SEMG signal using the selected simulation model(s).";
    private static final String SIMULATE_MUAPT_ITEM_NAME = "MUAP Trains";
    private static final String SIMULATE_MUAPT_ITEM_TOOLTIP = "Simulate a MUAP train using the selected simulation model(s).";

    // Fourier analysis item names / tooltips
    private static final String FOURIER_MENU_NAME = "Fourier Analysis";
    private static final String FOURIER_MAGNITUDE_SPECTRUM_ITEM_NAME = "Magnitude Spectrum";
    private static final String FOURIER_MAGNITUDE_SPECTRUM_ITEM_TOOLTIP = "Compute the magnitude spectrum of the selected signal(s).";
    private static final String FOURIER_PHASE_SPECTRUM_ITEM_NAME = "Phase Spectrum";
    private static final String FOURIER_PHASE_SPECTRUM_ITEM_TOOLTIP = "Compute the phase spectrum of the selected signal(s).";

    // Correlation analysis item names / tooltips
    private static final String SIGNAL_CORRELATION_MENU_NAME = "Correlation Analysis";
    private static final String SIGNAL_AUTO_CORRELATION_ITEM_NAME = "Auto-Correlation";
    private static final String SIGNAL_AUTO_CORRELATION_ITEM_TOOLTIP = "Compute the signal(s) Auto-Correlation coefficients.";
    private static final String SIGNAL_CROSS_CORRELATION_ITEM_NAME = "Cross-Correlation";
    private static final String SIGNAL_CROSS_CORRELATION_ITEM_TOOLTIP = "Compute Cross-Correlation coefficients of the two selected signals.";

    // Normalisation item names / tooltips
    private static final String NORMALISE_ITEM_NAME = "Normalise";
    private static final String NORMALISE_ITEM_TOOLTIP = "Normalise the selected signal(s).";

    // Controller used to handle events
    private ExplorerTreeController explorerTreeController;

    // New options
    private JMenu newMenu;
    private JMenuItem newSimulationItem;

    // Import and export options
    private JMenuItem importItem;
    private JMenuItem exportItem;

    // Refactoring options
    private JMenu refactorMenu;
    private JMenuItem refactorRenameItem;

    // Deletion option
    private JMenuItem deleteItem;

    // Simulation options
    private JMenu simulateMenu;
    private JMenuItem simulateSEMGItem;
    private JMenuItem simulateMUAPTItem;

    // Fourier analysis options
    private JMenu fourierMenu;
    private JMenuItem fourierMagnitudeSpectrumItem;
    private JMenuItem fourierPhaseSpectrumItem;

    // Signal correlation options
    private JMenu correlationMenu;
    private JMenuItem correlationAutoCorrelationItem;
    private JMenuItem correlationCrossCorrelationItem;

    // Normalisation option
    private JMenuItem normaliseItem;

    public ExplorerTreePopup(final ExplorerTreeController explorerTreeController)
    {
        super(explorerTreeController);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void initMenuItems()
    {
        // Create the new simulation menu
        newSimulationItem = createMenuItem(NEW_SIMULATION_ITEM_NAME, NEW_SIMULATION_ITEM_TOOLTIP);
        newMenu = createMenu(NEW_MENU_NAME, newSimulationItem);
        add(newMenu);

        // Create the import and export options
        importItem = createMenuItem(IMPORT_ITEM_NAME, IMPORT_ITEM_TOOLTIP);
        exportItem = createMenuItem(EXPORT_ITEM_NAME, EXPORT_ITEM_TOOLTIP);
        add(importItem);
        add(exportItem);
        add(new JSeparator(JSeparator.HORIZONTAL));

        // Create the refactor menu and the delete option
        refactorRenameItem = createMenuItem(REFACTOR_RENAME_ITEM_NAME, REFACTOR_RENAME_ITEM_TOOLTIP);
        refactorMenu = createMenu(REFACTOR_MENU_NAME, refactorRenameItem);
        deleteItem = createMenuItem(DELETE_ITEM_NAME, DELETE_ITEM_TOOLTIP);
        add(refactorMenu);
        add(deleteItem);
        add(new JSeparator(JSeparator.HORIZONTAL));

        // Create the simulation menu
        simulateSEMGItem = createMenuItem(SIMULATE_SEMG_ITEM_NAME, SIMULATE_SEMG_ITEM_TOOLTIP);
        simulateMUAPTItem = createMenuItem(SIMULATE_MUAPT_ITEM_NAME, SIMULATE_MUAPT_ITEM_TOOLTIP);
        simulateMenu = createMenu(SIMULATE_MENU_NAME, simulateSEMGItem, simulateMUAPTItem);
        add(simulateMenu);
        add(new JSeparator(JSeparator.HORIZONTAL));

        // Create the fourier menu
        fourierMagnitudeSpectrumItem = createMenuItem(FOURIER_MAGNITUDE_SPECTRUM_ITEM_NAME, FOURIER_MAGNITUDE_SPECTRUM_ITEM_TOOLTIP);
        fourierPhaseSpectrumItem = createMenuItem(FOURIER_PHASE_SPECTRUM_ITEM_NAME, FOURIER_PHASE_SPECTRUM_ITEM_TOOLTIP);
        fourierMenu = createMenu(FOURIER_MENU_NAME, fourierMagnitudeSpectrumItem, fourierPhaseSpectrumItem);

        // Create the correlation menu
        correlationAutoCorrelationItem = createMenuItem(SIGNAL_AUTO_CORRELATION_ITEM_NAME, SIGNAL_AUTO_CORRELATION_ITEM_TOOLTIP);
        correlationCrossCorrelationItem = createMenuItem(SIGNAL_CROSS_CORRELATION_ITEM_NAME, SIGNAL_CROSS_CORRELATION_ITEM_TOOLTIP);
        correlationMenu = createMenu(SIGNAL_CORRELATION_MENU_NAME, correlationAutoCorrelationItem, correlationCrossCorrelationItem);

        // Create the normalisation option
        normaliseItem = createMenuItem(NORMALISE_ITEM_NAME, NORMALISE_ITEM_TOOLTIP);
        add(fourierMenu);
        add(correlationMenu);
        add(normaliseItem);
    }


    public JMenuItem getNewSimulationItem()
    {
        return newSimulationItem;
    }

    private JMenuItem getImportItem()
    {
        return importItem;
    }

    public JMenuItem getExportItem()
    {
        return exportItem;
    }

    public JMenuItem getRefactorRenameItem()
    {
        return refactorRenameItem;
    }

    public JMenuItem getDeleteItem()
    {
        return deleteItem;
    }

    public JMenuItem getSimulateSEMGItem()
    {
        return simulateSEMGItem;
    }

    public JMenuItem getSimulateMUAPTItem()
    {
        return simulateMUAPTItem;
    }

    public JMenuItem getFourierMagnitudeSpectrumItem()
    {
        return fourierMagnitudeSpectrumItem;
    }

    public JMenuItem getFourierPhaseSpectrumItem()
    {
        return fourierPhaseSpectrumItem;
    }

    public JMenuItem getCorrelationAutoCorrelationItem()
    {
        return correlationAutoCorrelationItem;
    }

    public JMenuItem getCorrelationCrossCorrelationItem()
    {
        return correlationCrossCorrelationItem;
    }

    public JMenuItem getNormaliseItem()
    {
        return normaliseItem;
    }

    @Override
    public void enableMenuItemsFor(final Object[] objects)
    {
        // Check that objects are all signals
        final boolean objectsAreSignals = areOfSameInstance(Signal.class, objects);

        // Enable / disable refactor options
        final boolean enableRefactorMenu = objects != null && objects.length == 1;
        refactorMenu.setEnabled(enableRefactorMenu);

        // Enable / disable the delete item
        final boolean enableDeleteItem = objects != null && objects.length > 0;
        deleteItem.setEnabled(enableDeleteItem);

        // Enable / disable simulate items
        final boolean enableSimulateMenu = objects != null && objects.length > 0 && areOfSameInstance(AnatomicSimulator.class, objects);
        simulateMenu.setEnabled(enableSimulateMenu);

        // Enable / disable fourier items
        final boolean enableFourierMenu = objects != null && objects.length > 0 && objectsAreSignals;
        fourierMenu.setEnabled(enableFourierMenu);

        // Enable / disable correlation
        final boolean enableAutoCorrelationItem = objects != null && objects.length == 1,
                enableCrossCorrelationItem = objects != null && objects.length == 2,
                enableCorrelationMenu = objectsAreSignals && (enableAutoCorrelationItem || enableCrossCorrelationItem);
        correlationMenu.setEnabled(enableCorrelationMenu);
        correlationAutoCorrelationItem.setEnabled(enableAutoCorrelationItem);
        correlationCrossCorrelationItem.setEnabled(enableCrossCorrelationItem);

        // Enable / disable the normalise item
        final boolean enableNormaliseItem = objects != null && objects.length > 0 && objectsAreSignals;
        normaliseItem.setEnabled(enableNormaliseItem);

        // Call repaint to update components
        revalidate();
        repaint();
    }
}
