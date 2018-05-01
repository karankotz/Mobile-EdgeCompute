package com.edge.http.configuration.impl;

import java.util.regex.Pattern;

import com.edge.http.configuration.FilterMapping;
import com.edge.http.servlet.Filter;


public class FilterMappingImpl implements FilterMapping {

    private final Pattern urlPattern;
    private final Pattern urlExcludePattern;
    private final Class<? extends Filter> filterClass;

    public FilterMappingImpl(Pattern urlPattern, Pattern urlExcludePattern, Class<? extends Filter> filterClass) {
        this.urlPattern = urlPattern;
        this.urlExcludePattern = urlExcludePattern;
        this.filterClass = filterClass;
    }

    @Override
    public Pattern getUrlPattern() {
        return urlPattern;
    }

    @Override
    public Pattern getUrlExcludePattern() {
        return urlExcludePattern;
    }

    @Override
    public Class<? extends Filter> getFilterClass() {
        return filterClass;
    }
}
