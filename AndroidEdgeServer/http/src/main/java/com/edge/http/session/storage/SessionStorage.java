package com.edge.http.session.storage;

import java.io.IOException;

import com.edge.http.servlet.HttpSessionWrapper;


public interface SessionStorage {


    void persistSession(HttpSessionWrapper session) throws IOException;

    HttpSessionWrapper getSession(String id) throws IOException;

    boolean removeSession(HttpSessionWrapper session);
}
