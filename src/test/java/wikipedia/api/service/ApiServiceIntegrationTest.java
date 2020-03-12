package wikipedia.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import wikipedia.api.deserialization.types.PagesQueryResult;
import wikipedia.api.service.ApiService;

import java.io.IOException;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class ApiServiceIntegrationTest {

    private ApiService apiService;

    @BeforeEach
    public void setUp() {
        apiService = new ApiService();
    }

    /**
     * For the integration test to succeed:
     * - the machine running it needs to have an internet connection
     * - WikiMedia API needs to be up and running
     */
    @Test
    public void apiServiceIntegrationTest() throws IOException, InterruptedException {
        Collection<PagesQueryResult.Page> pages = apiService.getPagesContent(3);
        assertThat(pages).hasSize(3);
    }

}