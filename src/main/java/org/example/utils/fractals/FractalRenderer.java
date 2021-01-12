package org.example.utils.fractals;

import org.example.utils.fractals.fractals.Mandelbrot;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class FractalRenderer {

    private final int width;
    private final int height;
    private final Position position;
    private final double zoom;
    private final int[] colors;

    public FractalRenderer(int width, int height) {
        this.width = width;
        this.height = height;
        this.position = new Position(0, 0);
        this.zoom = 1;

        this.colors = new int[Mandelbrot.MAX_ITERATIONS + 1];
        this.generateColorPalette();
    }

    public FractalRenderer(int width, int height, Position position, double zoom) {
        this.width = width;
        this.height = height;
        this.position = position;
        this.zoom = zoom;

        this.colors = new int[Mandelbrot.MAX_ITERATIONS + 1];
        this.generateColorPalette();
    }

    public BufferedImage generateImage() {
        FractalGenerator fractal = new FractalGenerator(width, height);
        List<List<Integer>> pixels = fractal.generatePixels(this.position.start, this.position.end,this.zoom);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < pixels.size(); y++) {
            List<Integer> xs = pixels.get(y);
            for (int x = 0; x < xs.size(); x++) {
                image.setRGB(x, y, this.getRGBColorForNumberOfIterations(xs.get(x)));
            }
        }
        return image;
    }

    private int getRGBColorForNumberOfIterations(int iterations) {
        return colors[iterations];
    }

    public static class Position {
        public double start;
        public double end;

        public Position(double start, double end) {
            this.start = start;
            this.end = end;
        }
    }

    private void generateColorPalette() {
        for (int i = 0; i < Mandelbrot.MAX_ITERATIONS - 1; i++) {
            colors[i] = Color.HSBtoRGB(i/256f, 1, i/(i+8f));
        }
        colors[Mandelbrot.MAX_ITERATIONS] = Color.black.getRGB();
    }
}
