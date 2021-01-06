package org.example.utils.fractals;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class FractalGenerator {

    private int width;
    private int height;

    public FractalGenerator(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public BufferedImage generateImage() {
        Mandelbrot fractal = new Mandelbrot(width, height, -1.5, 0.5, -1.0, 1.0);
        List<List<Double>> pixels = fractal.generatePixels();

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
}
