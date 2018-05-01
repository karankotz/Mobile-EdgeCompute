package com.edge.http.exception;
public class UnexpectedSituationException extends RuntimeException {

    public UnexpectedSituationException(String message) {
        super(message);
    }

    public UnexpectedSituationException(String message, Throwable e) {
        super(message, e);
    }

    public UnexpectedSituationException(Throwable e) {
        super(e);
    }
}
