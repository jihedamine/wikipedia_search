package wikipedia.api.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import wikipedia.api.serialization.types.Page;
import wikipedia.api.serialization.types.PageList;
import wikipedia.api.serialization.types.PagesQueryResult;
import wikipedia.exceptions.JsonDeserializationException;

import java.util.Collection;

/**
 * Adapter converting json query results to Page objects
 */
@Component
public class PagesQueryResultAdapter {

    private final static Logger logger = LoggerFactory.getLogger(PagesQueryResultAdapter.class.getName());

    private Gson gson;

    public PagesQueryResultAdapter() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(PageList.class, new PageListDeserializer());
        gsonBuilder.setPrettyPrinting();
        this.gson = gsonBuilder.create();
    }

    /**
     * Returns a collection of page instances from a json pages string
     * @param pagesQueryResultJson json representing query results from a WikiMedia pages query
     * @return a collection of page instances from a json pages string
     * @throws JsonDeserializationException if deserialization fails
     */
    public Collection<Page> getPages(String pagesQueryResultJson) throws JsonDeserializationException {
        if (pagesQueryResultJson == null) {
            throw new JsonDeserializationException("Cannot deserialize null String");
        }
        PagesQueryResult pagesQueryResult = gson.fromJson(pagesQueryResultJson, PagesQueryResult.class);
        Collection<Page> pages = pagesQueryResult.getPages();
        logger.info("Converted {} pages from json to Page objects", pages.size());
        return pagesQueryResult.getPages();
    }
}
