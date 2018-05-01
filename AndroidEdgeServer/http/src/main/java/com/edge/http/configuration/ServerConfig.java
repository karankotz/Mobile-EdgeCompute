package com.edge.http.configuration;

import java.util.List;

import com.edge.http.MimeTypeMapping;
import com.edge.http.resource.provider.ResourceProvider;
public interface ServerConfig {

    /**
     * Returns base path.
     *
     * @return
     */
    String getBasePath();

    /**
     * Returns document root path.
     *
     * @return
     */
    String getDocumentRootPath();

    /**
     * Returns server temp path.
     *
     * @return
     */
    String getTempPath();

    /**
     * Returns the listen port.
     *
     * @return
     */
    int getListenPort();

    /**
     * Returns the mime type mapping.
     *
     * @return
     */
    MimeTypeMapping getMimeTypeMapping();

    /**
     * Returns the number of maximum allowed threads.
     *
     * @return
     */
    int getMaxServerThreads();

    /**
     * Returns whether the server should keep the connections alive.
     *
     * @return
     */
    boolean isKeepAlive();

    /**
     * Returns error 404 file path.
     *
     * @return
     */
    String getErrorDocument404Path();

    /**
     * Returns the error 403 file path.
     *
     * @return
     */
    String getErrorDocument403Path();

    /**
     * Returns the directory index.
     *
     * @return
     */
    List<String> getDirectoryIndex();


    List<String> getSupportedMethods();


    List<ResourceProvider> getResourceProviders();


    String getAttribute(String name);
}
