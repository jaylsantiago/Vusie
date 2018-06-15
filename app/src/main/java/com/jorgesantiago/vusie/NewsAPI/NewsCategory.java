package com.jorgesantiago.vusie.NewsAPI;

import com.jorgesantiago.vusie.R;

/**
 * Simple enum for our different news article categories and their associated string, color, icon, and background resources
 */
public enum NewsCategory {
    GENERAL(R.string.news_category_general, R.color.topStoriesColorTheme),
    ENTERTAINMENT(R.string.news_category_entertainment, R.color.entertainmentStoriesColorTheme),
    TECHNOLOGY(R.string.news_category_technology, R.color.techStoriesColorTheme),
    SPORTS(R.string.news_category_sports, R.color.sportsStoriesColorTheme),
    BUSINESS(R.string.news_category_business, R.color.businessStoriesColorTheme),
    SCIENCE(R.string.news_category_science, R.color.scienceStoriesColorTheme),
    HEALTH(R.string.news_category_health, R.color.sportsStoriesColorTheme);

    private final int stringResource;

    private int colorResource;

    NewsCategory(final int stringResource, final int colorResource) {
        this.stringResource = stringResource;
        this.colorResource = colorResource;
    }

    public static NewsCategory getNewsCategory(String category) {
        if (category.equals(TECHNOLOGY.name())) {
            return TECHNOLOGY;
        } else if (category.equals(BUSINESS.name())) {
            return BUSINESS;
        } else if (category.equals(SCIENCE.name())) {
            return SCIENCE;
        } else if (category.equals(SPORTS.name())) {
            return SPORTS;
        } else if (category.equals(ENTERTAINMENT.name())) {
            return ENTERTAINMENT;
        } else if (category.equals(GENERAL.name())) {
            return GENERAL;
        } else {
            return HEALTH;
        }
    }

    public int getNewsCategoryStringResource() {
        return stringResource;
    }

    public int getColorResource() {
        return colorResource;
    }
}
