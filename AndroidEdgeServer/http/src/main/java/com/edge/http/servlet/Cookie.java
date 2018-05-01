package com.edge.http.servlet;

public class Cookie {

    private String comment;
    private String domain;
    private String path;
    private int maxAge = -1;
    private String name;
    private String value;
    private boolean secure;
    private boolean httpOnly;

    public Cookie(String name, String value) throws IllegalArgumentException {
        checkNameForIllegalCharacters(name);

        this.name = name;
        this.value = value;
    }

    public Cookie(String name, int value) throws IllegalArgumentException {
        this(name, Integer.toString(value));
    }


    public Cookie(String name, long value) throws IllegalArgumentException {
        this(name, Long.toString(value));
    }


    public Cookie(String name, double value) throws IllegalArgumentException {
        this(name, Double.toString(value));
    }


    public Cookie(String name, boolean value) throws IllegalArgumentException {
        this(name, Boolean.toString(value));
    }


    private void checkNameForIllegalCharacters(String name) throws IllegalArgumentException {
        char illegalCharacters[] = {';', ' ', '\n', '\r', '\t'};
        for (char illegalChar : illegalCharacters) {
            if (name.indexOf(illegalChar) > -1) {
                throw new IllegalArgumentException("Cookie name must be composed of ASCI characters");
            }
        }
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDomain() {
        return domain;
    }


    public void setDomain(String domain) {
        this.domain = domain;
    }


    public String getPath() {
        return path;
    }


    public void setPath(String path) {
        this.path = path;
    }

    public int getMaxAge() {
        return maxAge;
    }


    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }


    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }


    public void setValue(String value) {
        this.value = value;
    }


    public boolean isSecure() {
        return secure;
    }


    public void setSecure(boolean secure) {
        this.secure = secure;
    }


    public boolean isHttpOnly() {
        return httpOnly;
    }


    public void setHttpOnly(boolean httpOnly) {
        this.httpOnly = httpOnly;
    }
}
