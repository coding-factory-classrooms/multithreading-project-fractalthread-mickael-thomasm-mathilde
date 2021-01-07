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
        double x = Double.parseDouble(req.queryParamOrDefault("x", "0"));
        double y = Double.parseDouble(req.queryParamOrDefault("y", "0"));
        double zoom = Double.parseDouble(req.queryParamOrDefault("zoom", "1"));
        int width = Integer.parseInt(req.queryParamOrDefault("width", "1000"));
        int height = Integer.parseInt(req.queryParamOrDefault("height", "1000"));

        res.type("image/jpeg");
        FractalGenerator fractalGenerator = new FractalGenerator(width, height, new FractalGenerator.Position(x, y), zoom);
        BufferedImage image = fractalGenerator.generateImage();

        return this.getFileData(image);
    }

    private byte[] getFileData(BufferedImage data) {
        try {
            ByteArrayOutputStream file = new ByteArrayOutputStream();
            ImageIO.write(data, "jpg", file);
            return file.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}
