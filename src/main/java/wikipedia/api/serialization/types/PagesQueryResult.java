package wikipedia.api.serialization.types;

import wikipedia.exceptions.JsonDeserializationException;

import java.util.Collection;

/**
 * Models a json query result from WikiMedia's API as a Java object
 */
public class PagesQueryResult {
    private Query query;

    /**
     * Get the pages returned in the query result
     * @return pages returned in the query result
     * @throws JsonDeserializationException if deserialization fails
     */
    public Collection<Page> getPages() throws JsonDeserializationException {
        if ((query == null) || (query.pages == null) || (query.pages.getPages() == null)) {
            throw new JsonDeserializationException(this.getClass());
        }
        return query.pages.getPages();
    }

    private class Query {
        private PageList pages;
    }

}
