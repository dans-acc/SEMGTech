package com.semgtech.display.controllers;

import com.semgtech.display.ui.popups.PopupMenu;
import com.semgtech.display.ui.trees.Tree;

import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public abstract class TreeController<T extends TreeModel, U extends PopupMenu, V extends Tree<T, U>>
        implements MouseListener, ActionListener
{

    // The tree for which the TreeController instance exists.
    protected V tree;

    /**
     * Constructor used to instantiate a new TreeController object
     * @param tree - the tree for which this controller exists
     */
    protected TreeController(final V tree)
    {
        this.tree = tree;
    }

    /**
     * @return the tree for which this controller exists.
     */
    public V getTree()
    {
        return tree;
    }

    /**
     * Appropriate checks are made in order to determine whether or not a valid object
     * was double clicked; method only permits double-clicks. If passed, the click on
     * the particular object is handled.
     *
     * @param mouseEvent - I/O event in the form of a mouse click; provided by the JVM
     */
    @Override
    public void mouseClicked(final MouseEvent mouseEvent)
    {
        if (mouseEvent.getSource() != tree)
            return;

        // Get the selected row within the explorer tree
        final int selectedRow = tree.getRowForLocation(mouseEvent.getX(), mouseEvent.getY());
        final TreePath selectedPath = tree.getPathForRow(selectedRow);
        if (selectedRow == -1 || selectedPath == null) {
            tree.clearSelection();
            return;
        }

        // Deal only with double clicks
        if (mouseEvent.getClickCount() == 2)
            return;

        // Get the node that was clicked, and handle the click
        final Object selectedNode = selectedPath.getLastPathComponent();
        if (selectedNode == null)
            return;
        handleClick(selectedNode);
    }

    protected abstract void handleClick(final Object object);

    /**
     * Trees are only able to handle popup menus with respect to presses. Therefore,
     * if the user has triggered the popup menu, we enable / disable menu items based
     * on the selected set of tree paths / nodes.
     *
     * @param mouseEvent - mouse I/O provided by the JVM.
     */
    @Override
    public void mousePressed(final MouseEvent mouseEvent)
    {
        if (!mouseEvent.isPopupTrigger())
            return;
        final Object[] selectedObjects = getSelectedObjects(tree.getSelectionPaths());
        tree.getPopup().enableMenuItemsFor(selectedObjects);
        tree.getPopup().show(tree, mouseEvent.getX(), mouseEvent.getY());
    }

    @Override
    public void mouseReleased(final MouseEvent mouseEvent) { }
    @Override
    public void mouseEntered(final MouseEvent mouseEvent) { }
    @Override
    public void mouseExited(final MouseEvent mouseEvent) { }

    /**
     * Handles the presses of buttons / menu items from within the trees popup menu.
     * The tree is repainted / validated based on whether or not the event manipulated
     * its structure.
     *
     * @param actionEvent - I/O provided by the JVM
     */
    @Override
    public void actionPerformed(final ActionEvent actionEvent)
    {
        final TreePath[] selectedPaths = tree.getSelectionPaths();

        // Based on the selected option, and nodes, carry out an appropriate task.
        final boolean updated = handleAction(
                actionEvent.getSource(),
                tree.getPopup(),
                selectedPaths,
                getSelectedObjects(selectedPaths)
        );

        // If an update occurred, update and repaint the tree
        if (!updated)
            return;
        tree.updateUI();
        tree.repaint();
    }

    /**
     * Provided an array of TreePaths, the method iteratively obtains the
     * corresponding objects.
     *
     * @param paths - the nodes that are selected in the tree.
     * @return an array of objects for which the nodes have been selected.
     */
    protected Object[] getSelectedObjects(final TreePath... paths)
    {
        if (paths == null || paths.length == 0)
            return null;
        final Object[] selectedObjects = new Object[paths.length];
        for (int index = 0; index < paths.length; ++index)
            selectedObjects[index] = paths[index].getLastPathComponent();
        return selectedObjects;
    }

    /**
     * Method handles the selection of a particular item from the trees popup menu.
     *
     * @param source - the source JComponent that triggered the event.
     * @param popupMenu - the popup menu from which the event was triggered.
     * @param paths - an array of selected tree paths.
     * @param objects - an array of selected objects; corresponding to the selected paths.
     * @return - true or false based on whether or not the tree has been manipulated.
     */
    protected abstract boolean handleAction(final Object source, final U popupMenu,
                                            final TreePath[] paths, final Object[] objects);
}
