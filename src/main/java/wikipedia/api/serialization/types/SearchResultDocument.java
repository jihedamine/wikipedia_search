package wikipedia.api.serialization.types;

public class SearchResultDocument {
    private String title;
    private float score;
    private String content;

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
