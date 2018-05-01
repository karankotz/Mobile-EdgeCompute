package com.edge.http.exception.protocol;

public class MalformedStatusLineException extends ProtocolException {

    public MalformedStatusLineException(String message) {
        super(message);
    }
}
