package com.semgtech.display.controllers;

import com.semgtech.display.ui.trees.EventTree;
import com.semgtech.display.windows.MainWindow;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EventTreeController implements MouseListener, ActionListener
{

    private EventTree eventTree;

    public EventTreeController(final EventTree eventTree)
    {
        this.eventTree = eventTree;
    }

    @Override
    public void mouseClicked(final MouseEvent mouseEvent) { }
    @Override
    public void mousePressed(final MouseEvent mouseEvent)
    {
        eventTree.getEventTreePopup().show(
                eventTree,
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
    @Override
    public void actionPerformed(final ActionEvent actionEvent) { }

}
