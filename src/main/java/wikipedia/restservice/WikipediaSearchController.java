package wikipedia.restservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import wikipedia.WikipediaSearchService;
import wikipedia.api.serialization.types.Page;
import wikipedia.api.serialization.types.SearchResultDocument;
import wikipedia.api.serialization.types.TermsCount;
import wikipedia.api.serialization.types.TopTerm;

import java.util.Collection;
import java.util.List;

@RestController
public class WikipediaSearchController {

    private final static Logger logger = LoggerFactory.getLogger(WikipediaSearchController.class.getName());

    @Autowired
    private WikipediaSearchService wikipediaSearchService;

    @GetMapping(value = "/wikisearch/randomPagesList", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Collection<Page> getRandomPagesList() {
        return wikipediaSearchService.getPages();
    }

    @GetMapping(value = "/wikisearch/indexedTermsCount", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public TermsCount getIndexedTermsCount() throws Exception {
        return wikipediaSearchService.getIndexedTerms();
    }

    @GetMapping(value = "/wikisearch/topTerms", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<TopTerm> getTopTerms(@RequestParam int nbTerms) throws Exception {
        return wikipediaSearchService.getTopTerms(nbTerms);
    }

    @GetMapping(value = "/wikisearch/search", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<SearchResultDocument> getTopTerms(@RequestParam String query) throws Exception {
        return wikipediaSearchService.getSearchResults(query);
    }

}
