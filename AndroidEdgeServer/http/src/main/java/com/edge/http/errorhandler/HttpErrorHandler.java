package com.edge.http.errorhandler;

import java.io.IOException;

import com.edge.http.servlet.HttpServletResponse;

public interface HttpErrorHandler {

    /**
     * Serves the error page.
     *
     * @param response
     * @throws IOException
     */
    void serve(HttpServletResponse response) throws IOException;
}
