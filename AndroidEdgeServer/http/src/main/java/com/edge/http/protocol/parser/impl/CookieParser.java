package com.edge.http.protocol.parser.impl;

import java.util.HashMap;
import java.util.Map;

import com.edge.http.protocol.parser.MalformedInputException;
import com.edge.http.protocol.parser.Parser;
import com.edge.http.servlet.Cookie;
import com.edge.http.utilities.Utilities;

public class CookieParser implements Parser<Map<String, Cookie>> {

    /**
     * Parses cookie string, returns an array representing cookies read.
     *
     * @param input
     * @return
     * @throws MalformedInputException
     */
    @Override
    public Map<String, Cookie> parse(String input) throws MalformedInputException {
        Map<String, Cookie> cookies = new HashMap<>();

        // Splitting separate cookies array
        String cookiesStr[] = input.split(";");
        for (int i = 0; i < cookiesStr.length; i++) {
            // Splitting cookie name=value pair
            String cookieValues[] = cookiesStr[i].split("=", 2);
            String cookieName = cookieValues[0].trim();
            if (cookieValues.length > 1 && cookieName.length() > 0) {
                Cookie cookie = new Cookie(cookieName, Utilities.urlDecode(cookieValues[1]));
                cookies.put(cookie.getName(), cookie);
            }
        }

        return cookies;
    }
}
