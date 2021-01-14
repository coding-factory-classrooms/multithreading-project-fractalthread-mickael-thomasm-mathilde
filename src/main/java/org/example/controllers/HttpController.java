package org.example.controllers;

import spark.Request;
import spark.Response;

public interface HttpController {
    public Object render(Request req, Response res);
}
