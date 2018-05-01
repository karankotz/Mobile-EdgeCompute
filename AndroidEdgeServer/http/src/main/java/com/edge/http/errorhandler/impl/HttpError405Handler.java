package com.edge.http.errorhandler.impl;

import java.io.IOException;

import com.edge.http.Headers;
import com.edge.http.errorhandler.AbstractHtmlErrorHandler;
import com.edge.http.servlet.HttpServletResponse;


public class HttpError405Handler extends AbstractHtmlErrorHandler {

    private String allowedMethods;

    public HttpError405Handler(String allowedMethods) {
        super(HttpServletResponse.STATUS_METHOD_NOT_ALLOWED, "Error 405 - Method Not Allowed",
                "<p>The method specified in the Request-Line is not allowed for the resource " +
                        "identified by the Request-URI.</p>", null);
        this.allowedMethods = allowedMethods;
    }


    @Override
    public void serve(HttpServletResponse response) throws IOException {
        response.getHeaders().setHeader(Headers.HEADER_ALLOW, allowedMethods);
        super.serve(response);
    }
}
