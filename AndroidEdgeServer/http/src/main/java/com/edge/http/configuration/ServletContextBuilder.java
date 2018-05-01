package com.edge.http.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.edge.http.servlet.ServletContextWrapper;
import com.edge.http.session.storage.SessionStorage;

public class ServletContextBuilder {

    private final List<ServletMapping> servletMappings = new ArrayList<>();
    private final List<FilterMapping> filterMappings = new ArrayList<>();
    private final Map<String, Object> attributes = new HashMap<>();

    private String contextPath;
    private ServletContextConfigurationBuilder parent;
    private SessionStorage sessionStorage;
    private ServerConfig serverConfig;

    /**
     * Creates a mapping builder. This constructor should be package scoped.
     *
     * @param parent
     * @param sessionStorage
     * @param serverConfig
     */
    ServletContextBuilder(ServletContextConfigurationBuilder parent,
                          SessionStorage sessionStorage,
                          ServerConfig serverConfig) {
        this.parent = parent;
        this.sessionStorage = sessionStorage;
        this.serverConfig = serverConfig;
    }

    public ServletMappingBuilder addServlet() {
        return new ServletMappingBuilder(this);
    }

    public FilterMappingBuilder addFilter() {
        return new FilterMappingBuilder(this);
    }

    public ServletContextBuilder withContextPath(String contextPath) {
        this.contextPath = contextPath;
        return this;
    }

    public ServletContextBuilder withAttribute(String name, Object value) {
        attributes.put(name, value);
        return this;
    }

    public ServletContextConfigurationBuilder end() {
        parent.addServletContext(new ServletContextWrapper(contextPath,
                servletMappings,
                filterMappings,
                attributes,
                serverConfig,
                sessionStorage
        ));
        return parent;
    }

    /**
     * Adds servlet mapping. This method should be package scoped.
     *
     * @param servletMapping
     * @return
     */
    ServletContextBuilder withServletMapping(ServletMapping servletMapping) {
        servletMappings.add(servletMapping);
        return this;
    }

    /**
     * Adds servlet mapping. This method should be package scoped.
     *
     * @param filterMapping
     * @return
     */
    ServletContextBuilder withFilterMapping(FilterMapping filterMapping) {
        filterMappings.add(filterMapping);
        return this;
    }
}
