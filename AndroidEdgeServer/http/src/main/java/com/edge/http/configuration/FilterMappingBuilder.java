package com.edge.http.configuration;

import java.util.regex.Pattern;

import com.edge.http.configuration.impl.FilterMappingImpl;
import com.edge.http.servlet.Filter;


public class FilterMappingBuilder {

    private final ServletContextBuilder servletContextBuilder;
    private Pattern urlPattern;
    private Pattern urlExcludedPattern;
    private Class<? extends Filter> clazz;

    FilterMappingBuilder(ServletContextBuilder servletContextBuilder) {
        this.servletContextBuilder = servletContextBuilder;
    }

    public FilterMappingBuilder withUrlPattern(Pattern urlPattern) {
        this.urlPattern = urlPattern;
        return this;
    }

    public FilterMappingBuilder withUrlExcludedPattern(Pattern urlExcludedPattern) {
        this.urlExcludedPattern = urlExcludedPattern;
        return this;
    }

    public FilterMappingBuilder withFilterClass(Class<? extends Filter> clazz) {
        this.clazz = clazz;
        return this;
    }

    public ServletContextBuilder end() {
        servletContextBuilder.withFilterMapping(new FilterMappingImpl(urlPattern, urlExcludedPattern, clazz));
        return servletContextBuilder;
    }
}
