package com.edge.http.servlet;

import java.util.Enumeration;


public interface HttpSession {


    void setAttribute(String name, Object value) throws IllegalStateException;


    Object getAttribute(String name) throws IllegalStateException;

    Enumeration getAttributeNames() throws IllegalStateException;


    long getCreationTime() throws IllegalStateException;

    String getId() /*throws IllegalStateException*/;


    long getLastAccessedTime() throws IllegalStateException;

    int getMaxInactiveInterval();

    ServletContext getServletContext();

    void setMaxInactiveInterval(int maxInactiveInterval);

    void invalidate() throws IllegalStateException;

    void removeAttribute(String name) throws IllegalStateException;

    boolean isNew() throws IllegalStateException;
}
