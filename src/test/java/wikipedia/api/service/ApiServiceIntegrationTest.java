package wikipedia.api.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wikipedia.api.serialization.types.Page;

import java.io.IOException;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ApiServiceIntegrationTest {

    @Autowired
    private ApiService apiService;

    /**
     * For the integration test to succeed:
     * - the machine running it needs to have an internet connection
     * - WikiMedia API needs to be up and running
     */
    @Test
    public void apiServiceIntegrationTest() throws IOException, InterruptedException {
        Collection<Page> pages = apiService.getPagesContent(3);
        assertThat(pages).hasSize(3);
    }

}