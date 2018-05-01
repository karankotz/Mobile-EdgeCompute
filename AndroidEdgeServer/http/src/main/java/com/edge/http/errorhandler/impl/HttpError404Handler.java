package com.edge.http.errorhandler.impl;

import com.edge.http.errorhandler.AbstractHtmlErrorHandler;
import com.edge.http.servlet.HttpServletResponse;

public class HttpError404Handler extends AbstractHtmlErrorHandler {

    public HttpError404Handler(String errorDocumentPath) {
        super(HttpServletResponse.STATUS_NOT_FOUND, "Error 404 - File Not Found",
                "<p>The server has not found anything matching the specified URL.</p>",
                errorDocumentPath);
    }
}
