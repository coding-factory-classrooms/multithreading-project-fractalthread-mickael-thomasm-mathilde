package org.example.utils.fractals;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class FractalRenderer {

    private final FractalType fractalType;
    private final int width;
    private final int height;
    private final Position position;
    private final double zoom;
    private final double realPart;
    private final double imaginaryPart;
    private final int maxIterations;
    private final int[] colors;
    private final boolean isEasterEgg;

    public FractalRenderer(int width, int height) {
        this.fractalType = FractalType.MANDELBROT;
        this.width = width;
        this.height = height;
        this.position = new Position(0, 0);
        this.zoom = 1;
        this.maxIterations = 5000;
        this.realPart = 0;
        this.imaginaryPart = 0;
        this.isEasterEgg = false;

        this.colors = new int[maxIterations + 1];
        this.generateColorPalette();
    }

    public FractalRenderer(FractalType fractalType, int width, int height, Position position, double zoom, double realPart, double imaginaryPart, int maxIterations, boolean isEasterEgg) {
        this.fractalType = fractalType;
        this.width = width;
        this.height = height;
        this.position = position;
        this.zoom = zoom;
        this.realPart = realPart;
        this.imaginaryPart = imaginaryPart;
        this.maxIterations = maxIterations;
        this.isEasterEgg = isEasterEgg;

        this.colors = new int[maxIterations + 1];
        this.generateColorPalette();
    }

    public BufferedImage generateImage() {
        FractalGenerator fractal = new FractalGenerator(this.fractalType, width, height, this.maxIterations);
        List<List<Integer>> pixels = fractal.generatePixels(this.position.start, this.position.end,this.zoom, this.realPart, this.imaginaryPart);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < pixels.size(); y++) {
            List<Integer> xs = pixels.get(y);
            for (int x = 0; x < xs.size(); x++) {
                image.setRGB(x, y, this.getRGBColorForNumberOfIterations(xs.get(x)));
            }
        }

        if (this.isEasterEgg) {
            this.drawEasterEgg(image);
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
        for (int i = 0; i < this.maxIterations - 1; i++) {
            colors[i] = Color.HSBtoRGB(i/256f, 1, i/(i+8f));
        }
        colors[this.maxIterations] = Color.black.getRGB();
    }

    private void drawEasterEgg(BufferedImage image) {
        Graphics2D g2d = image.createGraphics();
        g2d.setFont(new Font("Lato", Font.PLAIN, 30));
        g2d.drawString("Mathilde Arconte", this.width - 500, 50);
        g2d.drawString("Mickael Filipe", this.width - 500, 90);
        g2d.drawString("Thomas Guillouet", this.width - 500, 130);
        g2d.drawString("Robin Penea", this.width - 500, 170);
    }
}
