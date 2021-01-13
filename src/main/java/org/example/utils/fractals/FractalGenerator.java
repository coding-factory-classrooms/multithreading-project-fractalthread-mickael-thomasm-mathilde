package org.example.utils.fractals;

import org.example.utils.fractals.fractals.FractalConf;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class FractalGenerator {

    private final FractalType fractalType;
    private final int pixelWidth;
    private final int pixelHeight;

    public FractalGenerator(FractalType fractalType, int pixelWidth, int pixelHeight) {
        this.fractalType = fractalType;
        this.pixelWidth = pixelWidth;
        this.pixelHeight = pixelHeight;
    }

    public List<List<Integer>> generatePixels(double moveX, double moveY, double zoom, double realPart, double imaginaryPart) {
        FractalTaskFactory taskFactory = new FractalTaskFactory(
            fractalType,
            new FractalConf(realPart, imaginaryPart, pixelWidth, pixelHeight, moveX, moveY, zoom, 5000)
        );

        List<Future<List<Integer>>> tasks = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(16);
        for (int line = 0; line < pixelHeight; line++) {
            Callable<List<Integer>> task = taskFactory.createFractalLineTask(line);
            tasks.add(executorService.submit(task));
        }
        List<List<Integer>> pixel = new ArrayList<>();

        for (Future<List<Integer>> task : tasks ) {
            List<Integer> row = null;
            try {
                row = task.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            pixel.add(row);
        }
        return pixel;
    }
}
