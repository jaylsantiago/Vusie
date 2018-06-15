package com.jorgesantiago.vusie.NewsAPI;

import java.util.List;

public class NewsApiSourceResponse {

    private final String status;

    private List<ArticleSourceApiResponse> sources;

    public NewsApiSourceResponse(final String status, final List<ArticleSourceApiResponse> sources) {
        this.status = status;
        this.sources = sources;
    }

    public List<ArticleSourceApiResponse> getSources() {
        return sources;
    }

}
