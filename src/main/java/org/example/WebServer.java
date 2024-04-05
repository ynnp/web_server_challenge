package org.example;

import java.io.IOException;
import java.net.ServerSocket;

public class WebServer implements AutoCloseable {
    private static final int PORT_NUMBER = 80;
    private static WebServer webServer;
    private ServerSocket serverSocket;

    public static WebServer getInstance() {
        if (webServer == null) {
            webServer = new WebServer();
        }

        return webServer;
    }

    private WebServer() {
        initServerSocket();
    }

    private void initServerSocket() {
        try {
            serverSocket = new ServerSocket(PORT_NUMBER);
            serverSocket.setReuseAddress(true);
        } catch (IOException e) {
            System.err.println("ERROR: Couldn't initiate server socket.\n" + e.getMessage());
        }
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
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
