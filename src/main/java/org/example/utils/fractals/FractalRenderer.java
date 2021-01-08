package org.example.utils.fractals;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class FractalRenderer {

    private final int width;
    private final int height;
    private final Position position;
    private final double zoom;

    public FractalRenderer(int width, int height) {
        this.width = width;
        this.height = height;
        this.position = new Position(0, 0);
        this.zoom = 1;
    }

    public FractalRenderer(int width, int height, Position position, double zoom) {
        this.width = width;
        this.height = height;
        this.position = position;
        this.zoom = zoom;
    }

    public BufferedImage generateImage() {
        FractalGenerator fractal = new FractalGenerator(width, height);
        List<List<Double>> pixels = fractal.generatePixels(this.position.start, this.position.end,this.zoom);

        double minIntensity = calcMin(pixels);
        double maxIntensity = calcMax(pixels);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < pixels.size(); y++) {
            List<Double> xs = pixels.get(y);
            for (int x = 0; x < xs.size(); x++) {
                double intensity = this.getIntensityForPixel(xs.get(x), minIntensity, maxIntensity);
                image.setRGB(x, y, this.getRGBColorForIntensity(intensity));
            }
        }

        return image;
    }

    private double calcMin(List<List<Double>> pixels) {
        double minIntensity = 1;
        for (List<Double> row : pixels) {
            for (double col : row) {
                minIntensity = Math.min(minIntensity, col);
            }
        }
        return minIntensity;
    }

    private double calcMax(List<List<Double>> pixels) {
        double maxIntensity = 1;
        for (List<Double> row : pixels) {
            for (double col : row) {
                maxIntensity = Math.max(maxIntensity, col);
            }
        }
        return maxIntensity;
    }

    private double getIntensityForPixel(double value, double minIntensity, double maxIntensity) {
        return (value - minIntensity) / (maxIntensity - minIntensity);
    }

    private int getRGBColorForIntensity(double intensity) {
        int colorInt = (int) (255 * intensity);
        return new Color(colorInt, colorInt, colorInt).getRGB();
    }

    public static class Position {
        public double start;
        public double end;

        public Position(double start, double end) {
            this.start = start;
            this.end = end;
        }
    }
}