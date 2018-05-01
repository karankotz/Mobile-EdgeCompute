package com.edge.http.servlet;

import java.io.OutputStream;
import java.io.PrintWriter;

public class ServletPrintWriter extends PrintWriter {

    private boolean wasWriteEndAlreadyCalled = false;

    public ServletPrintWriter(final OutputStream outputStream) {
        super(outputStream);
    }

    /**
     * Writes the end of the message. This method should be called once only.
     */
    public void writeEnd() {
        if (wasWriteEndAlreadyCalled) {
            throw new IllegalStateException("This method can be called once only.");
        }
        wasWriteEndAlreadyCalled = true;
    }
}
