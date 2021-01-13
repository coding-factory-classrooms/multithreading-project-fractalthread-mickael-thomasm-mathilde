package org.example.utils.fractals.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Source: https://gist.github.com/dackerman/7bc682d41ce632602af9e1a9858c82fc
 */
public class Mandelbrot implements Fractal, Callable<List<Integer>>{
    public static int MAX_ITERATIONS = 5000;
    private static final int MOVE_MULTIPLIER = 100;

    private final double xSkip;
    private final double ySkip;
    private final double x0;
    private final double y0;
    private final FractalConf fractalConf;
    private final int y;

    public Mandelbrot(int y, FractalConf configuration) {
        this.y = y;
        this.fractalConf = configuration;
        this.x0 = -1.5;
        this.xSkip = (0.5 - this.x0) / configuration.width;
        this.y0 = -1.0;
        this.ySkip = (1.0 - this.y0) / configuration.height;
    }

    @Override
    public int calculatePixel(int pixelX, int pixelY) {
        double x = (x0 + (pixelX + (fractalConf.moveX * MOVE_MULTIPLIER)) * xSkip) / fractalConf.zoom;
        double y = (y0 + (pixelY + (fractalConf.moveY * MOVE_MULTIPLIER)) * ySkip) / fractalConf.zoom;
        double ix = 0;
        double iy = 0;
        int iteration = 0;
        while (ix * ix + iy * iy < 4 && iteration < MAX_ITERATIONS) {
            double xtemp = ix * ix - iy * iy + x;
            iy = 2 * ix * iy + y;
            ix = xtemp;
            iteration++;
        }
        return iteration;
    }

    @Override
    public List<Integer> call() {
        List<Integer> row = new ArrayList<>();
        for (int x = 0; x < fractalConf.width; x++) {
            row.add(calculatePixel(x, y));
        }
        return row;
    }
}
