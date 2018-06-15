package com.jorgesantiago.vusie.NewsAPI;

import java.util.List;

public class NewsApiArticleResponse {

        private final String status;

        private int totalResults;

        private List<ArticleApiResponse> articles;

        public NewsApiArticleResponse(final String status, final int totalResults, final List<ArticleApiResponse> articles){
            this.status = status;
            this.totalResults = totalResults;
            this.articles = articles;
        }

        public List<ArticleApiResponse> getArticles() {
            return articles;
        }
}
