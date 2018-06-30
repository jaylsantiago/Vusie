package com.jorgesantiago.vusie.Utilities;

import android.util.Log;

import com.jorgesantiago.vusie.NewsAPI.ArticleApiResponse;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Utility class that contains helper and utility methods related to dealing with Articles
 */
public class ArticleUtility {

    /**
     * Helper method to determine whether a specified {@link ArticleApiResponse} meets the criteria to be saved and later displayed to the user
     *
     * @param article to be tested
     * @return true is article is eligible for persistence
     */
    public static boolean doesArticlePassSaveCriteria(ArticleApiResponse article) {
        return doesArticleHaveValidUrl(article) && doesArticleHaveTitle(article) && doesArticleHaveValidImageUrl(article);
    }

    /* Unfortunately, not all articles are in english from the NewsAPI, even when we specifically request so in the url parameters
     this means we have to do additional sanity checks client-side.
     To determine if an article is *most likely* in english, we take a small sample from the article text, and check the character's
     unicode value to filter out articles that contain any characters from another language (ex: arabic, chinese, japanese characters, etc.)
    */
    private static boolean isArticleInEnglish(ArticleApiResponse article) {
        String sampleArticleText = article.getTitle();
        for (int i = 0; i < sampleArticleText.length(); ) {
            int c = sampleArticleText.codePointAt(i);
            if ((c >= 0x00C0 && c < 0x2400) || c >= 0x2C00) {
                Log.i("Utilities", "Article not in english! " + sampleArticleText);
                return false;
            }
            i += Character.charCount(c);
        }
        return true;
    }

    // make sure we have an image url so we can pass it into Picasso to load and display to user
    private static boolean doesArticleHaveValidImageUrl(ArticleApiResponse article) {
        return article.getUrlToImage() != null && !article.getUrlToImage().isEmpty();
    }

    // make sure we have an actual article url to load and display the article for the user to read
    private static boolean doesArticleHaveValidUrl(ArticleApiResponse article) {
        return article.getUrl() != null && !article.getUrl().isEmpty();
    }

    // make sure we have a title to display to the user
    private static boolean doesArticleHaveTitle(ArticleApiResponse article) {
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
