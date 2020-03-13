package wikipedia.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.MultiTerms;
import org.apache.lucene.index.Terms;
import org.apache.lucene.misc.HighFreqTerms;
import org.apache.lucene.misc.TermStats;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wikipedia.api.serialization.types.Page;
import wikipedia.api.serialization.types.SearchResultDocument;
import wikipedia.api.serialization.types.TermsCount;
import wikipedia.api.serialization.types.TopTerm;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Lucene indexer, indexes documents and provides services to search for documents and indexed terms
 */
@Component
public class LuceneIndexer {

    public static final String PAGE_CONTENT_FIELD = "body";
    public static final String PAGE_TITLE_FIELD = "title";
    public static final int MAX_RESULTS = 100;
    public static final String LUCENE_INDEX_PATH = "/tmp/lucene_index";

    private final Directory memoryIndex;
    private final Analyzer analyzer;

    @Autowired
    public LuceneIndexer() throws IOException {
        memoryIndex = FSDirectory.open(Paths.get(LUCENE_INDEX_PATH));
        analyzer = new WikipediaCustomAnalyzer();
    }

    /**
     * Index a collection of WikiMedia page titles and contents in the lucene indexer
     * @param pages Wikimedia pages to index
     * @throws IOException if an I/O error occurs
     */
    public void indexDocuments(Collection<Page> pages) throws IOException {
        var indexWriterConfig = new IndexWriterConfig(analyzer);
        indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        try (var indexWriter = new IndexWriter(memoryIndex, indexWriterConfig)) {
            for (Page page : pages) {
                indexDocument(page.getTitle(), page.getContent(), indexWriter);
            }

            indexWriter.flush();
            indexWriter.commit();
        }
    }

    /**
     * Adds a document representing a WikiMedia page to the index
     * @param title title of the page
     * @param body content of the page
     * @param indexWriter indexWriter used to write the document to the lucene index
     * @throws IOException if an I/O error occurs
     */
    public void indexDocument(String title, String body, IndexWriter indexWriter) throws IOException {
        Document document = new Document();

        document.add(new TextField(PAGE_TITLE_FIELD, title, Field.Store.YES));
        document.add(new TextField(PAGE_CONTENT_FIELD, body, Field.Store.YES));

        indexWriter.addDocument(document);
    }

    /**
     * Returns the number of indexed terms in the indexed content of the WikiMedia pages
     * @return the number of indexed terms in the indexed content of the WikiMedia pages
     * @throws IOException if an I/O error occurs
     */
    public TermsCount getIndexedTermsCount() throws IOException {
        try (var indexReader = DirectoryReader.open(memoryIndex)) {
            Terms terms = MultiTerms.getTerms(indexReader, PAGE_CONTENT_FIELD);
            return new TermsCount(terms.size());
        }
    }

    /**
     * Returns the most frequent terms in the indexed content of the WikiMedia pages
     * @param nbTerms number of most frequent terms to return
     * @return the most frequent terms in the indexed content of the WikiMedia pages
     * @throws Exception if an error happens when getting the most frequent terms from the lucene index
     */
    public List<TopTerm> getTopTerms(int nbTerms) throws Exception {
        try (var indexReader = DirectoryReader.open(memoryIndex)) {
            List<TopTerm> topTerms = new ArrayList<>();
            TermStats[] stats = HighFreqTerms.getHighFreqTerms(indexReader,
                    nbTerms, PAGE_CONTENT_FIELD, new HighFreqTerms.DocFreqComparator());
            for (TermStats termStats : stats) {
                String termText = termStats.termtext.utf8ToString();
                topTerms.add(new TopTerm(termText, termStats.docFreq));
            }
            return topTerms;
        }
    }

    /**
     * Runs a search on the lucene index of the WikiMedia pages and returns a list of matching results with a relevance score
     * @param queryString the query string
     * @return a list of matching WikiMedia page titles and contents, along with a relevance score according to the search query
     * @throws IOException if an I/O error occurs
     * @throws ParseException when failing to parse the query with an analyzer
     */
    public List<SearchResultDocument> getSearchResults(String queryString) throws IOException, ParseException {
        try (var indexReader = DirectoryReader.open(memoryIndex)) {
            List<SearchResultDocument> documents = new ArrayList<>();
            Query query = new QueryParser(PAGE_CONTENT_FIELD, analyzer).parse(queryString);
            var searcher = new IndexSearcher(indexReader);
            TopDocs topDocs = searcher.search(query, MAX_RESULTS);
            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                Document document = searcher.doc(scoreDoc.doc);
                documents.add(new SearchResultDocument(document.get(PAGE_TITLE_FIELD),
                        scoreDoc.score, document.get(PAGE_CONTENT_FIELD)));
            }
            return documents;
        }
    }

}
