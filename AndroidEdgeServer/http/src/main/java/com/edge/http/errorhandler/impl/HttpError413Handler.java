package com.edge.http.errorhandler.impl;

import com.edge.http.errorhandler.AbstractPlainTextHttpErrorHandler;
import com.edge.http.servlet.HttpServletResponse;


public class HttpError413Handler extends AbstractPlainTextHttpErrorHandler {

    public HttpError413Handler() {
        super(HttpServletResponse.REQUEST_ENTITY_TOO_LARGE, "Error 413 Request Entity Too Large");
    }
}
