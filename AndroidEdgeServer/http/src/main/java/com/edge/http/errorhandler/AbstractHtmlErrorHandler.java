package com.edge.http.errorhandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.edge.http.servlet.HttpResponseWrapper;
import com.edge.http.servlet.HttpServletResponse;
import com.edge.http.utilities.IOUtilities;

public abstract class AbstractHtmlErrorHandler extends AbstractPlainTextHttpErrorHandler {

    private final String errorDocumentPath;
    protected String explanation;

    public AbstractHtmlErrorHandler(String status, String message, String explanation,
                                    String errorDocumentPath) {
        super(status, message);
        this.errorDocumentPath = errorDocumentPath;
        this.explanation = explanation;
    }

    @Override
    public void serve(HttpServletResponse response) throws IOException {
        response.setStatus(status);
        response.setContentType("text/html");

        if (errorDocumentPath == null || errorDocumentPath.equals("")) {
            serveDocument(response);
        } else {
            File file = new File(errorDocumentPath);
            if (file.exists()) {
                serveFile(response, file);
            } else {
                throw new IOException(status + " occurred, specified error handler (" + errorDocumentPath + ") was not found.");
            }
        }
    }

    private void serveDocument(HttpServletResponse response) throws IOException {
        HtmlErrorDocument doc = new HtmlErrorDocument();
        doc.setTitle(message);
        doc.setMessage(explanation);
        String msg = doc.toString();

        response.getWriter().write(msg);
        ((HttpResponseWrapper) response).flush();
    }

    private void serveFile(HttpServletResponse response, File file) throws IOException {
        response.setContentLength(file.length());
        ((HttpResponseWrapper) response).flushHeaders();
        InputStream inputStream = new FileInputStream(file);
        try {
            ((HttpResponseWrapper) response).serveStream(inputStream);
            ((HttpResponseWrapper) response).flush();
        } finally {
            IOUtilities.closeSilently(inputStream);
        }
    }
}
