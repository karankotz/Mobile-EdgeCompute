package com.edge.http.protocol.parser.impl;

import com.edge.http.RequestStatus;
import com.edge.http.protocol.parser.MalformedInputException;
import com.edge.http.protocol.parser.Parser;

public class RequestStatusParser implements Parser<RequestStatus> {

    private static final int NUMBER_OF_CHUNKS = 3;

    @Override
    public RequestStatus parse(String input) throws MalformedInputException {
        RequestStatus status = new RequestStatus();
        status.setQueryString("");
        String uri;


        String statusArray[] = input.split(" ", NUMBER_OF_CHUNKS);

        if (statusArray.length < NUMBER_OF_CHUNKS) {
            throw new MalformedInputException("Input status string should be composed out of " +
                    NUMBER_OF_CHUNKS + " chunks. Received " + input);
        }

        // First element of the array is the HTTP method
        status.setMethod(statusArray[0].toUpperCase());
        // Second element of the array is the HTTP queryString
        uri = statusArray[1];

        // Protocol is the third part of the status line
        status.setProtocol(statusArray[2].trim());

        int questionMarkPosition = uri.indexOf("?");
        if (questionMarkPosition > -1) {
            status.setQueryString(uri.substring(questionMarkPosition + 1));
            uri = uri.substring(0, questionMarkPosition);
        }
        status.setUri(uri);
        return status;
    }
}
