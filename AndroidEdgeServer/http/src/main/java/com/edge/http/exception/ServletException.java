package com.edge.http.exception;

public class ServletException extends Exception {
    public ServletException(String s) {
        super(s);
    }

    public ServletException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ServletException(Throwable throwable) {
        super(throwable);
    }
}
