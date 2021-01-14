package org.example.utils.fractals;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        int width = 2500;
        int height = 2000;

        for(int i = 0; i < 10; i++) {
            long start = System.currentTimeMillis();
            FractalRenderer fractalRenderer = new FractalRenderer(width, height);
            BufferedImage image = fractalRenderer.generateImage();
            try {
                ImageIO.write(image, "jpg", new File("fractals/fractal.jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            long elapsedTime = System.currentTimeMillis() - start;
            System.out.println("Elapsed: " + elapsedTime + " ms");
        }
    }
}
