package com.edge.http.configuration;

import java.util.regex.Pattern;

import com.edge.http.servlet.HttpServlet;

public interface ServletMapping {


    Pattern getUrlPattern();

    Class<? extends HttpServlet> getServletClass();
}
