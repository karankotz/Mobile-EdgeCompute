package com.edge.http.servlet;

import java.io.OutputStream;


public class ChunkedPrintWriter extends ServletPrintWriter {

    // TODO Move this capability to the ServletOutputStreamWrapper

    private static final String NEW_LINE = "\r\n";
    private static final String END_LINE = "0\r\n\r\n";


    public ChunkedPrintWriter(OutputStream out) {
        super(out);
    }

    @Override
    public void println() {
        // Overwrites the original new line character
        synchronized (lock) {
            print(NEW_LINE);
        }
    }

    @Override
    public void write(String str) {
        String head = Long.toHexString(str.length()).toUpperCase() + NEW_LINE;
        super.write(head);
        super.write(str);
        super.write(NEW_LINE);
    }

    @Override
    public void writeEnd() {
        super.writeEnd();
        super.write(END_LINE);
    }
}
