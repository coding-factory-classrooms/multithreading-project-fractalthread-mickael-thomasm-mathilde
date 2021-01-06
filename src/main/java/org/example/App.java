package org.example;

import org.example.core.Conf;
import org.example.core.Template;
import org.example.middlewares.LoggerMiddleware;
import org.example.utils.fractals.FractalGenerator;
import spark.Spark;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class App {
    public static void main(String[] args) {
        int size = 1000;
        long start = System.currentTimeMillis();
        FractalGenerator fractalGenerator = new FractalGenerator(size, size);
        BufferedImage image = fractalGenerator.generateImage();
        try {
            ImageIO.write(image, "jpg", new File("fractals/fractal.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        long elapsedTime = System.currentTimeMillis() - start;
        System.out.println("Elapsed: " + elapsedTime + " ms");

        initialize();
        Spark.get("/", (req, res) -> {
            return Template.render("home.html", new HashMap<>());
        });
    }

    static void initialize() {
        Template.initialize();

        // Display exceptions in logs
        Spark.exception(Exception.class, (e, req, res) -> e.printStackTrace());

        // Serve static files (img/css/js)
        Spark.staticFiles.externalLocation(Conf.STATIC_DIR.getPath());

        // Configure server port
        Spark.port(Conf.HTTP_PORT);
        final LoggerMiddleware log = new LoggerMiddleware();
        Spark.before(log::process);
    }
}
