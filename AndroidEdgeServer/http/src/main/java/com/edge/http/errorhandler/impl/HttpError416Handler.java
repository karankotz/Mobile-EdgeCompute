
package com.edge.http.errorhandler.impl;

import com.edge.http.errorhandler.AbstractPlainTextHttpErrorHandler;
import com.edge.http.servlet.HttpServletResponse;


public class HttpError416Handler extends AbstractPlainTextHttpErrorHandler {

    public HttpError416Handler() {
        super(HttpServletResponse.STATUS_RANGE_NOT_SATISFIABLE, "Error 416 Range Not Satisfiable");
    }
}
