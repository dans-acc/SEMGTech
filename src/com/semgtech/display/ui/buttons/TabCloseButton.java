package com.semgtech.display.ui.buttons;

import com.semgtech.display.controllers.TabCloseButtonController;

import javax.swing.*;
import java.awt.*;

public class TabCloseButton extends JButton
{

    private static final String CLOSE_BUTTON_NAME = "x";
    private static final String CLOSE_BUTTON_TOOLTIP = "Click to close the tab";

    public TabCloseButton()
    {
        super(CLOSE_BUTTON_NAME);
        initTabCloseButton();
    }

    private void initTabCloseButton()
    {
        // Create the controller and register the events
        addMouseListener(new TabCloseButtonController(this));

        // Set various button properties
        setToolTipText(CLOSE_BUTTON_TOOLTIP);
        setMargin(new Insets(0, 0, 0, 0));
        setBorderPainted(false);
        setBorder(BorderFactory.createEmptyBorder());
        setOpaque(false);
    }
}
