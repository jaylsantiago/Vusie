package com.jorgesantiago.vusie.NewsAPI;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Interface of api calls to use with retrofit
 */
public interface ApiInterface {

    @GET("/v2/sources?apiKey=449faafc02744826be3781c2b9628272")
    Observable<NewsApiSourceResponse> getSources();

    @GET("/v2/top-headlines?language=en&pageSize=100&sortyBy=relevance&category=general&apiKey=449faafc02744826be3781c2b9628272")
    Observable<NewsApiArticleResponse> getTopHeadlinesFromGeneral();

    @GET("/v2/top-headlines?language=en&pageSize=100&sortyBy=relevance&category=technology&apiKey=449faafc02744826be3781c2b9628272")
    Observable<NewsApiArticleResponse> getTopHeadlinesFromTechnology();

    @GET("/v2/top-headlines?language=en&pageSize=100&sortyBy=relevance&category=business&apiKey=449faafc02744826be3781c2b9628272")
    Observable<NewsApiArticleResponse> getTopHeadlinesFromBusiness();

    @GET("/v2/top-headlines?language=en&pageSize=100&sortyBy=relevance&category=entertainment&apiKey=449faafc02744826be3781c2b9628272")
    Observable<NewsApiArticleResponse> getTopHeadlinesFromEntertainment();

    @GET("/v2/top-headlines?language=en&pageSize=100&sortyBy=relevance&category=science&apiKey=449faafc02744826be3781c2b9628272")
    Observable<NewsApiArticleResponse> getTopHeadlinesFromScience();

    @GET("/v2/top-headlines?language=en&pageSize=100&sortyBy=relevance&category=sports&apiKey=449faafc02744826be3781c2b9628272")
    Observable<NewsApiArticleResponse> getTopHeadlinesFromSports();

    @GET("/v2/top-headlines?language=en&pageSize=100&sortyBy=relevance&category=health&apiKey=449faafc02744826be3781c2b9628272")
    Observable<NewsApiArticleResponse> getTopHeadlinesFromHealth();

    @GET
    
}
