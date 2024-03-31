package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.example.Configuration.PORT_NUMBER;

public class WebServer {
    public static ServerSocket createServerSocket() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT_NUMBER);
            serverSocket.setReuseAddress(true);
        } catch (IOException e) {
            System.err.println("ERROR: Couldn't initiate server socket.\n" + e.getMessage());
        }

        return serverSocket;
    }

    public static Socket createClientSocket(ServerSocket serverSocket) {
        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("ERROR: Couldn't initiate client socket.\n" + e.getMessage());
        }

        return clientSocket;
    }
}
