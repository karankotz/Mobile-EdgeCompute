package com.edge.http.servlet;

import java.io.IOException;

import com.edge.http.exception.ServletException;

public interface FilterChain {

    void doFilter(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
}
