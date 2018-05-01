package com.edge.http.controller;

import com.edge.http.WebServer;


public interface Controller {


    void start() throws IllegalStateException;

    void stop() throws IllegalStateException;

    WebServer getWebServer();
}
