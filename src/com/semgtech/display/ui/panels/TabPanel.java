package com.semgtech.display.ui.panels;

import com.semgtech.display.ui.buttons.TabCloseButton;

import javax.swing.*;

public class TabPanel extends JPanel
{

    private static final String TAB_PANEL_NAME = "%s ";

    // The two objects the tab is responsible for displaying; main panel and side panel, respectively
    private Object tabObject;
    private Object sideObject;

    // The button used for closing the tab
    private TabCloseButton closeButton;

    public TabPanel(final Object tabObject, final Object sideObject)
    {
        this.tabObject = tabObject;
        this.sideObject = sideObject;

        // Create the tab label
        final JLabel tabNameLabel = new JLabel(String.format(TAB_PANEL_NAME, tabObject.toString()));
        tabNameLabel.setBorder(BorderFactory.createEmptyBorder());
        setAlignmentX(LEFT_ALIGNMENT);

        // Create the close button for the tab
        closeButton = new TabCloseButton();
        closeButton.setAlignmentX(RIGHT_ALIGNMENT);

        // Create the tab panel itself
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder());
        add(tabNameLabel);
        add(closeButton);
    }

    public Object getTabObject()
    {
        return tabObject;
    }

    public Object getSideObject()
    {
        return sideObject;
    }

    public TabCloseButton getCloseButton()
    {
        return closeButton;
    }
}
