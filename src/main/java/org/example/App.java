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
import java.nio.file.Files;
import java.util.HashMap;

public class App {
    public static void main(String[] args) {
        initialize();

        Spark.get("/", (req, res) -> {
            return Template.render("home.html", new HashMap<>());
        });

        Spark.get("/fractal", (req, res) -> {
            res.type("image/jpeg");
            int size = 1000;
            FractalGenerator fractalGenerator = new FractalGenerator(size, size);
            BufferedImage image = fractalGenerator.generateImage();
            try {
                File file = new File("fractals/fractal.jpg");
                ImageIO.write(image, "jpg", file);
                return Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(image);
            return "";
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
