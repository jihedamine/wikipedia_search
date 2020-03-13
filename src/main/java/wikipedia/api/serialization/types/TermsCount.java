package wikipedia.api.serialization.types;

/**
 * Java type to represent a term count as a Json object
 */
public class TermsCount {
    private long termsCount;

    public TermsCount(long termsCount) {
        this.termsCount = termsCount;
    }

    public long getTermsCount() {
        return termsCount;
    }

    public void setTermsCount(long termsCount) {
        this.termsCount = termsCount;
    }
}
