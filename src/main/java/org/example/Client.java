package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Client implements AutoCloseable {
    private Socket clientSocket;

    public Client(WebServer webServer) {
        initClientSocket(webServer.getServerSocket());
    }

    private void initClientSocket(ServerSocket serverSocket) {
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("ERROR: Couldn't initiate client socket.\n" + e.getMessage());
        }
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    @Override
    public void close() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("ERROR: Couldn't close client socket.\n" + e.getMessage());
        }
    }
}
