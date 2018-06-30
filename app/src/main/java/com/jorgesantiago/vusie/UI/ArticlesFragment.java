package com.jorgesantiago.vusie.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jorgesantiago.vusie.NewsAPI.NewsCategory;
import com.jorgesantiago.vusie.R;
import com.jorgesantiago.vusie.RoomDB.ArticleRepository;
import com.jorgesantiago.vusie.ViewModel.ArticleViewModel;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.DaggerFragment;
import shivam.developer.featuredrecyclerview.FeatureLinearLayoutManager;
import shivam.developer.featuredrecyclerview.FeaturedRecyclerView;

public class ArticlesFragment extends DaggerFragment {

    @Inject
    ArticleRepository articleRepository;

    private ArticleCardContentAdapter articleCardContentAdapter;

    private ArticleViewModel articleViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        articleCardContentAdapter = new ArticleCardContentAdapter(getActivity());
        ArticleViewModel.ArticleViewModelFactory articleViewModelFactory = new ArticleViewModel.ArticleViewModelFactory(new ArticleViewModel(articleRepository));
        articleViewModel = ViewModelProviders.of(this, articleViewModelFactory).get(ArticleViewModel.class);
        subscribeToNewsCategory((NewsCategory) getArguments().getSerializable("category"));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FeaturedRecyclerView recyclerView = (FeaturedRecyclerView) inflater.inflate(R.layout.article_recycler_view, container, false);
        recyclerView.setAdapter(articleCardContentAdapter);
        recyclerView.setHasFixedSize(true);
        // must use the FeatureLinearLayoutManager with FeaturedRecyclerView for smooth scrolling
        recyclerView.setLayoutManager(new FeatureLinearLayoutManager(getActivity()));
        return recyclerView;
    }

    // tell the adapter which articles to display, and also observe to the LiveData object so it can automatically update when database is updated
    private void subscribeToNewsCategory(NewsCategory newsCategory) {
        switch (newsCategory) {
            case TECHNOLOGY:
                articleViewModel.getTechNewsArticles().observe(this, articles -> articleCardContentAdapter.setNewsArticles(articles));
                break;
            case BUSINESS:
                articleViewModel.getBusinessNewsArticles().observe(this, articles -> articleCardContentAdapter.setNewsArticles(articles));
                break;
            case SCIENCE:
                articleViewModel.getScienceNewsArticles().observe(this, articles -> articleCardContentAdapter.setNewsArticles(articles));
                break;
            case SPORTS:
                articleViewModel.getSportsNewsArticles().observe(this, articles -> articleCardContentAdapter.setNewsArticles(articles));
                break;
            case ENTERTAINMENT:
                articleViewModel.getEntertainmentNewsArticles().observe(this, articles -> articleCardContentAdapter.setNewsArticles(articles));
                break;
            case GENERAL:
                articleViewModel.getGeneralNewsArticles().observe(this, articles -> articleCardContentAdapter.setNewsArticles(articles));
                break;
            case HEALTH:
                articleViewModel.getHealthNewsArticles().observe(this, articles -> articleCardContentAdapter.setNewsArticles(articles));
                break;
        }
    }
}
