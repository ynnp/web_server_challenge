package org.example.handlers;

import org.example.request.Request;
import org.example.response.ContentType;
import org.example.response.Response;
import org.example.response.ResponseCode;

import java.io.IOException;
import java.io.OutputStream;

public interface RequestHandler {
    void handle(Request request, OutputStream outputStream) throws IOException;
    default Response buildResponse(ResponseCode responseCode, ContentType contentType, String body) {
        return new Response.Builder()
                .withResponseCode(responseCode)
                .withContentType(contentType)
                .withBody(body)
                .build();
    }

    default Response buildResponse(ResponseCode responseCode, ContentType contentType) {
        return new Response.Builder()
                .withResponseCode(responseCode)
                .withContentType(contentType)
                .build();
    }

    default Response buildResponse(ResponseCode responseCode) {
        return new Response.Builder()
                .withResponseCode(responseCode)
                .build();
    }

    default void send(Response response, OutputStream outputStream) {
        System.out.println("INFO: Sending response: " + response.toString());
        try {
            outputStream.write(response.serialize());
            outputStream.flush();
        } catch (IOException e) {
            System.err.println("ERROR: Couldn't send response: " + response.toString() + "\n" + e.getMessage());
        }
    }
}
