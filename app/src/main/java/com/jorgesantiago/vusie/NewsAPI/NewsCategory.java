package com.jorgesantiago.vusie.NewsAPI;

import com.jorgesantiago.vusie.R;

/**
 * Simple enum for our different news article categories and their associated string and scheme color
 */
public enum NewsCategory {
    GENERAL(R.string.news_category_general, R.color.generalStoriesColorTheme),
    ENTERTAINMENT(R.string.news_category_entertainment, R.color.entertainmentStoriesColorTheme),
    TECHNOLOGY(R.string.news_category_technology, R.color.techStoriesColorTheme),
    SPORTS(R.string.news_category_sports, R.color.sportsStoriesColorTheme),
    BUSINESS(R.string.news_category_business, R.color.businessStoriesColorTheme),
    SCIENCE(R.string.news_category_science, R.color.scienceStoriesColorTheme),
    HEALTH(R.string.news_category_health, R.color.healthStoriesColorTheme);

    private final int stringResource;

    private final int colorResource;

    NewsCategory(final int stringResource, final int colorResource) {
        this.stringResource = stringResource;
        this.colorResource = colorResource;
    }

    /**
     * Gets the associated string resource for the given NewsCategory
     *
     * @return the String resource ID
     */
    public int getNewsCategoryStringResource() {
        return stringResource;
    }

    /**
     * Gets the associated color resource for the given NewsCategory
     *
     * @return the color resource ID
     */
    public int getColorResource() {
        return colorResource;
    }
}
