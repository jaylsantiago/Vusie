package com.jorgesantiago.vusie.NewsAPI;

public class ArticleApiResponse {

    private ArticleSourceApiResponse source;

    private String author;

    private String title;

    private String description;

    private String url;

    private String urlToImage;

    private String publishedAt;

    private NewsCategory articleCategory;

    public ArticleApiResponse() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getSource() {
        return source.getName();
    }

    public void setSource(ArticleSourceApiResponse source) {
        this.source = source;
    }

    public String getSourceId() {
        return source.getId();
    }

    public NewsCategory getArticleCategory() {
        return articleCategory;
    }

    public void setArticleCategory(NewsCategory articleCategory) {
        this.articleCategory = articleCategory;
    }
}
