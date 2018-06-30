package com.jorgesantiago.vusie.UI;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.jorgesantiago.vusie.NewsAPI.NewsCategory;
import com.jorgesantiago.vusie.R;
import com.rd.PageIndicatorView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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

        // small fading animation used on the category title when the user switches between categories in the view pager
        fadeIn = AnimationUtils.loadAnimation(BrowseArticlesActivity.this, android.R.anim.fade_in);
        fadeIn.setDuration(2000);
        fadeOut = AnimationUtils.loadAnimation(BrowseArticlesActivity.this, android.R.anim.fade_out);
        fadeOut.setDuration(1000);

        // set up the default category view, which will always be the first category in ordinal value of NewsCategory
        title.setText(NewsCategory.values()[0].getNewsCategoryStringResource());
        pageIndicatorView.setSelected(0);
        pageIndicatorView.setSelectedColor(ContextCompat.getColor(this, NewsCategory.values()[0].getColorResource()));
        title.setTextColor(ContextCompat.getColor(this, NewsCategory.values()[0].getColorResource()));

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
            categoryFragment.setArguments(categoryBundle);
            adapter.addFragment(categoryFragment, getString(category.getNewsCategoryStringResource()));
        }

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // no-op
            }

            @Override
            public void onPageSelected(int position) {
                // as the user pages through the fragments

                // fade out the current category title
                title.startAnimation(fadeOut);

                // update the category title to the one the user is switching to
                int categoryTitle = NewsCategory.values()[position].getNewsCategoryStringResource();

                // update the color scheme to the color scheme of the category the user is switching to
                int color = ContextCompat.getColor(BrowseArticlesActivity.this, NewsCategory.values()[position].getColorResource());

                // update the selected position on the page indicator
                pageIndicatorView.setSelection(position);

                // update category title text
                title.setText(categoryTitle);

                // update category title color
                title.setTextColor(color);

                // update the selected indicator color
                pageIndicatorView.setSelectedColor(color);

                // fade the new category title in
                title.startAnimation(fadeIn);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // no-op
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
