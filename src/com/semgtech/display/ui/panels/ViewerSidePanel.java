package com.semgtech.display.ui.panels;

import com.semgtech.api.simulation.AnatomicSimulator;
import com.semgtech.api.utils.signals.LoggedSignal;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ViewerSidePanel extends JPanel
{

    // Create a default side panel for the side view
    private class DefaultSidePanel extends JPanel
    {
        private static final String DEFAULT_SIDE_PANEL_NAME = "Side View";
        private static final String DEFAULT_SIDE_PANEL_TOOLTIP = "Select a tab or an item from the Project Explorer.";

        public DefaultSidePanel()
        {
            // Create an empty side panel view
            final JPanel empty = new JPanel();
            empty.setBorder(BorderFactory.createLoweredBevelBorder());
            empty.setToolTipText(DEFAULT_SIDE_PANEL_TOOLTIP);
            empty.setBackground(Color.WHITE);

            // Create the default side-panel view
            setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createEmptyBorder(),
                    DEFAULT_SIDE_PANEL_NAME,
                    TitledBorder.LEFT,
                    TitledBorder.TOP
            ));
            setLayout(new BorderLayout());
            add(empty, BorderLayout.CENTER);
        }
    }

    private List<SideView> sideViews;
    private DefaultSidePanel defaultSidePanel;

    protected ViewerSidePanel()
    {
        sideViews = new ArrayList<>();

        // Set various side panel properties
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder());

        // Create and set the default side panel view
        defaultSidePanel = new DefaultSidePanel();
        showObjectSidePanel(defaultSidePanel);
    }

    public List<SideView> getSideViews()
    {
        return sideViews;
    }

    public DefaultSidePanel getDefaultSidePanel()
    {
        return defaultSidePanel;
    }

    public void displayObjectSidePanel(final Object object, final boolean open)
    {
        // Get the side view if one already exists
        SideView objectSideView = getExistingSideView(object);
        if (objectSideView != null) {
            if (open)
                showObjectSidePanel(objectSideView);
            return;
        }

        // Create a new side view for the object
        objectSideView = createObjectSidePanel(object);
        if (objectSideView == null) {
            showObjectSidePanel(defaultSidePanel);
            return;
        }

        // Add the side view to the list of side views i.e. cache it.
        sideViews.add(objectSideView);

        // Display the object structure view
        showObjectSidePanel(objectSideView);
    }

    public SideView getExistingSideView(final Object object)
    {
        // If the object is null, create a default panel for the structure view
        if (object == null)
            return null;

        // Find the existing view
        for (final SideView sideView : sideViews) {
            if (sideView.getObject() == object)
                return sideView;
        }
        return null;
    }

    private SideView createObjectSidePanel(final Object object)
    {
        if (object == null)
            return null;
        else if (object instanceof LoggedSignal)
            return new EventSidePanel((LoggedSignal) object);
        else if(object instanceof AnatomicSimulator)
            return new AnatomicSidePanel((AnatomicSimulator) object);

        return null;
    }

    private void showObjectSidePanel(final JComponent sideView)
    {
        // Set the side panels side view
        removeAll();
        add(sideView, BorderLayout.CENTER);

        // Revalidate and repaint the new side view
        sideView.revalidate();
        sideView.repaint();

        // Revalidate and repaint the side view container
        revalidate();
        repaint();
    }

    public void closeObjectStructurePanels(final Object object)
    {
        if (object == null)
            return;

        // Get the objects side view
        final SideView sideView = getExistingSideView(object);
        if (sideView == null)
            return;
        sideViews.remove(sideView);

        // If there are no more side views present, set the default side view
        if (!sideViews.isEmpty())
            return;
        showObjectSidePanel(defaultSidePanel);
    }
}
