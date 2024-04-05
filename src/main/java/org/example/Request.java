package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Request {
    private final String method;
    private final String resource;
    private String[] requestAsArray;

    public Request(InputStream inputStream) {
        parseRequestIntoRequestAsArray(inputStream);
        method = extractMethodFromRequestAsArray();
        resource = extractResourceFromRequestAsArray();
    }

    private void parseRequestIntoRequestAsArray(InputStream inputStream) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = bufferedReader.readLine();
            requestAsArray = line.split(" ");
        } catch (IOException e) {
            System.err.println("ERROR: Couldn't parse client request.\n" + e.getMessage());
        }
    }

    private String extractMethodFromRequestAsArray() {
        return requestAsArray[0];
    }

    private String extractResourceFromRequestAsArray() {
        return requestAsArray[1];
    }

    public String getMethod() {
        return method;
    }

    public String getResource() {
        return resource;
    }

    public boolean isGetMethod() {
        return method.equals("GET");
    }

    public boolean isRootResource() {
        return resource.equals("/");
    }

    public boolean isIndexHtmlResource() {
        return resource.equals("/index.html");
    }
}
