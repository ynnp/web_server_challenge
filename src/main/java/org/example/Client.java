package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Client implements AutoCloseable {
    private final int id;
    private Socket clientSocket;

    public Client(ServerSocket serverSocket) {
        this.id = generateId();
        connectTo(serverSocket);
    }

    private static int generateId() {
        return new Random().nextInt(1000);
    }

    private void connectTo(ServerSocket serverSocket) {
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("ERROR: Couldn't initiate client socket.\n" + e.getMessage());
        }
    }

    public int getId() {
        return id;
    }

    public InputStream getInputStream() {
        InputStream inputStream = null;
        try {
            inputStream = clientSocket.getInputStream();
        } catch (IOException e) {
            System.err.println("ERROR: Couldn't initialize client's input stream.\n" + e.getMessage());
        }

        return inputStream;
    }

    public OutputStream getOutputStream() {
        OutputStream outputStream = null;
        try {
            outputStream = clientSocket.getOutputStream();
        } catch (IOException e) {
            System.err.println("ERROR: Couldn't initialize client's output stream.\n" + e.getMessage());
        }

        return outputStream;
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
