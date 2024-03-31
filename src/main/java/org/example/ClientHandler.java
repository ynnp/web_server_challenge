package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static org.example.WebServer.createClientSocket;
import static org.example.WebServer.createServerSocket;

public class ClientHandler {
    final String RESPONSE_200_OK = "HTTP/1.1 200 OK\r\n\r\n";
    final String RESPONSE_404_NOT_FOUND = "HTTP/1.1 404 NOT FOUND \r\n\r\n";

    public void sendResponseToClient() {
        try (ServerSocket serverSocket = createServerSocket();
             Socket clientSocket = createClientSocket(serverSocket)) {
            String requestedResource = getRequestedResource(clientSocket);
            if (requestedResource.equals("/")) {
                String response = RESPONSE_200_OK + "Requested path: " + requestedResource + "\r\n";
                clientSocket.getOutputStream().write(response.getBytes(StandardCharsets.UTF_8));
            } else {
                clientSocket.getOutputStream().write(RESPONSE_404_NOT_FOUND.getBytes(StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            System.err.println("ERROR: Couldn't send response to client.\n" + e.getMessage());
        }
    }

    private String getRequestedResource(Socket clientSocket) {
        String[] linesArray = new String[0];
        try {
            InputStream inputStream = clientSocket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = bufferedReader.readLine();
            linesArray = line.split(" ");
        } catch (IOException e) {
            System.err.println("ERROR: Couldn't read client request.\n" + e.getMessage());
        }

        return linesArray[1];
    }
}
