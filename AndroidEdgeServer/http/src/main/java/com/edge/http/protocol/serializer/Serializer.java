package com.edge.http.protocol.serializer;

public interface Serializer<T> {

    String serialize(T input);
}
