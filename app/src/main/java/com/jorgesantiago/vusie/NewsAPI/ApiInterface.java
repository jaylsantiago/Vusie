package com.jorgesantiago.vusie.NewsAPI;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Interface that contains api endpoints to use with retrofit
 */
public interface ApiInterface {

    // TODO hide API keys instead of displaying them in plain-text? This isn't an open-sourced project, so maybe not a top priority...
    // TODO consider creating a generic NewsAPIRequest object to pass in as request body? -- The endpoint URLs are a little redundant

    @GET("/v2/top-headlines?country=us&category=general&apiKey=449faafc02744826be3781c2b9628272")
    Observable<NewsApiArticleResponse> getTopHeadlinesFromGeneral();

    @GET("/v2/top-headlines?country=us&category=technology&apiKey=449faafc02744826be3781c2b9628272")
    Observable<NewsApiArticleResponse> getTopHeadlinesFromTechnology();

    @GET("/v2/top-headlines?country=us&category=business&apiKey=449faafc02744826be3781c2b9628272")
    Observable<NewsApiArticleResponse> getTopHeadlinesFromBusiness();

    @GET("/v2/top-headlines?country=us&category=entertainment&apiKey=449faafc02744826be3781c2b9628272")
    Observable<NewsApiArticleResponse> getTopHeadlinesFromEntertainment();

    @GET("/v2/top-headlines?country=us&category=science&apiKey=449faafc02744826be3781c2b9628272")
    Observable<NewsApiArticleResponse> getTopHeadlinesFromScience();

    @GET("/v2/top-headlines?country=us&category=sports&apiKey=449faafc02744826be3781c2b9628272")
    Observable<NewsApiArticleResponse> getTopHeadlinesFromSports();

    @GET("/v2/top-headlines?country=us&category=health&apiKey=449faafc02744826be3781c2b9628272")
    Observable<NewsApiArticleResponse> getTopHeadlinesFromHealth();

}
