package com.edge.http.exception.protocol;

public class ProtocolException extends RuntimeException {

    public ProtocolException(String message) {
        super(message);
    }

    public ProtocolException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
