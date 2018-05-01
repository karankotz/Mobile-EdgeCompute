package com.edge.http.protocol.serializer.impl;
import java.util.Set;

import com.edge.http.Headers;
import com.edge.http.protocol.serializer.Serializer;

public class HeadersSerializer implements Serializer<Headers> {

    private static final String NEW_LINE = "\r\n";
    private static final String KEY_VALUE_SEPARATOR = ": ";

    @Override
    public String serialize(Headers headers) {
        Set<String> names = headers.keySet();
        StringBuilder sb = new StringBuilder();
        for (String name : names) {
            sb.append(name)
                    .append(KEY_VALUE_SEPARATOR)
                    .append(headers.getHeader(name))
                    .append(NEW_LINE);
        }
        sb.append(NEW_LINE);

        return sb.toString();
    }
}
