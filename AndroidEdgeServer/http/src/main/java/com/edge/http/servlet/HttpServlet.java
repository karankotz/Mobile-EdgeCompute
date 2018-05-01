
package com.edge.http.servlet;

import com.edge.http.exception.ServletException;

public abstract class HttpServlet implements Servlet {

    private ServletConfig servletConfig;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        this.servletConfig = servletConfig;
        init();
    }

    @Override
    public void init() throws ServletException {

    }

    @Override
    public void destroy() {
        // Empty by default, should be overwritten by the implementing servlet
    }

    @Override
    public String getServletInfo() {
        return "";
    }

    /**
     * Returns servlet context.
     *
     * @return
     */
    public ServletContext getServletContext() {
        return servletConfig == null ? null : servletConfig.getServletContext();
    }
}
