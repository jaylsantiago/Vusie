package com.jorgesantiago.vusie.api.request;

import com.jorgesantiago.vusie.api.NewsCategory;
import com.jorgesantiago.vusie.api.response.NewsApiArticleResponse;
import com.jorgesantiago.vusie.api.response.NewsApiResponse;

import java.util.List;

import retrofit2.Retrofit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * This API manager will actually execute our API calls using retrofit.
 */
public class NewsApiManager {
    private final Retrofit retrofit;

    /**
     * Constructor for the {@link NewsApiManager}
     *
     * @param retrofit used to execute the API calls on a background thread
     */
    public NewsApiManager(final Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    /**
     * Executes call to the general headlines endpoint of the NewsApiService on a background thread
     *
     * @return an observable that emits a {@link NewsApiResponse}
     */
    public Observable<List<NewsApiArticleResponse>> topHeadlinesFromGeneral() {
        return retrofit.create(NewsApiService.class)
                .getTopHeadlinesFromGeneral()
                .map(NewsApiResponse::getArticles)
                .map(articleApiResponseList -> {
                    // category is not actually apart of the API response, so we set it manually based on what endpoint we hit, needed to display articles by category
                    for (NewsApiArticleResponse article : articleApiResponseList) {
                        article.setArticleCategory(NewsCategory.GENERAL);
                    }
                    return articleApiResponseList;
                })
                .doOnError(Throwable::printStackTrace)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Executes call to the health headlines endpoint of the NewsApiService on a background thread
     *
     * @return an observable that emits a {@link NewsApiResponse}
     */
    public Observable<List<NewsApiArticleResponse>> topHeadlinesFromHealth() {
        return retrofit.create(NewsApiService.class)
                .getTopHeadlinesFromHealth()
                .map(NewsApiResponse::getArticles)
                .map(articleApiResponseList -> {
                    // category is not actually apart of the API response, so we set it manually based on what endpoint we hit, needed to display articles by category
                    for (NewsApiArticleResponse article : articleApiResponseList) {
                        article.setArticleCategory(NewsCategory.HEALTH);
                    }
                    return articleApiResponseList;
                })
                .doOnError(Throwable::printStackTrace)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Executes call to the technology headlines endpoint of the NewsApiService on a background thread
     *
     * @return an observable that emits a {@link NewsApiResponse}
     */
    public Observable<List<NewsApiArticleResponse>> topHeadlinesFromTechnology() {
        return retrofit.create(NewsApiService.class)
                .getTopHeadlinesFromTechnology()
                .map(NewsApiResponse::getArticles)
                .map(articleApiResponseList -> {
                    // category is not actually apart of the API response, so we set it manually based on what endpoint we hit, needed to display articles by category
                    for (NewsApiArticleResponse article : articleApiResponseList) {
                        article.setArticleCategory(NewsCategory.TECHNOLOGY);
                    }
                    return articleApiResponseList;
                })
                .doOnError(Throwable::printStackTrace)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Executes call to the business headlines endpoint of the NewsApiService on a background thread
     *
     * @return an observable that emits a {@link NewsApiResponse}
     */
    public Observable<List<NewsApiArticleResponse>> topHeadlinesFromBusiness() {
        return retrofit.create(NewsApiService.class)
                .getTopHeadlinesFromBusiness()
                .map(NewsApiResponse::getArticles)
                .map(articleApiResponseList -> {
                    // category is not actually apart of the API response, so we set it manually based on what endpoint we hit, needed to display articles by category
                    for (NewsApiArticleResponse article : articleApiResponseList) {
                        article.setArticleCategory(NewsCategory.BUSINESS);
                    }
                    return articleApiResponseList;
                })
                .doOnError(Throwable::printStackTrace)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Executes call to the entertainment headlines endpoint of the NewsApiService on a background thread
     *
     * @return an observable that emits a {@link NewsApiResponse}
     */
    public Observable<List<NewsApiArticleResponse>> topHeadlinesFromEntertainment() {
        return retrofit.create(NewsApiService.class)
                .getTopHeadlinesFromEntertainment()
                .map(NewsApiResponse::getArticles)
                .map(articleApiResponseList -> {
                    // category is not actually apart of the API response, so we set it manually based on what endpoint we hit, needed to display articles by category
                    for (NewsApiArticleResponse article : articleApiResponseList) {
                        article.setArticleCategory(NewsCategory.ENTERTAINMENT);
                    }
                    return articleApiResponseList;
                })
                .doOnError(Throwable::printStackTrace)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Executes call to the sports headlines endpoint of the NewsApiService on a background thread
     *
     * @return an observable that emits a {@link NewsApiResponse}
     */
    public Observable<List<NewsApiArticleResponse>> topHeadlinesFromSports() {
        return retrofit.create(NewsApiService.class)
                .getTopHeadlinesFromSports()
                .map(NewsApiResponse::getArticles)
                .map(articleApiResponseList -> {
                    // category is not actually apart of the API response, so we set it manually based on what endpoint we hit, needed to display articles by category
                    for (NewsApiArticleResponse article : articleApiResponseList) {
                        article.setArticleCategory(NewsCategory.SPORTS);
                    }
                    return articleApiResponseList;
                })
                .doOnError(Throwable::printStackTrace)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Executes call to the science headlines endpoint of the NewsApiService on a background thread
     *
     * @return an observable that emits a {@link NewsApiResponse}
     */
    public Observable<List<NewsApiArticleResponse>> topHeadlinesFromScience() {
        return retrofit.create(NewsApiService.class)
                .getTopHeadlinesFromScience()
                .map(NewsApiResponse::getArticles)
                .map(articleApiResponseList -> {
                    // category is not actually apart of the API response, so we set it manually based on what endpoint we hit, needed to display articles by category
                    for (NewsApiArticleResponse article : articleApiResponseList) {
                        article.setArticleCategory(NewsCategory.SCIENCE);
                    }
                    return articleApiResponseList;
                })
                .doOnError(Throwable::printStackTrace)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
