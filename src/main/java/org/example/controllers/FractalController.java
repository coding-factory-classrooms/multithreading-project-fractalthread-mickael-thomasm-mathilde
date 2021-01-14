package org.example.controllers;

import org.example.utils.cache.LRUCache;
import org.example.utils.fractals.FractalRenderer;
import org.example.utils.fractals.FractalType;
import spark.Request;
import spark.Response;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;

public class FractalController implements HttpController {

    private final LRUCache<String, byte[]> cache = new LRUCache<>(500);

    @Override
    public Object render(Request req, Response res) {
        res.type("image/jpeg");

        String fractalType = req.queryParamOrDefault("type", "MANDELBROT").toUpperCase(Locale.ROOT);
        double x = Double.parseDouble(req.queryParamOrDefault("x", "0"));
        double y = Double.parseDouble(req.queryParamOrDefault("y", "0"));
        double zoom = Double.parseDouble(req.queryParamOrDefault("zoom", "1"));
        double r = Double.parseDouble(req.queryParamOrDefault("r", "0"));
        double i = Double.parseDouble(req.queryParamOrDefault("i", "0"));
        int width = Integer.parseInt(req.queryParamOrDefault("width", "1000"));
        int height = Integer.parseInt(req.queryParamOrDefault("height", "1000"));

        String key = String.format("%s_res_%dx%d_xPos_%f_yPos_%f_zoom_%f_r_%f_i_%f", fractalType, width, height, x, y, zoom, r, i);
        if (cache.contains(key)) {
            return cache.get(key);
        }

        FractalRenderer fractalRenderer = new FractalRenderer(
            FractalType.valueOf(fractalType),
            width,
            height,
            new FractalRenderer.Position(x, y),
            zoom,
            r,
            i,
            5000
        );
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
