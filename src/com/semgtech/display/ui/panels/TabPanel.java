package com.semgtech.display.ui.panels;

import com.semgtech.display.ui.buttons.TabCloseButton;

import javax.swing.*;

public class TabPanel extends JPanel
{

    private Object tabObject;
    private TabCloseButton tabCloseButton;

    public TabPanel(final Object tabObject)
    {
        this.tabObject = tabObject;
        initTabPanel();
    }

    private void initTabPanel()
    {
        // Create the tab label
        final JLabel tabNameLabel = new JLabel(String.format("%s  ", tabObject.toString()));
        tabNameLabel.setBorder(BorderFactory.createEmptyBorder());

        // Create the close button for the tab
        tabCloseButton = new TabCloseButton();

        // Create the tab panel itself
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder());
        add(tabNameLabel);
        add(tabCloseButton);
    }

    public Object getTabObject()
    {
        return tabObject;
    }

    public TabCloseButton getTabCloseButton()
    {
        return tabCloseButton;
    }
}
