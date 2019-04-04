package com.semgtech.display.ui.panes;

import com.semgtech.api.utils.signals.Signal;
import com.semgtech.api.utils.signals.correlations.AutoCorrelation;
import com.semgtech.api.utils.signals.correlations.CrossCorrelation;
import com.semgtech.api.utils.signals.correlations.SignalCorrelation;
import com.semgtech.api.utils.signals.spectrum.MagnitudeSpectrum;
import com.semgtech.api.utils.signals.spectrum.PhaseSpectrum;
import com.semgtech.display.controllers.ViewerTabPaneController;
import com.semgtech.display.ui.panels.TabPanel;
import com.semgtech.display.ui.panels.ViewerSidePanel;
import com.semgtech.display.utils.JFreeChartUtils;
import com.semgtech.display.windows.MainWindow;

import javax.swing.*;

public class ViewerTabPane extends JTabbedPane
{

    // Error names
    private static final String OBJECT_PANEL_NOT_FOUND_MESSAGE = "Failed to display: %s";
    private static final String OBJECT_PANEL_NOT_FOUND_TITLE = "Display Error";

    // Signal panel names
    private static final String SIGNAL_SERIES_NAME = "Samples";
    private static final String SIGNAL_CHART_X_AXIS_NAME = "Time";
    private static final String SIGNAL_CHART_Y_AXIS_NAME = "mV";

    // Magnitude panel names
    private static final String MAGNITUDE_SERIES_NAME = "Magnitude";
    private static final String MAGNITUDE_CHART_X_AXIS_NAME = "Frequency";
    private static final String MAGNITUDE_CHART_Y_AXIS_NAME = "Magnitude";

    // Names for auto and cross correlation panel names
    private static final String AUTO_CORRELATION_SERIES_NAME = "Auto-Correlation Coefficients";
    private static final String CROSS_CORRELATION_SERIES_NAME = "Cross-Correlation Coefficients";
    private static final String CORRELATION_CHART_X_AXIS_NAME = "Delay (Lag)";
    private static final String CORRELATION_CHART_Y_AXIS_NAME = "Correlation (Normalised)";

    private ViewerSidePanel viewerSidePanel;
    private ViewerTabPaneController viewerTabPaneController;

    public ViewerTabPane(final ViewerSidePanel viewerSidePanel)
    {
        // Initialise the main variables
        this.viewerSidePanel = viewerSidePanel;

        // Controller responsible for handling selection
        viewerTabPaneController = new ViewerTabPaneController(this);

        // Init the tab pane controller
        setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        addChangeListener(viewerTabPaneController);
    }

    public ViewerSidePanel getViewerSidePanel()
    {
        return viewerSidePanel;
    }

    public ViewerTabPaneController getViewerTabPaneController()
    {
        return viewerTabPaneController;
    }

    public void displayObjectTabPanel(final Object tabObject, final Object sideObject,
                                      final boolean open)
    {
        // Check if there is currently a tab open with the object
        final int existingTabIndex = getExistingTabObjectIndex(tabObject);
        if (existingTabIndex != -1) {
            setSelectedIndex(existingTabIndex);
            return;
        }

        // Create a panel for the object
        final JComponent objectPanel = createObjectTabPanel(tabObject);
        if (objectPanel == null) {
            JOptionPane.showMessageDialog(
                    MainWindow.getInstance(),
                    String.format(OBJECT_PANEL_NOT_FOUND_MESSAGE, tabObject.toString()),
                    OBJECT_PANEL_NOT_FOUND_TITLE,
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // Display the tab on the viewer, and show it in the side viewer
        showObjectTabPanel(objectPanel, createObjectTab(tabObject, sideObject), open);
        viewerSidePanel.displayObjectSidePanel(sideObject, open);
    }

    public int getExistingTabObjectIndex(final Object tabObject)
    {
        if (tabObject == null)
            return -1;

        // Get the index of the tab object
        for (int tab = 0; tab < getTabCount(); ++tab) {
            if (((TabPanel) getTabComponentAt(tab)).getTabObject() == tabObject)
                return tab;
        }

        return -1;
    }

    public int getNumTabsReferencingSideViewObject(final Object sideObject)
    {
        if (sideObject == null)
            return -1;

        // Calculate the number of side objects
        int tab = 0, numSideObjects = 0;
        for (; tab < getTabCount(); ++tab) {
            if (((TabPanel) getTabComponentAt(tab)).getSideObject() == sideObject)
                ++numSideObjects;
        }

        return numSideObjects;
    }

    private JComponent createObjectTabPanel(final Object object)
    {
        if (object == null)
            return null;

        // Create the appropriate panel based on the provided object
        if (object instanceof Signal)
            return JFreeChartUtils.createXYLineChartPanel(
                    JFreeChartUtils.createSignalXYSeriesCollection((Signal) object, SIGNAL_SERIES_NAME),
                    object.toString(),
                    SIGNAL_CHART_X_AXIS_NAME,
                    SIGNAL_CHART_Y_AXIS_NAME
            );
        else if (object instanceof MagnitudeSpectrum)
            return JFreeChartUtils.createHistogramPanel(
                    JFreeChartUtils.createMagnitudeSpectrumHistogramDataset((MagnitudeSpectrum) object, MAGNITUDE_SERIES_NAME),
                    object.toString(),
                    MAGNITUDE_CHART_X_AXIS_NAME,
                    MAGNITUDE_CHART_Y_AXIS_NAME
            );
        else if (object instanceof PhaseSpectrum)
            return null;
        else if (object instanceof AutoCorrelation)
            return JFreeChartUtils.createXYLineChartPanel(
                    JFreeChartUtils.createCorrelationXYSeriesCollection((SignalCorrelation) object, AUTO_CORRELATION_SERIES_NAME),
                    object.toString(),
                    CORRELATION_CHART_X_AXIS_NAME,
                    CORRELATION_CHART_Y_AXIS_NAME
            );
        else if (object instanceof CrossCorrelation)
            return JFreeChartUtils.createXYLineChartPanel(
                    JFreeChartUtils.createCorrelationXYSeriesCollection((SignalCorrelation) object, CROSS_CORRELATION_SERIES_NAME),
                    object.toString(),
                    CORRELATION_CHART_X_AXIS_NAME,
                    CORRELATION_CHART_Y_AXIS_NAME
            );
        return null;
    }

    private TabPanel createObjectTab(final Object tabObject, final Object structureObject)
    {
        final TabPanel objectTab = new TabPanel(tabObject, structureObject);
        objectTab.getCloseButton().addActionListener(viewerTabPaneController);
        return objectTab;
    }

    private void showObjectTabPanel(final JComponent objectPanel, final TabPanel objectTab,
                                    final boolean open)
    {
        addTab(null, objectPanel);
        setTabComponentAt(getTabCount() - 1, objectTab);
        if (open)
            setSelectedIndex(getTabCount() - 1);
    }

    public synchronized void closeObjectTabPanel(final Object object)
    {
        if (object == null)
            return;

        // If the object exists, remove it from the tab
        final int existingTabIndex = getExistingTabObjectIndex(object);
        if (existingTabIndex == -1)
            return;
        closeObjectTabPanel(existingTabIndex);
    }

    public synchronized void closeObjectTabPanel(final int existingTabIndex)
    {
        // Get the tab that correlates to the index
        TabPanel tabPanel = (TabPanel) getTabComponentAt(existingTabIndex);
        if (tabPanel == null)
            return;

        // Remove the tab from the viewer tab panel
        removeTabAt(existingTabIndex);

        // If there is one or less side views open, close it!
        final int existingSideViews = getNumTabsReferencingSideViewObject(tabPanel.getSideObject());
        if (existingSideViews > 1)
            return;
        viewerSidePanel.closeObjectStructurePanels(tabPanel.getSideObject());
    }
}
