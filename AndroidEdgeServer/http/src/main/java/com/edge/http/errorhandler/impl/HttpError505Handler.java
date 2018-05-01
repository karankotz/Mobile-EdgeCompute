
package com.edge.http.errorhandler.impl;

import com.edge.http.errorhandler.AbstractPlainTextHttpErrorHandler;
import com.edge.http.servlet.HttpServletResponse;

public class HttpError505Handler extends AbstractPlainTextHttpErrorHandler {

    public HttpError505Handler() {
        super(HttpServletResponse.HTTP_VERSION_NOT_SUPPORTED, "Error 505 - HTTP Version Not Supported");
    }
}
