package com.jorgesantiago.vusie.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

/**
 * {@link Dao} (data access object), we specify SQL queries and associate them with method calls.
 * The compiler checks the SQL and generates queries from convenience annotations for common queries, such as @Insert.
 * The DAO must be an interface or abstract class.
 * By default, all queries must be executed on a separate thread.
 * {@link androidx.room.Room} uses the {@link Dao} to create a clean API for our code.
 */
@Dao
public interface ArticleDao {

    /**
     * Inserts an article into our Room DB
     *
     * @param article to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ArticleDatabaseEntity article);

    /**
     * Clears the database of all articles
     */
    @Query("DELETE FROM article_table")
    void deleteAllArticle();

    /**
     * Queries the database for all articles that have an articleCategory of 0 (0 is the ordinal value for the enum GENERAL in {@link com.jorgesantiago.vusie.api.NewsCategory})
     *
     * @return a LiveData object that contains a list of ArticleDatabaseEntity's that can be observed/subscribed for changes to the DB
     */
    @Query("SELECT * FROM article_table WHERE articleCategory = 0")
    LiveData<List<ArticleDatabaseEntity>> getAllGeneralNewsArticles();

    /**
     * Queries the database for all articles that have an articleCategory of 1 (1 is the ordinal value for the enum ENTERTAINMENT in {@link com.jorgesantiago.vusie.api.NewsCategory})
     *
     * @return a LiveData object that contains a list of ArticleDatabaseEntity's that can be observed/subscribed for changes to the DB
     */
    @Query("SELECT * FROM article_table WHERE articleCategory = 1")
    LiveData<List<ArticleDatabaseEntity>> getAllEntertainmentNewsArticles();

    /**
     * Queries the database for all articles that have an articleCategory of 2 (2 is the ordinal value for the enum TECHNOLOGY in {@link com.jorgesantiago.vusie.api.NewsCategory})
     *
     * @return a LiveData object that contains a list of ArticleDatabaseEntity's that can be observed/subscribed for changes to the DB
     */
    @Query("SELECT * FROM article_table WHERE articleCategory = 2")
    LiveData<List<ArticleDatabaseEntity>> getAllTechNewsArticles();

    /**
     * Queries the database for all articles that have an articleCategory of 3 (3 is the ordinal value for the enum SPORTS in {@link com.jorgesantiago.vusie.api.NewsCategory})
     *
     * @return a LiveData object that contains a list of ArticleDatabaseEntity's that can be observed/subscribed for changes to the DB
     */
    @Query("SELECT * FROM article_table WHERE articleCategory = 3")
    LiveData<List<ArticleDatabaseEntity>> getAllSportsNewsArticles();

    /**
     * Queries the database for all articles that have an articleCategory of 4 (4 is the ordinal value for the enum BUSINESS in {@link com.jorgesantiago.vusie.api.NewsCategory})
     *
     * @return a LiveData object that contains a list of ArticleDatabaseEntity's that can be observed/subscribed for changes to the DB
     */
    @Query("SELECT * FROM article_table WHERE articleCategory = 4")
    LiveData<List<ArticleDatabaseEntity>> getAllBusinessNewsArticles();

    /**
     * Queries the database for all articles that have an articleCategory of 5 (5 is the ordinal value for the enum SCIENCE in {@link com.jorgesantiago.vusie.api.NewsCategory})
     *
     * @return a LiveData object that contains a list of ArticleDatabaseEntity's that can be observed/subscribed for changes to the DB
     */
    @Query("SELECT * FROM article_table WHERE articleCategory = 5")
    LiveData<List<ArticleDatabaseEntity>> getAllScienceNewsArticles();

    /**
     * Queries the database for all articles that have an articleCategory of 6 (6 is the ordinal value for the enum HEALTH in {@link com.jorgesantiago.vusie.api.NewsCategory})
     *
     * @return a LiveData object that contains a list of ArticleDatabaseEntity's that can be observed/subscribed for changes to the DB
     */
    @Query("SELECT * FROM article_table WHERE articleCategory = 6")
    LiveData<List<ArticleDatabaseEntity>> getAllHealthNewsArticles();
}
