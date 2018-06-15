package com.jorgesantiago.vusie.RoomDB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * {@link Dao} (data access object), we specify SQL queries and associate them with method calls.
 * The compiler checks the SQL and generates queries from convenience annotations for common queries, such as @Insert.
 * The DAO must be an interface or abstract class.
 * By default, all queries must be executed on a separate thread.
 * {@link androidx.room.Room} uses the {@link Dao} to create a clean API for our code.
 */
@Dao
public interface ArticleDao {

    // No SQL needed, @Insert is a convenience annotation, Room knows what to do
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ArticleDatabaseEntity article);

    @Query("DELETE FROM article_table")
    void deleteAllArticle();

    // 0 is the ordinal value for the enum GE
    @Query("SELECT * FROM article_table ORDER BY publishedAt DESC")
    LiveData<List<ArticleDatabaseEntity>> getAllNewsArticles();

    // 0 is the ordinal value for the enum GENERAL
    @Query("SELECT * FROM article_table WHERE articleCategory = 0")
    LiveData<List<ArticleDatabaseEntity>> getAllGeneralNewsArticles();

    // 1 is the ordinal value for the enum ENTERTAINMENT
    @Query("SELECT * FROM article_table WHERE articleCategory = 1")
    LiveData<List<ArticleDatabaseEntity>> getAllEntertainmentNewsArticles();

    // 2 is the ordinal value for the enum TECHNOLOGY
    @Query("SELECT * FROM article_table WHERE articleCategory = 2")
    LiveData<List<ArticleDatabaseEntity>> getAllTechNewsArticles();

    // 3 is the ordinal value for the enum SPORTS
    @Query("SELECT * FROM article_table WHERE articleCategory = 3")
    LiveData<List<ArticleDatabaseEntity>> getAllSportsNewsArticles();

    // 4 is the ordinal value for the enum BUSINESS
    @Query("SELECT * FROM article_table WHERE articleCategory = 4")
    LiveData<List<ArticleDatabaseEntity>> getAllBusinessNewsArticles();

    // 5 is the ordinal value for the enum SCIENCE
    @Query("SELECT * FROM article_table WHERE articleCategory = 5")
    LiveData<List<ArticleDatabaseEntity>> getAllScienceNewsArticles();

    // 6 is the ordinal value for the enum HEALTH
    @Query("SELECT * FROM article_table WHERE articleCategory = 6")
    LiveData<List<ArticleDatabaseEntity>> getAllHealthNewsArticles();
}
