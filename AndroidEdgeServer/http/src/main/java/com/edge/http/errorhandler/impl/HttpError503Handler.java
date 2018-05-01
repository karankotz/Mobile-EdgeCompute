package com.edge.http.errorhandler.impl;

import com.edge.http.errorhandler.AbstractPlainTextHttpErrorHandler;
import com.edge.http.servlet.HttpServletResponse;

public class HttpError503Handler extends AbstractPlainTextHttpErrorHandler {

    public HttpError503Handler() {
        super(HttpServletResponse.STATUS_SERVICE_UNAVAILABLE, "Error 503 - Service Unavailable");
    }
}
