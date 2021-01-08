package org.example.controllers;

import org.example.utils.cache.LRUCache;
import org.example.utils.fractals.FractalGenerator;
import spark.Request;
import spark.Response;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class FractalController implements HttpController {

    private final LRUCache<String, byte[]> cache = new LRUCache<>();

    @Override
    public Object render(Request req, Response res) {
        double x = Double.parseDouble(req.queryParamOrDefault("x", "0"));
        double y = Double.parseDouble(req.queryParamOrDefault("y", "0"));
        double zoom = Double.parseDouble(req.queryParamOrDefault("zoom", "1"));
        int width = Integer.parseInt(req.queryParamOrDefault("width", "1000"));
        int height = Integer.parseInt(req.queryParamOrDefault("height", "1000"));

        String key = String.format("%dx%d_xPos_%f_yPos_%f_zoom_%f", width, height, x, y, zoom);
        if (cache.contains(key)) {
            return cache.get(key);
        }

        res.type("image/jpeg");
        FractalGenerator fractalGenerator = new FractalGenerator(width, height, new FractalGenerator.Position(x, y), zoom);
        BufferedImage image = fractalGenerator.generateImage();

        byte[] fileData = this.getFileData(image);
        cache.add(key, fileData);

        return fileData;
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
