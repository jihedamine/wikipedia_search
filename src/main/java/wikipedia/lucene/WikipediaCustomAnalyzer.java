package wikipedia.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.charfilter.HTMLStripCharFilter;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.en.EnglishPossessiveFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

import java.io.Reader;
import java.util.Arrays;
import java.util.List;

public class WikipediaCustomAnalyzer extends Analyzer {
    private static final List<String> customStopWords = Arrays.asList(
            "ref", "title", "reflist", "reference", "cite", "accessdate", "http", "from",
            "references", "url", "id", "category", "image", "image_caption", "infobox", "links"
    );
    private static final CharArraySet customStopSet = new CharArraySet(customStopWords, false);
    private static final CharArraySet CUSTOM_STOP_WORDS_SET = CharArraySet.unmodifiableSet(customStopSet);

    @Override
    protected Reader initReader(String fieldName, Reader reader) {
        return new HTMLStripCharFilter(reader);
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        Tokenizer tokenizer = new StandardTokenizer();
        TokenStream stream = new EnglishPossessiveFilter(tokenizer);
        stream = new LowerCaseFilter(stream);
        stream = new StopFilter(stream, EnglishAnalyzer.ENGLISH_STOP_WORDS_SET);
        stream = new StopFilter(stream, CUSTOM_STOP_WORDS_SET);
        return new TokenStreamComponents(tokenizer, stream);
    }
}
