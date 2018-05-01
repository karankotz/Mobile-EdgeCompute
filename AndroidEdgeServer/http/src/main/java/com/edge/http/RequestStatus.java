package com.edge.http;

public class RequestStatus {

    private String method;
    private String uri;
    private String queryString;
    private String protocol;

    /**
     * Returns HTTP method.
     *
     * @return
     */
    public String getMethod() {
        return method;
    }


    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * Returns requested URI. The URI does not contain query string.
     *
     * @return
     */
    public String getUri() {
        return uri;
    }

    /**
     * Sets requested URI. The URI must not contain query string.
     *
     * @param uri
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * Returns request query string.
     *
     * @return
     */
    public String getQueryString() {
        return queryString;
    }

    /**
     * Sets request query string.
     *
     * @param queryString
     */
    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    /**
     * Returns HTTP protocol.
     *
     * @return
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * Sets HTTP protocol.
     *
     * @param protocol
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
