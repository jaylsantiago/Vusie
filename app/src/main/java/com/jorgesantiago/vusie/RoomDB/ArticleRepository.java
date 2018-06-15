package com.jorgesantiago.vusie.RoomDB;

import android.util.Log;

import com.jorgesantiago.vusie.ArticleUtilities;
import com.jorgesantiago.vusie.NewsAPI.ApiManager;
import com.jorgesantiago.vusie.NewsAPI.ArticleApiResponse;
import com.jorgesantiago.vusie.NewsAPI.ArticleSourceApiResponse;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

/**
 * This repository will manage query threads and serves as an abstraction layer between
 * UI/ViewModel layers and the data persistence layers.
 */
public class ArticleRepository {

    private static final BehaviorSubject<Boolean> refreshDone = BehaviorSubject.create(false);
    private static ApiManager newsApiManager;
    private static ArticleDao articleDao;
    private static boolean intervalStarted = false;
    private static boolean isRefreshInProgress = false;

    /**
     * Creates an {@link ArticleRepository} instance and updates its {@link LiveData} lists of articles
     */
    @Inject
    public ArticleRepository(ArticleDao articleDao, ApiManager newsApiManager) {
        ArticleRepository.articleDao = articleDao;
        ArticleRepository.newsApiManager = newsApiManager;
        clearDatabase();
        startApiFetchInterval();
    }

    public static Observable<Boolean> notifyWhenRefreshIsComplete() {
        return refreshDone;
    }

    private void startApiFetchInterval(){
        // according to NewsAPI docs, their articles database updates every 15 minutes, so we check every 15 minutes to make sure we have all the latest news
        Observable.interval(15, TimeUnit.MINUTES).subscribe(aLong -> {
            Log.i("REPO", "15 MIN INTERVAL REACHED");
            refreshRepository();
        });
    }

    public void refreshRepository() {
        // hit our endpoints whenever called to get the latest articles from the API
        isOkToFetch().flatMap(okToFetch -> {
            isRefreshInProgress = true;
            return Observable.merge(getTechNewsArticlesFromAPI(),
                    getBusinessNewsArticlesFromAPI(),
                    getScienceNewsArticlesFromAPI(),
                    getSportsNewsArticlesFromAPI(),
                    getEntertainmentNewsArticlesFromAPI(),
                    getGeneralNewsArticlesFromAPI(),
                    getHealthNewsArticlesFromAPI());
        }).subscribe(
                // for each
                articleApiResponses -> {
                    for (ArticleApiResponse apiResponse : articleApiResponses) {
                        // filter out articles without an image -- this is a media/image centered app
                        if (ArticleUtilities.doesArticlePassSaveCriteria(apiResponse)) {
                            insert(ArticleDatabaseEntity.convertApiResponseToDbEntity(apiResponse));
                        }
                    }
                },

                // on error
                Throwable::printStackTrace,

                //on completed
                () -> {
                    isRefreshInProgress = false;
                    refreshDone.onNext(true);
                }
        );
    }

    private Observable<Boolean> isOkToFetch() {
        return Observable.just(isRefreshInProgress).filter(isRefreshInProgress -> !isRefreshInProgress);
    }

    /**
     * Gets the locally cached list of top news articles
     *
     * @return {@link LiveData} list of articles that can be observed on the UI thread to automatically notify observers when database has been updated
     */
    public LiveData<List<ArticleDatabaseEntity>> getAllNewsArticles() {
        return articleDao.getAllNewsArticles();
    }

    private Observable<List<ArticleSourceApiResponse>> getSourcesFromAPI() {
        return newsApiManager.sources();
    }

    /**
     * Gets the locally cached list of tech news articles
     *
     * @return {@link LiveData} list of articles that can be observed on the UI thread to automatically notify observers when database has been updated
     */
    public LiveData<List<ArticleDatabaseEntity>> getTechNewsArticlesFromDB() {
        return articleDao.getAllTechNewsArticles();
    }

    private Observable<List<ArticleApiResponse>> getTechNewsArticlesFromAPI() {
        return newsApiManager.topHeadlinesFromTechnology();
    }

    /**
     * Gets the locally cached list of business news articles
     *
     * @return {@link LiveData} list of articles that can be observed on the UI thread to automatically notify observers when database has been updated
     */
    public LiveData<List<ArticleDatabaseEntity>> getBusinessNewsArticlesFromDB() {
        return articleDao.getAllBusinessNewsArticles();
    }

    private Observable<List<ArticleApiResponse>> getBusinessNewsArticlesFromAPI() {
        return newsApiManager.topHeadlinesFromBusiness();
    }

    /**
     * Gets the locally cached list of science news articles
     *
     * @return {@link LiveData} list of articles that can be observed on the UI thread to automatically notify observers when database has been updated
     */
    public LiveData<List<ArticleDatabaseEntity>> getScienceNewsArticlesFromDB() {
        return articleDao.getAllScienceNewsArticles();
    }

    private Observable<List<ArticleApiResponse>> getScienceNewsArticlesFromAPI() {
        return newsApiManager.topHeadlinesFromScience();
    }

    /**
     * Gets the locally cached list of sports news articles
     *
     * @return {@link LiveData} list of articles that can be observed on the UI thread to automatically notify observers when database has been updated
     */
    public LiveData<List<ArticleDatabaseEntity>> getSportsNewsArticlesFromDB() {
        return articleDao.getAllSportsNewsArticles();
    }

    private Observable<List<ArticleApiResponse>> getSportsNewsArticlesFromAPI() {
        return newsApiManager.topHeadlinesFromSports();
    }

    /**
     * Gets the locally cached list of entertainment news articles
     *
     * @return {@link LiveData} list of articles that can be observed on the UI thread to automatically notify observers when database has been updated
     */
    public LiveData<List<ArticleDatabaseEntity>> getEntertainmentNewsArticlesFromDB() {
        return articleDao.getAllEntertainmentNewsArticles();
    }

    private Observable<List<ArticleApiResponse>> getEntertainmentNewsArticlesFromAPI() {
        return newsApiManager.topHeadlinesFromEntertainment();
    }

    public LiveData<List<ArticleDatabaseEntity>> getGeneralNewsArticlesFromDB() {
        return articleDao.getAllGeneralNewsArticles();
    }

    private Observable<List<ArticleApiResponse>> getGeneralNewsArticlesFromAPI() {
        return newsApiManager.topHeadlinesFromGeneral();
    }

    public LiveData<List<ArticleDatabaseEntity>> getHealthNewsArticlesFromDB() {
        return articleDao.getAllHealthNewsArticles();
    }

    private Observable<List<ArticleApiResponse>> getHealthNewsArticlesFromAPI() {
        return newsApiManager.topHeadlinesFromHealth();
    }

    /**
     * Deletes all articles currently in the database. This method only gets called when a full article update is about to take place, ensuring the database contains only the most up to date articles.
     */
    private void clearDatabase() {
        // Room database transactions must be done off the UI thread or an exception is thrown
        Observable.just("Just make an observable out of nothing")
                .subscribeOn(Schedulers.io())
                .map(imAnObservable -> {
                    Log.i("REPO", "CLEARING DATABASE");
                    articleDao.deleteAllArticle();
                    return imAnObservable;
                })
                .subscribe();
    }

    /**
     * Inserts an article into the database.
     *
     * @param article to be saved into the database.
     */
    private void insert(ArticleDatabaseEntity article) {
        // Room database transactions must be done off the UI thread or an exception is thrown
        Observable.just(article)
                .subscribeOn(Schedulers.io())
                .map(articleDatabaseEntity -> {
                    Log.i("REPO", "INSERTING INTO DATABASE");
                    articleDao.insert(articleDatabaseEntity);
                    return articleDatabaseEntity;
                })
                .doOnError(Throwable::printStackTrace)
                .subscribe();
    }
}

