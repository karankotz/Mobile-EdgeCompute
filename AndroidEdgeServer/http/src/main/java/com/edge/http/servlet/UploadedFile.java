package com.edge.http.servlet;

import java.io.File;

public class UploadedFile {

    private final String postFieldName;
    private final String fileName;
    private final File file;

    public UploadedFile(String postFieldName, String fileName, File file) {
        this.postFieldName = postFieldName;
        this.fileName = fileName;
        this.file = file;
    }

    /**
     * Deletes temporary file if the file has not been moved to another location
     *
     * @return true if deleted
     */
    public boolean destroy() {
        if (file.exists()) {
            return file.delete();
        }

        return false;
    }

    /**
     * Returns the HTML form postFieldName
     *
     * @return the HTML form postFieldName
     */
    public String getPostFieldName() {
        return postFieldName;
    }

    /**
     * Returns the postFieldName of uploaded file
     *
     * @return the postFieldName of uploaded file
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Returns uploaded file
     *
     * @return
     */
    public File getFile() {
        return file;
    }
}
