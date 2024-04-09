package org.example.response;

public enum ContentType {
    TEXT_PLAIN("text/plain"),
    TEXT_HTML("text/html");

    final String contentType;

    ContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return "Content-Type: " + contentType;
    }
}
