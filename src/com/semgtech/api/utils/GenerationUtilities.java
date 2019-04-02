package com.semgtech.api.utils;

import com.semgtech.api.utils.vector.Vector3d;

import java.util.Random;

public class GenerationUtilities
{

    public static Vector3d nextPosition(final Vector3d regionCentre, final double regionRadius,
                                        final double componentRadius, final double componentZCoordinate)
    {
        return nextPosition(regionCentre.getX(), regionCentre.getY(), regionRadius,
                componentRadius, componentZCoordinate);
    }

    public static Vector3d nextPosition(final double regionCentreX, final double regionCentreY,
                                        final double regionRadius, final double componentRadius,
                                        final double componentZCoordinate)
    {
        final double r = (regionRadius - componentRadius) * Math.sqrt(Math.random()),
                theta = Math.random() * 2 * Math.PI;

        return new Vector3d(
                regionCentreX + r * Math.cos(theta),
                regionCentreY + r * Math.sin(theta),
                componentZCoordinate
        );
    }

    public static double nextStartingTime(final double min, final double max,
                                          final double mean)
    {
        final double random = min + (max - min) * Math.random();
        return random / mean;
    }
}
