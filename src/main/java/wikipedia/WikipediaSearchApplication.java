package wikipedia;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class WikipediaSearchApplication {
    private final static Logger logger = LoggerFactory.getLogger(WikipediaSearchApplication.class.getName());

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(WikipediaSearchApplication.class, args);

        StringBuilder endpointPrefix = new StringBuilder("http://");
        endpointPrefix.append(getHostAddress());
        endpointPrefix.append(":");
        endpointPrefix.append(getHostPort(applicationContext));

        System.out.println(getEndpointsMessage(endpointPrefix.toString()));
    }

    private static String getEndpointsMessage(String endpointPrefix) {
        return "The application exposes a simple REST API that provides the following capabilities:\n" +
                "- It returns the list of pages selected and indexed\n" +
                "`GET " + endpointPrefix + "/wikisearch/randomPagesList`\n" +
                "- It returns the number of terms indexed\n" +
                "`GET " + endpointPrefix + "/wikisearch/indexedTermsCount`\n" +
                "- It returns a list of the top n terms\n" +
                "`GET " + endpointPrefix + "/wikisearch/topTerms?nbTerms=<nbTerms>`\n" +
                "- Given a search query, it returns the matching pages, in most relevant to least, along with a \"score\" which gives an indication of relative relevance\n" +
                "`GET " + endpointPrefix + "/wikisearch/search?query=<searchQuery>`\n" +
                "All responses from the API are in JSON format.";
    }

    private static Integer getHostPort(ApplicationContext applicationContext) {
        return applicationContext.getBean(Environment.class).getProperty("server.port", Integer.class, 8080);
    }

    private static String getHostAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            logger.warn("Cannot determine host address", e);
            return "host";
        }
    }

}
