package com.edge.hierarchical.resource.provider.impl;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import home.Calculator;
import home.Index;
import home.Login;
import home.Logout;
import home.ServerStats;
import home.filter.LogoutFilter;
import home.filter.SecurityFilter;
import com.edge.http.MimeTypeMapping;
import com.edge.http.configuration.ServerConfig;
import com.edge.http.cli.DefaultServerConfigFactory;
import com.edge.http.configuration.ServletContextConfigurationBuilder;
import com.edge.http.protocol.parser.impl.RangeParser;
import com.edge.http.protocol.serializer.impl.RangePartHeaderSerializer;
import com.edge.http.resource.provider.ResourceProvider;
import com.edge.http.resource.provider.impl.FileResourceProvider;
import com.edge.http.servlet.RangeHelper;
import com.edge.http.session.storage.SessionStorage;
import com.edge.hierarchical.AssetResourceProvider;

public class AndroidServerConfigFactory extends DefaultServerConfigFactory {

    private Object context;

    public AndroidServerConfigFactory(Object context) {
        this.context = context;
    }

    @Override
    protected String getBasePath() {
        String baseConfigPath;
        if (context != null) {
            baseConfigPath = Environment.getExternalStorageDirectory() + "/httpd/";
        } else {
            baseConfigPath = "./app/src/main/assets/conf/";
        }
        return baseConfigPath;
    }

    @Override
    protected String getTempPath() {
        if (context != null) {
            return ((Context) context).getCacheDir().getAbsolutePath() + File.separator + "webserver" + File.separator;
        } else {
            return super.getTempPath();
        }
    }

    @Override
    protected Map<String, Object> getAdditionalServletContextAttributes() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put(Context.class.getName(), context);
        return attributes;
    }

    @Override
    protected Set<ResourceProvider> getAdditionalResourceProviders(ServerConfig serverConfig) {
        Set<ResourceProvider> resourceProviders = new HashSet<>();
        resourceProviders.add(getAssetsResourceProvider(serverConfig.getMimeTypeMapping()));
        return resourceProviders;
    }

    @Override
    protected ServletContextConfigurationBuilder getServletContextConfigurationBuilder(SessionStorage sessionStorage, ServerConfig serverConfig) {
        return super.getServletContextConfigurationBuilder(sessionStorage, serverConfig)
                .addServletContext()
                    .withContextPath("/api/1.0")
                .end()

                .addServletContext()
                    .withContextPath("/home")
                    .addFilter()
                        .withUrlPattern(Pattern.compile("^.*$"))
                        .withUrlExcludedPattern(Pattern.compile("^/(?:Login|Logout)"))
                        .withFilterClass(SecurityFilter.class)
                    .end()
                    .addFilter()
                        .withUrlPattern(Pattern.compile("^/Logout$"))
                        .withFilterClass(LogoutFilter.class)
                    .end()
                    .addServlet()
                        .withUrlPattern(Pattern.compile("^/Index$"))
                        .withServletClass(Index.class)
                    .end()
                    .addServlet()
                        .withUrlPattern(Pattern.compile("^/$"))
                        .withServletClass(Index.class)
                    .end()
                    .addServlet()
                        .withUrlPattern(Pattern.compile("^/Login$"))
                        .withServletClass(Login.class)
                    .end()
                    .addServlet()
                    .withUrlPattern(Pattern.compile("^/Calculator$"))
                    .withServletClass(Calculator.class)
                    .end()
                    .addServlet()
                        .withUrlPattern(Pattern.compile("^/Logout$"))
                        .withServletClass(Logout.class)
                    .end()
                    .addServlet()
                        .withUrlPattern(Pattern.compile("^/ServerStats$"))
                        .withServletClass(ServerStats.class)
                    .end()
                .end();

    }

    private ResourceProvider getAssetsResourceProvider(MimeTypeMapping mimeTypeMapping) {
        String assetBasePath = "public";
        if (context != null) {
            AssetManager assetManager = ((Context) context).getResources().getAssets();
            return new AssetResourceProvider(assetManager, assetBasePath);
        } else {
            return new FileResourceProvider(new RangeParser(), new RangeHelper(),
                    new RangePartHeaderSerializer(), mimeTypeMapping, "./app/src/main/assets/" + assetBasePath);
        }
    }
}
