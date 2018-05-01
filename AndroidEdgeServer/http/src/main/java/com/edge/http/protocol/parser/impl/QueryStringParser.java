package com.edge.http.protocol.parser.impl;

import java.util.HashMap;
import java.util.Map;

import com.edge.http.protocol.parser.MalformedInputException;
import com.edge.http.protocol.parser.Parser;
import com.edge.http.utilities.Utilities;

public class QueryStringParser implements Parser<Map<String, String>> {

    @Override
    public Map<String, String> parse(String queryString) throws MalformedInputException {
        Map<String, String> parameters = new HashMap<>();
        String queryParametersArray[] = queryString.split("&");
        for (int i = 0; i < queryParametersArray.length; i++) {
            if (queryParametersArray[i].length() == 0) {
                continue;
            }

            String parameterPair[] = queryParametersArray[i].split("=", 2);
            if (parameterPair[0].length() == 0) {
                continue;
            }

            if(parameterPair.length > 1) {
                parameters.put(parameterPair[0], Utilities.urlDecode(parameterPair[1]));
            }
        }

        return parameters;
    }
}
