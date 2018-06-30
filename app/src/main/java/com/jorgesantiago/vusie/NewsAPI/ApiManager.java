package com.jorgesantiago.vusie.NewsAPI;

import java.util.List;

import retrofit2.Retrofit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * This API manager will actually execute our API calls using retrofit.
 */
public class ApiManager {
    private final Retrofit retrofit;

    /**
     * Constructor for the {@link ApiManager}
     *
     * @param retrofit used to execute the API calls on a background thread
     */
    public ApiManager(final Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    /**
     * Executes call to the general headlines endpoint of the ApiInterface on a background thread
     *
     * @return an observable that emits a {@link NewsApiArticleResponse}
     */
    public Observable<List<ArticleApiResponse>> topHeadlinesFromGeneral() {
        return retrofit.create(ApiInterface.class)
                .getTopHeadlinesFromGeneral()
                .map(NewsApiArticleResponse::getArticles)
                .map(articleApiResponseList -> {
                    // category is not actually apart of the API response, so we set it manually based on what endpoint we hit, needed to display articles by category
                    for (ArticleApiResponse article : articleApiResponseList) {
                        article.setArticleCategory(NewsCategory.GENERAL);
                    }
                    return articleApiResponseList;
                })
                .doOnError(Throwable::printStackTrace)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Executes call to the health headlines endpoint of the ApiInterface on a background thread
     *
     * @return an observable that emits a {@link NewsApiArticleResponse}
     */
    public Observable<List<ArticleApiResponse>> topHeadlinesFromHealth() {
        return retrofit.create(ApiInterface.class)
                .getTopHeadlinesFromHealth()
                .map(NewsApiArticleResponse::getArticles)
                .map(articleApiResponseList -> {
                    // category is not actually apart of the API response, so we set it manually based on what endpoint we hit, needed to display articles by category
                    for (ArticleApiResponse article : articleApiResponseList) {
                        article.setArticleCategory(NewsCategory.HEALTH);
                    }
                    return articleApiResponseList;
                })
                .doOnError(Throwable::printStackTrace)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Executes call to the technology headlines endpoint of the ApiInterface on a background thread
     *
     * @return an observable that emits a {@link NewsApiArticleResponse}
     */
    public Observable<List<ArticleApiResponse>> topHeadlinesFromTechnology() {
        return retrofit.create(ApiInterface.class)
                .getTopHeadlinesFromTechnology()
                .map(NewsApiArticleResponse::getArticles)
                .map(articleApiResponseList -> {
                    // category is not actually apart of the API response, so we set it manually based on what endpoint we hit, needed to display articles by category
                    for (ArticleApiResponse article : articleApiResponseList) {
                        article.setArticleCategory(NewsCategory.TECHNOLOGY);
                    }
                    return articleApiResponseList;
                })
                .doOnError(Throwable::printStackTrace)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Executes call to the business headlines endpoint of the ApiInterface on a background thread
     *
     * @return an observable that emits a {@link NewsApiArticleResponse}
     */
    public Observable<List<ArticleApiResponse>> topHeadlinesFromBusiness() {
        return retrofit.create(ApiInterface.class)
                .getTopHeadlinesFromBusiness()
                .map(NewsApiArticleResponse::getArticles)
                .map(articleApiResponseList -> {
                    // category is not actually apart of the API response, so we set it manually based on what endpoint we hit, needed to display articles by category
                    for (ArticleApiResponse article : articleApiResponseList) {
                        article.setArticleCategory(NewsCategory.BUSINESS);
                    }
                    return articleApiResponseList;
                })
                .doOnError(Throwable::printStackTrace)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Executes call to the entertainment headlines endpoint of the ApiInterface on a background thread
     *
     * @return an observable that emits a {@link NewsApiArticleResponse}
     */
    public Observable<List<ArticleApiResponse>> topHeadlinesFromEntertainment() {
        return retrofit.create(ApiInterface.class)
                .getTopHeadlinesFromEntertainment()
                .map(NewsApiArticleResponse::getArticles)
                .map(articleApiResponseList -> {
                    // category is not actually apart of the API response, so we set it manually based on what endpoint we hit, needed to display articles by category
                    for (ArticleApiResponse article : articleApiResponseList) {
                        article.setArticleCategory(NewsCategory.ENTERTAINMENT);
                    }
                    return articleApiResponseList;
                })
                .doOnError(Throwable::printStackTrace)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Executes call to the sports headlines endpoint of the ApiInterface on a background thread
     *
     * @return an observable that emits a {@link NewsApiArticleResponse}
     */
    public Observable<List<ArticleApiResponse>> topHeadlinesFromSports() {
        return retrofit.create(ApiInterface.class)
                .getTopHeadlinesFromSports()
                .map(NewsApiArticleResponse::getArticles)
                .map(articleApiResponseList -> {
                    // category is not actually apart of the API response, so we set it manually based on what endpoint we hit, needed to display articles by category
                    for (ArticleApiResponse article : articleApiResponseList) {
                        article.setArticleCategory(NewsCategory.SPORTS);
                    }
                    return articleApiResponseList;
                })
                .doOnError(Throwable::printStackTrace)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Executes call to the science headlines endpoint of the ApiInterface on a background thread
     *
     * @return an observable that emits a {@link NewsApiArticleResponse}
     */
    public Observable<List<ArticleApiResponse>> topHeadlinesFromScience() {
        return retrofit.create(ApiInterface.class)
                .getTopHeadlinesFromScience()
                .map(NewsApiArticleResponse::getArticles)
                .map(articleApiResponseList -> {
                    // category is not actually apart of the API response, so we set it manually based on what endpoint we hit, needed to display articles by category
                    for (ArticleApiResponse article : articleApiResponseList) {
                        article.setArticleCategory(NewsCategory.SCIENCE);
                    }
                    return articleApiResponseList;
                })
                .doOnError(Throwable::printStackTrace)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
