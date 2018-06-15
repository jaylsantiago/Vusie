package com.jorgesantiago.vusie.NewsAPI;

public class ArticleSourceApiResponse {

    private final String id;

    private String name;

    private String description;

    private String url;

    private String category;

    public ArticleSourceApiResponse(final String id, final String name, final String description, final String url, final String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }
}
