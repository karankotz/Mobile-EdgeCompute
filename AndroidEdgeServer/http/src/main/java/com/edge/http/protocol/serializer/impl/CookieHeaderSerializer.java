package com.edge.http.protocol.serializer.impl;
import java.util.Date;
import com.edge.http.protocol.serializer.Serializer;
import com.edge.http.servlet.Cookie;
import com.edge.http.utilities.DateProvider;
import com.edge.http.utilities.Utilities;

public class CookieHeaderSerializer implements Serializer<Cookie> {

    private static final String SEPARATOR = "; ";
    private static final String EQUALS = "=";

    private final DateProvider dateProvider;

    public CookieHeaderSerializer(DateProvider dateProvider) {
        this.dateProvider = dateProvider;
    }

    @Override
    public String serialize(Cookie cookie) {
        StringBuilder sb = new StringBuilder();
        sb.append(cookie.getName())
                .append(EQUALS)
                .append(Utilities.urlEncode(cookie.getValue()));

        if (cookie.getMaxAge() != -1) {
            sb.append(SEPARATOR)
                    .append("Expires")
                    .append(EQUALS)
                    .append(getExpires(cookie.getMaxAge()));
        }
        if (cookie.getPath() != null) {
            sb.append(SEPARATOR)
                    .append("Path")
                    .append(EQUALS)
                    .append(cookie.getPath());
        }
        if (cookie.getDomain() != null) {
            sb.append(SEPARATOR)
                    .append("Domain")
                    .append(EQUALS)
                    .append(cookie.getDomain());
        }
        if (cookie.getComment() != null) {
            sb.append(SEPARATOR)
                    .append("Comment")
                    .append(EQUALS)
                    .append(cookie.getComment());
        }
        if (cookie.isHttpOnly()) {
            sb.append(SEPARATOR)
                    .append("HttpOnly");
        }
        if (cookie.isSecure()) {
            sb.append(SEPARATOR)
                    .append("Secure");
        }

        return sb.toString();
    }

    private String getExpires(long maxAge) {
        long maxAgeMs = maxAge * 1000L;
        return Utilities.dateFormat(new Date(dateProvider.now().getTime() + maxAgeMs));
    }
}
