package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Client implements AutoCloseable {
    private final int id;
    private Socket clientSocket;

    public Client(ServerSocket serverSocket) {
        id = new Random().nextInt(1000);
        initClientSocket(serverSocket);
    }

    private void initClientSocket(ServerSocket serverSocket) {
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("ERROR: Couldn't initiate client socket.\n" + e.getMessage());
        }
    }

    public int getId() {
        return id;
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
