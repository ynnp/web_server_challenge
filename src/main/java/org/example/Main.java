package org.example;

public class Main {
    public static void main(String[] args) {
        try (WebServer webServer = WebServer.getInstance();
             Client client = new Client(webServer)) {
            ClientHandler clientHandler = new ClientHandler(client);
            clientHandler.sendResponseToClient();
        }
    }
}