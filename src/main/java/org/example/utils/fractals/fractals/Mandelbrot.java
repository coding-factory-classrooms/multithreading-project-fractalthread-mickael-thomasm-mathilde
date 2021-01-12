package org.example.utils.fractals.fractals;

import org.example.utils.fractals.fractals.properties.FractalMove;
import org.example.utils.fractals.fractals.properties.FractalSize;

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
    private final FractalSize size;
    private final FractalMove fractalMove;
    private final int y;

    public Mandelbrot(int y, FractalSize size, FractalMove fractalMove) {
        this.y = y;
        this.size = size;
        this.x0 = -1.5;
        this.xSkip = (0.5 - this.x0) / size.width;
        this.y0 = -1.0;
        this.ySkip = (1.0 - this.y0) / size.height;
        this.fractalMove = fractalMove;
    }

    @Override
    public int calculatePixel(int pixelX, int pixelY, double moveX, double moveY, double zoom) {
        double x = (x0 + (pixelX + (moveX * MOVE_MULTIPLIER)) * xSkip) / zoom;
        double y = (y0 + (pixelY + (moveY * MOVE_MULTIPLIER)) * ySkip) / zoom;
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
        for (int x = 0; x < size.width; x++) {
            row.add(calculatePixel(x, y, fractalMove.moveX, fractalMove.moveY, fractalMove.zoom));
        }
        return row;
    }
}
