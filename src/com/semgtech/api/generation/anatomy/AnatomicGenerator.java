package com.semgtech.api.generation.anatomy;

import com.semgtech.api.simulation.anatomy.AnatomicComponent;
import com.semgtech.api.utils.vector.Vector3d;

public interface AnatomicGenerator<T extends AnatomicComponent>
{

    T nextAnatomicComponent(final Vector3d position, final double radius);

}
