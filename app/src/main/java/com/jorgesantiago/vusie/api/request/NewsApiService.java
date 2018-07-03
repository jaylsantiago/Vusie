package com.jorgesantiago.vusie.api.request;

import com.jorgesantiago.vusie.api.response.NewsApiResponse;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Interface that contains api endpoints to use with retrofit
 */
public interface NewsApiService {

    // TODO hide API keys instead of displaying them in plain-text? This isn't an open-sourced project, so maybe not a top priority...
    // TODO consider creating a generic NewsAPIRequest object to pass in as request body? -- The endpoint URLs are a little redundant

    @GET("top-headlines?country=us&category=general&apiKey=449faafc02744826be3781c2b9628272")
    Observable<NewsApiResponse> getTopHeadlinesFromGeneral();

    @GET("top-headlines?country=us&category=technology&apiKey=449faafc02744826be3781c2b9628272")
    Observable<NewsApiResponse> getTopHeadlinesFromTechnology();

    @GET("top-headlines?country=us&category=business&apiKey=449faafc02744826be3781c2b9628272")
    Observable<NewsApiResponse> getTopHeadlinesFromBusiness();

    @GET("top-headlines?country=us&category=entertainment&apiKey=449faafc02744826be3781c2b9628272")
    Observable<NewsApiResponse> getTopHeadlinesFromEntertainment();

    @GET("top-headlines?country=us&category=science&apiKey=449faafc02744826be3781c2b9628272")
    Observable<NewsApiResponse> getTopHeadlinesFromScience();

    @GET("top-headlines?country=us&category=sports&apiKey=449faafc02744826be3781c2b9628272")
    Observable<NewsApiResponse> getTopHeadlinesFromSports();

    @GET("top-headlines?country=us&category=health&apiKey=449faafc02744826be3781c2b9628272")
    Observable<NewsApiResponse> getTopHeadlinesFromHealth();

}
