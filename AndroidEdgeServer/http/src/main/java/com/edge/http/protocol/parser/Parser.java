package com.edge.http.protocol.parser;
public interface Parser<T> {

    T parse(String input) throws MalformedInputException;
}
