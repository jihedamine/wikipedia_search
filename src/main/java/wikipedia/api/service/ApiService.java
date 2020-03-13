package wikipedia.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wikipedia.api.ApiBuilder;
import wikipedia.api.serialization.PagesQueryResultAdapter;
import wikipedia.api.serialization.types.Page;
import wikipedia.http.HttpRequestHandler;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;

/**
 * Provides services to get pages from Wikipedia using WikiMedia's API
 */
@Component
public class ApiService {
    private final static Logger logger = LoggerFactory.getLogger(ApiService.class.getName());

    @Autowired
    private HttpRequestHandler httpRequestHandler;

    @Autowired
    private PagesQueryResultAdapter pagesQueryResultAdapter;

    /**
     * Returns a collection of random Wikipedia pages as {@see wikipedia.gson.deserialization.types.PagesQueryResult.Page}
     * @param nbRandomPages number of random pages to retrieve
     * @return a collection of random Wikipedia pages as {@see wikipedia.gson.deserialization.types.PagesQueryResult.Page}
     * @throws IOException if an I/O error occurs
     * @throws InterruptedException if the operation is interrupted
     */
    public Collection<Page> getPagesContent(int nbRandomPages) throws IOException, InterruptedException {
        logger.info("Querying WikiMedia API for {} random pages", nbRandomPages);
        URI uri = ApiBuilder.uriForRandomPagesWithContent(nbRandomPages);
        String pagesQueryResultJson = httpRequestHandler.getCompressedResponseAsString(uri);
        logger.info("Received WikiMedia query response {}", pagesQueryResultJson);
        return pagesQueryResultAdapter.getPages(pagesQueryResultJson);
    }

}
