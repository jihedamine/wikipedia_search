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

    /**
     * Returns the list of WikiMedia pages loaded when initializing the search service
     * @return the list of WikiMedia pages loaded when initializing the search service
     */
    public Collection<Page> getPages() {
        return Collections.unmodifiableCollection(pages);
    }

    /**
     * Returns the number of indexed terms in the indexed content of the WikiMedia pages
     * @return the number of indexed terms in the indexed content of the WikiMedia pages
     */
    public TermsCount getIndexedTerms() throws Exception {
        return luceneIndexer.getIndexedTermsCount();
    }

    /**
     * Returns the most frequent terms in the indexed content of the WikiMedia pages
     * @param nbTerms number of most frequent terms to return
     * @return the most frequent terms in the indexed content of the WikiMedia pages
     * @throws Exception if an error happens when getting the most frequent terms from the lucene index
     */
    public List<TopTerm> getTopTerms(int nbTerms) throws Exception {
        return luceneIndexer.getTopTerms(nbTerms);
    }

    /**
     * Runs a search on the lucene index of the WikiMedia pages and returns a list of matching results with a relevance score
     * @param query the query string
     * @return a list of matching WikiMedia page titles and contents, along with a relevance score according to the search query
     * @throws IOException if an I/O error occurs
     * @throws ParseException when failing to parse the query with an analyzer
     */
    public List<SearchResultDocument> getSearchResults(String query) throws IOException, ParseException {
        return luceneIndexer.getSearchResults(query);
    }
}
