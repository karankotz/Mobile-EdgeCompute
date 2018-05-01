package com.edge.http.errorhandler.impl;

import java.io.IOException;
import java.util.List;

import com.edge.http.configuration.ServerConfig;
import com.edge.http.Statistics;
import com.edge.http.errorhandler.HttpErrorHandler;
import com.edge.http.errorhandler.HttpErrorHandlerResolver;
import com.edge.http.exception.AccessDeniedException;
import com.edge.http.exception.MethodNotAllowedException;
import com.edge.http.exception.NotFoundException;
import com.edge.http.exception.protocol.LengthRequiredException;
import com.edge.http.exception.protocol.PayloadTooLargeProtocolException;
import com.edge.http.exception.protocol.ProtocolException;
import com.edge.http.exception.protocol.RangeNotSatisfiableProtocolException;
import com.edge.http.exception.protocol.StatusLineTooLongProtocolException;
import com.edge.http.exception.protocol.UnsupportedProtocolException;
import com.edge.http.exception.protocol.UriTooLongProtocolException;

public class HttpErrorHandlerResolverImpl implements HttpErrorHandlerResolver {

    private ServerConfig serverConfig;

    public HttpErrorHandlerResolverImpl(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

    @Override
    public HttpErrorHandler getHandler(RuntimeException e) {
        Throwable fallbackException;

        try {
            if (e instanceof ProtocolException) {
                return getProtocolExceptionHandler((ProtocolException) e);
            } else if (e instanceof AccessDeniedException) {
                return new HttpError403Handler(serverConfig.getErrorDocument403Path());
            } else if (e instanceof NotFoundException) {
                Statistics.incrementError404();
                return new HttpError404Handler(serverConfig.getErrorDocument404Path());
            } else if (e instanceof MethodNotAllowedException) {
                return new HttpError405Handler(getAllowedMethods());
            } else {
                fallbackException = e;
            }
        } catch (Throwable handlingException) {
            fallbackException = handlingException;
        }

        Statistics.incrementError500();
        return new HttpError500Handler().setReason(fallbackException);
    }

    /**
     * Returns coma separated allowed methods
     *
     * @return
     */
    private String getAllowedMethods() {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> supportedMethods = serverConfig.getSupportedMethods();
        for (int i = 0; i < supportedMethods.size(); i++) {
            stringBuilder.append(supportedMethods.get(i));
            if (i != supportedMethods.size() - 1) {
                stringBuilder.append(", ");
            }
        }

        return stringBuilder.toString();
    }

    /**
     * Returns resolved handler for given ProtocolException.
     *
     * @param e
     * @return
     */
    private HttpErrorHandler getProtocolExceptionHandler(ProtocolException e) {
        if (e instanceof UriTooLongProtocolException || e instanceof StatusLineTooLongProtocolException) {
            return new HttpError414Handler();
        } else if (e instanceof LengthRequiredException) {
            return new HttpError411Handler();
        } else if (e instanceof UnsupportedProtocolException) {
            return new HttpError505Handler();
        } else if (e instanceof PayloadTooLargeProtocolException) {
            return new HttpError413Handler();
        } else if (e instanceof RangeNotSatisfiableProtocolException) {
            return new HttpError416Handler();
        }

        return new HttpError400Handler();
    }
}
