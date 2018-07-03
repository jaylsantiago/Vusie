package com.jorgesantiago.vusie.api;

import com.jorgesantiago.vusie.R;

/**
 * Simple enum for our different news article categories and their associated string and scheme color
 */
public enum NewsCategory {
    GENERAL(R.string.news_category_general),
    ENTERTAINMENT(R.string.news_category_entertainment),
    TECHNOLOGY(R.string.news_category_technology),
    SPORTS(R.string.news_category_sports),
    BUSINESS(R.string.news_category_business),
    SCIENCE(R.string.news_category_science),
    HEALTH(R.string.news_category_health);

    private final int stringResource;

    NewsCategory(final int stringResource) {
        this.stringResource = stringResource;
    }

    /**
     * Gets the associated string resource for the given NewsCategory
     *
     * @return the String resource ID
     */
    public int getNewsCategoryStringResource() {
        return stringResource;
    }
}
