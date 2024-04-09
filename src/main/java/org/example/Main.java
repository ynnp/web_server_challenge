package org.example;

public class Main {
    public static void main(String[] args) {
        WebServer webServer = new WebServer(80, 3);
        webServer.run();
    }
}