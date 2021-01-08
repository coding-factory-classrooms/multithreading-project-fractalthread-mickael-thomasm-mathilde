package org.example.controllers;

import org.example.utils.fractals.FractalRenderer;
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
        double zoom = Double.parseDouble(req.queryParamOrDefault("zoom", "1"));

        res.type("image/jpeg");
        FractalRenderer fractalRenderer = new FractalRenderer(size, size, new FractalRenderer.Position(x, y), zoom);
        BufferedImage image = fractalRenderer.generateImage();

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
