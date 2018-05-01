package com.edge.http.exception.protocol;

public class LengthRequiredException extends ProtocolException {
    public LengthRequiredException() {
        super("Length header is required for POST requests.");
    }
}
