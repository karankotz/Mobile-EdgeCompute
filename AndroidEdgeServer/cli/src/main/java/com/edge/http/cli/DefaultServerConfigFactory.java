package com.edge.http.cli;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import example.Chunked;
import example.ChunkedWithDelay;
import example.Cookies;
import example.Forbidden;
import example.ForbiddenByFilter;
import example.Index;
import example.InternalServerError;
import example.NotFound;
import example.Session;
import example.Streaming;
import example.filter.FakeSecuredFilter;
import com.edge.http.configuration.ServerConfig;
import com.edge.http.configuration.ServerConfigFactory;
import com.edge.http.configuration.ServletContextConfigurationBuilder;
import com.edge.http.configuration.impl.ServerConfigImpl;
import com.edge.http.protocol.parser.impl.RangeParser;
import com.edge.http.protocol.serializer.impl.RangePartHeaderSerializer;
import com.edge.http.resource.provider.ResourceProvider;
import com.edge.http.resource.provider.impl.FileResourceProvider;
import com.edge.http.resource.provider.impl.ServletResourceProvider;
import com.edge.http.servlet.DefaultServletContainer;
import com.edge.http.servlet.RangeHelper;
import com.edge.http.servlet.ServletContextWrapper;
import com.edge.http.session.storage.FileSessionStorage;
import com.edge.http.session.storage.SessionStorage;

public class DefaultServerConfigFactory implements ServerConfigFactory {

    private static final Logger LOGGER = Logger.getLogger(DefaultServerConfigFactory.class.getName());

    @Override
    public ServerConfig getServerConfig() {
        return getServerConfig(getBasePath());
    }


    protected String getTempPath() {
        return System.getProperty("java.io.tmpdir") + File.separator + "webserver" + File.separator;
    }


    protected String getBasePath() {
        return "." + File.separator + "httpd" + File.separator;
    }


    protected Map<String, Object> getAdditionalServletContextAttributes() {
        return new HashMap<>();
    }

    protected Set<ResourceProvider> getAdditionalResourceProviders(ServerConfig serverConfig) {
        return new HashSet<>();
    }

    protected ServletContextConfigurationBuilder getServletContextConfigurationBuilder(SessionStorage sessionStorage, ServerConfig serverConfig) {
        return ServletContextConfigurationBuilder.create()
                .withSessionStorage(sessionStorage)
                .withServerConfig(serverConfig)
                .addServletContext()
                    .withContextPath("/example")
                    .addFilter()
                        .withUrlPattern(Pattern.compile("^/secured/.*$"))
                        .withFilterClass(FakeSecuredFilter.class)
                    .end()
                    .addServlet()
                        .withUrlPattern(Pattern.compile("^/Chunked$"))
                        .withServletClass(Chunked.class)
                    .end()
                    .addServlet()
                        .withUrlPattern(Pattern.compile("^/ChunkedWithDelay$"))
                        .withServletClass(ChunkedWithDelay.class)
                    .end()
                    .addServlet()
                        .withUrlPattern(Pattern.compile("^/Cookies$"))
                        .withServletClass(Cookies.class)
                    .end()
                    .addServlet()
                        .withUrlPattern(Pattern.compile("^/Forbidden$"))
                        .withServletClass(Forbidden.class)
                    .end()
                    .addServlet()
                        .withUrlPattern(Pattern.compile("^/Index$"))
                        .withServletClass(Index.class)
                    .end()
                    .addServlet()
                        .withUrlPattern(Pattern.compile("^/$"))
                        .withServletClass(Index.class)
                    .end()
                    .addServlet()
                        .withUrlPattern(Pattern.compile("^/InternalServerError$"))
                        .withServletClass(InternalServerError.class)
                    .end()
                    .addServlet()
                        .withUrlPattern(Pattern.compile("^/NotFound$"))
                        .withServletClass(NotFound.class)
                    .end()
                    .addServlet()
                        .withUrlPattern(Pattern.compile("^/Session$"))
                        .withServletClass(Session.class)
                    .end()
                    .addServlet()
                        .withUrlPattern(Pattern.compile("^/Streaming$"))
                        .withServletClass(Streaming.class)
                    .end()
                    .addServlet()
                        .withUrlPattern(Pattern.compile("^/secured/ForbiddenByFilter"))
                        .withServletClass(ForbiddenByFilter.class)
                    .end()
                .end();
    }

    private ServerConfig getServerConfig(String baseConfigPath) {
        ServerConfigImpl serverConfig;

        String tempPath = getTempPath();

        try {
            serverConfig = ServerConfigImpl.createFromPath(baseConfigPath, tempPath);
        } catch (IOException e) {
            LOGGER.warning("Unable to read server config. Using the default configuration. " + e.getMessage());
            serverConfig = new ServerConfigImpl(tempPath);
        }

        serverConfig.setResourceProviders(selectActiveResourceProviders(serverConfig));
        return serverConfig;
    }

    private List<ServletContextWrapper> getServletContexts(ServerConfig serverConfig) {
        ServletContextConfigurationBuilder servletContextConfigurationBuilder
                = getServletContextConfigurationBuilder(new FileSessionStorage(serverConfig.getTempPath()), serverConfig);

        List<ServletContextWrapper> servletContexts = servletContextConfigurationBuilder.build();

        for (ServletContextWrapper servletContextWrapper : servletContexts) {
            for (Map.Entry<String, Object> entry : getAdditionalServletContextAttributes().entrySet()) {
                servletContextWrapper.setAttribute(entry.getKey(), entry.getValue());
            }
        }

        return servletContexts;
    }

    private List<ResourceProvider> selectActiveResourceProviders(ServerConfig serverConfig) {
        List<ResourceProvider> resourceProviders = new ArrayList<>();

        resourceProviders.add(getFileResourceProvider(serverConfig));

        for (ResourceProvider resourceProvider : getAdditionalResourceProviders(serverConfig)) {
            resourceProviders.add(resourceProvider);
        }

        resourceProviders.add(getServletResourceProvider(serverConfig));
        return resourceProviders;
    }

    private FileResourceProvider getFileResourceProvider(ServerConfig serverConfig) {
        return new FileResourceProvider(new RangeParser(), new RangeHelper(),
                new RangePartHeaderSerializer(), serverConfig.getMimeTypeMapping(),
                serverConfig.getDocumentRootPath());
    }

    private ServletResourceProvider getServletResourceProvider(ServerConfig serverConfig) {
        return new ServletResourceProvider(
                new DefaultServletContainer(),
                getServletContexts(serverConfig)
        );
    }
}
