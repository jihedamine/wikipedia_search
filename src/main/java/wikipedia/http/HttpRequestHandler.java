package wikipedia.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;

/**
 * Helper class to send http requests and process their result
 */
public class HttpRequestHandler {

    private final static Logger logger = LoggerFactory.getLogger(HttpRequestHandler.class.getName());

    private HttpClient client;

    public HttpRequestHandler() {
        client = HttpClient.newHttpClient();
    }

    /**
     * Sends an HTTP request and get a compressed gzip result. Un-compresses the result and returns it as a string
     * @param uri URI to send the request to
     * @return String representing the result of the request, if the request is successful
     * @throws IOException if an I/O error occurs when sending the request
     * @throws InterruptedException if the operation is interrupted
     */
    public String getCompressedResponseAsString(URI uri) throws IOException, InterruptedException {
        HttpRequest request = getHttpRequest(uri);
        InputStream responseBodyInputStream = getResponse(request);
        byte[] outputStream = getUncompressedByteArray(responseBodyInputStream);
        String responseString = new String(outputStream, StandardCharsets.UTF_8);
        logger.debug("Received HTTP response {} for HTTP request {}", responseString, uri.toString());
        return responseString;
    }

    InputStream getResponse(HttpRequest request) throws IOException, InterruptedException {
        HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
        return response.body();
    }

    /**
     * Builds an HttpRequest that accepts gzipped responses
     * @param uri URI the HTTP request is sent to
     * @return an HttpRequest that accepts gzipped responses
     */
    private HttpRequest getHttpRequest(URI uri) {
        return HttpRequest.newBuilder()
                    .GET()
                    .header("User-Agent", "MyRandomPagesRetriever (jihedamine@icloud.com)")
                    .header("Accept-Encoding", "gzip")
                    .uri(uri)
                    .build();
    }

    /**
     * Un-compresses a GZIPInputStream response {@see java.util.zip.GZIPInputStream}
     * @param inputStream the GZIP-ed inputStream
     * @return the un-compressed inputStream as a string
     * @throws IOException if an I/O error has occurred
     */
    private byte[] getUncompressedByteArray(InputStream inputStream) throws IOException {
        try (var is = new GZIPInputStream(inputStream); var outputStream = new ByteArrayOutputStream()) {
            is.transferTo(outputStream);
            return outputStream.toByteArray();
        }
    }
}
