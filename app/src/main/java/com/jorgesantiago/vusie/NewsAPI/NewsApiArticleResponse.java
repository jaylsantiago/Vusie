package com.jorgesantiago.vusie.NewsAPI;

import java.util.List;

/**
 * Our main JSON to POJO representation of the response we get from NewsAPI that contains the list of articles we want
 */
public class NewsApiArticleResponse {

    //The status of our request/response
    private final String status;

    //The number of articles we got back from the API
    private int totalResults;

    private List<ArticleApiResponse> articles;

    public NewsApiArticleResponse(final String status, final int totalResults, final List<ArticleApiResponse> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    /**
     * Extracts the list of articles from our NewsApiArticleResponse
     *
     * @return the list of articles
     */
    public List<ArticleApiResponse> getArticles() {
        return articles;
    }
}
