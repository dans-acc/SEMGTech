package com.semgtech.api.simulation.anatomy;

import com.semgtech.api.utils.vector.Vector3d;

import java.util.ArrayList;
import java.util.List;

public abstract class AnatomicComposite<T extends AnatomicComponent>
    extends AnatomicComponent
{

    protected List<T> components;

    protected AnatomicComposite(final String name, final Vector3d position,
                                final double radius)
    {
        super(name, position, radius);
        this.components = new ArrayList<>();
    }

    public List<T> getComponents()
    {
        return components;
    }

    public int getNumComponents()
    {
        return components.size();
    }

    public int getTotalNumComponents()
    {
        int totalNumComponents = components.size();
        for (final T component : components) {
            if (component instanceof AnatomicComposite) {
                totalNumComponents += ((AnatomicComposite) component).getTotalNumComponents();
            }
        }
        return totalNumComponents;
    }
}
