package wikipedia.api.serialization.types;

/**
 * Java type to represent a top term in the set of indexed terms as a Json object
 */
public class TopTerm {

    private String term;
    private int count;

    /**
     *
     * @param term indexed term
     * @param count number of occurrences of the term in the index
     */
    public TopTerm(String term, int count) {
        this.term = term;
        this.count = count;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
