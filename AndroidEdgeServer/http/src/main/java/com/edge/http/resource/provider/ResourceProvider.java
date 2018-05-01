package com.edge.http.resource.provider;
import java.io.IOException;

import com.edge.http.servlet.HttpRequestWrapper;
import com.edge.http.servlet.HttpResponseWrapper;

public interface ResourceProvider {


    boolean canLoad(String path);

    void load(String path, HttpRequestWrapper request, HttpResponseWrapper response) throws IOException;
}
