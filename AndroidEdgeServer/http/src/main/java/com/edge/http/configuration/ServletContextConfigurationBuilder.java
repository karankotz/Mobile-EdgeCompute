package com.edge.http.configuration;

import java.util.ArrayList;
import java.util.List;

import com.edge.http.servlet.ServletContextWrapper;
import com.edge.http.session.storage.SessionStorage;

public class ServletContextConfigurationBuilder {

    private final List<ServletContextWrapper> servletContextWrappers = new ArrayList<>();

    private SessionStorage sessionStorage;
    private ServerConfig serverConfig;


    private ServletContextConfigurationBuilder() {
    }

    public static ServletContextConfigurationBuilder create() {
        return new ServletContextConfigurationBuilder();
    }

    public ServletContextConfigurationBuilder withSessionStorage(SessionStorage sessionStorage) {
        this.sessionStorage = sessionStorage;
        return this;
    }

    public ServletContextConfigurationBuilder withServerConfig(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
        return this;
    }

    public ServletContextBuilder addServletContext() {
        return new ServletContextBuilder(this, sessionStorage, serverConfig);
    }

    public List<ServletContextWrapper> build() {
        return servletContextWrappers;
    }

    ServletContextConfigurationBuilder addServletContext(ServletContextWrapper servletContextWrapper) {
        servletContextWrapper.setAttribute(ServerConfig.class.getName(), serverConfig);
        servletContextWrappers.add(servletContextWrapper);
        return this;
    }
}
