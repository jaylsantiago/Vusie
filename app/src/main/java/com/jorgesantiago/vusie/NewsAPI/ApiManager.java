package com.jorgesantiago.vusie.NewsAPI;

import java.util.List;

import retrofit2.Retrofit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ApiManager {
    private final Retrofit retrofit;

    /**
     * Constructor for the {@link ApiManager} that can be injected via
     *
     * @param retrofit used to execute the API calls on a background thread
     */
    public ApiManager(final Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    /**
     * Executes call to the sources endpoint of the ApiInterface on a background thread
     *
     * @return an observable that emits a {@link NewsApiSourceResponse}
     */
    public Observable<List<ArticleSourceApiResponse>> sources() {
        return retrofit.create(ApiInterface.class)
                .getSources()
                .flatMap(newsApiSourceResponse -> Observable.just(newsApiSourceResponse.getSources()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<ArticleApiResponse>> topHeadlinesFromGeneral() {
        return retrofit.create(ApiInterface.class)
                .getTopHeadlinesFromGeneral()
                .flatMap(newsApiResponse -> Observable.just(newsApiResponse.getArticles()))
                .flatMap(articleApiResponseList -> {
                    for (ArticleApiResponse article : articleApiResponseList) {
                        article.setArticleCategory(NewsCategory.GENERAL);
                    }
                    return Observable.just(articleApiResponseList);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<ArticleApiResponse>> topHeadlinesFromHealth() {
        return retrofit.create(ApiInterface.class)
                .getTopHeadlinesFromHealth()
                .flatMap(newsApiResponse -> Observable.just(newsApiResponse.getArticles()))
                .flatMap(articleApiResponseList -> {
                    for (ArticleApiResponse article : articleApiResponseList) {
                        article.setArticleCategory(NewsCategory.HEALTH);
                    }
                    return Observable.just(articleApiResponseList);
                })
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
                .flatMap(newsApiResponse -> Observable.just(newsApiResponse.getArticles()))
                .flatMap(articleApiResponseList -> {
                    for (ArticleApiResponse article : articleApiResponseList) {
                        article.setArticleCategory(NewsCategory.TECHNOLOGY);
                    }
                    return Observable.just(articleApiResponseList);
                })
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
                .flatMap(newsApiResponse -> Observable.just(newsApiResponse.getArticles()))
                .flatMap(articleApiResponseList -> {
                    for (ArticleApiResponse article : articleApiResponseList) {
                        article.setArticleCategory(NewsCategory.BUSINESS);
                    }
                    return Observable.just(articleApiResponseList);
                })
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
                .flatMap(newsApiResponse -> Observable.just(newsApiResponse.getArticles()))
                .flatMap(articleApiResponseList -> {
                    for (ArticleApiResponse article : articleApiResponseList) {
                        article.setArticleCategory(NewsCategory.ENTERTAINMENT);
                    }
                    return Observable.just(articleApiResponseList);
                })
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
                .flatMap(newsApiResponse -> Observable.just(newsApiResponse.getArticles()))
                .flatMap(articleApiResponseList -> {
                    for (ArticleApiResponse article : articleApiResponseList) {
                        article.setArticleCategory(NewsCategory.SPORTS);
                    }
                    return Observable.just(articleApiResponseList);
                })
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
                .flatMap(newsApiResponse -> Observable.just(newsApiResponse.getArticles()))
                .flatMap(articleApiResponseList -> {
                    for (ArticleApiResponse article : articleApiResponseList) {
                        article.setArticleCategory(NewsCategory.SCIENCE);
                    }
                    return Observable.just(articleApiResponseList);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
