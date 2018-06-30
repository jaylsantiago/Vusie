package com.jorgesantiago.vusie.RoomDB;

import com.jorgesantiago.vusie.NewsAPI.ArticleApiResponse;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Our Article database entity object. We map our {@link ArticleApiResponse} into an {@link ArticleDatabaseEntity}
 * to store in our Room database.
 * <p>
 * This class is almost identical to our {@link ArticleApiResponse} but instead of making this
 * one class the response POJO and the database entity, it would be better to separate the concerns
 * of these into two different classes, making the code more modular and robust.
 */
@Entity(tableName = "article_table", indices = {@Index(value = "url", unique = true)})
public class ArticleDatabaseEntity {

    @PrimaryKey(autoGenerate = true)
    private int articleID;

    // The source this article came from.
    @ColumnInfo
    private String source;

    //The author of the article
    @ColumnInfo
    private String author;

    //The headline or title of the article.
    @ColumnInfo
    private String title;

    //A description or snippet from the article.
    @ColumnInfo
    private String description;

    //The direct URL to the article. We use this as a unique index because no two articles should have the same exact url.
    // (sometimes one article will be included in responses from multiple endpoints)
    @ColumnInfo
    private String url;

    //The URL to a relevant image for the article.
    @ColumnInfo
    private String urlToImage;

    //The date and time that the article was published, in UTC (+000)
    @ColumnInfo
    private String publishedAt;

    //The article category, stored as an int by the NewsCategory ordinal value
    @ColumnInfo
    private int articleCategory;

    public ArticleDatabaseEntity() {
    }

    /**
     * Converts our API response POJO into an {@link ArticleDatabaseEntity} that Room can use to save the article into the database
     *
     * @param apiResponse the article to be converted
     */
    public static ArticleDatabaseEntity convertApiResponseToDbEntity(final ArticleApiResponse apiResponse) {
        ArticleDatabaseEntity articleDatabaseEntity = new ArticleDatabaseEntity();
        articleDatabaseEntity.setAuthor(apiResponse.getAuthor());
        articleDatabaseEntity.setDescription(apiResponse.getDescription());
        articleDatabaseEntity.setPublishedAt(apiResponse.getPublishedAt());
        articleDatabaseEntity.setSource(apiResponse.getSource());
        articleDatabaseEntity.setTitle(apiResponse.getTitle());
        articleDatabaseEntity.setUrl(apiResponse.getUrl());
        articleDatabaseEntity.setUrlToImage(apiResponse.getUrlToImage());
        articleDatabaseEntity.setArticleCategory(apiResponse.getArticleCategory().ordinal());
        return articleDatabaseEntity;
    }

    public int getArticleID() {
        return articleID;
    }

    public void setArticleID(int articleID) {
        this.articleID = articleID;
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

    public int getArticleCategory() {
        return articleCategory;
    }

    public void setArticleCategory(int articleTypeOrdinal) {
        this.articleCategory = articleTypeOrdinal;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
