package com.edge.http.servlet.loader;

import com.edge.http.exception.ServletException;
import com.edge.http.servlet.HttpServlet;
import com.edge.http.servlet.HttpServletRequest;
import com.edge.http.servlet.HttpServletResponse;
import com.edge.http.servlet.ServletConfig;

public class SampleServlet extends HttpServlet {

    private int initializedCounter = 0;
    private int destroyedCounter = 0;

    private ServletConfig servletConfig;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        this.servletConfig = servletConfig;
        init();
    }

    @Override
    public void init() throws ServletException {
        super.init();
        initializedCounter++;
    }

    @Override
    public void destroy() {
        super.destroy();
        destroyedCounter++;
    }

    /**
     * Used for testing purpose only
     *
     * @param request
     * @param response
     * @throws ServletException
     */
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {

    }

    public int getInitializedCounter() {
        return initializedCounter;
    }

    public int getDestroyedCounter() {
        return destroyedCounter;
    }

    public ServletConfig getServletConfig() {
        return servletConfig;
    }
}
