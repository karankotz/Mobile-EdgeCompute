package com.edge.http;

public class PathHelper {

    private static final String SLASH = "/";

    public boolean isPathContainingIllegalCharacters(String path) {
        return path == null || path.startsWith("../") || path.indexOf("/../") != -1;
    }

    public String getNormalizedDirectoryPath(String path) {
        if (isDirectoryPath(path)) {
            return path;
        }
        return path + SLASH;
    }

    public boolean isDirectoryPath(String originalPath) {
        return originalPath.substring(originalPath.length() - 1).equals(SLASH);
    }
}
