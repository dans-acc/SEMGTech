package com.semgtech.display.controllers;

import com.semgtech.display.ui.buttons.TabCloseButton;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TabCloseButtonController
        implements MouseListener
{

    // The button for which this controller exists
    private TabCloseButton tabCloseButton;

    /**
     * Constructor used to instantiate the button controller.
     *
     * @param tabCloseButton - the button for which this controller is responsible for manipulating.
     */
    public TabCloseButtonController(final TabCloseButton tabCloseButton)
    {
        this.tabCloseButton = tabCloseButton;
    }

    @Override
    public void mouseClicked(final MouseEvent mouseEvent) { }
    @Override
    public void mousePressed(final MouseEvent mouseEvent) { }
    @Override
    public void mouseReleased(final MouseEvent mouseEvent) { }

    /**
     * Changes the colour of the button text to provide a form of feedback to the user.
     * Hovering over the button results in the buttons text being changed to red.
     *
     * @param mouseEvent - I/O event provided by the JVM; called upon the mouse hovering over the button.
     */
    @Override
    public void mouseEntered(final MouseEvent mouseEvent)
    {
        tabCloseButton.setForeground(Color.RED);
    }

    /**
     * Changes the colour of the button text to provide a form of feedback to the user.
     * Hovering away from the button results in the red colour being dismissed; the colour
     * of the button text is set to black.
     *
     * @param mouseEvent - I/O event provided by the JVM; called upon the mouse hovering away from the button.
     */
    @Override
    public void mouseExited(final MouseEvent mouseEvent)
    {
        tabCloseButton.setForeground(Color.BLACK);
    }

}
