package wikipedia.api.serialization.types;

import java.util.List;

/**
 * Java type representing a WikiMedia PageList Json object
 */
public class PageList {
    private List<Page> pages;

    public PageList(List<Page> pages) {
        this.pages = pages;
    }

    public List<Page> getPages() {
        return pages;
    }
}
