package com.edge.http.configuration;

import java.util.regex.Pattern;

import com.edge.http.configuration.impl.ServletMappingImpl;
import com.edge.http.servlet.HttpServlet;

public class ServletMappingBuilder {

    private final ServletContextBuilder servletContextBuilder;
    private Pattern urlPattern;
    private Class<? extends HttpServlet> servletClass;

    ServletMappingBuilder(ServletContextBuilder servletContextBuilder) {
        this.servletContextBuilder = servletContextBuilder;
    }

    public ServletMappingBuilder withUrlPattern(Pattern urlPattern) {
        this.urlPattern = urlPattern;
        return this;
    }

    public ServletMappingBuilder withServletClass(Class<? extends HttpServlet> servletClass) {
        this.servletClass = servletClass;
        return this;
    }

    public ServletContextBuilder end() {
        servletContextBuilder.withServletMapping(new ServletMappingImpl(urlPattern, servletClass));
        return servletContextBuilder;
    }
}
