package com.edge.http.servlet.impl;

import java.io.IOException;
import java.util.ArrayDeque;

import com.edge.http.exception.ServletException;
import com.edge.http.servlet.Filter;
import com.edge.http.servlet.FilterChain;
import com.edge.http.servlet.HttpServletRequest;
import com.edge.http.servlet.HttpServletResponse;

public class FilterChainImpl implements FilterChain {

    private ArrayDeque<Filter> filters;

    public FilterChainImpl(ArrayDeque<Filter> filters) {
        this.filters = filters;
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        filters.pop().doFilter(request, response, this);
    }
}
