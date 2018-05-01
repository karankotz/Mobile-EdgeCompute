package com.edge.hierarchical;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;

import com.edge.http.exception.UnexpectedSituationException;
import com.edge.http.resource.provider.ResourceProvider;
import com.edge.http.servlet.HttpRequestWrapper;
import com.edge.http.servlet.HttpResponseWrapper;
import com.edge.http.servlet.HttpServletResponse;
import com.edge.http.utilities.IOUtilities;

public class AssetResourceProvider implements ResourceProvider {

    private final AssetManager assetManager;
    private final String basePath;

    public AssetResourceProvider(AssetManager assetManager, String basePath) {
        this.assetManager = assetManager;
        this.basePath = basePath;
    }

    @Override
    public boolean canLoad(String path) {
        try {
            getInputStream(getAssetPath(path));
            return true;
        } catch (IOException e) {
        }

        return false;
    }

    private InputStream getInputStream(String assetPath) throws IOException {
        return assetManager.open(assetPath);
    }

    @Override
    public void load(String path, HttpRequestWrapper request, HttpResponseWrapper response) {
        String assetPath = getAssetPath(path);
        InputStream inputStream = null;
        try {
            inputStream = getInputStream(assetPath);

            response.setStatus(HttpServletResponse.STATUS_OK);
            try (AssetFileDescriptor afd = assetManager.openFd(assetPath)) {
                response.setContentLength(afd.getLength());
            } catch (IOException e) {
            }


            response.flushHeaders();
            response.serveStream(inputStream);
        } catch (IOException e) {
            throw new UnexpectedSituationException(e);
        } finally {
            IOUtilities.closeSilently(inputStream);
        }
    }

    @NonNull
    private String getAssetPath(String path) {
        return basePath + path;
    }
}
