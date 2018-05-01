package com.edge.http.servlet;

import java.util.Enumeration;
import java.util.List;

import com.edge.http.configuration.FilterMapping;
import com.edge.http.configuration.ServletMapping;

public interface ServletContext {

    /**
     * Sets context attribute.
     *
     * @param name
     * @param value
     * @throws IllegalStateException
     */
    void setAttribute(String name, Object value);

    /**
     * Gets context attribute of the specified name.
     *
     * @param name Attribute name
     * @return
     * @throws IllegalStateException
     */
    Object getAttribute(String name);

    /**
     * Returns enumeration representing attribute names.
     *
     * @return
     * @throws IllegalStateException
     */
    Enumeration getAttributeNames();

    /**
     * Returns the MIME type of the specified file, or null if the MIME type is not known.
     *
     * @param file
     * @return
     */
    String getMimeType(String file);

    /**
     * Returns servlet URL pattern to servlet class mappings.
     *
     * @return
     */
    List<ServletMapping> getServletMappings();

    /**
     * Returns filter URL pattern to filter class mappings.
     *
     * @return
     */
    List<FilterMapping> getFilterMappings();

    /**
     * Returns servlet context path.
     *
     * @return
     */
    String getContextPath();
}
