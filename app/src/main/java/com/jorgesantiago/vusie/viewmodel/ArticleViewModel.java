package com.jorgesantiago.vusie.viewmodel;

import com.jorgesantiago.vusie.data.ArticleDatabaseEntity;
import com.jorgesantiago.vusie.data.ArticleRepository;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * This ArticleViewModel serves as an abstraction layer between the ui and any of the backend logic.
 * It does not expose to the ui any implementation regarding the database, or the repository. It simply
 * provides the View with data to display.
 */
public class ArticleViewModel extends ViewModel {

    private final ArticleRepository articleRepository;

    private final BehaviorSubject<Boolean> repositoryRefreshDone = BehaviorSubject.create(false);

    // cache of our news articles
    private final LiveData<List<ArticleDatabaseEntity>> techNewsArticles;
    private final LiveData<List<ArticleDatabaseEntity>> businessNewsArticles;
    private final LiveData<List<ArticleDatabaseEntity>> scienceNewsArticles;
    private final LiveData<List<ArticleDatabaseEntity>> sportsNewsArticles;
    private final LiveData<List<ArticleDatabaseEntity>> entertainmentNewsArticles;
    private final LiveData<List<ArticleDatabaseEntity>> generalNewsArticles;
    private final LiveData<List<ArticleDatabaseEntity>> healthNewsArticles;

    @Inject
    public ArticleViewModel(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;

        // subscribe to repository update status
        articleRepository.notifyWhenRefreshIsComplete()
                .filter(refreshComplete -> refreshComplete)
                .subscribe(refreshDone -> repositoryRefreshDone.onNext(true));

        // update our in-memory cache
        techNewsArticles = articleRepository.getTechNewsArticlesFromDB();
        businessNewsArticles = articleRepository.getBusinessNewsArticlesFromDB();
        scienceNewsArticles = articleRepository.getScienceNewsArticlesFromDB();
        sportsNewsArticles = articleRepository.getSportsNewsArticlesFromDB();
        entertainmentNewsArticles = articleRepository.getEntertainmentNewsArticlesFromDB();
        generalNewsArticles = articleRepository.getGeneralNewsArticlesFromDB();
        healthNewsArticles = articleRepository.getHealthNewsArticlesFromDB();
    }

    /**
     * Tells the repository to ping the API and retrieve/refresh our articles. Can be called from the view layer to request an update.
     */
    public void refreshRepository() {
        articleRepository.refreshRepository();
    }

    /**
     * Emits an Observable of type Boolean to let any subscribers know that the repository refresh has been completed
     *
     * @return the observable to be subscribed to
     */
    public Observable<Boolean> notifyWhenRefreshIsComplete() {
        return repositoryRefreshDone;
    }

    /**
     * Gets the locally cached list of tech news articles
     *
     * @return {@link LiveData} list of articles that can be observed on the ui thread to automatically notify observers when database has been updated
     */
    public LiveData<List<ArticleDatabaseEntity>> getTechNewsArticles() {
        return techNewsArticles;
    }

    /**
     * Gets the locally cached list of business news articles
     *
     * @return {@link LiveData} list of articles that can be observed on the ui thread to automatically notify observers when database has been updated
     */
    public LiveData<List<ArticleDatabaseEntity>> getBusinessNewsArticles() {
        return businessNewsArticles;
    }

    /**
     * Gets the locally cached list of science news articles
     *
     * @return {@link LiveData} list of articles that can be observed on the ui thread to automatically notify observers when database has been updated
     */
    public LiveData<List<ArticleDatabaseEntity>> getScienceNewsArticles() {
        return scienceNewsArticles;
    }

    /**
     * Gets the locally cached list of sports news articles
     *
     * @return {@link LiveData} list of articles that can be observed on the ui thread to automatically notify observers when database has been updated
     */
    public LiveData<List<ArticleDatabaseEntity>> getSportsNewsArticles() {
        return sportsNewsArticles;
    }

    /**
     * Gets the locally cached list of entertainment news articles
     *
     * @return {@link LiveData} list of articles that can be observed on the ui thread to automatically notify observers when database has been updated
     */
    public LiveData<List<ArticleDatabaseEntity>> getEntertainmentNewsArticles() {
        return entertainmentNewsArticles;
    }

    /**
     * Gets the locally cached list of general news articles
     *
     * @return {@link LiveData} list of articles that can be observed on the ui thread to automatically notify observers when database has been updated
     */
    public LiveData<List<ArticleDatabaseEntity>> getGeneralNewsArticles() {
        return generalNewsArticles;
    }

    /**
     * Gets the locally cached list of health news articles
     *
     * @return {@link LiveData} list of articles that can be observed on the ui thread to automatically notify observers when database has been updated
     */
    public LiveData<List<ArticleDatabaseEntity>> getHealthNewsArticles() {
        return healthNewsArticles;
    }
}