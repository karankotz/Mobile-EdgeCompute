
package com.edge.http;

public class MultipartHeadersPart {

    private String fileName;
    private String contentType;
    private String name;

    /**
     * Returns the uploaded file name
     *
     * @return
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Returns the content type of the uploaded file
     *
     * @return
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Returns the name of the form post field
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the uploaded file name
     *
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Sets the content type of the uploaded file
     *
     * @param contentType
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * Sets the name of the form post field
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
}
