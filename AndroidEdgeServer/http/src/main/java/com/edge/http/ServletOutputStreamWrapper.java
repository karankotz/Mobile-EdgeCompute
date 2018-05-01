package com.edge.http;

import java.io.IOException;
import java.io.OutputStream;

import com.edge.http.servlet.HttpResponseWrapper;
import com.edge.http.servlet.ServletOutputStream;

public class ServletOutputStreamWrapper extends ServletOutputStream {

    private final OutputStream outputStream;
    private final HttpResponseWrapper response;

    public ServletOutputStreamWrapper(final OutputStream outputStream, final HttpResponseWrapper httpResponse) {
        this.outputStream = outputStream;
        this.response = httpResponse;
    }

    @Override
    public void write(int b) throws IOException {
        flushHeadersIfPossible();
        outputStream.write(b);
    }

    @Override
    public void write(byte b[]) throws IOException {
        flushHeadersIfPossible();
        outputStream.write(b);
    }

    @Override
    public void write(byte b[], int off, int len) throws IOException {
        flushHeadersIfPossible();
        outputStream.write(b, off, len);
    }

    @Override
    public void flush() throws IOException {
        outputStream.flush();
    }

    private void flushHeadersIfPossible() throws IOException {
        if (!response.isCommitted()) {
            response.flushHeaders();
        }
    }

    @Override
    public void close() throws IOException {
        outputStream.close();
    }
}
