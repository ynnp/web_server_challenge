package org.example.handlers;

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

public class GetRequestHandler implements RequestHandler {
    OutputStream outputStream;
    public GetRequestHandler() {
    }

    @Override
    public void handle(Request request, OutputStream outputStream) throws IOException {
        this.outputStream = outputStream;
        if (request.isRootPath()) {
            sendWelcomePageResponse();
        } else {
            sendNotFoundResponse(request.getPath());
        }
    }

    private void sendWelcomePageResponse() throws IOException {
        Response response = buildResponse(ResponseCode.OK, ContentType.TEXT_HTML, getWelcomeHtmlPage());
        send(response, outputStream);
    }

    private void sendNotFoundResponse(String path) {
        Response response = buildResponse(ResponseCode.NOT_FOUND, ContentType.TEXT_PLAIN,
                "ERROR: Sorry, resource " + path + " was not found.");
        send(response, outputStream);
    }

    private String getWelcomeHtmlPage() throws MalformedURLException {
        StringBuilder htmlPage = new StringBuilder();
        String line;
        URL URL = new URL("file:/Users/ianak/IdeaProjects/webserver/src/main/resources/index.html");
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
