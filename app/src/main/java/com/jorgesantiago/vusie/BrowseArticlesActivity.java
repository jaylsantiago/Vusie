package com.jorgesantiago.vusie;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.jorgesantiago.vusie.NewsAPI.NewsCategory;
import com.rd.PageIndicatorView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class BrowseArticlesActivity extends AppCompatActivity {


    private TextView title;
    private PageIndicatorView pageIndicatorView;
    private ViewPager viewPager;
    private Animation fadeIn;
    private Animation fadeOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_articles);
        viewPager = findViewById(R.id.viewPager);
        pageIndicatorView = findViewById(R.id.pageIndicatorView);
        title = findViewById(R.id.categoryTitle);

        fadeIn = AnimationUtils.loadAnimation(BrowseArticlesActivity.this, android.R.anim.fade_in);
        fadeIn.setDuration(2000);
        fadeOut = AnimationUtils.loadAnimation(BrowseArticlesActivity.this, android.R.anim.fade_out);
        fadeOut.setDuration(1000);

        title.setText(NewsCategory.GENERAL.getNewsCategoryStringResource());
        pageIndicatorView.setSelected(0);
        pageIndicatorView.setSelectedColor(getResources().getColor(NewsCategory.GENERAL.getColorResource()));
        title.setTextColor(getResources().getColor(NewsCategory.GENERAL.getColorResource()));

        setupViewPager();

    }

    // Add Fragments to Tabs
    private void setupViewPager() {
        CategoryFragmentAdapter adapter = new CategoryFragmentAdapter(getSupportFragmentManager());

        //Add a fragment to the adapter for each of our news categories
        for (NewsCategory category : NewsCategory.values()) {
            Bundle categoryBundle = new Bundle();
            categoryBundle.putSerializable("category", category);
            ArticlesFragment categoryFragment = new ArticlesFragment();
            categoryFragment.setArguments(categoryBundle);
            adapter.addFragment(categoryFragment, getString(category.getNewsCategoryStringResource()));
        }

        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                title.startAnimation(fadeOut);
                int categoryTitle = NewsCategory.values()[position].getNewsCategoryStringResource();
                int color = getResources().getColor(NewsCategory.values()[position].getColorResource());
                pageIndicatorView.setSelection(position);
                title.setText(categoryTitle);
                title.setTextColor(color);
                pageIndicatorView.setSelectedColor(color);
                title.startAnimation(fadeIn);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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

        // add to this adapters fragment list
        void addFragment(Fragment fragment, String title) {
            fragmentList.add(new Pair<>(fragment, title));
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentList.get(position).second;
        }
    }
}
