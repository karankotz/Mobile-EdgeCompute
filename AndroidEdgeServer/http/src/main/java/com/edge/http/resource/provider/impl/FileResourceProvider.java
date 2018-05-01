package com.edge.http.resource.provider.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.edge.http.Headers;
import com.edge.http.MimeTypeMapping;
import com.edge.http.exception.protocol.ProtocolException;
import com.edge.http.exception.protocol.RangeNotSatisfiableProtocolException;
import com.edge.http.protocol.parser.MalformedInputException;
import com.edge.http.protocol.parser.impl.RangeParser;
import com.edge.http.protocol.serializer.impl.RangePartHeaderSerializer;
import com.edge.http.resource.provider.ResourceProvider;
import com.edge.http.servlet.HttpRequestWrapper;
import com.edge.http.servlet.HttpResponseWrapper;
import com.edge.http.servlet.HttpServletResponse;
import com.edge.http.servlet.Range;
import com.edge.http.servlet.RangeHelper;
import com.edge.http.utilities.IOUtilities;
import com.edge.http.utilities.RandomStringGenerator;
import com.edge.http.utilities.Utilities;

public class FileResourceProvider implements ResourceProvider {

    private final RangeParser rangeParser;
    private final RangeHelper rangeHelper;
    private final RangePartHeaderSerializer rangePartHeaderSerializer;

    private final MimeTypeMapping mimeTypeMapping;
    private final String basePath;

    public FileResourceProvider(final RangeParser rangeParser,
                                final RangeHelper rangeHelper,
                                final RangePartHeaderSerializer rangePartHeaderSerializer,
                                final MimeTypeMapping mimeTypeMapping,
                                final String basePath) {
        this.rangeParser = rangeParser;
        this.rangeHelper = rangeHelper;
        this.rangePartHeaderSerializer = rangePartHeaderSerializer;
        this.mimeTypeMapping = mimeTypeMapping;
        this.basePath = basePath;
    }

    @Override
    public boolean canLoad(String path) {
        File file = getFile(path);
        return file.exists() && file.isFile();
    }

    @Override
    public void load(String path, HttpRequestWrapper request, HttpResponseWrapper response) throws IOException {
        File file = getFile(path);

        // A server MUST ignore a Range header field received with a request method other than GET.
        boolean isGetRequest = request.getMethod().equals(HttpRequestWrapper.METHOD_GET);
        boolean isPartialRequest = isGetRequest && request.getHeaders().containsHeader(Headers.HEADER_RANGE);

        if (isPartialRequest) {
            loadPartialContent(request, response, file);
        } else {
            loadCompleteContent(request, response, file);
        }
    }

    private File getFile(String uri) {
        return new File(basePath + uri);
    }

    private void loadCompleteContent(HttpRequestWrapper request, HttpResponseWrapper response, File file) throws IOException {
        response.setContentType(mimeTypeMapping.getMimeTypeByExtension(Utilities.getExtension(file.getName())));
        response.setStatus(HttpServletResponse.STATUS_OK);
        response.setContentLength(file.length());
        response.getHeaders().setHeader(Headers.HEADER_ACCEPT_RANGES, "bytes");
        response.flushHeaders();

        if (!request.getMethod().equals(HttpRequestWrapper.METHOD_HEAD)) {
            InputStream fileInputStream = new FileInputStream(file);
            try {
                response.serveStream(fileInputStream);
            } finally {
                IOUtilities.closeSilently(fileInputStream);
            }
        }

        response.flush();
    }

    private void loadPartialContent(HttpRequestWrapper request, HttpResponseWrapper response, File file) throws IOException {
        List<Range> ranges;
        try {
            ranges = rangeParser.parse(request.getHeader(Headers.HEADER_RANGE));
        } catch (MalformedInputException e) {
            throw new ProtocolException("Malformed range header", e);
        }

        if (!rangeHelper.isSatisfiable(ranges, file.length())) {
            throw new RangeNotSatisfiableProtocolException();
        }

        response.setStatus(HttpServletResponse.STATUS_PARTIAL_CONTENT);
        response.getHeaders().setHeader(Headers.HEADER_CONTENT_RANGE, "bytes " + getRanges(ranges) + "/" + file.length());

        String contentType = mimeTypeMapping.getMimeTypeByExtension(Utilities.getExtension(file.getName()));

        long rangeLength = rangeHelper.getTotalLength(ranges);

        String boundary = null;
        if (ranges.size() == 1) {
            response.setContentLength(rangeLength);
            response.setContentType(contentType);
        } else {
            boundary = RandomStringGenerator.generate();
            response.setContentLength(rangePartHeaderSerializer.getPartHeadersLength(ranges, boundary, contentType, file.length()) + rangeLength);

            response.setContentType("multipart/byteranges; boundary=" + boundary);
        }
        response.flushHeaders();

        // TODO Test with large values, greater than those of BufferedInputStream internal buffer
        InputStream fileInputStream = new BufferedInputStream(new FileInputStream(file));
        if (ranges.size() == 1) {
            response.serveStream(fileInputStream, ranges.get(0));
        } else {
            response.serveStream(fileInputStream, ranges, boundary, contentType, file.length());
        }

        IOUtilities.closeSilently(fileInputStream);

        response.flush();
    }

    private String getRanges(List<Range> ranges) {
        StringBuilder rangesString = new StringBuilder();
        int counter = 0;
        for (Range range : ranges) {
            rangesString.append(range.getFrom()).append("-").append(range.getTo());
            if (++counter < ranges.size()) {
                rangesString.append(",");
            }
        }
        return rangesString.toString();
    }
}
