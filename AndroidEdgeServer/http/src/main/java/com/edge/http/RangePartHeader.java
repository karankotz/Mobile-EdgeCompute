package com.edge.http;

import com.edge.http.servlet.Range;

public class RangePartHeader {

    private final Range range;
    private final String boundary;
    private final String contentType;
    private final long totalLength;

    public RangePartHeader(final Range range,
                           final String boundary,
                           final String contentType,
                           final long totalLength) {
        this.range = range;
        this.boundary = boundary;
        this.contentType = contentType;
        this.totalLength = totalLength;
    }

    public Range getRange() {
        return range;
    }

    public String getBoundary() {
        return boundary;
    }

    public String getContentType() {
        return contentType;
    }

    public long getTotalLength() {
        return totalLength;
    }
}
