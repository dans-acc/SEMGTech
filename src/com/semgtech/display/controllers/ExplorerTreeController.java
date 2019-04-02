package com.semgtech.display.controllers;

import com.semgtech.api.simulation.AnatomicSimulator;
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

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ExplorerTreeController implements MouseListener, ActionListener
{

    // Option panel rename title and message
    private static final String RENAME_OPTION_TITLE = "Rename";
    private static final String RENAME_OPTION_MESSAGE = "Rename: %s:";

    // Option panel delete title and message
    private static final String DELETE_OPTION_TITLE = "Delete";
    private static final String DELETE_OPTION_MESSAGE = "Confirm the deletion of the selected nodes?";

    // Magnitude and phase names
    private static final String MAGNITUDE_NEW_NAME = "%s Magnitude Spectrum";
    private static final String PHASE_NEW_NAME = "%s Phase Spectrum";

    // Auto correlation name
    private static final String AUTO_CORRELATION_NEW_NAME = "%s Auto-Correlation";

    // cross correlation related names
    private static final String CROSS_CORRELATION_MAXLAG_OPTION_TITLE = "Cross-Correlation Max Lag";
    private static final String CROSS_CORRELATION_MAXLAG_OPTION_MESSAGE = "Define the maximum lag:";
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
        final int selectedRow = explorerTree.getRowForLocation(mouseEvent.getX(), mouseEvent.getY());
        final TreePath selectedPath = explorerTree.getPathForRow(selectedRow);
        if (selectedRow == -1 || selectedPath == null) {
            explorerTree.clearSelection();
            return;
        }
        if (mouseEvent.getClickCount() != 2)
            return;
        final DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
        if (selectedNode == null)
            return;
        // TODO: Come back to this! I don't like it!
        MainWindow.getInstance().displayObjectPanel(selectedNode.getUserObject(), true);
    }

    @Override
    public void mousePressed(final MouseEvent mouseEvent)
    {
        if (!mouseEvent.isPopupTrigger())
            return;
        final Object[] selectedObjects = getSelectedObjects(explorerTree.getSelectionPaths());
        explorerTree.getExplorerTreePopup().enableAppropriateItems(selectedObjects);
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
        // Get all the selected paths
        if (selectedPaths == null || selectedPaths.length == 0)
            return null;

        // Get all of the selected objects
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
        if (source == explorerTreePopup.getRefactorRenameItem())
            renameObject(selectedPaths, selectedObjects);
        else if (source == explorerTreePopup.getDeleteItem())
            deleteObject(selectedPaths, selectedObjects);
        else if (source == explorerTreePopup.getFourierMagnitudeSpectrumItem())
            computeSignalMagnitudeSpectrum(selectedPaths, selectedObjects);
        else if (source == explorerTreePopup.getFourierPhaseSpectrumItem())
            computeSignalPhaseSpectrum(selectedPaths, selectedObjects);
        else if (source == explorerTreePopup.getSignalAutoCorrelationItem())
            computeSignalAutoCorrelation(selectedPaths, selectedObjects);
        else if (source == explorerTreePopup.getSignalCrossCorrelationItem())
            computeSignalCrossCorrelation(selectedPaths, selectedObjects);
        else if (source == explorerTreePopup.getNormaliseItem())
            normaliseSignal(selectedPaths, selectedObjects);

        // Repaint the explorer tree
        explorerTree.repaint();
    }

    private void renameObject(final TreePath[] selectedPaths, final Object[] selectedObjects)
    {
        // Get the object that is to be renamed
        final Object selectedObject = selectedObjects[0];

        // Get the new name of the component
        String name = null;
        do {
            name = JOptionPane.showInputDialog(
                    MainWindow.getInstance(),
                    String.format(RENAME_OPTION_MESSAGE, selectedObject),
                    RENAME_OPTION_TITLE,
                    JOptionPane.QUESTION_MESSAGE
            );
        } while (name != null && name.isEmpty());

        // If a valid name has been provided, then update the instance
        if (name != null) {
            setObjectName(selectedObject, name);
            ((DefaultTreeModel) explorerTree.getModel()).reload((DefaultMutableTreeNode) selectedPaths[0].getLastPathComponent());
        }
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

    private void deleteObject(final TreePath[] selectedPaths, final Object[] selectedObjects)
    {
        // Confirm the deletion of the objects
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

        // Delete the selected nodes, and remove them from the tab
        DefaultMutableTreeNode node = null;
        for (int path = 0; path < selectedPaths.length; ++path) {
            node = (DefaultMutableTreeNode) selectedPaths[path].getLastPathComponent();
            ((DefaultTreeModel) explorerTree.getModel()).removeNodeFromParent(node);
            MainWindow.getInstance().closeObjectPanel(node.getUserObject());
        }
    }

    private void computeSignalMagnitudeSpectrum(final TreePath[] selectedPaths, final Object[] selectedObjects)
    {
        Signal signal = null;
        MagnitudeSpectrum magnitudeSpectrum = null;
        for (int index = 0; index < selectedObjects.length; ++index) {

            // Compute the signal magnitude spectrum
            signal = (Signal) selectedObjects[index];
            magnitudeSpectrum = new MagnitudeSpectrum(String.format(MAGNITUDE_NEW_NAME, signal), signal);

            // Add the normalised magnitude spectrum
            explorerTree.addChildObject(selectedPaths[index], magnitudeSpectrum, false, true);
        }
    }

    private void computeSignalPhaseSpectrum(final TreePath[] selectedPaths, final Object[] selectedObjects)
    {
        Signal signal = null;
        PhaseSpectrum phaseSpectrum = null;
        for (int index = 0; index < selectedObjects.length; ++index) {

            // Compute the signal magnitude spectrum
            signal = (Signal) selectedObjects[index];
            phaseSpectrum = new PhaseSpectrum(String.format(PHASE_NEW_NAME, signal), signal);

            // Add the normalised magnitude spectrum
            explorerTree.addChildObject(selectedPaths[index], phaseSpectrum, false, true);
        }
    }

    private void computeSignalAutoCorrelation(final TreePath[] selectedPaths, final Object[] selectedObjects)
    {
        Signal signal = null;
        AutoCorrelation autoCorrelation = null;
        for (int index = 0; index < selectedObjects.length; ++index) {

            // Compute the signals auto correlation
            signal = (Signal) selectedObjects[index];
            autoCorrelation = new AutoCorrelation(String.format(AUTO_CORRELATION_NEW_NAME, signal),
                    signal.getSampledAmplitudes().length, false, signal);

            // Add the auto correlated signal to tree
            explorerTree.addChildObject(selectedPaths[index], autoCorrelation, false, true);
        }
    }

    private void computeSignalCrossCorrelation(final TreePath[] selectedPaths, final Object[] selectedObjects)
    {
        // Get the two signals that are to be cross-correlated
        Signal signalX = (Signal) selectedObjects[0];
        Signal signalY = (Signal) selectedObjects[1];

        // Prompt the user to obtain the max-lag value
        String maxLagInput = null;
        int maxLag = 0;
        do {
            maxLagInput = JOptionPane.showInputDialog(
                    MainWindow.getInstance(),
                    CROSS_CORRELATION_MAXLAG_OPTION_MESSAGE,
                    CROSS_CORRELATION_MAXLAG_OPTION_TITLE,
                    JOptionPane.QUESTION_MESSAGE
            );
            try {
                maxLag = Integer.parseInt(maxLagInput);
                break;
            } catch (final NumberFormatException e) { }
        } while (maxLagInput != null);

        // The user did not want to compute the cross correlation coefficients between the two signals
        if (maxLagInput == null)
            return;

        // Compute the cross-correlation coefficients of the two signals
        final CrossCorrelation crossCorrelation = new CrossCorrelation(String.format(CROSS_CORRELATION_NEW_NAME, signalX.getName(), signalY.getName()),
                maxLag, false, signalX, signalY);

        // Add the cross correlation to the first selected signal
        explorerTree.addChildObject(selectedPaths[0], crossCorrelation, false, true);
    }

    private void normaliseSignal(final TreePath[] selectedPaths, final Object[] selectedObjects)
    {
        Signal normalisedSignal = null;
        for (int index = 0; index < selectedObjects.length; ++index) {

            // Normalise the signal
            normalisedSignal = selectedObjects[index] instanceof LoggedSignal
                    ? ((LoggedSignal) selectedObjects[index]).normalise()
                    : ((Signal) selectedObjects[index]).normalise();

            // Add the normalised signal to under the original signal
            explorerTree.addChildObject(selectedPaths[index], normalisedSignal, true, true);
        }
    }
}
