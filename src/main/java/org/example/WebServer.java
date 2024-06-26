package org.example;

import org.example.handlers.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServer implements Runnable, AutoCloseable {
    private final int port;
    private final ExecutorService executorService;
    private ServerSocket serverSocket;

    public WebServer(int port, int threadsNumber) {
        this.port = port;
        this.executorService = Executors.newFixedThreadPool(threadsNumber);
    }

    @Override
    public void run() {
        openServerSocket();
        while (true) {
            Client client = new Client(serverSocket);
            System.out.println("INFO: Created client with id: " + client.getId());
            ClientHandler clientHandler = new ClientHandler(client);
            executorService.submit(clientHandler::sendResponse);
        }
    }

    private void openServerSocket() {
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setReuseAddress(true);
        } catch (IOException e) {
            System.err.println("ERROR: Couldn't initiate server socket.\n" + e.getMessage());
        }
    }

    @Override
    public void close() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.err.println("ERROR: Couldn't close server socket.\n" + e.getMessage());
        }
    }
}
