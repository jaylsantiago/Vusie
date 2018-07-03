package com.jorgesantiago.vusie.utilities;

import com.jorgesantiago.vusie.api.response.NewsApiArticleResponse;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Utility class that contains helper and utility methods related to dealing with Articles
 */
public final class ArticleUtility {

    /**
     * Helper method to determine whether a specified {@link NewsApiArticleResponse} meets the criteria to be saved and later displayed to the user
     *
     * @param article to be tested
     * @return true is article is eligible for persistence
     */
    public static boolean doesArticlePassSaveCriteria(NewsApiArticleResponse article) {
        return doesArticleHaveUrl(article) && doesArticleHaveTitle(article) && doesArticleHaveImageUrl(article);
    }

    /**
     * Helper method to check if the specified article is in english
     *
     * @param article to be checked
     * @return true if article is in english
     */
    public static boolean isArticleInEnglish(NewsApiArticleResponse article) {

        /* Unfortunately, not all articles are in english from the api, even when we specifically request so in the url parameters
         * this means we have to do additional sanity checks client-side.
         * To determine if an article is *most likely* in english, we take a small sample from the article text, and check the character's
         * unicode value to filter out articles that contain any characters from another language (ex: arabic, chinese, japanese characters, etc.)
         */
        String sampleArticleText = article.getTitle();
        for (int i = 0; i < sampleArticleText.length(); ) {
            int c = sampleArticleText.codePointAt(i);
            if ((c >= 0x00C0 && c < 0x2400) || c >= 0x2C00) {
                return false;
            }
            i += Character.charCount(c);
        }
        return true;
    }

    /**
     * Helper method that checks if an article has an image url so we can pass it into Picasso to load for us
     *
     * @param article to be checked
     * @return true if article has valid image url
     */
    public static boolean doesArticleHaveImageUrl(NewsApiArticleResponse article) {
        return article.getUrlToImage() != null && !article.getUrlToImage().isEmpty();
    }

    /**
     * Helper method that checks if an article has a url
     *
     * @param article to be checked
     * @return true if article has valid url
     */
    public static boolean doesArticleHaveUrl(NewsApiArticleResponse article) {
        return article.getUrl() != null && !article.getUrl().isEmpty();
    }

    /**
     * Helper method that checks if an article has a title
     *
     * @param article to be checked
     * @return true if article has valid title
     */
    public static boolean doesArticleHaveTitle(NewsApiArticleResponse article) {
        return article.getTitle() != null && !article.getTitle().isEmpty();
    }

    /**
     * Helper method to get us the domain URL of the source so we can feed it into the Clearbit Logo API and Picasso
     *
     * @return URL of the source
     */
    public static String getSourceLogoUrl(String url) {
        String sourceLogoUrl = "";

        //Basically this method gets us the base domain URL of the source from the article URL
        // the 3rd '/' is what we want the position of --> https://www.yoursource.com/
        int pos = url.indexOf("/");
        int n = 3;
        while (--n > 0 && pos != -1) {
            pos = url.indexOf("/", pos + 1);
        }

        String query = null;
        try {
            // we get our URl from the substring of the beginning, to the position of the 3rd '/'
            query = URLEncoder.encode(url.substring(0, pos), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (query != null) {
            // the URL we build to use ClearBit's logo API which conveniently gives us any domain's logo which we can pass into Picasso to load
            sourceLogoUrl = "https://logo.clearbit.com/" + query;
        }
        return sourceLogoUrl;
    }

}
