package com.jorgesantiago.vusie.api.response;

import java.util.List;

/**
 * Our main JSON to POJO representation of the response we get from api that contains the list of articles we want
 */
public class NewsApiResponse {

    //The status of our request/response
    private final String status;

    //The number of articles we got back from the API
    private int totalResults;

    private List<NewsApiArticleResponse> articles;

    public NewsApiResponse(final String status, final int totalResults, final List<NewsApiArticleResponse> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    /**
     * Extracts the list of articles from our NewsApiResponse
     *
     * @return the list of articles
     */
    public List<NewsApiArticleResponse> getArticles() {
        return articles;
    }
}
