package wikipedia.api.serialization.types;

/**
 * Java type to represent a search result document as a Json object
 */
public class SearchResultDocument {
    private String title;
    private float score;
    private String content;

    /**
     *
     * @param title title of the WikiMedia page matching the query
     * @param score relevance score of the page as a result of the query
     * @param content content of the page
     */
    public SearchResultDocument(String title, float score, String content) {
        this.title = title;
        this.score = score;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
