package wikipedia.api.deserialization;

import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wikipedia.exceptions.JsonDeserializationException;
import wikipedia.api.deserialization.types.PagesQueryResult;

import java.util.Collection;

/**
 * Adapter converting json query results to Page objects
 */
public class PagesQueryResultAdapter {

    private final static Logger logger = LoggerFactory.getLogger(PagesQueryResultAdapter.class.getName());

    private GsonBuilder gsonBuilder;

    public PagesQueryResultAdapter() {
        this.gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(PagesQueryResult.PageList.class, new PageListDeserializer());
    }

    /**
     * Returns a collection of page instances from a json pages string
     * @param pagesQueryResultJson json representing query results from a WikiMedia pages query
     * @return a collection of page instances from a json pages string
     * @throws JsonDeserializationException if deserialization fails
     */
    public Collection<PagesQueryResult.Page> getPages(String pagesQueryResultJson) throws JsonDeserializationException {
        if (pagesQueryResultJson == null) {
            throw new JsonDeserializationException("Cannot deserialize null String");
        }
        PagesQueryResult pagesQueryResult = gsonBuilder.create().fromJson(pagesQueryResultJson, PagesQueryResult.class);
        Collection<PagesQueryResult.Page> pages = pagesQueryResult.getPages();
        logger.info("Converted {} pages from json to Page objects", pages.size());
        return pagesQueryResult.getPages();
    }
}
