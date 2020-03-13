package wikipedia;

import org.apache.lucene.queryparser.classic.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import wikipedia.api.serialization.types.Page;
import wikipedia.api.serialization.types.SearchResultDocument;
import wikipedia.api.serialization.types.TermsCount;
import wikipedia.api.serialization.types.TopTerm;
import wikipedia.api.service.ApiService;
import wikipedia.exceptions.InitializationError;
import wikipedia.lucene.LuceneIndexer;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class WikipediaSearchService {

    private final static Logger logger = LoggerFactory.getLogger(WikipediaSearchService.class.getName());

    private final LuceneIndexer luceneIndexer;
    private final Collection<Page> pages;

    public WikipediaSearchService(ApiService apiService, LuceneIndexer luceneIndexer) throws InitializationError {
        this.luceneIndexer = luceneIndexer;

        try {
            this.pages = apiService.getPagesContent(10);
            luceneIndexer.indexDocuments(pages);
        } catch (Exception e) {
            logger.error("Failed to load and index Wikipedia pages", e);
            throw new InitializationError("Failed to load and index wikipedia pages", e);
        }
    }

    public Collection<Page> getPages() {
        return Collections.unmodifiableCollection(pages);
    }

    public TermsCount getIndexedTerms() throws Exception {
        return luceneIndexer.getIndexedTerms();
    }

    public List<TopTerm> getTopTerms(int nbTerms) throws Exception {
        return luceneIndexer.getTopTerms(nbTerms);
    }

    public List<SearchResultDocument> getSearchResults(String query) throws IOException, ParseException {
        return luceneIndexer.getSearchResults(query);
    }
}
