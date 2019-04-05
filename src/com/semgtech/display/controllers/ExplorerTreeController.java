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
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ExplorerTreeController implements MouseListener, ActionListener
{

    // Option panel new simulation titles, messages and input names
    private static final String NEW_SIMULATION_OPTION_TITLE = "New Simulation";
    private static final String NEW_SIMULATION_OPTION_NAME_INPUT_NAME = "Name:";
    private static final String NEW_SIMULATION_OPTION_TYPE_INPUT_NAME = "Type:";

    // Option panel rename title and message
    private static final String RENAME_OPTION_TITLE = "Rename";
    private static final String RENAME_OPTION_MESSAGE = "Provide the new name of the selected object:";

    // Option panel delete title and message
    private static final String DELETE_OPTION_TITLE = "Delete";
    private static final String DELETE_OPTION_MESSAGE = "Confirm the deletion of the selected nodes?";

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

    // The explorer tree the controller belongs to
    private ExplorerTree explorerTree;

    public ExplorerTreeController(final ExplorerTree explorerTree)
    {
        this.explorerTree = explorerTree;
    }

    @Override
    public void mouseClicked(final MouseEvent mouseEvent)
    {
        if (mouseEvent.getSource() != explorerTree)
            return;

        // Get the selected row within the explorer tree
        final int selectedRow = explorerTree.getRowForLocation(mouseEvent.getX(), mouseEvent.getY());
        final TreePath selectedPath = explorerTree.getPathForRow(selectedRow);
        if (selectedRow == -1 || selectedPath == null) {
            explorerTree.clearSelection();
            return;
        }

        // Deal only with double clicks
        if (mouseEvent.getClickCount() >= 2)
            return;

        // Get the node that was clicked
        final DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
        if (selectedNode == null)
            return;

        // Display the selected node
        final Object userObject = selectedNode.getUserObject();
        MainWindow.getInstance().getViewerMainPanel().displayObjectPanels(userObject, userObject, true);
    }

    @Override
    public void mousePressed(final MouseEvent mouseEvent)
    {
        // Handle only popup menus
        if (!mouseEvent.isPopupTrigger())
            return;

        // Get all of the selected objects
        final Object[] selectedObjects = getSelectedObjects(explorerTree.getSelectionPaths());

        // Disable / enable options based on what is selected
        explorerTree.getExplorerTreePopup().enableMenuItemsFor(selectedObjects);
        explorerTree.getExplorerTreePopup().show(
                explorerTree,
                mouseEvent.getX(),
                mouseEvent.getY()
        );
    }

    @Override
    public void mouseReleased(final MouseEvent mouseEvent) { }
    @Override
    public void mouseEntered(final MouseEvent mouseEvent) { }
    @Override
    public void mouseExited(final MouseEvent mouseEvent) { }


    private Object[] getSelectedObjects(final TreePath... selectedPaths)
    {
        // If there are no paths selected, return null
        if (selectedPaths == null || selectedPaths.length == 0)
            return null;

        // Get all the selected object instances from within the DefaultMutableNodes
        final Object[] selectedObjects = new Object[selectedPaths.length];
        for (int index = 0; index < selectedPaths.length; ++index)
            selectedObjects[index] = ((DefaultMutableTreeNode) selectedPaths[index].getLastPathComponent()).getUserObject();

        return selectedObjects;
    }

    @Override
    public void actionPerformed(final ActionEvent actionEvent)
    {
        // Get the explorer popup, and the option selected
        final ExplorerTreePopup explorerTreePopup = explorerTree.getExplorerTreePopup();
        final Object source = actionEvent.getSource();

        // Get all the selected paths and objects
        final TreePath[] selectedPaths = explorerTree.getSelectionPaths();
        final Object[] selectedObjects = getSelectedObjects(selectedPaths);

        // Determine the next course of action
        if (source == explorerTreePopup.getNewSimulationItem())
            newSimulation();
        else if (source == explorerTreePopup.getRefactorRenameItem())
            renameObject(selectedPaths, selectedObjects);
        else if (source == explorerTreePopup.getDeleteItem())
            deleteObject(selectedPaths);
        else if (source == explorerTreePopup.getFourierMagnitudeSpectrumItem())
            computeSignalMagnitudeSpectrum(selectedPaths, selectedObjects);
        else if (source == explorerTreePopup.getFourierPhaseSpectrumItem())
            computeSignalPhaseSpectrum(selectedPaths, selectedObjects);
        else if (source == explorerTreePopup.getCorrelationAutoCorrelationItem())
            computeSignalAutoCorrelation(selectedPaths, selectedObjects);
        else if (source == explorerTreePopup.getCorrelationCrossCorrelationItem())
            computeSignalCrossCorrelation(selectedPaths, selectedObjects);
        else if (source == explorerTreePopup.getNormaliseItem())
            normaliseSignal(selectedPaths, selectedObjects);

        // Repaint the explorer tree
        explorerTree.repaint();
    }

    private void newSimulation()
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
                return;
        } while (simulationName.getText() == null || simulationName.getText().isEmpty());

        AudioFormat format = new AudioFormat(1000, 16, 1, false, true);

        // Depending on the selected input, create the appropriate simulation type
        AnatomicSimulator simulator = simulationTypes.getSelectedIndex() == 0
                ? new GaussianAnatomicSimulator(simulationName.getText(), null, format)
                : new KaghazchiAnatomicSimulator(simulationName.getText(), null, format);

        // Add the simulator to the tree and display it
        explorerTree.addChildObject(
                explorerTree.getModel().getRoot(),
                simulator, true
        );
    }

    private void renameObject(final TreePath[] selectedPaths, final Object[] selectedObjects)
    {
        // Get the object that's to be renamed
        final Object selectedObject = selectedObjects[0];

        // Obtain the new name via user input
        String name = null;
        do {
            name = JOptionPane.showInputDialog(
                    MainWindow.getInstance(),
                    RENAME_OPTION_MESSAGE,
                    RENAME_OPTION_TITLE,
                    JOptionPane.QUESTION_MESSAGE
            );
        } while (name != null && name.isEmpty());

        // If the name is valid, and exists, we can set the object name
        if (name == null)
            return;
        setObjectName(selectedObject, name);
        explorerTree.getModel().reload((DefaultMutableTreeNode) selectedPaths[0].getLastPathComponent());
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

    private void deleteObject(final TreePath[] selectedPaths)
    {
        // Confirm the deletion of the selected objects
        final int selected = JOptionPane.showConfirmDialog(
                MainWindow.getInstance(),
                DELETE_OPTION_MESSAGE,
                DELETE_OPTION_TITLE,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        // Check whether the user has confirmed the deletion
        if (selected != 0)
            return;

        // Loop through the paths, deleting the selected nodes
        DefaultMutableTreeNode treeNode = null;
        for (final TreePath treePath : selectedPaths) {
            treeNode = (DefaultMutableTreeNode) treePath.getLastPathComponent();
            explorerTree.getModel().removeNodeFromParent(treeNode);
            //MainWindow.getInstance().getViewerMainPanel().closeObjectPanels(treeNode.getUserObject());
        }
    }

    private void computeSignalMagnitudeSpectrum(final TreePath[] selectedPaths, final Object[] selectedObjects)
    {
        Signal signal = null;
        MagnitudeSpectrum magnitudeSpectrum = null;
        for (int index = 0; index < selectedObjects.length; ++index) {
            signal = (Signal) selectedObjects[index];
            magnitudeSpectrum = new MagnitudeSpectrum(String.format(MAGNITUDE_NEW_NAME, signal), signal);
            explorerTree.addChildObject(selectedPaths[index], magnitudeSpectrum, false, true);
        }
    }

    private void computeSignalPhaseSpectrum(final TreePath[] selectedPaths, final Object[] selectedObjects)
    {
        Signal signal = null;
        PhaseSpectrum phaseSpectrum = null;
        for (int index = 0; index < selectedObjects.length; ++index) {
            signal = (Signal) selectedObjects[index];
            phaseSpectrum = new PhaseSpectrum(String.format(PHASE_NEW_NAME, signal), signal);
            explorerTree.addChildObject(selectedPaths[index], phaseSpectrum, false, true);
        }
    }

    private void computeSignalAutoCorrelation(final TreePath[] selectedPaths, final Object[] selectedObjects)
    {
        Signal signal = null;
        AutoCorrelation autoCorrelation = null;
        for (int index = 0; index < selectedObjects.length; ++index) {
            signal = (Signal) selectedObjects[index];
            autoCorrelation = new AutoCorrelation(String.format(AUTO_CORRELATION_NEW_NAME, signal),
                    signal.getSampledAmplitudes().length, false, signal);
            explorerTree.addChildObject(selectedPaths[index], autoCorrelation, false, true);
        }
    }

    private void computeSignalCrossCorrelation(final TreePath[] selectedPaths, final Object[] selectedObjects)
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
            return;

        // Compute the cross-correlation coefficients of the two signals
        final CrossCorrelation crossCorrelation = new CrossCorrelation(
                String.format(CROSS_CORRELATION_NEW_NAME, signalX.getName(), signalY.getName()),
                maxLag, false, signalX, signalY
        );

        // Add the cross correlated object to the explorer tree
        explorerTree.addChildObject(selectedPaths[0], crossCorrelation, false, true);
    }

    private void normaliseSignal(final TreePath[] selectedPaths, final Object[] selectedObjects)
    {
        Signal normalisedSignal = null;
        for (int index = 0; index < selectedObjects.length; ++index) {
            normalisedSignal = selectedObjects[index] instanceof LoggedSignal
                    ? ((LoggedSignal) selectedObjects[index]).normalise()
                    : ((Signal) selectedObjects[index]).normalise();
            explorerTree.addChildObject(selectedPaths[index], normalisedSignal, true, true);
        }
    }
}
