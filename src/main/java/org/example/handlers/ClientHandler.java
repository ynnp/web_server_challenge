package org.example.handlers;

import org.example.Client;
import org.example.request.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientHandler {
    private final Client client;

    public ClientHandler(Client client) {
        this.client = client;
    }

    public void sendResponse() {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()))) {
            Request request = Request.from(bufferedReader);
            System.out.println("INFO: Client " + client.getId() + " requested resource: " + request.getPath());

            switch (request.getMethod()) {
                case GET -> new GetRequestHandler().handle(request, client.getOutputStream());
                default -> throw new IllegalArgumentException(request.getMethod() + " method is not supported.");
            }
        } catch (IOException e) {
            System.err.println("ERROR: Couldn't send response to client with id: " + client.getId() + "\n" + e.getMessage());
        }
    }
}
