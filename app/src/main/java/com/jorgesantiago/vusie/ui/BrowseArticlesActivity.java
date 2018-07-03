package com.jorgesantiago.vusie.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.jorgesantiago.vusie.R;
import com.jorgesantiago.vusie.api.NewsCategory;
import com.jorgesantiago.vusie.utilities.NetworkUtility;
import com.jorgesantiago.vusie.viewmodel.ArticleViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.util.Pair;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import dagger.android.support.DaggerAppCompatActivity;

public class BrowseArticlesActivity extends DaggerAppCompatActivity {

    @Inject
    ArticleViewModel articleViewModel;

    private Toolbar toolBar;
    private TabLayout tabs;
    private ContentLoadingProgressBar refreshingArticlesProgressBar;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_articles);
        toolBar = findViewById(R.id.toolBar);
        tabs = findViewById(R.id.tabLayout);
        refreshingArticlesProgressBar = findViewById(R.id.refreshingArticlesProgressBar);
        viewPager = findViewById(R.id.viewPager);

        setSupportActionBar(toolBar);

        // we set our own custom text view in the toolbar so we can set a custom font on the title
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // subscribe to notifications of repository refresh updates
        articleViewModel.notifyWhenRefreshIsComplete()
                .filter(refreshComplete -> refreshComplete)
                .subscribe(refreshDone -> refreshingArticlesProgressBar.setVisibility(View.GONE));

        setupViewPager();
    }

    // Add Fragments to Tabs
    private void setupViewPager() {
        CategoryFragmentAdapter adapter = new CategoryFragmentAdapter(getSupportFragmentManager());

        //Add a fragment to the adapter for each of our news categories
        for (NewsCategory category : NewsCategory.values()) {
            Bundle categoryBundle = new Bundle();
            // enums are serializable
            categoryBundle.putSerializable("category", category);
            ArticlesFragment categoryFragment = new ArticlesFragment();
            // tell the fragment which category of articles to display
            categoryFragment.setArguments(categoryBundle);
            adapter.addFragment(categoryFragment, getString(category.getNewsCategoryStringResource()));
        }

        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_browse_articles, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh_articles) {
            refreshingArticlesProgressBar.setVisibility(View.VISIBLE);
            tellRepositoryToRefresh();
        }

        return super.onOptionsItemSelected(item);
    }

    private void tellRepositoryToRefresh() {
        if (NetworkUtility.validNetworkState(this)) {
            articleViewModel.refreshRepository();
        } else {
            notifyUserOfInvalidNetworkState();
        }
    }

    private void notifyUserOfInvalidNetworkState() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(getString(R.string.no_network_dialog_title));
        alertDialogBuilder.setMessage(getString(R.string.no_network_dialog_message));
        alertDialogBuilder.setNeutralButton(getString(R.string.ok), (dialogInterface, i) -> tellRepositoryToRefresh());
        alertDialogBuilder.show();
    }

    // Simple fragment adapter that holds a list of Pair<Fragment fragment, String fragmentTitle>
    private static class CategoryFragmentAdapter extends FragmentPagerAdapter {

        private final List<Pair<Fragment, CharSequence>> fragmentList = new ArrayList<>();

        CategoryFragmentAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            // get the fragment
            return fragmentList.get(position).first;
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        // add to this adapter's fragment list
        void addFragment(Fragment fragment, String title) {
            fragmentList.add(new Pair<>(fragment, title));
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentList.get(position).second;
        }
    }
}
