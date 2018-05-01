
package com.edge.http.servlet;

public class ServletConfigWrapper implements ServletConfig {

    private final ServletContext servletContext;

    public ServletConfigWrapper(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public ServletContext getServletContext() {
        return servletContext;
    }
}
