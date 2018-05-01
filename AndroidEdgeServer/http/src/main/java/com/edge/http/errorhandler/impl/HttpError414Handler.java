package com.edge.http.errorhandler.impl;

import com.edge.http.errorhandler.AbstractPlainTextHttpErrorHandler;
import com.edge.http.servlet.HttpServletResponse;


public class
HttpError414Handler extends AbstractPlainTextHttpErrorHandler {

    public HttpError414Handler() {
        super(HttpServletResponse.STATUS_URI_TOO_LONG, "Error 414 - URI Too Long");
    }
}
