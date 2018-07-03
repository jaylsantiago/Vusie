package com.jorgesantiago.vusie.data;

import com.jorgesantiago.vusie.api.request.NewsApiManager;
import com.jorgesantiago.vusie.api.response.NewsApiArticleResponse;
import com.jorgesantiago.vusie.utilities.ArticleUtility;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;
import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

/**
 * This repository will manage API requests and DB query threads and serves as an abstraction layer between
 * ui/ViewModel layers and the data persistence layers.
 */
@Singleton
public class ArticleRepository {

    // Hot observable so using a Behavior Subject to remember the last emitted boolean just incase the repository finishes its refresh while no one is subscribed
    private static final BehaviorSubject<Boolean> refreshDone = BehaviorSubject.create(false);
    private static boolean isRefreshInProgress = false;
    private static NewsApiManager newsApiManager;
    private static ArticleDao articleDao;

    /**
     * Creates an {@link ArticleRepository} instance and clears the database of any old articles
     */
    @Inject
    public ArticleRepository(final ArticleDao articleDao, final NewsApiManager newsApiManager) {
        ArticleRepository.articleDao = articleDao;
        ArticleRepository.newsApiManager = newsApiManager;
        clearDatabase();
    }

    /**
     * Emits an Observable of type Boolean to let any subscribers know that the repository refresh has been completed
     *
     * @return the observable to be subscribed to
     */
    public Observable<Boolean> notifyWhenRefreshIsComplete() {
        return refreshDone;
    }

    /**
     * Method to tell the repository to make the network requests to fetch our news article category endpoints for us
     */
    public void refreshRepository() {
        isOkToFetch().flatMap(okToFetch -> {
            isRefreshInProgress = true;
            refreshDone.onNext(false);
            // set off all our fetch observable chains, and merge them into one stream so we can insert the articles into our DB
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
                    for (NewsApiArticleResponse apiResponse : articleApiResponses) {
                        // filter out low-quality articles (ex: no article image, articles not in english, etc.)
                        if (ArticleUtility.doesArticlePassSaveCriteria(apiResponse)) {
                            // map our api response object to our article database entity representation so we can save to DB
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

    /**
     * Gets the locally stored list of tech news articles
     *
     * @return {@link LiveData} list of articles that can be observed on the ui thread to automatically notify observers when database has been updated
     */
    public LiveData<List<ArticleDatabaseEntity>> getTechNewsArticlesFromDB() {
        return articleDao.getAllTechNewsArticles();
    }

    /**
     * Gets the locally stored list of business news articles
     *
     * @return {@link LiveData} list of articles that can be observed on the ui thread to automatically notify observers when database has been updated
     */
    public LiveData<List<ArticleDatabaseEntity>> getBusinessNewsArticlesFromDB() {
        return articleDao.getAllBusinessNewsArticles();
    }

    /**
     * Gets the locally stored list of science news articles
     *
     * @return {@link LiveData} list of articles that can be observed on the ui thread to automatically notify observers when database has been updated
     */
    public LiveData<List<ArticleDatabaseEntity>> getScienceNewsArticlesFromDB() {
        return articleDao.getAllScienceNewsArticles();
    }

    /**
     * Gets the locally stored list of sports news articles
     *
     * @return {@link LiveData} list of articles that can be observed on the ui thread to automatically notify observers when database has been updated
     */
    public LiveData<List<ArticleDatabaseEntity>> getSportsNewsArticlesFromDB() {
        return articleDao.getAllSportsNewsArticles();
    }

    /**
     * Gets the locally stored list of entertainment news articles
     *
     * @return {@link LiveData} list of articles that can be observed on the ui thread to automatically notify observers when database has been updated
     */
    public LiveData<List<ArticleDatabaseEntity>> getEntertainmentNewsArticlesFromDB() {
        return articleDao.getAllEntertainmentNewsArticles();
    }

    /**
     * Gets the locally stored list of general news articles
     *
     * @return {@link LiveData} list of articles that can be observed on the ui thread to automatically notify observers when database has been updated
     */
    public LiveData<List<ArticleDatabaseEntity>> getGeneralNewsArticlesFromDB() {
        return articleDao.getAllGeneralNewsArticles();
    }

    /**
     * Gets the locally stored list of health news articles
     *
     * @return {@link LiveData} list of articles that can be observed on the ui thread to automatically notify observers when database has been updated
     */
    public LiveData<List<ArticleDatabaseEntity>> getHealthNewsArticlesFromDB() {
        return articleDao.getAllHealthNewsArticles();
    }

    // ping our API tech news endpoint
    private Observable<List<NewsApiArticleResponse>> getTechNewsArticlesFromAPI() {
        return newsApiManager.topHeadlinesFromTechnology();
    }

    // ping our API business news endpoint
    private Observable<List<NewsApiArticleResponse>> getBusinessNewsArticlesFromAPI() {
        return newsApiManager.topHeadlinesFromBusiness();
    }

    // ping our API science news endpoint
    private Observable<List<NewsApiArticleResponse>> getScienceNewsArticlesFromAPI() {
        return newsApiManager.topHeadlinesFromScience();
    }

    // ping our API sports news endpoint
    private Observable<List<NewsApiArticleResponse>> getSportsNewsArticlesFromAPI() {
        return newsApiManager.topHeadlinesFromSports();
    }

    // ping our API entertainment news endpoint
    private Observable<List<NewsApiArticleResponse>> getEntertainmentNewsArticlesFromAPI() {
        return newsApiManager.topHeadlinesFromEntertainment();
    }

    // ping our API tech general endpoint
    private Observable<List<NewsApiArticleResponse>> getGeneralNewsArticlesFromAPI() {
        return newsApiManager.topHeadlinesFromGeneral();
    }

    // ping our API tech health endpoint
    private Observable<List<NewsApiArticleResponse>> getHealthNewsArticlesFromAPI() {
        return newsApiManager.topHeadlinesFromHealth();
    }

    // make sure there isnt a refresh in progress before starting another one
    private Observable<Boolean> isOkToFetch() {
        return Observable.just(isRefreshInProgress).filter(isRefreshInProgress -> !isRefreshInProgress);
    }

    //Deletes all articles currently in the database
    private void clearDatabase() {
        // Room database transactions must be done off the ui thread or an exception is thrown
        Observable.just("Just make an observable out of nothing") // Observable.empty() doesnt work here... -- probably a better way to make an observable from scratch
                .subscribeOn(Schedulers.io())
                .map(imAnObservable -> {
                    articleDao.deleteAllArticle();
                    return imAnObservable;
                })
                .doOnError(Throwable::printStackTrace)
                .subscribe();
    }

    // Inserts an article into the database.
    private void insert(final ArticleDatabaseEntity article) {
        // Room database transactions must be done off the ui thread or an exception is thrown
        Observable.just(article)
                .subscribeOn(Schedulers.io())
                .map(articleDatabaseEntity -> {
                    articleDao.insert(articleDatabaseEntity);
                    return articleDatabaseEntity;
                })
                .doOnError(Throwable::printStackTrace)
                .subscribe();
    }
}

