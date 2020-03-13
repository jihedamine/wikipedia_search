# Wikipedia Search
This project implements a simple search API for Wikipedia.

Using the Wikipedia (MediaWiki) API, the application picks 10 random Wikipedia pages on startup.

For these 10 random pages, it creates a simple search index for the content of the pages. 

The application exposes a simple REST API that provides the following capabilities:
- It returns the list of pages selected and indexed

`GET http://host:port/wikisearch/randomPagesList`

- It returns the number of terms indexed

`GET http://host:port/wikisearch/indexedTermsCount`

- It returns a list of the top n terms

`GET http://host:port/wikisearch/topTerms?nbTerms=<nbTerms>`

- Given a search query, it returns the matching pages, in most relevant to least, along with a "score" which gives an indication of relative relevance

`GET http://host:port/wikisearch/search?query=<searchQuery>`

All responses from the API are in JSON format.

##Running the application

The source code language level is Java 11, so you need a JDK 11 or a more recent version to compile the code.

To Run the application, clone this repository and open a terminal at the root folder of the repository.

Either build a jar and run it:

`$> mvn package`

`$> java -jar target/wikipedia_search-1.0-SNAPSHOT.jar`

Or directly run the spring boot maven goal

`$> mvn spring-boot:run`