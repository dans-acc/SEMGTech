package com.semgtech.display.models;

import com.semgtech.api.simulation.anatomy.AnatomicComponent;
import com.semgtech.api.simulation.anatomy.AnatomicComposite;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class AnatomicTreeModel implements TreeModel
{

    private AnatomicComponent root;

    public AnatomicTreeModel(final AnatomicComponent root)
    {
        this.root = root;
    }

    @Override
    public Object getRoot()
    {
        return root;
    }

    /**
     * Method performs appropriate checks to determine whether the anatomic component is
     * a leaf within the encompassing tree structure.
     *
     * @param anatomicComponentNode - the anatomic component being checked.
     * @return - true or false based on whether or not the component is a lief in the tree structure.
     */
    @Override
    public boolean isLeaf(final Object anatomicComponentNode)
    {
        if (anatomicComponentNode instanceof AnatomicComposite)
            return ((AnatomicComposite) anatomicComponentNode).getComponents().isEmpty();
        return anatomicComponentNode instanceof AnatomicComponent;
    }

    /**
     * Makes appropriate checks. If
     *
     * @param parentAnatomicComponent
     * @param index -
     * @return - an instance of an anatomic component or composite. If no component is present at the
     * index, null is returned.
     */
    @Override
    public Object getChild(final Object parentAnatomicComponent,
                           final int index)
    {
        if (parentAnatomicComponent == null)
            return null;
        else if (!(parentAnatomicComponent instanceof AnatomicComposite))
            return null;
        final AnatomicComposite anatomicComposite = (AnatomicComposite) parentAnatomicComponent;
        if (anatomicComposite.getComponents().isEmpty())
            return null;
        return anatomicComposite.getComponents().get(index);
    }

    @Override
    public int getChildCount(final Object parentAnatomicComponent)
    {
        if (!(parentAnatomicComponent instanceof AnatomicComposite))
            return -1;
        final AnatomicComposite anatomicComposite = (AnatomicComposite) parentAnatomicComponent;
        return anatomicComposite.getComponents().size();
    }

    @Override
    public int getIndexOfChild(final Object parentAnatomicComponent,
                               final Object childAnatomicComponent)
    {
        if (parentAnatomicComponent == null || childAnatomicComponent == null)
            return -1;
        else if (!(parentAnatomicComponent instanceof AnatomicComposite))
            return -1;
        final AnatomicComposite anatomicComposite = (AnatomicComposite) parentAnatomicComponent;
        if (!anatomicComposite.getComponents().contains(childAnatomicComponent))
            return -1;
        return anatomicComposite.getComponents().indexOf(childAnatomicComponent);
    }

    @Override
    public void valueForPathChanged(final TreePath path, final Object value) { }
    @Override
    public void addTreeModelListener(final TreeModelListener l) {}
    @Override
    public void removeTreeModelListener(final TreeModelListener l) {}

}
