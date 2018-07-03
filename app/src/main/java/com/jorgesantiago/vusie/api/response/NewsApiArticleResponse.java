package com.jorgesantiago.vusie.api.response;

import com.jorgesantiago.vusie.api.NewsCategory;

/**
 * Our Article JSON to POJO representation of the response we get from the api.
 * <p>
 * This class is almost identical to our {@link com.jorgesantiago.vusie.data.ArticleDatabaseEntity} but instead of making this
 * one class the response POJO and the database entity (all we would have to do is add the Room annotations), it would be
 * better to separate the concerns of these into two different classes, making the code more modular and robust in the event we
 * need to change either one
 */
public class NewsApiArticleResponse {

    private NewsApiSourceResponse source;

    private String author;

    private String title;

    private String description;

    private String url;

    private String urlToImage;

    private String publishedAt;

    private NewsCategory articleCategory;

    public NewsApiArticleResponse(NewsApiSourceResponse source, String author, String title, String description, String url, String urlToImage, String publishedAt, NewsCategory articleCategory) {
        this.source = source;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.articleCategory = articleCategory;
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

    public void setSource(NewsApiSourceResponse source) {
        this.source = source;
    }

    public NewsCategory getArticleCategory() {
        return articleCategory;
    }

    public void setArticleCategory(NewsCategory articleCategory) {
        this.articleCategory = articleCategory;
    }
}
