package com.edge.http.protocol.serializer.impl;

import java.util.List;

import com.edge.http.Headers;
import com.edge.http.RangePartHeader;
import com.edge.http.servlet.Range;
import com.edge.http.protocol.serializer.Serializer;

public class RangePartHeaderSerializer implements Serializer<RangePartHeader> {

    private static final String NEW_LINE = "\r\n";
    private static final String DASH_DASH = "--";

    @Override
    public String serialize(RangePartHeader input) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(DASH_DASH)
                .append(input.getBoundary())
                .append(NEW_LINE)
                .append(Headers.HEADER_CONTENT_TYPE)
                .append(": ")
                .append(input.getContentType())
                .append(NEW_LINE)
                .append(Headers.HEADER_CONTENT_RANGE)
                .append(": bytes ")
                .append(input.getRange().getFrom())
                .append("-")
                .append(input.getRange().getTo())
                .append("/")
                .append(input.getTotalLength())
                .append(NEW_LINE)
                .append(NEW_LINE);

        return stringBuilder.toString();
    }

    /**
     * Returns the last deliminator.
     *
     * @param boundary
     * @return
     */
    public String serializeLastBoundaryDeliminator(String boundary) {
        return DASH_DASH + boundary + DASH_DASH + NEW_LINE;
    }

    public long getPartHeadersLength(List<Range> ranges, String boundary, String contentType, long totalLength) {
        if (ranges.size() < 2) {
            return 0;
        }

        String partHeader = serialize(new RangePartHeader(new Range(0L, 0L), boundary, contentType, 0L));
        int partHeaderWithoutDigits = partHeader.length() - 3;

        long partHeadersLength = 0;

        // Initial new line
        partHeadersLength += NEW_LINE.length();

        for (Range range : ranges) {
            partHeadersLength += NEW_LINE.length()
                    + partHeaderWithoutDigits
                    + Long.toString(range.getFrom()).length()
                    + Long.toString(range.getTo()).length()
                    + Long.toString(totalLength).length();
        }

        partHeadersLength += serializeLastBoundaryDeliminator(boundary).length();

        return partHeadersLength;
    }
}
