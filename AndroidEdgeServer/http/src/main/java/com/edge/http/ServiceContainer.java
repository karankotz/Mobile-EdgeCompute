package com.edge.http;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.edge.http.configuration.ServerConfig;
import com.edge.http.errorhandler.HttpErrorHandlerResolver;
import com.edge.http.errorhandler.impl.HttpErrorHandlerResolverImpl;
import com.edge.http.protocol.parser.impl.CookieParser;
import com.edge.http.protocol.parser.impl.HeadersParser;
import com.edge.http.protocol.parser.impl.MultipartHeadersPartParser;
import com.edge.http.protocol.parser.impl.QueryStringParser;
import com.edge.http.protocol.parser.impl.RequestStatusParser;
import com.edge.http.protocol.serializer.impl.CookieHeaderSerializer;
import com.edge.http.protocol.serializer.impl.HeadersSerializer;
import com.edge.http.protocol.serializer.impl.RangePartHeaderSerializer;
import com.edge.http.servlet.HttpServletRequestWrapperFactory;
import com.edge.http.servlet.HttpServletResponseWrapperFactory;
import com.edge.http.servlet.RangeHelper;
import com.edge.http.servlet.StreamHelper;
import com.edge.http.utilities.DateProvider;

public class ServiceContainer {

    private HttpServletRequestWrapperFactory requestWrapperFactory;
    private HttpServletResponseWrapperFactory responseFactory;
    private ThreadPoolExecutor threadPoolExecutor;
    private HttpErrorHandlerResolver httpErrorHandlerResolver;
    private PathHelper pathHelper;

    public ServiceContainer(final ServerConfig serverConfig) {

        HeadersParser headersParser = new HeadersParser();

        requestWrapperFactory = new HttpServletRequestWrapperFactory(headersParser,
                new QueryStringParser(),
                new RequestStatusParser(),
                new CookieParser(),
                new MultipartHeadersPartParser(headersParser),
                serverConfig.getTempPath()
        );

        responseFactory = new HttpServletResponseWrapperFactory(
                new HeadersSerializer(),
                new CookieHeaderSerializer(new DateProvider()),
                new StreamHelper(
                        new RangeHelper(),
                        new RangePartHeaderSerializer()
                )
        );

        threadPoolExecutor = new ThreadPoolExecutor(1, serverConfig.getMaxServerThreads(),
                20, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(serverConfig.getMaxServerThreads() * 3),
                Executors.defaultThreadFactory(),
                new ServiceUnavailableHandler(responseFactory)
        );

        httpErrorHandlerResolver = new HttpErrorHandlerResolverImpl(serverConfig);

        pathHelper = new PathHelper();

    }

    public HttpServletRequestWrapperFactory getRequestWrapperFactory() {
        return requestWrapperFactory;
    }

    public HttpServletResponseWrapperFactory getResponseFactory() {
        return responseFactory;
    }

    public ThreadPoolExecutor getThreadPoolExecutor() {
        return threadPoolExecutor;
    }

    public HttpErrorHandlerResolver getHttpErrorHandlerResolver() {
        return httpErrorHandlerResolver;
    }

    public PathHelper getPathHelper() {
        return pathHelper;
    }
}
