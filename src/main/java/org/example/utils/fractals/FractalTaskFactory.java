package org.example.utils.fractals;

import org.example.utils.fractals.fractals.properties.FractalMove;
import org.example.utils.fractals.fractals.properties.FractalSize;
import org.example.utils.fractals.fractals.Mandelbrot;

import java.util.List;
import java.util.concurrent.Callable;

public class FractalTaskFactory {

    private final FractalType fractal;
    private final FractalSize size;
    private final FractalMove move;

    public FractalTaskFactory(FractalType fractal, FractalSize size, FractalMove move) {
        this.fractal = fractal;
        this.size = size;
        this.move = move;
    }

    public Callable<List<Integer>> createFractalLineTask(int lineNumber) {
        switch (fractal) {
            case MANDELBROT: return new Mandelbrot(lineNumber, size, move);
        }
        return null;
    }
}
