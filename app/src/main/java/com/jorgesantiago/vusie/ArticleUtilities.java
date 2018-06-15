package com.jorgesantiago.vusie;

import android.util.Log;

import com.jorgesantiago.vusie.NewsAPI.ArticleApiResponse;

public class ArticleUtilities {

    public static boolean doesArticlePassSaveCriteria(ArticleApiResponse article) {
        return isArticleInEnglish(article) && doesArticleHaveValidImageUrl(article);
    }

    private static boolean isArticleInEnglish(ArticleApiResponse article) {
        String sampleArticleText = article.getTitle();
        for (int i = 0; i < sampleArticleText.length(); ) {
            int c = sampleArticleText.codePointAt(i);
            if ((c >= 0x00C0 && c < 0x2400) || c >= 0x2C00) {
                Log.i("Utilities", "Article not in english!" + sampleArticleText);
                return false;
            }
            i += Character.charCount(c);
        }
        return true;
    }

    private static boolean doesArticleHaveValidImageUrl(ArticleApiResponse article) {
        return article.getUrlToImage() != null && !article.getUrlToImage().isEmpty();
    }

}
