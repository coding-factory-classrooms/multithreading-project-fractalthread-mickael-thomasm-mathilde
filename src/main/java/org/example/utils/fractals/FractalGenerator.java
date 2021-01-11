package org.example.utils.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FractalGenerator {

    private final int pixelWidth;
    private final int pixelHeight;

    public FractalGenerator(int pixelWidth, int pixelHeight) {
        this.pixelWidth = pixelWidth;
        this.pixelHeight = pixelHeight;
    }

    public List<List<Integer>> generatePixels(double moveX, double moveY, double zoom) {
        List<Future<List<Integer>>> futurMandel = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(16);
        for (int y = 0; y < pixelHeight; y++) {
            Mandelbrot task = new Mandelbrot(y,new Mandelbrot.Size(pixelWidth,pixelHeight),-1.5,0.5,-1.0,1.0, new Mandelbrot.Move(moveX,moveY), zoom);
            futurMandel.add(executorService.submit(task));
        }
        List<List<Integer>> pixel = new ArrayList<>();

        for (Future<List<Integer>> task : futurMandel ) {
            List<Integer> row = null;
            try {
                row = task.get();
            } catch (InterruptedException | ExecutionException  e) {
                e.printStackTrace();
            }
            pixel.add(row);
        }
        return pixel;
    }
}
