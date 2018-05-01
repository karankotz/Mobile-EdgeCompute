package com.edge.http.errorhandler.impl;

import org.junit.Test;

import com.edge.http.configuration.ServerConfig;
import com.edge.http.errorhandler.HttpErrorHandler;
import com.edge.http.errorhandler.HttpErrorHandlerResolver;
import com.edge.http.exception.NotFoundException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

public class HttpErrorHandlerResolverImplTest {

    @Test
    public void shouldCaptureIntermediateExceptions() {
        ServerConfig serverConfig = null; // WIll throw NPE
        HttpErrorHandlerResolver httpErrorHandlerResolver = new HttpErrorHandlerResolverImpl(serverConfig);
        HttpErrorHandler handler = httpErrorHandlerResolver.getHandler(new NotFoundException());
        assertThat(handler, is(instanceOf(HttpError500Handler.class)));
    }
}
