package org.example;

import org.example.controllers.FractalController;
import org.example.controllers.HomeController;
import org.example.core.Conf;
import org.example.core.Template;
import org.example.middlewares.LoggerMiddleware;
import spark.Spark;

public class App {
    public static void main(String[] args) {
        initialize();

        Spark.get("/", (req, res) -> new HomeController().render(req, res));

        Spark.get("/fractal", (req, res) -> new FractalController().render(req, res));
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
