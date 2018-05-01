package com.edge.http;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import com.edge.http.errorhandler.impl.HttpError503Handler;
import com.edge.http.servlet.HttpServletResponseWrapperFactory;
import com.edge.http.utilities.IOUtilities;

public class ServiceUnavailableHandler implements RejectedExecutionHandler {

    private final HttpServletResponseWrapperFactory responseFactory;

    public ServiceUnavailableHandler(final HttpServletResponseWrapperFactory responseFactory) {
        this.responseFactory = responseFactory;
    }

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        if (r instanceof ServerRunnable) {
            Socket socket = ((ServerRunnable) r).getSocket();
            try {
                (new HttpError503Handler()).serve(responseFactory.createFromSocket(socket));
            } catch (IOException e) {
            } finally {
                IOUtilities.closeSilently(socket);
            }
        }
    }
}
