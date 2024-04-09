package org.example;

import org.example.request.Request;
import org.example.response.ContentType;
import org.example.response.Response;
import org.example.response.ResponseCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ClientHandler {
    private final Client client;

    public ClientHandler(Client client) {
        this.client = client;
    }

    public void sendResponseToClient() {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getClientSocket().getInputStream()));
             OutputStream outputStream = client.getClientSocket().getOutputStream()) {
            Request request = Request.from(bufferedReader);
            System.out.println("INFO: Client " + client.getId() + " requested resource: " + request.getPath());
            if (request.isRootPath()) {
                writeSuccessfulResponse(outputStream);
            } else {
                writeUnsuccessfulResponse(request.getPath(), outputStream);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeSuccessfulResponse(OutputStream outputStream) throws IOException {
        Response response = new Response.Builder()
                .withResponseCode(ResponseCode.OK)
                .withContentType(ContentType.TEXT_HTML)
                .withBody(getWelcomeHtmlPage())
                .build();
        writeResponse(response, outputStream);
    }

    private void writeUnsuccessfulResponse(String path, OutputStream outputStream) throws IOException {
        Response response = new Response.Builder()
                .withResponseCode(ResponseCode.NOT_FOUND)
                .withContentType(ContentType.TEXT_PLAIN)
                .withBody("ERROR: Sorry, resource " + path + " was not found.")
                .build();
        writeResponse(response, outputStream);
    }

    private void writeResponse(Response response, OutputStream outputStream) throws IOException {
        System.out.println("INFO: Sending response: " + response.toString());
        outputStream.write(response.serialize());
        outputStream.flush();
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
