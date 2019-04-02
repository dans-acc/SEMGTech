package com.semgtech.display.controllers;

import com.semgtech.display.ui.buttons.TabCloseButton;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TabCloseButtonController implements MouseListener
{

    private TabCloseButton tabCloseButton;

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

    @Override
    public void mouseEntered(final MouseEvent mouseEvent)
    {
        tabCloseButton.setForeground(Color.RED);
    }

    @Override
    public void mouseExited(final MouseEvent mouseEvent)
    {
        tabCloseButton.setForeground(Color.BLACK);
    }

}
