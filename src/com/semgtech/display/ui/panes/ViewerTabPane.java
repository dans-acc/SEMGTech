package com.semgtech.display.ui.panes;

import com.semgtech.api.utils.signals.LoggedSignal;
import com.semgtech.api.utils.signals.Signal;
import com.semgtech.api.utils.signals.correlations.AutoCorrelation;
import com.semgtech.api.utils.signals.correlations.CrossCorrelation;
import com.semgtech.api.utils.signals.correlations.SignalCorrelation;
import com.semgtech.api.utils.signals.spectrum.MagnitudeSpectrum;
import com.semgtech.api.utils.signals.spectrum.PhaseSpectrum;
import com.semgtech.display.controllers.ViewerTabPaneController;
import com.semgtech.display.ui.panels.TabPanel;
import com.semgtech.display.utils.JFreeChartUtils;
import com.semgtech.display.windows.MainWindow;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;

import javax.swing.*;

public class ViewerTabPane extends JTabbedPane
{

    private static final String OBJECT_PANEL_NOT_FOUND_MESSAGE = "Failed to display: %s";
    private static final String OBJECT_PANEL_NOT_FOUND_TITLE = "Display Error";

    private static final String SIGNAL_SERIES_NAME = "%s Samples";
    private static final String SIGNAL_CHART_X_AXIS_NAME = "Time";
    private static final String SIGNAL_CHART_Y_AXIS_NAME = "mV";

    private static final String SIGNAL_MAGNITUDE_SERIES_NAME = "Magnitude";
    private static final String SIGNAL_MAGNITUDE_CHART_X = "Frequency";
    private static final String SIGNAL_MAGNITUDE_CHART_Y = "Magnitude";

    private static final String SIGNAL_CORRELATION_CHART_X_AXIS_NAME = "Delay (Lag)";
    private static final String SIGNAL_CORRELATION_CHART_Y_AXIS_NAME = "Correlation (Normalised)";
    private static final String SIGNAL_AUTO_CORRELATION_SERIES_NAME = "Auto-Correlation Coefficients";
    private static final String SIGNAL_CROSS_CORRELATION_SERIES_NAME = "Cross-Correlation Coefficients";

    private ViewerTabPaneController viewerTabPaneController;

    public ViewerTabPane()
    {
        initViewerPanel();
    }

    private void initViewerPanel()
    {
        // Create the viewer controller
        viewerTabPaneController = new ViewerTabPaneController(this);

        // Set tab properties
        setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }

    public synchronized void displayObjectPanel(final Object object, final boolean open)
    {
        // Check if there is currently a tab open with the object
        final int existingTabIndex = getExistingObjectTabIndex(object);
        if (existingTabIndex != -1) {
            setSelectedIndex(existingTabIndex);
            return;
        }

        // Create a panel for the object
        final JComponent objectPanel = createObjectPanel(object);
        if (objectPanel == null) {
            JOptionPane.showMessageDialog(
                    MainWindow.getInstance(),
                    String.format(OBJECT_PANEL_NOT_FOUND_MESSAGE, object.toString()),
                    OBJECT_PANEL_NOT_FOUND_TITLE,
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // Display the tab on the viewer
        showObject(objectPanel, createObjectTab(object), open);
    }

    private int getExistingObjectTabIndex(final Object object)
    {
        if (object == null)
            return -1;

        // Find the tab index of the already open object
        for (int tab = 0; tab < getTabCount(); ++tab) {
            if (((TabPanel) getTabComponentAt(tab)).getTabObject() == object)
                return tab;
        }
        return -1;
    }

    private JComponent createObjectPanel(final Object object)
    {
        if (object == null)
            return null;

        // Create the appropriate panel based on the provided object
        if (object instanceof Signal)
            return createSignalPanel((Signal) object);
        else if (object instanceof MagnitudeSpectrum)
            return createMagnitudeSpectrumPanel((MagnitudeSpectrum) object);
        else if (object instanceof PhaseSpectrum)
            return createPhaseSpectrumPanel((PhaseSpectrum) object);
        else if (object instanceof AutoCorrelation)
            return createSignalCorrelationPanel((SignalCorrelation) object, SIGNAL_AUTO_CORRELATION_SERIES_NAME);
        else if (object instanceof CrossCorrelation)
            return createSignalCorrelationPanel((SignalCorrelation) object, SIGNAL_CROSS_CORRELATION_SERIES_NAME);

        return null;
    }

    private TabPanel createObjectTab(final Object object)
    {
        final TabPanel objectTab = new TabPanel(object);
        objectTab.getTabCloseButton().addActionListener(viewerTabPaneController);
        return objectTab;
    }

    private void showObject(final JComponent objectPanel, final TabPanel objectTab,
                                  final boolean open)
    {
        addTab(null, objectPanel);
        setTabComponentAt(getTabCount() - 1, objectTab);
        if (open)
            setSelectedIndex(getTabCount() - 1);
    }

    public synchronized void closeObject(final Object object)
    {
        if (object == null)
            return;
        final int existingTabIndex = getExistingObjectTabIndex(object);
        if (existingTabIndex == -1)
            return;
        removeTabAt(existingTabIndex);
    }

    private JComponent createLoggedSignalPanel(final LoggedSignal loggedSignal)
    {
        return null;
    }

    private JComponent createSignalPanel(final Signal signal)
    {
        final JFreeChart chart = ChartFactory.createXYLineChart(
                signal.getName(),
                SIGNAL_CHART_X_AXIS_NAME,
                SIGNAL_CHART_Y_AXIS_NAME,
                JFreeChartUtils.createSignalXYSeriesCollection(signal, String.format(SIGNAL_SERIES_NAME, signal.getName())),
                PlotOrientation.VERTICAL,
                true, true, false
        );
        return new ChartPanel(chart);
    }

    private JComponent createMagnitudeSpectrumPanel(final MagnitudeSpectrum magnitudeSpectrum)
    {
        final JFreeChart chart = ChartFactory.createHistogram(
                magnitudeSpectrum.getName(),
                SIGNAL_MAGNITUDE_CHART_X,
                SIGNAL_MAGNITUDE_CHART_Y,
                JFreeChartUtils.createMagnitudeSpectrumHistogramDataset(magnitudeSpectrum, SIGNAL_MAGNITUDE_SERIES_NAME),
                PlotOrientation.VERTICAL,
                true, true, false
        );
        return new ChartPanel(chart);
    }

    private JComponent createPhaseSpectrumPanel(final PhaseSpectrum phaseSpectrum)
    {
        return null;
    }

    private JComponent createSignalCorrelationPanel(final SignalCorrelation signalCorrelation,
                                                    final String seriesName)
    {
        final JFreeChart chart = ChartFactory.createXYLineChart(
                signalCorrelation.getName(),
                SIGNAL_CORRELATION_CHART_X_AXIS_NAME,
                SIGNAL_CORRELATION_CHART_Y_AXIS_NAME,
                JFreeChartUtils.createCorrelationXYSeriesCollection(signalCorrelation, seriesName),
                PlotOrientation.VERTICAL,
                true, true, false
        );
        return new ChartPanel(chart);
    }
}
