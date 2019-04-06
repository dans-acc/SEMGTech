package com.semgtech.display.ui.popups;

import com.semgtech.display.controllers.TreeController;

import javax.swing.*;

public abstract class PopupMenu extends JPopupMenu
{

    // The controller used to handle popup menu button I/O
    private TreeController controller;

    protected PopupMenu(final TreeController controller)
    {
        this.controller = controller;
        addMouseListener(controller);
        initMenuItems();
    }

    public TreeController getController()
    {
        return controller;
    }

    protected abstract void initMenuItems();

    /**
     * A convenient means of creating a new JMenu that's to be added to the
     * main popup menu; handles the creation, naming, and the addition of JMenuItems
     * (options).
     *
     * @param name - the name of the sub-menu.
     * @param components - the list of components that are to be added.
     * @return - a new instance of the JMenu will all provided parameters encompassed.
     */
    protected JMenu createMenu(final String name, final JComponent... components)
    {
        final JMenu menu = new JMenu(name);
        for (final JComponent component : components)
            menu.add(component);
        return menu;
    }

    /**
     * A convenient means of creating a new JMenuItem that's to be added to the
     * main popup menu; sets the name, listener and tooltip with one method call.
     *
     * @param name - the name of the menu item.
     * @param tooltip - the text displayed when the item is hovered over.
     * @return - a new instance of JMenuItem - with all parameters set.
     */
    protected JMenuItem createMenuItem(final String name, final String tooltip)
    {
        final JMenuItem menuItem = new JMenuItem(name);
        menuItem.addActionListener(controller);
        menuItem.setToolTipText(tooltip);
        return menuItem;
    }

    /**
     * Applies 'filters' to menu items to prevent users from selecting invalid /
     * inapplicable options within the popup menu.
     *
     * @param objects - an array of objects selected in the tree
     */
    public abstract void enableMenuItemsFor(final Object[] objects);

    /**
     * Applies reflection to an array of objects in order to determine whether or
     * not that are all of the same instance.
     *
     * @param clazz - The class against which we're checking all of the objects in the array.
     * @param objects - the array of objects we're checking.
     * @return true if all objects are of the clazz instance, false otherwise.
     */
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
