package com.edge.http.errorhandler;

import java.io.IOException;

import com.edge.http.servlet.HttpServletResponse;
import com.edge.http.servlet.HttpResponseWrapper;

public abstract class AbstractPlainTextHttpErrorHandler implements HttpErrorHandler {

    protected final String status;
    protected final String message;

    public AbstractPlainTextHttpErrorHandler(String status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public void serve(HttpServletResponse response) throws IOException {
        response.setStatus(status);
        response.setContentType("text/plain");
        response.setContentLength(message.length());
        response.getWriter().write(message);
        ((HttpResponseWrapper) response).flush();
    }
}
