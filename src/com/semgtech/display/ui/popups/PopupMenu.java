package com.semgtech.display.ui.popups;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

public abstract class PopupMenu<T extends MouseListener & ActionListener> extends JPopupMenu
{

    private T controller;

    protected PopupMenu(final T controller)
    {
        this.controller = controller;
        addMouseListener(controller);
        initMenuItems();
    }

    public T getController()
    {
        return controller;
    }

    protected abstract void initMenuItems();


    protected JMenu createMenu(final String name, final JComponent... components)
    {
        final JMenu menu = new JMenu(name);
        for (final JComponent component : components)
            menu.add(component);
        return menu;
    }

    protected JMenuItem createMenuItem(final String name, final String tooltip)
    {
        final JMenuItem menuItem = new JMenuItem(name);
        menuItem.addActionListener(controller);
        menuItem.setToolTipText(tooltip);
        return menuItem;
    }


    public abstract void enableMenuItemsFor(final Object[] selectedObjects);

    protected boolean areOfSameInstance(final Class clazz, final Object[] objects)
    {
        if (objects == null)
            return false;
        for (Object object : objects) {
            if (!clazz.isInstance(object))
                return false;
        }
        return true;
    }
}
