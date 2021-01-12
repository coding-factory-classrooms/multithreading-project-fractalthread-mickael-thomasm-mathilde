package org.example.utils.fractals;

import org.example.utils.fractals.fractals.properties.FractalSize;
import org.example.utils.fractals.fractals.properties.FractalMove;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class FractalGenerator {

    private final int pixelWidth;
    private final int pixelHeight;

    public FractalGenerator(int pixelWidth, int pixelHeight) {
        this.pixelWidth = pixelWidth;
        this.pixelHeight = pixelHeight;
    }

    public List<List<Integer>> generatePixels(double moveX, double moveY, double zoom) {
        FractalTaskFactory taskFactory = new FractalTaskFactory(
            FractalType.MANDELBROT,
            new FractalSize(pixelWidth, pixelHeight),
            new FractalMove(moveX,moveY, zoom)
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
            } catch (InterruptedException | ExecutionException  e) {
                e.printStackTrace();
            }
            pixel.add(row);
        }
        return pixel;
    }
}
