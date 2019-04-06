package com.semgtech.display.ui.buttons;

import com.semgtech.display.controllers.TabCloseButtonController;

import javax.swing.*;
import java.awt.*;

public class TabCloseButton extends JButton
{

    // The tabs close button name and tooltip
    private static final String CLOSE_BUTTON_NAME = "x";
    private static final String CLOSE_BUTTON_TOOLTIP = "Click to close the tab";

    public TabCloseButton()
    {
        super(CLOSE_BUTTON_NAME);
        setToolTipText(CLOSE_BUTTON_TOOLTIP);
        addMouseListener(new TabCloseButtonController(this));
        setMargin(new Insets(0, 0, 0, 0));
        setBorderPainted(false);
        setBorder(BorderFactory.createEmptyBorder());
        setOpaque(false);
    }
}
