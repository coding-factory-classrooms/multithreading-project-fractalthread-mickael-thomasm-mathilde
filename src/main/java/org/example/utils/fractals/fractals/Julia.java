package org.example.utils.fractals.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class Julia implements Fractal, Callable<List<Integer>> {
    private final int lineNumber;
    private final FractalConf configuration;

    public Julia(int lineNumber, FractalConf configuration) {
        this.lineNumber = lineNumber;
        this.configuration = configuration;
    }

    @Override
    public int calculatePixel(int pixelX, int pixelY) {
        double cRe, cIm;
        double newRe, newIm, oldRe, oldIm;

        //pick some values for the constant c, this determines the shape of the Julia Set
        cRe = -0.62772;
        cIm = 0.42193;

        int w = configuration.width;
        int h = configuration.height;

        newRe = 1.5 * (pixelX - w / 2) / (0.5 * configuration.zoom * w) + configuration.moveX;
        newIm = (pixelY - h / 2) / (0.5 * configuration.zoom * h) + configuration.moveY;

        int iterations;
        for (iterations = 0; iterations < 5000; iterations++) {
            //remember value of previous iteration
            oldRe = newRe;
            oldIm = newIm;
            //the actual iteration, the real and imaginary part are calculated
            newRe = oldRe * oldRe - oldIm * oldIm + cRe;
            newIm = 2 * oldRe * oldIm + cIm;
            //if the point is outside the circle with radius 2: stop
            if ((newRe * newRe + newIm * newIm) > 4) break;
        }
        return iterations;
//        }
    }

    @Override
    public List<Integer> call() throws Exception {
        List<Integer> row = new ArrayList<>();
        for (int x = 0; x < configuration.width; x++) {
            row.add(calculatePixel(x, lineNumber));
        }
        return row;
    }
}
