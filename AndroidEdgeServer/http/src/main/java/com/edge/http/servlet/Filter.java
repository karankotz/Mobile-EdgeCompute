package com.edge.http.servlet;

import java.io.IOException;

import com.edge.http.exception.ServletException;

public interface Filter {


    void init(FilterConfig filterConfig) throws ServletException;

    void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException;
}
