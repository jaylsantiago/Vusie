package com.jorgesantiago.vusie.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.jorgesantiago.vusie.RoomDB.ArticleDatabaseEntity;
import com.jorgesantiago.vusie.RoomDB.ArticleRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * This ArticleViewModel serves as an abstraction layer between the UI and any of the backend logic.
 * It does not expose to the UI any implementation regarding the database, or the repository.
 */
public class ArticleViewModel extends ViewModel {

    // cache of our news articles
    private final LiveData<List<ArticleDatabaseEntity>> techNewsArticles;

    private LiveData<List<ArticleDatabaseEntity>> businessNewsArticles;

    private LiveData<List<ArticleDatabaseEntity>> scienceNewsArticles;

    private LiveData<List<ArticleDatabaseEntity>> sportsNewsArticles;

    private LiveData<List<ArticleDatabaseEntity>> entertainmentNewsArticles;

    private LiveData<List<ArticleDatabaseEntity>> generalNewsArticles;

    private LiveData<List<ArticleDatabaseEntity>> healthNewsArticles;

    private LiveData<List<ArticleDatabaseEntity>> allNewsArticles;

    @Inject
    public ArticleViewModel(ArticleRepository articleRepository) {
        techNewsArticles = articleRepository.getTechNewsArticlesFromDB();
        businessNewsArticles = articleRepository.getBusinessNewsArticlesFromDB();
        scienceNewsArticles = articleRepository.getScienceNewsArticlesFromDB();
        sportsNewsArticles = articleRepository.getSportsNewsArticlesFromDB();
        entertainmentNewsArticles = articleRepository.getEntertainmentNewsArticlesFromDB();
        generalNewsArticles = articleRepository.getGeneralNewsArticlesFromDB();
        healthNewsArticles = articleRepository.getHealthNewsArticlesFromDB();
        allNewsArticles = articleRepository.getAllNewsArticles();
    }

    public LiveData<List<ArticleDatabaseEntity>> getAllNewsArticles() {
        return allNewsArticles;
    }

    /**
     * Gets the locally cached list of tech news articles
     * @return {@link LiveData} list of articles that can be observed on the UI thread to automatically notify observers when database has been updated
     */
    public LiveData<List<ArticleDatabaseEntity>> getTechNewsArticles() {
        // hiding implementation so the UI doesnt directly interact with the repository
        return techNewsArticles;
    }

    /**
     * Gets the locally cached list of business news articles
     * @return {@link LiveData} list of articles that can be observed on the UI thread to automatically notify observers when database has been updated
     */
    public LiveData<List<ArticleDatabaseEntity>> getBusinessNewsArticles() {
        // hiding implementation so the UI doesnt directly interact with the repository
        return businessNewsArticles;
    }

    /**
     * Gets the locally cached list of science news articles
     * @return {@link LiveData} list of articles that can be observed on the UI thread to automatically notify observers when database has been updated
     */
    public LiveData<List<ArticleDatabaseEntity>> getScienceNewsArticles() {
        // hiding implementation so the UI doesnt directly interact with the repository
        return scienceNewsArticles;
    }

    /**
     * Gets the locally cached list of sports news articles
     * @return {@link LiveData} list of articles that can be observed on the UI thread to automatically notify observers when database has been updated
     */
    public LiveData<List<ArticleDatabaseEntity>> getSportsNewsArticles() {
        // hiding implementation so the UI doesnt directly interact with the repository
        return sportsNewsArticles;
    }

    /**
     * Gets the locally cached list of entertainment news articles
     * @return {@link LiveData} list of articles that can be observed on the UI thread to automatically notify observers when database has been updated
     */
    public LiveData<List<ArticleDatabaseEntity>> getEntertainmentNewsArticles() {
        // hiding implementation so the UI doesnt directly interact with the repository
        return entertainmentNewsArticles;
    }

    public LiveData<List<ArticleDatabaseEntity>> getGeneralNewsArticles() {
        return generalNewsArticles;
    }

    public LiveData<List<ArticleDatabaseEntity>> getHealthNewsArticles() {
        return healthNewsArticles;
    }

    public static class ArticleViewModelFactory implements ViewModelProvider.Factory{

        private ArticleViewModel articleViewModel;

        @Inject
        public ArticleViewModelFactory(ArticleViewModel viewModel) {
            this.articleViewModel = viewModel;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(ArticleViewModel.class)) {
                return (T) articleViewModel;
            }
            throw new IllegalArgumentException("Unknown class name");
        }
    }
}