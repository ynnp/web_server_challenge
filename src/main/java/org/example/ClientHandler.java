package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ClientHandler {
    private final Client client;

    public ClientHandler(Client client) {
        this.client = client;
    }

    public void sendResponseToClient() {
        Request request = getClientsRequest();
        try {
            if (request.isGetMethod() && (request.isRootResource() || request.isIndexHtmlResource())) {
                writeSuccessfulResponse();
            } else {
                writeUnsuccessfulResponse();
            }
        } catch (IOException e) {
            System.err.println("ERROR: Couldn't send response to a client.\n" + e.getMessage());
        }

    }

    private Request getClientsRequest() {
        Request request = null;
        try {
            InputStream inputStream = client.getClientSocket().getInputStream();
            request = new Request(inputStream);
        } catch (IOException e) {
            System.err.println("ERROR: Couldn't initialize client's request.\n" + e.getMessage());
        }

        return request;
    }

    private void writeSuccessfulResponse() throws IOException {
        writeResponse("HTTP/1.1 200 OK\r\n\r\n" + getWelcomeHtmlPage());
    }

    private void writeUnsuccessfulResponse() throws IOException {
        writeResponse("HTTP/1.1 404 NOT FOUND \r\n\r\n");
    }

    private void writeResponse(String response) throws IOException {
        client.getClientSocket().getOutputStream().write(response.getBytes(StandardCharsets.UTF_8));
    }

    private String getWelcomeHtmlPage() throws MalformedURLException {
        StringBuilder htmlPage = new StringBuilder();
        String line;
        URL URL = new URL("file:/webserver/src/main/resources/index.html");
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(URL.openStream()))) {
            while ((line = bufferedReader.readLine()) != null) {
                htmlPage.append(line);
            }
        } catch (Exception e) {
            System.err.println("ERROR: Couldn't open index.html file.\n" + e.getMessage());
        }

        return htmlPage.toString();
    }
}
