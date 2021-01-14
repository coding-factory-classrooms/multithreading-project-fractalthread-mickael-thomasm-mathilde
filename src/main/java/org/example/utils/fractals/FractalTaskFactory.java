package org.example.utils.fractals;

import org.example.utils.fractals.fractals.FractalConf;
import org.example.utils.fractals.fractals.Julia;
import org.example.utils.fractals.fractals.Mandelbrot;

import java.util.List;
import java.util.concurrent.Callable;

public class FractalTaskFactory {

    private final FractalType fractal;
    private final FractalConf configuration;

    public FractalTaskFactory(FractalType fractal, FractalConf configuration) {
        this.fractal = fractal;
        this.configuration = configuration;
    }

    public Callable<List<Integer>> createFractalLineTask(int lineNumber) {
        switch (fractal) {
            case MANDELBROT: return new Mandelbrot(lineNumber, configuration);
            case JULIA: return new Julia(lineNumber, configuration);
        }
        return null;
    }
}
