
package com.edge.http.servlet;

import com.edge.http.exception.ServletException;

public interface Servlet {

    /**
     * Initialization method that can be overwritten.
     *
     * @throws ServletException
     */
    void init() throws ServletException;

    /**
     * The servlet initialization method. The reusable resources should be
     * initialized in the init method.
     *
     * @param servletConfig
     */
    void init(ServletConfig servletConfig) throws ServletException;

    /**
     * Called by the container to indicate to a servlet that is is going to be taken out of service.
     */
    void destroy();

    /**
     * Called by servlet container, the main servlet logic method.
     */
    void service(HttpServletRequest request, HttpServletResponse response) throws ServletException; /* IOException */

    /**
     * Returns servlet info such as author or copyright.
     *
     * @return
     */
    String getServletInfo();
}
