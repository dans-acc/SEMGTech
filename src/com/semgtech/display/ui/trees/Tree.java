package com.semgtech.display.ui.trees;

import com.semgtech.display.controllers.TreeController;
import com.semgtech.display.ui.popups.PopupMenu;

import javax.swing.*;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public abstract class Tree<T extends TreeModel, U extends PopupMenu> extends JTree
{

    protected T model;
    protected TreeController controller;
    protected U popup;

    @Override
    public T getModel()
    {
        return model;
    }

    public TreeController getController()
    {
        return controller;
    }

    public U getPopup()
    {
        return popup;
    }

    protected abstract void initTree();

    /**
     * Add a child object to the specified TreePath path.
     *
     * @param path - the path under which the new child object is to be added.
     * @param child - the object that's to be added to the ree.
     * @param children - whether or not the node accepts children
     * @param expand - whether or not the parent path should be expanded post addition of the new object.
     */
    public void addChild(final TreePath path, final Object child,
                         final boolean children, final boolean expand)
    {
        addChild(path.getLastPathComponent(), child, children);
        if (!expand)
            return;
        expandPath(path);
    }

    public abstract void addChild(final Object parent, final Object child,
                                  final boolean children);

    public abstract void removeChild(final TreePath path);
}
