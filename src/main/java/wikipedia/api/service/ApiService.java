package wikipedia.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wikipedia.api.ApiBuilder;
import wikipedia.api.deserialization.types.PagesQueryResult;
import wikipedia.api.deserialization.PagesQueryResultAdapter;
import wikipedia.http.HttpRequestHandler;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;

/**
 * Provides services to get pages from Wikipedia using WikiMedia's API
 */
public class ApiService {
    private final static Logger logger = LoggerFactory.getLogger(ApiService.class.getName());

    private HttpRequestHandler httpRequestHandler;
    private PagesQueryResultAdapter pagesQueryResultAdapter;

    public ApiService() {
        httpRequestHandler = new HttpRequestHandler();
        pagesQueryResultAdapter = new PagesQueryResultAdapter();
    }

    /**
     * Returns a collection of random Wikipedia pages as {@see wikipedia.gson.deserialization.types.PagesQueryResult.Page}
     * @param nbRandomPages number of random pages to retrieve
     * @return a collection of random Wikipedia pages as {@see wikipedia.gson.deserialization.types.PagesQueryResult.Page}
     * @throws IOException if an I/O error occurs
     * @throws InterruptedException if the operation is interrupted
     */
    public Collection<PagesQueryResult.Page> getPagesContent(int nbRandomPages) throws IOException, InterruptedException {
        logger.info("Querying WikiMedia API for {} random pages", nbRandomPages);
        URI uri = ApiBuilder.uriForRandomPagesWithContent(nbRandomPages);
        String pagesQueryResultJson = httpRequestHandler.getCompressedResponseAsString(uri);
        logger.info("Received WikiMedia query response {}", pagesQueryResultJson);
        return pagesQueryResultAdapter.getPages(pagesQueryResultJson);
    }

}
