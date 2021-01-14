package org.example.utils.fractals.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Sources: https://gist.github.com/dackerman/7bc682d41ce632602af9e1a9858c82fc, https://lodev.org/cgtutor/juliamandelbrot.html
 */
public class Mandelbrot implements Fractal, Callable<List<Integer>>{
    private final FractalConf fractalConf;
    private final int y;

    public Mandelbrot(int y, FractalConf configuration) {
        this.y = y;
        this.fractalConf = configuration;
    }

    @Override
    public int calculatePixel(int pixelX, int pixelY) {
        double pr, pi;           //real and imaginary part of the pixel p
        double newRe, newIm, oldRe, oldIm;

        pr = 1.5 * (pixelX - fractalConf.width / 2) / (0.5 * fractalConf.zoom * fractalConf.width) + fractalConf.moveX;
        pi = (pixelY - fractalConf.height / 2) / (0.5 * fractalConf.zoom * fractalConf.height) + fractalConf.moveY;
        newRe = 0.38499999;
        newIm = -0.52;
        oldRe = oldIm = 0; //these should start at 0,0

        int i;
        for(i = 0; i < fractalConf.maxIterations; i++)
        {
            //remember value of previous iteration
            oldRe = newRe;
            oldIm = newIm;
            //the actual iteration, the real and imaginary part are calculated
            newRe = oldRe * oldRe - oldIm * oldIm + pr;
            newIm = 2 * oldRe * oldIm + pi;
            //if the point is outside the circle with radius 2: stop
            if((newRe * newRe + newIm * newIm) > 4) break;
        }
        return i;
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
