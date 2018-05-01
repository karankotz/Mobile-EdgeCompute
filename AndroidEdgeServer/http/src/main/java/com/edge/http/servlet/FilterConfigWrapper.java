package com.edge.http.servlet;

public class FilterConfigWrapper implements FilterConfig {

    private final ServletContext servletContext;

    public FilterConfigWrapper(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public ServletContext getServletContext() {
        return servletContext;
    }
}
