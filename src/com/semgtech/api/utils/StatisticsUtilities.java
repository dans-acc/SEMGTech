package com.semgtech.api.utils;

import java.util.Arrays;

public class StatisticsUtilities
{

    public static int nextPoisson(final double lambda)
    {
        final double l = Math.exp(-lambda);
        double p = 1.0;
        int k = 0;
        do {
            k++;
            p *= Math.random();
        } while (p > l);
        return k - 1;
    }

    public static int nextPoisson(final double min, final double max,
                                  final double lambda)
    {
        int k = 0;
        do {
            k = nextPoisson(lambda);
        } while (k < min || k > max);
        return k;
    }

    public static double nextGaussian()
    {
        double v1, v2, s;

        do {
            v1 = 2.0d * Math.random() - 1.0d;
            v2 = 2.0d * Math.random() - 1.0d;
            s = v1 * v1 + v2 * v2;
        } while (s >= 1.0d || s == 0.0d);

        s = Math.sqrt((-2.0d * Math.log(s)) / s);

        return v1 * s;
    }

    public static double nextGaussian(final double mean, final double std,
                                      final double min, final double max)
    {
        double random;
        do {
            random = mean + nextGaussian() * std;
        } while (random < min || random > max);
        return random;
    }

    public static double nextGaussian(final double mean, final double std)
    {
        return nextGaussian(mean, std, mean - std, mean + std);
    }

    public static double[] promoteToDouble(final float... floats)
    {
        if (floats == null)
            throw new NullPointerException("Float array is null");

        final double[] doubles = new double[floats.length];
        if (doubles.length == 0)
            return doubles;

        for (int elementIndex = 0; elementIndex < floats.length; ++elementIndex)
            doubles[elementIndex] = floats[elementIndex];

        return doubles;
    }

    public static float[] demoteToFloat(final double... doubles)
    {
        if (doubles == null)
            throw new NullPointerException("Double array is null");

        final float[] floats = new float[doubles.length];
        if (floats.length == 0)
            return floats;

        for (int elementIndex = 0; elementIndex < doubles.length; ++elementIndex)
            floats[elementIndex] = (float) (doubles[elementIndex]);

        return floats;
    }

    public static double mean(final double[] array)
    {
        double sum = 0;
        for (int index = 0; index < array.length; ++index)
            sum += array[index];
        return sum / (double) array.length;
    }

    public static float mean(final float[] array)
    {
        float sum = 0;
        for (int index = 0; index < array.length; ++index)
            sum += array[index];
        return sum / array.length;
    }

    public static double[] normalise(final double[] doubles)
    {
        if (doubles.length <= 0)
            throw new IllegalArgumentException("Provided double array is empty");

        // Compute the min, max and range
        final double min = Arrays.stream(doubles).min().getAsDouble(),
                max = Arrays.stream(doubles).max().getAsDouble(),
                range = max - min;

        // Normalise the double array
        final double[] normalised = new double[doubles.length];
        for (int index = 0; index < doubles.length; ++index)
            normalised[index] = (doubles[index] - min) / range;

        return normalised;
    }


    public static float[] normalise(final float[] floats)
    {
        if (floats.length <= 0)
            throw new IllegalArgumentException("Provided float array is empty");

        // Compute the min, max and range
        float min = 0, max = 0, range = 0;
        for (final float value : floats) {
            if (value < min)
                min = value;
            else if (value > max)
                max = value;
        }
        range = max - min;

        // Normalise the float array
        final float[] normalised = new float[floats.length];
        for (int index = 0; index < floats.length; ++index)
            normalised[index] = (floats[index] - min) / range;

        return normalised;
    }

}
