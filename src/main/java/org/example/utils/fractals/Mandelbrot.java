package org.example.utils.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Source: https://gist.github.com/dackerman/7bc682d41ce632602af9e1a9858c82fc
 */
public class Mandelbrot implements Callable<List<Integer>>{
    public static int MAX_ITERATIONS = 5000;
    private static final int MOVE_MULTIPLIER = 100;

    private final double xSkip;
    private final double ySkip;
    private final double x0;
    private final double y0;
    private final Size size;
    private final Move move;

    private final double zoom;
    private final int y;

    public Mandelbrot(int y, Size size, double startX, double endX, double startY, double endY, Move move, double zoom) {
        this.y = y;
        this.size = size;
        this.x0 = startX;
        this.move = move;
        this.xSkip = (endX - startX) / size.width;
        this.y0 = startY;
        this.ySkip = (endY - startY) / size.height;
        this.zoom = zoom;
    }

    protected int calculatePixel(int pixelX, int pixelY, double moveX, double moveY) {
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
    public List<Integer> call() throws Exception {
        List<Integer> row = new ArrayList<>();
        for (int x = 0; x < size.width; x++) {
            row.add(calculatePixel(x, y, move.moveX, move.moveY));
        }
        return row;
    }

    public static class Size {
        public final int width;
        public final int height;

        public Size(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }

    public static class Move
    {
        public final double moveX;
        public final double moveY;

        public Move(double moveX, double moveY) {
            this.moveX = moveX;
            this.moveY = moveY;
        }
    }
}
