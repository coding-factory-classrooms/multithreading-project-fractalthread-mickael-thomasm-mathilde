package org.example.controllers;

import org.example.utils.fractals.FractalGenerator;
import spark.Request;
import spark.Response;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class FractalController implements HttpController {
    @Override
    public Object render(Request req, Response res) {
        int size = 1000;
        double x = Double.parseDouble(req.queryParamOrDefault("x", "0"));
        double y = Double.parseDouble(req.queryParamOrDefault("y", "0"));

        res.type("image/jpeg");
        FractalGenerator fractalGenerator = new FractalGenerator(size, size, new FractalGenerator.Position(x, y));
        BufferedImage image = fractalGenerator.generateImage();
        try {
            ByteArrayOutputStream file = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", file);
            return file.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(image);
        return "";
    }
}
