package org.example.controllers;

import org.example.utils.cache.LRUCache;
import org.example.utils.fractals.FractalRenderer;
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

        String key = "xPos_" + x + "_yPos" + y + "_zoom_" + zoom;
        if (cache.contains(key)) {
            return cache.get(key);
        }

        res.type("image/jpeg");
        FractalRenderer fractalRenderer = new FractalRenderer(width, height, new FractalRenderer.Position(x, y), zoom);
        BufferedImage image = fractalRenderer.generateImage();

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
