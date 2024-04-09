package org.example.response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Response {
    private final static byte[] CRLF = "\r\n".getBytes();

    private final ResponseCode responseCode;
    private final ContentType contentType;
    private final String body;

    private Response(ResponseCode responseCode, ContentType contentType, String body) {
        this.responseCode = responseCode;
        this.contentType = contentType;
        this.body = body;
    }

    @Override
    public String toString() {
        return responseCode.toString();
    }

    public byte[] serialize() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            writeResponseCode(byteArrayOutputStream);
            writeContentType(byteArrayOutputStream);
            writeBody(byteArrayOutputStream);
            writeEndOfPackage(byteArrayOutputStream);
        } catch (IOException e) {
            System.err.println("ERROR: Couldn't serialize response.\n" + e.getMessage());
        }

        return byteArrayOutputStream.toByteArray();
    }

    private void writeResponseCode(ByteArrayOutputStream byteArrayOutputStream) throws IOException {
        byteArrayOutputStream.write(("HTTP/1.1 " + responseCode.toString()).getBytes());
        writeEndOfPackage(byteArrayOutputStream);
    }

    private void writeContentType(ByteArrayOutputStream byteArrayOutputStream) throws IOException {
        if (contentType != null) {
            byteArrayOutputStream.write(contentType.toString().getBytes());
            writeEndOfPackage(byteArrayOutputStream);
        }
    }

    private void writeBody(ByteArrayOutputStream byteArrayOutputStream) throws IOException {
        if (body != null) {
            byteArrayOutputStream.write(("Content-Length: " + body.length()).getBytes());
            writeEndOfPackage(byteArrayOutputStream);
            writeEndOfPackage(byteArrayOutputStream);
            byteArrayOutputStream.write(body.getBytes());
        }
    }

    private static void writeEndOfPackage(ByteArrayOutputStream byteArrayOutputStream) throws IOException {
        byteArrayOutputStream.write(CRLF);
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
