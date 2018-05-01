package com.edge.http;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.edge.http.configuration.ServerConfig;
import com.edge.http.utilities.IOUtilities;
import com.edge.http.utilities.Utilities;

public class WebServer extends Thread {

    private static final Logger LOGGER = Logger.getLogger(WebServer.class.getName());

    public static final String NAME = "AndroidHTTPServer";
    public static final String VERSION = "0.1.5-dev";
    public static final String SIGNATURE = NAME + "/" + VERSION;

    private final ServerSocket serverSocket;
    private final ServerConfig serverConfig;

    private boolean listen;

    public WebServer(final ServerSocket serverSocket, final ServerConfig serverConfig) {
        this.serverSocket = serverSocket;
        this.serverConfig = serverConfig;
    }

    @Override
    public void run() {
        ServiceContainer serviceContainer = new ServiceContainer(serverConfig);

        try {
            while (listen) {
                try {
                    serviceContainer.getThreadPoolExecutor().execute(
                            new ServerRunnable(serverSocket.accept(),
                                    serverConfig,
                                    serviceContainer.getRequestWrapperFactory(),
                                    serviceContainer.getResponseFactory(),
                                    serviceContainer.getHttpErrorHandlerResolver(),
                                    serviceContainer.getPathHelper()));
                } catch (IOException e) {
                    if (listen) {
                        LOGGER.log(Level.SEVERE, "Communication error", e);
                    }
                }
            }
        } finally {
            IOUtilities.closeSilently(serverSocket);
            serviceContainer.getThreadPoolExecutor().shutdown();
        }
    }

    /**
     * Starts the web server
     */
    public boolean startServer() {
        listen = true;

        if (!(new File(serverConfig.
                getDocumentRootPath())
                .isDirectory())) {
            LOGGER.log(Level.WARNING, "DocumentRoot does not exist! PATH {0}", new Object[]{
                    serverConfig.getDocumentRootPath()
            });
        }

        if (!isNumberOfThreadsSufficient() || !isTempPathWritable() || !bindSocket()) {
            listen = false;
            return false;
        }

        LOGGER.log(Level.INFO, "Server has been started. Listening on port {0}", new Object[]{
                serverConfig.getListenPort()
        });

        Utilities.clearDirectory(serverConfig.getTempPath());

        start();
        return true;
    }

    private boolean isNumberOfThreadsSufficient() {
        if (serverConfig.getMaxServerThreads() < 1) {
            LOGGER.log(Level.SEVERE, "MaxThreads should be greater or equal to 1! {0} is given.",
                    new Object[]{serverConfig.getMaxServerThreads()});
            return false;
        }
        return true;
    }

    private boolean bindSocket() {
        try {
            serverSocket.bind(new InetSocketAddress(serverConfig.getListenPort()));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Unable to start server: unable to listen on port " +
                    serverConfig.getListenPort(), e);
            return false;
        }
        return true;
    }

    private boolean isTempPathWritable() {
        File tempPath = new File(serverConfig.getTempPath());

        if (!tempPath.exists()) {
            boolean successCreatedMissingDirectory = tempPath.mkdirs();
            if (!successCreatedMissingDirectory) {
                LOGGER.log(Level.SEVERE, "TempPath does not exist and can not be created! PATH {0}", new Object[]{
                        serverConfig.getTempPath()
                });
                return false;
            }
        }

        if (!tempPath.canWrite()) {
            LOGGER.log(Level.SEVERE, "TempPath is not writable! PATH {0}", new Object[]{
                    serverConfig.getTempPath()
            });
            return false;
        }

        return true;
    }

    public void stopServer() {
        listen = false;
        IOUtilities.closeSilently(serverSocket);
        LOGGER.info("Server has been stopped.");
    }

    public boolean isRunning() {
        return listen;
    }

    public ServerConfig getServerConfig() {
        return serverConfig;
    }
}
