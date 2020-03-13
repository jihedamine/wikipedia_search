package wikipedia.api.serialization;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import wikipedia.api.serialization.types.Page;
import wikipedia.exceptions.JsonDeserializationException;
import wikipedia.api.serialization.types.PagesQueryResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PagesQueryResultAdapterTest {

    private PagesQueryResultAdapter pagesQueryResultAdapter;

    @BeforeEach
    public void setUp() {
        pagesQueryResultAdapter = new PagesQueryResultAdapter();
    }

    @Test
    public void testQueryResultAdapterThrowsExceptionForNullInput() {
        assertThatThrownBy(() -> {
            pagesQueryResultAdapter.getPages(null);
        })
                .isInstanceOf(JsonDeserializationException.class)
                .hasMessage("Cannot deserialize null String");
    }

    @Test
    public void testQueryResultAdapterThrowsExceptionForInvalidJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("key", "value");
        String jsonString = jsonObject.toString();
        assertThatThrownBy(() -> {
            pagesQueryResultAdapter.getPages(jsonString);
        })
                .isInstanceOf(JsonDeserializationException.class)
                .hasMessageStartingWith("Cannot deserialize input");
    }

    @Test
    public void testQueryResultAdapterReturnsPagesForValidJsonHavingOnePage() throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass()
                .getResourceAsStream("/validQueryResult.json")))) {
            JsonElement jsonElement = JsonParser.parseReader(reader);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            String jsonString = jsonObject.toString();
            Collection<Page> pageCollection = pagesQueryResultAdapter.getPages(jsonString);
            assertThat(pageCollection).hasSize(1);
            Page page = pageCollection.iterator().next();
            assertThat(page.getTitle()).isEqualTo("My Page Title");
            assertThat(page.getContent()).isEqualTo("My Page Content");
        }
    }

    @Test
    public void testQueryResultAdapterReturnsPagesForValidJsonHavingMultiplePages() throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass()
                .getResourceAsStream("/validQueryMultipleResults.json")))) {
            JsonElement jsonElement = JsonParser.parseReader(reader);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            String jsonString = jsonObject.toString();
            Collection<Page> pageCollection = pagesQueryResultAdapter.getPages(jsonString);
            assertThat(pageCollection).hasSize(2);
            for (Page page : pageCollection) {
                assertThat(page.getTitle()).satisfiesAnyOf(
                        title -> assertThat(title).isEqualTo("My Page Title"),
                        title -> assertThat(title).isEqualTo("My Page Title 2")
                );
                assertThat(page.getContent()).satisfiesAnyOf(
                        content -> assertThat(content).isEqualTo("My Page Content"),
                        content -> assertThat(content).isEqualTo("My Page Content 2")
                );
            }
        }
    }
}