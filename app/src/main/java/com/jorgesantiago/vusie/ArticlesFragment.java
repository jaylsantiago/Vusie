package com.jorgesantiago.vusie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jorgesantiago.vusie.NewsAPI.NewsCategory;
import com.jorgesantiago.vusie.RoomDB.ArticleRepository;
import com.jorgesantiago.vusie.ViewModel.ArticleViewModel;
import com.jorgesantiago.vusie.application.VusieApplication;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import shivam.developer.featuredrecyclerview.FeatureLinearLayoutManager;
import shivam.developer.featuredrecyclerview.FeaturedRecyclerView;

public class ArticlesFragment extends Fragment {

    private ArticleCardContentAdapter articleCardContentAdapter;

    private ArticleViewModel articleViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        articleCardContentAdapter = new ArticleCardContentAdapter(getActivity());

        ArticleRepository articleRepository = VusieApplication.get(Objects.requireNonNull(getActivity())).articleRepository();

        ArticleViewModel.ArticleViewModelFactory articleViewModelFactory = new ArticleViewModel.ArticleViewModelFactory(new ArticleViewModel(articleRepository));

        // by retrieving/setting the view model this way we ensure we retain the instance of the view model
        articleViewModel = ViewModelProviders.of(this, articleViewModelFactory).get(ArticleViewModel.class);

        subscribeToNewsCategory((NewsCategory) getArguments().getSerializable("category"));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FeaturedRecyclerView recyclerView = (FeaturedRecyclerView) inflater.inflate(R.layout.article_recycler_view, container, false);
        recyclerView.setAdapter(articleCardContentAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new FeatureLinearLayoutManager(getActivity()));
        return recyclerView;
    }

    private void subscribeToNewsCategory(NewsCategory newsCategory) {
        switch (newsCategory) {
            case TECHNOLOGY:
                // all the child fragments need to do is tell the adapter which articles to display, and also observe to the LiveData object so it can automatically update when database is updated
                articleViewModel.getTechNewsArticles().observe(this, articles -> articleCardContentAdapter.setNewsArticles(articles));
                break;
            case BUSINESS:
                // all the child fragments need to do is tell the adapter which articles to display, and also observe to the LiveData object so it can automatically update when database is updated
                articleViewModel.getBusinessNewsArticles().observe(this, articles -> articleCardContentAdapter.setNewsArticles(articles));
                break;
            case SCIENCE:
                // all the child fragments need to do is tell the adapter which articles to display, and also observe to the LiveData object so it can automatically update when database is updated
                articleViewModel.getScienceNewsArticles().observe(this, articles -> articleCardContentAdapter.setNewsArticles(articles));
                break;
            case SPORTS:
                // all the child fragments need to do is tell the adapter which articles to display, and also observe to the LiveData object so it can automatically update when database is updated
                articleViewModel.getSportsNewsArticles().observe(this, articles -> articleCardContentAdapter.setNewsArticles(articles));
                break;
            case ENTERTAINMENT:
                // all the child fragments need to do is tell the adapter which articles to display, and also observe to the LiveData object so it can automatically update when database is updated
                articleViewModel.getEntertainmentNewsArticles().observe(this, articles -> articleCardContentAdapter.setNewsArticles(articles));
                break;
            case GENERAL:
                // all the child fragments need to do is tell the adapter which articles to display, and also observe to the LiveData object so it can automatically update when database is updated
                articleViewModel.getGeneralNewsArticles().observe(this, articles -> articleCardContentAdapter.setNewsArticles(articles));
                break;
            case HEALTH:
                // all the child fragments need to do is tell the adapter which articles to display, and also observe to the LiveData object so it can automatically update when database is updated
                articleViewModel.getHealthNewsArticles().observe(this, articles -> articleCardContentAdapter.setNewsArticles(articles));
                break;
        }

    }

}
