package com.edge.http.errorhandler;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import com.edge.http.FileUtils;
import com.edge.http.protocol.serializer.Serializer;
import com.edge.http.protocol.serializer.impl.RangePartHeaderSerializer;
import com.edge.http.servlet.HttpResponseWrapper;
import com.edge.http.servlet.RangeHelper;
import com.edge.http.servlet.StreamHelper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.mock;

public class AbstractHtmlErrorHandlerTest {

    private static OutputStream outputStream;
    private static HttpResponseWrapper response;

    @Before
    public void setUp() {
        outputStream = new ByteArrayOutputStream();
        response = new HttpResponseWrapper(
                mock(Serializer.class),
                mock(Serializer.class),
                new StreamHelper(new RangeHelper(), new RangePartHeaderSerializer()),
                outputStream);
    }

    @Test(expected = IOException.class)
    public void shouldThrowExceptionWhenDocumentPathIsMissing() throws Exception {
        AbstractHtmlErrorHandler handler
                = new SampleHtmlErrorHanlder("500", "", "", "/tmp/nonexistend");
        handler.serve(response);
    }

    @Test
    public void shouldServeBuiltinDocument() throws Exception {
        AbstractHtmlErrorHandler handler
                = new SampleHtmlErrorHanlder("500", "MSG_TOKEN", "EXPLANATION_TOKEN", null);
        handler.serve(response);

        assertThat(response.getStatus(), containsString("500"));
        assertThat(outputStream.toString(), containsString("MSG_TOKEN"));
        assertThat(outputStream.toString(), containsString("EXPLANATION_TOKEN"));
    }

    @Test
    public void shouldServeExternalDocument() throws IOException {
        File file = FileUtils.writeToTempFile("FILE_TOKEN");

        AbstractHtmlErrorHandler handler
                = new SampleHtmlErrorHanlder("", "", "", file.getAbsolutePath());
        handler.serve(response);
        assertThat(outputStream.toString(), containsString("FILE_TOKEN"));
    }

    private static class SampleHtmlErrorHanlder extends AbstractHtmlErrorHandler {
        public SampleHtmlErrorHanlder(String status, String message, String explanation, String errorDocumentPath) {
            super(status, message, explanation, errorDocumentPath);
        }
    }
}
