package com.edge.http.errorhandler.impl;

import com.edge.http.errorhandler.AbstractPlainTextHttpErrorHandler;
import com.edge.http.servlet.HttpServletResponse;


public class HttpError411Handler extends AbstractPlainTextHttpErrorHandler {

    public HttpError411Handler() {
        super(HttpServletResponse.STATUS_LENGTH_REQUIRED, "Error 411 - Length Required");
    }
}
