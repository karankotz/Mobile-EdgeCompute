package com.edge.http.servlet;

import com.edge.http.exception.FilterInitializationException;
import com.edge.http.exception.ServletException;
import com.edge.http.exception.ServletInitializationException;

public interface ServletContainer {


    Servlet getServletForClass(Class<? extends HttpServlet> servletClass, ServletConfig servletConfig)
            throws ServletInitializationException, ServletException;

    Filter getFilterForClass(Class<? extends Filter> filterClass, FilterConfig filterConfig)
            throws FilterInitializationException, ServletException;
}
