package org.example.request;

import java.io.BufferedReader;
import java.io.IOException;

public class Request {
    private final Method method;
    private final String path;

    public static Request from(BufferedReader bufferedReader) {
        String[] requestAsArray = readRequestIntoArrayFrom(bufferedReader);
        Method method = extractMethodFrom(requestAsArray);
        String path = extractPathFrom(requestAsArray);

        return new Request(method, path);
    }

    private static String[] readRequestIntoArrayFrom(BufferedReader bufferedReader) {
        String[] requestAsArray = new String[0];
        try {
            requestAsArray = bufferedReader.readLine().split(" ");
        } catch (IOException e) {
            System.err.println("ERROR: Couldn't process client's request.\n" + e.getMessage());
        }

        return requestAsArray;
    }

    private static Method extractMethodFrom(String[] requestAsArray) {
        String method = requestAsArray[0];
        return switch (method) {
            case "GET" -> Method.GET;
            default -> throw new IllegalArgumentException("ERROR: " + method + " method is not allowed.\n");
        };
    }

    private static String extractPathFrom(String[] requestAsArray) {
        return requestAsArray[1];
    }

    private Request(Method method, String path) {
        this.method = method;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public boolean isRootPath() {
        return path.equals("/") || path.equals("/index.html");
    }
}
