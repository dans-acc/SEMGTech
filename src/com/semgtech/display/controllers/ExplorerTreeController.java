package com.semgtech.display.controllers;

import com.semgtech.api.simulation.AnatomicSimulator;
import com.semgtech.api.simulation.GaussianAnatomicSimulator;
import com.semgtech.api.simulation.KaghazchiAnatomicSimulator;
import com.semgtech.api.utils.signals.LoggedSignal;
import com.semgtech.api.utils.signals.Signal;
import com.semgtech.api.utils.signals.correlations.AutoCorrelation;
import com.semgtech.api.utils.signals.correlations.CrossCorrelation;
import com.semgtech.api.utils.signals.correlations.SignalCorrelation;
import com.semgtech.api.utils.signals.spectrum.MagnitudeSpectrum;
import com.semgtech.api.utils.signals.spectrum.PhaseSpectrum;
import com.semgtech.api.utils.signals.spectrum.Spectrum;
import com.semgtech.display.ui.popups.ExplorerTreePopup;
import com.semgtech.display.ui.trees.ExplorerTree;
import com.semgtech.display.windows.MainWindow;

import javax.sound.sampled.AudioFormat;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class ExplorerTreeController extends TreeController<DefaultTreeModel, ExplorerTreePopup, ExplorerTree>
{

    // Option panel new simulation titles, messages and input names
    private static final String NEW_SIMULATION_OPTION_TITLE = "New Simulation";
    private static final String NEW_SIMULATION_OPTION_NAME_INPUT_NAME = "Name:";
    private static final String NEW_SIMULATION_OPTION_TYPE_INPUT_NAME = "Type:";

    // Option panel rename title and message
    private static final String RENAME_OPTION_TITLE = "Rename";
    private static final String RENAME_OPTION_MESSAGE = "Provide the new name of the selected object:";

    // Option panel delete title and message
    private static final String DELETE_PANEL_TITLE = "Delete";
    private static final String DELETE_PANEL_MESSAGE = "Confirm the deletion of the selected nodes?";

    // Magnitude and phase names
    private static final String MAGNITUDE_NEW_NAME = "%s Magnitude Spectrum";
    private static final String PHASE_NEW_NAME = "%s Phase Spectrum";

    // Auto correlation name
    private static final String AUTO_CORRELATION_NEW_NAME = "%s Auto-Correlation";

    // cross correlation related names
    private static final String CROSS_CORRELATION_MAX_LAG_OPTION_TITLE = "Cross-Correlation Max Lag";
    private static final String CROSS_CORRELATION_MAX_LAG_OPTION_MESSAGE = "Define the maximum lag:";
    private static final String CROSS_CORRELATION_MAX_LAG_OPTION_EXCEPTION = "\n(Ensure that the input is an integer between 0 and %d)";
    private static final String CROSS_CORRELATION_NEW_NAME = "%s and %s Cross-Correlation";

    public ExplorerTreeController(final ExplorerTree explorerTree)
    {
        super(explorerTree);
    }

    /**
     * Handles a double click event. Method deals with DefaultMutableTreeNodes only.
     * When a valid DefaultMutableTreeNode is clicked, the user object is obtained
     * and subsequently displayed in the viewer panel.
     *
     * @param object - the object that was double clicked.
     */
    @Override
    public void handleClick(final Object object)
    {
        if (!(object instanceof DefaultMutableTreeNode))
            return;

        // Cast as DefaultMutableTreeNode, get the object.
        final DefaultMutableTreeNode node = (DefaultMutableTreeNode) object;
        final Object userObject = node.getUserObject();

        // Display the selected user object
        MainWindow.getInstance().getViewerMainPanel().displayObjectPanels(userObject, userObject, true);
    }

    /**
     * We're having to override this method since this tree deals with DefaultMutableTreeNode's
     * directly. Thus, we're having to 'extract' the desired objects.
     *
     * @param paths - the nodes that are selected in the tree
     * @return - an array of objects for which the nodes have been selected
     */
    @Override
    protected Object[] getSelectedObjects(final TreePath... paths)
    {
        final Object[] defaultMutableTreeNodes = super.getSelectedObjects(paths);
        if (defaultMutableTreeNodes == null)
            return null;
        final Object[] selectedObjects = new Object[defaultMutableTreeNodes.length];
        for (int index = 0; index < defaultMutableTreeNodes.length; ++index)
            selectedObjects[index] = ((DefaultMutableTreeNode) defaultMutableTreeNodes[index]).getUserObject();
        return selectedObjects;
    }

    @Override
    protected boolean handleAction(final Object source, final ExplorerTreePopup popup,
                                   final TreePath[] paths, final Object[] objects)
    {
        if (source == popup.getNewSimulationItem())
            return handleNewSimulation();
        else if (source == popup.getRefactorRenameItem())
            return handleRefactorRename(paths, objects);
        else if (source == popup.getDeleteItem())
            return handleDeletion(paths, objects);
        else if (source == popup.getFourierMagnitudeSpectrumItem())
            return handleMagnitudeSpectrum(paths, objects);
        else if (source == popup.getFourierPhaseSpectrumItem())
            return handlePhaseSpectrum(paths, objects);
        else if (source == popup.getCorrelationAutoCorrelationItem())
            return handleAutoCorrelation(paths, objects);
        else if (source == popup.getCorrelationCrossCorrelationItem())
            return handleCrossCorrelation(paths, objects);
        else if (source == popup.getNormaliseItem())
            return handleNormalisation(paths, objects);
        return false;
    }

    private boolean handleNewSimulation()
    {
        // The names of the simulation types
        final String[] simulationTypeNames = new String[] {
                GaussianAnatomicSimulator.DEFAULT_NAME,
                KaghazchiAnatomicSimulator.DEFAULT_NAME
        };

        // Create the necessary inputs
        final JTextField simulationName = new JTextField();
        final JComboBox<String> simulationTypes = new JComboBox<>(simulationTypeNames);

        // Create the window input panel
        final Object[] inputs = new Object[] {
                NEW_SIMULATION_OPTION_NAME_INPUT_NAME, simulationName,
                NEW_SIMULATION_OPTION_TYPE_INPUT_NAME, simulationTypes
        };

        // Prompt the user with a dialog window until they select the desired option
        int option;
        do {
            option = JOptionPane.showConfirmDialog(
                    MainWindow.getInstance(),
                    inputs,
                    NEW_SIMULATION_OPTION_TITLE,
                    JOptionPane.OK_CANCEL_OPTION
            );
            if (option == JOptionPane.CANCEL_OPTION)
                return false;
        } while (simulationName.getText() == null || simulationName.getText().isEmpty());

        AudioFormat format = new AudioFormat(1000, 16, 1, false, true);

        // Depending on the selected input, create the appropriate simulation type
        AnatomicSimulator simulator = simulationTypes.getSelectedIndex() == 0
                ? new GaussianAnatomicSimulator(simulationName.getText(), null, format)
                : new KaghazchiAnatomicSimulator(simulationName.getText(), null, format);

        // Add the simulator to the tree and display it
        tree.addChild(tree.getModel().getRoot(), simulator, true);
        return true;
    }

    @SuppressWarnings("Duplicates")
    private boolean handleRefactorRename(final TreePath[] selectedPaths, final Object[] selectedObjects)
    {
        final DefaultMutableTreeNode selectedObject = (DefaultMutableTreeNode) selectedObjects[0];
        String name = null;
        do {
            name = JOptionPane.showInputDialog(
                    MainWindow.getInstance(),
                    RENAME_OPTION_MESSAGE,
                    RENAME_OPTION_TITLE,
                    JOptionPane.QUESTION_MESSAGE
            );
        } while (name != null && name.isEmpty());
        if (name == null)
            return false;
        setObjectName(selectedObject, name);
        tree.getModel().reload(selectedObject);
        //explorerTree.getModel().reload((DefaultMutableTreeNode) selectedPaths[0].getLastPathComponent());
        return true;
    }

    private void setObjectName(final Object selectedObject, final String name)
    {
        if (selectedObject instanceof AnatomicSimulator)
            ((AnatomicSimulator) selectedObject).setName(name);
        else if (selectedObject instanceof Signal)
            ((Signal) selectedObject).setName(name);
        else if (selectedObject instanceof Spectrum)
            ((Spectrum) selectedObject).setName(name);
        else if (selectedObject instanceof SignalCorrelation)
            ((SignalCorrelation) selectedObject).setName(name);
    }

    @SuppressWarnings("Duplicates")
    private boolean handleDeletion(final TreePath[] paths, final Object[] objects)
    {
        // Confirm the deletion of the selected objects
        final int selected = JOptionPane.showConfirmDialog(
                MainWindow.getInstance(),
                DELETE_PANEL_MESSAGE,
                DELETE_PANEL_TITLE,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        // Check whether the user has confirmed the deletion
        if (selected == JOptionPane.NO_OPTION)
            return false;

        // Loop through all of the paths, closing their tabs / side panels and removing them from the tree.
        DefaultMutableTreeNode node = null;
        for (final TreePath path : paths) {
            node = (DefaultMutableTreeNode) path.getLastPathComponent();
            MainWindow.getInstance().getViewerMainPanel().getViewerTabPane().closeObjectTabPanel(node.getUserObject());
            tree.removeChild(path);
        }
        return true;
    }

    private boolean handleMagnitudeSpectrum(final TreePath[] selectedPaths, final Object[] selectedObjects)
    {
        Signal signal = null;
        MagnitudeSpectrum magnitudeSpectrum = null;
        for (int index = 0; index < selectedObjects.length; ++index) {
            signal = (Signal) selectedObjects[index];
            magnitudeSpectrum = new MagnitudeSpectrum(String.format(MAGNITUDE_NEW_NAME, signal), signal);
            tree.addChild(selectedPaths[index], magnitudeSpectrum, false, true);
        }
        return true;
    }

    private boolean handlePhaseSpectrum(final TreePath[] selectedPaths, final Object[] selectedObjects)
    {
        Signal signal = null;
        PhaseSpectrum phaseSpectrum = null;
        for (int index = 0; index < selectedObjects.length; ++index) {
            signal = (Signal) selectedObjects[index];
            phaseSpectrum = new PhaseSpectrum(String.format(PHASE_NEW_NAME, signal), signal);
            tree.addChild(selectedPaths[index], phaseSpectrum, false, true);
        }
        return true;
    }

    private boolean handleAutoCorrelation(final TreePath[] selectedPaths, final Object[] selectedObjects)
    {
        Signal signal = null;
        AutoCorrelation autoCorrelation = null;
        for (int index = 0; index < selectedObjects.length; ++index) {
            signal = (Signal) selectedObjects[index];
            autoCorrelation = new AutoCorrelation(String.format(AUTO_CORRELATION_NEW_NAME, signal),
                    signal.getSampledAmplitudes().length, false, signal);
            tree.addChild(selectedPaths[index], autoCorrelation, false, true);
        }
        return true;
    }

    private boolean handleCrossCorrelation(final TreePath[] selectedPaths, final Object[] selectedObjects)
    {
        // Get the two signals that are to be cross-correlated
        final Signal signalX = (Signal) selectedObjects[0],
                signalY = (Signal) selectedObjects[1];

        // Prompt the user to obtain the max-lag value
        String message = CROSS_CORRELATION_MAX_LAG_OPTION_MESSAGE;
        String input = null;
        int maxLag = -1;
        do {
            input = JOptionPane.showInputDialog(
                    MainWindow.getInstance(),
                    message,
                    CROSS_CORRELATION_MAX_LAG_OPTION_TITLE,
                    JOptionPane.QUESTION_MESSAGE
            );
            try {
                maxLag = Integer.parseInt(input);
                if (maxLag <= 0 || maxLag > signalY.getSampledTimings().length)
                    throw new NumberFormatException();
                break;
            } catch (final NumberFormatException e) {
                message = CROSS_CORRELATION_MAX_LAG_OPTION_MESSAGE
                        + String.format(CROSS_CORRELATION_MAX_LAG_OPTION_EXCEPTION, signalY.getSampledAmplitudes().length);
            }
        } while (input != null);

        // The user did not want to compute the cross correlation coefficients between the two signals
        if (input == null)
            return false;

        // Compute the cross-correlation coefficients of the two signals
        final CrossCorrelation crossCorrelation = new CrossCorrelation(
                String.format(CROSS_CORRELATION_NEW_NAME, signalX.getName(), signalY.getName()),
                maxLag, false, signalX, signalY
        );

        // Add the cross correlated object to the explorer tree
        tree.addChild(selectedPaths[0], crossCorrelation, false, true);
        return true;
    }

    /**
     * Method normalises the selected signals
     *
     * @param selectedPaths - the paths of the signals that are to be normalised
     * @param selectedObjects - objects (signals) corresponding to the paths
     * @return true or false based on whether or not the normalisation was successful
     */
    private boolean handleNormalisation(final TreePath[] selectedPaths, final Object[] selectedObjects)
    {
        Signal normalisedSignal = null;
        for (int index = 0; index < selectedObjects.length; ++index) {
            normalisedSignal = selectedObjects[index] instanceof LoggedSignal
                    ? ((LoggedSignal) selectedObjects[index]).normalise()
                    : ((Signal) selectedObjects[index]).normalise();

            tree.addChild(selectedPaths[index], normalisedSignal, true, true);
        }
        return true;
    }
}
