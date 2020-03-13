package wikipedia.api;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * Utility class for interactions with WikiMedia's API
 */
public class ApiBuilder {

    private static final int CONTENT_ARTICLE_NAMESPACE_CODE = 0;
    private static final String MAX_LAG_SECONDS = "60";

    /**
     * Get URI to fetch random pages using the WikiMedia API
     * @param nbRandomPages number of random pages to fetch
     * @return URI to fetch random pages using the WikiMedia API
     */
    public static URI uriForRandomPagesWithContent(int nbRandomPages) {
        return UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("en.wikipedia.org")
                .path("w/api.php")
                .queryParam("action", "query")
                .queryParam("format", "json")
                .queryParam("prop", "revisions")
                .queryParam("rvslots", "main")
                .queryParam("generator", "random")
                .queryParam("rvprop", "content")
                .queryParam("maxlag", MAX_LAG_SECONDS)
                .queryParam("grnnamespace", CONTENT_ARTICLE_NAMESPACE_CODE)
                .queryParam("grnlimit", nbRandomPages)
                .build()
                .toUri();
    }
}
