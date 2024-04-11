package org.example.response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Response {
    private final ByteArrayOutputStream byteArrayOutputStream;
    private final ResponseCode responseCode;
    private final ContentType contentType;
    private final String body;

    public byte[] serialize() {
        try {
            writeResponseCodeToOutputStream();
            writeContentTypeToOutputStream();
            writeBodyToOutputStream();
            writeNewLineToOutputStream();
        } catch (IOException e) {
            System.err.println("ERROR: Couldn't serialize response.\n" + e.getMessage());
        }

        return byteArrayOutputStream.toByteArray();
    }

    private void writeResponseCodeToOutputStream() throws IOException {
        byteArrayOutputStream.write(("HTTP/1.1 " + responseCode.toString()).getBytes());
        writeNewLineToOutputStream();
    }

    private void writeContentTypeToOutputStream() throws IOException {
        if (contentType != null) {
            byteArrayOutputStream.write(contentType.toString().getBytes());
            writeNewLineToOutputStream();
        }
    }

    private void writeBodyToOutputStream() throws IOException {
        if (body != null) {
            byteArrayOutputStream.write(("Content-Length: " + body.length()).getBytes());
            writeNewLineToOutputStream();
            writeNewLineToOutputStream();
            byteArrayOutputStream.write(body.getBytes());
        }
    }

    private void writeNewLineToOutputStream() throws IOException {
        byteArrayOutputStream.write("\r\n".getBytes());
    }

    private Response(ResponseCode responseCode, ContentType contentType, String body) {
        this.byteArrayOutputStream = new ByteArrayOutputStream();
        this.responseCode = responseCode;
        this.contentType = contentType;
        this.body = body;
    }

    @Override
    public String toString() {
        return responseCode.toString() + "\n" + contentType.toString() + "\n" + body;
    }

    public static class Builder {
        private ResponseCode responseCode;
        private ContentType contentType;
        private String body;

        public Builder() {
        }

        public Builder withResponseCode(ResponseCode responseCode) {
            this.responseCode = responseCode;

            return this;
        }

        public Builder withContentType(ContentType contentType) {
            this.contentType = contentType;

            return this;
        }

        public Builder withBody(String body) {
            this.body = body;

            return this;
        }

        public Response build() {
            if (responseCode == null) {
                throw new IllegalArgumentException("ERROR: Response code can not be empty!");
            }

            return new Response(responseCode, contentType, body);
        }
    }
}
