package wikipedia.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import wikipedia.api.ApiBuilder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPOutputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

class HttpRequestHandleTest {

    private HttpRequestHandler httpRequestHandler;

    @BeforeEach
    public void setUp() throws IOException, InterruptedException {
        httpRequestHandler = mock(HttpRequestHandler.class);
        doCallRealMethod().when(httpRequestHandler).getCompressedResponseAsString(any());
    }

    @Test
    public void wikipediaSearchRequestIntegrationTest() throws IOException, InterruptedException {
        final String expectedResponse = "My Compressed response";
        byte[] gzippedResponse = compress(expectedResponse);
        doReturn(new ByteArrayInputStream(gzippedResponse)).when(httpRequestHandler).getResponse(any());
        String result = httpRequestHandler.getCompressedResponseAsString(ApiBuilder.uriForRandomPagesWithContent(1));
        assertThat(result).isEqualTo(expectedResponse);
    }

    public static byte[] compress(final String str) throws IOException {
        byte[] result;
        try (var obj = new ByteArrayOutputStream()) {
            var gzip = new GZIPOutputStream(obj);
            gzip.write(str.getBytes(StandardCharsets.UTF_8));
            gzip.flush();
            gzip.close();
            result = obj.toByteArray();
        }
        return result;
    }
}