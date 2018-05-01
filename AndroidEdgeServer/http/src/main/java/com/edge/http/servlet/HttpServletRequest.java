
package com.edge.http.servlet;

import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;

import com.edge.http.Headers;

public interface HttpServletRequest extends ServletRequest {

    String getAuthType();


    String getContextPath();


    ServletContext getServletContext();

    String getPathTranslated();


    String getPathInfo();


    String getRemoteUser();


    Principal getUserPrincipal();


    boolean isRequestedSessionIdFromCookie();


    boolean isRequestedSessionIdFromURL();


    boolean isRequestedSessionIdValid();


    boolean isUserInRole(String role);


    String getRequestURI();

    StringBuilder getRequestURL();

    String getMethod();

    String getHeader(String name);


    int getIntHeader(String name);


    long getDateHeader(String name);

    Enumeration getHeaderNames();


    Cookie[] getCookies();

    String getQueryString();

    String getRequestedSessionId();

    HttpSession getSession(boolean create);

    HttpSession getSession();

    Collection<UploadedFile> getUploadedFiles();

    boolean isMultipart();

    Headers getHeaders();

    Cookie getCookie(String cookieName);

    String getPostParameter(String paramName);
}
