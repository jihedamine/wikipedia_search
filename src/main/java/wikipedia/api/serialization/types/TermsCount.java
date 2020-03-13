package wikipedia.api.serialization.types;

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
