package org.example.utils.fractals.fractals;

public interface Fractal {
    int calculatePixel(int pixelX, int pixelY, double moveX, double moveY, double zoom);
}
