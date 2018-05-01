package com.edge.http.errorhandler;

public interface HttpErrorHandlerResolver {
    HttpErrorHandler getHandler(RuntimeException e);
}
