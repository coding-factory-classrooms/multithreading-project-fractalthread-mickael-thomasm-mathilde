package org.example.utils.fractals.fractals;

public class FractalConf {
    public int maxIterations;
    public final int width;
    public final int height;
    public final double moveX;
    public final double moveY;
    public final double zoom;

    public FractalConf(int width, int height, double moveX, double moveY, double zoom, int maxIterations) {
        this.width = width;
        this.height = height;
        this.moveX = moveX;
        this.moveY = moveY;
        this.zoom = zoom;
        this.maxIterations = maxIterations;
    }
}
