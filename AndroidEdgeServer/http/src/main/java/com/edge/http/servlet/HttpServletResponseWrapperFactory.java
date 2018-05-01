package com.edge.http.servlet;

import java.io.IOException;
import java.net.Socket;

import com.edge.http.Headers;
import com.edge.http.protocol.serializer.Serializer;


public class HttpServletResponseWrapperFactory {

    private final Serializer<Headers> headersSerializer;
    private final Serializer<Cookie> cookieHeaderSerializer;
    private final StreamHelper streamHelper;


    public HttpServletResponseWrapperFactory(Serializer<Headers> headersSerializer,
                                             Serializer<Cookie> cookieHeaderSerializer,
                                             StreamHelper streamHelper) {
        this.headersSerializer = headersSerializer;
        this.cookieHeaderSerializer = cookieHeaderSerializer;
        this.streamHelper = streamHelper;
    }
    public HttpResponseWrapper createFromSocket(Socket socket) throws IOException {
        return new HttpResponseWrapper(headersSerializer, cookieHeaderSerializer, streamHelper, socket.getOutputStream());
    }
}
