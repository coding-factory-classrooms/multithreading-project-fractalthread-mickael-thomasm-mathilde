package org.example.utils.fractals;

import java.util.ArrayList;
import java.util.List;

/**
 * Source: https://gist.github.com/dackerman/7bc682d41ce632602af9e1a9858c82fc
 */
public class Mandelbrot {
    private static final int MAX_ITERATIONS = 5000;
    private static final int MOVE_MULTIPLIER = 100;

    private final double xSkip;
    private final double ySkip;
    private final double x0;
    private final double y0;
    private final int pixelWidth;
    private final int pixelHeight;

    private final double zoom;

    public Mandelbrot(int pixelWidth, int pixelHeight, double startX, double endX, double startY, double endY, double zoom) {
        this.pixelWidth = pixelWidth;
        this.pixelHeight = pixelHeight;
        this.x0 = startX;
        this.xSkip = (endX - startX) / pixelWidth;
        this.y0 = startY;
        this.ySkip = (endY - startY) / pixelHeight;
        this.zoom = zoom;
    }

    public List<List<Double>> generatePixels(double moveX, double moveY) {
        List<List<Double>> pixels = new ArrayList<>();
        for (int y = 0; y < pixelHeight; y++) {
            List<Double> row = new ArrayList<>();
            for (int x = 0; x < pixelWidth; x++) {
                row.add(calculatePixel(x, y, moveX, moveY));
            }
            pixels.add(row);
        }
        return pixels;
    }

    public double calculatePixel(int pixelX, int pixelY, double moveX, double moveY) {
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
        return Math.log(iteration) / Math.log(MAX_ITERATIONS);
    }
}
