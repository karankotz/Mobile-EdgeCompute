package com.edge.http.servlet;

import java.util.List;

public class RangeHelper {

    /**
     * Returns computed range length.
     *
     * @param range
     * @return
     */
    public long getRangeLength(Range range) {
        return range.getTo() - range.getFrom() + 1;
    }

    /**
     * Tells whether the range is valid.
     *
     * @param range
     * @return
     */
    public boolean isRangeValid(Range range) {
        return (range.getFrom() > -1 && range.getTo() >= range.getFrom());
    }

    /**
     * Computes total length of the provided ranges.
     *
     * @param ranges
     * @return
     */
    public long getTotalLength(List<Range> ranges) {
        int totalLength = 0;
        for (Range range : ranges) {
            totalLength += getRangeLength(range);
        }
        return totalLength;
    }

    /**
     * Tells whether the ranges are satisfiable for the given stream length
     *
     * @param ranges
     * @param streamLength
     * @return
     */
    public boolean isSatisfiable(Iterable<Range> ranges, long streamLength) {
        for (Range range : ranges) {
            if (range.getTo() >= streamLength || !isRangeValid(range)) {
                return false;
            }
        }

        return true;
    }
}
