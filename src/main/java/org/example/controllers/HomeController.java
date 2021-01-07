package org.example.controllers;

import org.example.core.Template;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public class HomeController implements HttpController {
    @Override
    public Object render(Request req, Response res) {
        return Template.render("home.html", new HashMap<>());
    }
}
