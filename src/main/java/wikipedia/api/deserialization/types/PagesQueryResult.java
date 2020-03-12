package wikipedia.api.deserialization.types;

import com.google.gson.annotations.SerializedName;
import wikipedia.exceptions.JsonDeserializationException;

import java.util.Collection;
import java.util.List;

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
        if ((query == null) || (query.pages == null) || (query.pages.pages == null)) {
            throw new JsonDeserializationException(this.getClass());
        }
        return query.pages.getPages();
    }

    public class Query {
        private PageList pages;
    }

    public class PageList {
        private List<Page> pages;

        public PageList(List<Page> pages) {
            this.pages = pages;
        }

        public List<Page> getPages() {
            return pages;
        }
    }

    public class Page {
        private String title;
        private List<Revision> revisions;

        public String getTitle() {
            return title;
        }

        public String getContent() {
            if (revisions.isEmpty()) {
                return "";
            }

            return revisions.iterator().next().slots.main.content;
        }
    }

    private class Revision {
        private Slots slots;
    }

    private class Slots {
        private MainSlot main;
    }

    private class MainSlot {
        @SerializedName("*")
        private String content;
    }
}
