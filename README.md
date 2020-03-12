# Wikipedia Search
This project implements a simple search API for Wikipedia.

Using the Wikipedia (MediaWiki) API, the application picks 10 random Wikipedia pages on startup.

For these 10 random pages, it creates a simple search index for the content of the pages. 

The application exposes a simple REST API that provides the following capabilities:
- It returns the list of pages selected and indexed

`GET http://host:port/wikisearch/randomPagesList`

- It returns the number of terms indexed

`GET http://host:port/wikisearch/indexedTermsCount`

- It returns a list of the top n terms, for each page

`GET http://host:port/wikisearch/topTerms`

- Given a search query, it returns the matching pages, in most relevant to least, along with a "score" which gives an indication of relative relevance

`GET http://host:port/wikisearch/pages?query=<searchQuery>`

All responses from the API are in JSON format.

