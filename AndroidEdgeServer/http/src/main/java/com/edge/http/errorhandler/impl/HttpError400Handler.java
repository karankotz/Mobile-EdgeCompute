package com.edge.http.errorhandler.impl;

import com.edge.http.errorhandler.AbstractPlainTextHttpErrorHandler;
import com.edge.http.servlet.HttpServletResponse;


public class HttpError400Handler extends AbstractPlainTextHttpErrorHandler {

    public HttpError400Handler() {
        super(HttpServletResponse.STATUS_BAD_REQUEST, "Error 400 - Bad Request");
    }
}
