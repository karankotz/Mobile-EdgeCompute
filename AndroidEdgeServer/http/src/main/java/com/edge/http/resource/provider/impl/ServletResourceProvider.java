package com.edge.http.resource.provider.impl;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.edge.http.Headers;
import com.edge.http.configuration.FilterMapping;
import com.edge.http.configuration.ServletMapping;
import com.edge.http.exception.FilterInitializationException;
import com.edge.http.exception.ServletException;
import com.edge.http.exception.ServletInitializationException;
import com.edge.http.exception.UnexpectedSituationException;
import com.edge.http.resource.provider.ResourceProvider;
import com.edge.http.servlet.Filter;
import com.edge.http.servlet.FilterChain;
import com.edge.http.servlet.FilterConfig;
import com.edge.http.servlet.FilterConfigWrapper;
import com.edge.http.servlet.HttpRequestWrapper;
import com.edge.http.servlet.HttpResponseWrapper;
import com.edge.http.servlet.HttpServletRequest;
import com.edge.http.servlet.HttpServletResponse;
import com.edge.http.servlet.HttpSessionWrapper;
import com.edge.http.servlet.Servlet;
import com.edge.http.servlet.ServletConfigWrapper;
import com.edge.http.servlet.ServletContainer;
import com.edge.http.servlet.ServletContext;
import com.edge.http.servlet.ServletContextHelper;
import com.edge.http.servlet.ServletContextWrapper;
import com.edge.http.servlet.UploadedFile;
import com.edge.http.servlet.impl.FilterChainImpl;

public class ServletResourceProvider implements ResourceProvider {

    private static final Logger LOGGER = Logger.getLogger(ServletResourceProvider.class.getName());

    private final ServletContainer servletContainer;
    private final List<ServletContextWrapper> servletContexts;
    private final ServletContextHelper servletContextHelper = new ServletContextHelper();

    public ServletResourceProvider(final ServletContainer servletContainer,
                                   final List<ServletContextWrapper> servletContexts) {
        this.servletContainer = servletContainer;
        this.servletContexts = servletContexts;
    }

    @Override
    public boolean canLoad(String path) {
        ServletContext servletContext = servletContextHelper.getResolvedContext(servletContexts, path);
        return servletContext != null && servletContextHelper.getResolvedServletMapping(servletContext, path) != null;
    }

    @Override
    public void load(String path, HttpRequestWrapper request, HttpResponseWrapper response) throws IOException {
        ServletContextWrapper servletContext = servletContextHelper.getResolvedContext(servletContexts, path);
        Objects.requireNonNull(servletContext);
        ServletMapping servletMapping = servletContextHelper.getResolvedServletMapping(servletContext, path);

        request.setServletContext(servletContext);

        Servlet servlet = getServlet(servletMapping, new ServletConfigWrapper(servletContext));

        response.setStatus(HttpServletResponse.STATUS_OK);
        try {
            FilterChainImpl filterChain = getFilterChain(path, servletContext, servlet);
            filterChain.doFilter(request, response);
            terminate(request, response);
        } catch (ServletException | FilterInitializationException e) {
            throw new UnexpectedSituationException(e);
        }
    }

    private Servlet getServlet(ServletMapping servletMapping, ServletConfigWrapper servletConfig) {
        Servlet servlet;
        try {
            servlet = servletContainer.getServletForClass(servletMapping.getServletClass(), servletConfig);
        } catch (ServletInitializationException | ServletException e) {
            throw new UnexpectedSituationException(e);
        }
        return servlet;
    }

    private FilterChainImpl getFilterChain(String path, ServletContextWrapper servletContext, final Servlet servlet)
            throws FilterInitializationException, ServletException {

        ArrayDeque<Filter> arrayDeque = new ArrayDeque<>(getFilterMappingsForPath(path, servletContext));
        arrayDeque.add(new Filter() {
            @Override
            public void init(FilterConfig filterConfig) throws ServletException {

            }

            @Override
            public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
                servlet.service(request, response);
            }
        });
        return new FilterChainImpl(arrayDeque);
    }

    private List<Filter> getFilterMappingsForPath(String path, ServletContextWrapper servletContext)
            throws FilterInitializationException, ServletException {

        FilterConfig filterConfig = new FilterConfigWrapper(servletContext);

        List<Filter> filters = new ArrayList<>();
        for (FilterMapping filterMapping : servletContextHelper.getFilterMappingsForPath(servletContext, path)) {
            filters.add(servletContainer.getFilterForClass(filterMapping.getFilterClass(), filterConfig));
        }

        return filters;
    }

    /**
     * Terminates servlet. Sets all necessary headers, flushes content.
     *
     * @param request
     * @param response
     * @throws IOException
     */
    private void terminate(HttpRequestWrapper request, HttpResponseWrapper response) throws IOException {
        freeUploadedUnprocessedFiles(request.getUploadedFiles());

        HttpSessionWrapper session = (HttpSessionWrapper) request.getSession(false);
        if (session != null) {
            try {
                ((ServletContextWrapper) request.getServletContext()).handleSession(session, response);
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, "Unable to persist session", e);
            }
        }

        if (!response.isCommitted()) {
            if (response.getContentType() == null) {
                response.setContentType("text/html");
            }

            response.getHeaders().setHeader(Headers.HEADER_CACHE_CONTROL, "no-cache");
            response.getHeaders().setHeader(Headers.HEADER_PRAGMA, "no-cache");
        }

        response.flush();
    }

    private void freeUploadedUnprocessedFiles(Collection<UploadedFile> uploadedFiles) {
        for (UploadedFile uploadedFile : uploadedFiles) {
            uploadedFile.destroy();
        }
    }
}
