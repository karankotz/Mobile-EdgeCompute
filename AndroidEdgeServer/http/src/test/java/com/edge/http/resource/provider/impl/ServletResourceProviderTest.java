package com.edge.http.resource.provider.impl;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Pattern;

import com.edge.http.configuration.FilterMapping;
import com.edge.http.configuration.ServletMapping;
import com.edge.http.configuration.impl.ServletMappingImpl;
import com.edge.http.exception.ServletException;
import com.edge.http.exception.ServletInitializationException;
import com.edge.http.exception.UnexpectedSituationException;
import com.edge.http.protocol.serializer.Serializer;
import com.edge.http.servlet.HttpRequestWrapper;
import com.edge.http.servlet.HttpResponseWrapper;
import com.edge.http.servlet.HttpSessionWrapper;
import com.edge.http.servlet.Servlet;
import com.edge.http.servlet.ServletConfig;
import com.edge.http.servlet.ServletContainer;
import com.edge.http.servlet.ServletContextWrapper;
import com.edge.http.servlet.StreamHelper;
import com.edge.http.servlet.loader.SampleServlet;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ServletResourceProviderTest {

    private static ServletContainer servletContainer;
    private static ServletContextWrapper servletContext;
    private static ServletResourceProvider servletResourceProvider;
    private static HttpRequestWrapper request;
    private static HttpResponseWrapper response;

    @Before
    public void setUp() throws ServletException, ServletInitializationException {
        servletContainer = mock(ServletContainer.class);

        when(servletContainer.getServletForClass(any(Class.class), any(ServletConfig.class))).
                thenReturn(mock(Servlet.class));

        servletContext = mock(ServletContextWrapper.class);
        when(servletContext.getContextPath()).thenReturn("/");
        ServletMapping servletMapping = new ServletMappingImpl(Pattern.compile("^.*$"), SampleServlet.class);
        when(servletContext.getServletMappings()).thenReturn(Arrays.asList(servletMapping));
        when(servletContext.getFilterMappings()).thenReturn(Collections.<FilterMapping>emptyList());

        servletResourceProvider = new ServletResourceProvider(
                servletContainer,
                Arrays.asList(servletContext)
        );

        response = new HttpResponseWrapper(mock(
                Serializer.class),
                mock(Serializer.class),
                mock(StreamHelper.class),
                mock(OutputStream.class));

        request = mock(HttpRequestWrapper.class);
        when(request.getServletContext()).thenReturn(servletContext);
    }

    @Test
    public void shouldHandleSessionOnTerminateIfSessionExists() throws IOException {
        when(request.getSession(false)).thenReturn(new HttpSessionWrapper("1"));
        servletResourceProvider.load("/", request, response);
        verify(servletContext, times(1)).handleSession(any(HttpSessionWrapper.class),
                any(HttpResponseWrapper.class));
    }

    @Test
    public void shouldNotHandleSessionOnTerminateIfSessionExists() throws IOException {
        when(request.getSession(false)).thenReturn(null);
        servletResourceProvider.load("/", request, response);
        verify(servletContext, times(0)).handleSession(any(HttpSessionWrapper.class),
                any(HttpResponseWrapper.class));
    }

    @Test(expected = UnexpectedSituationException.class)
    public void shouldWrapServletInitializationException()
            throws IOException, ServletException, ServletInitializationException {
        when(servletContainer.getServletForClass(any(Class.class), any(ServletConfig.class)))
                .thenThrow(new ServletInitializationException(new Exception()));
        servletResourceProvider.load("/", request, response);
    }
}
