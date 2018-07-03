package com.jorgesantiago.vusie.utilities;

import com.jorgesantiago.vusie.api.NewsCategory;
import com.jorgesantiago.vusie.api.response.NewsApiArticleResponse;
import com.jorgesantiago.vusie.api.response.NewsApiSourceResponse;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ArticleUtilityTest {

    private NewsApiArticleResponse testArticle;

    @Before
    public void setUp() {
        testArticle = new NewsApiArticleResponse(new NewsApiSourceResponse("CNN"),
                "Veronica Stracqualursi",
                "Trump claims he never pressured House GOP on immigration bill, despite tweeting otherwise",
                "Washington (CNN)President Donald Trump claimed on Saturday that he never pressured House Republicans to pass an immigration overhaul bill -- either the GOP leadership-backed legislation or more conservative proposal.",
                "https://www.cnn.com/2018/06/30/politics/trump-republican-immigration-bill/index.html",
                "https://cnn.com/path-to-image.jpg",
                "2018-07-01T12:33:00Z",
                NewsCategory.GENERAL);
    }

    @Test
    public void validArticle_ShouldPassSaveCriteria() {
        assertTrue(ArticleUtility.doesArticlePassSaveCriteria(testArticle));
    }

    @Test
    public void invalidArticles_ShouldNotPassSaveCriteria() {
        testArticle.setUrl(null);
        assertFalse(ArticleUtility.doesArticlePassSaveCriteria(testArticle));

        testArticle.setUrl("https://www.cnn.com/2018/06/30/politics/trump-republican-immigration-bill/index.html");
        testArticle.setUrlToImage(null);
        assertFalse(ArticleUtility.doesArticlePassSaveCriteria(testArticle));
    }

    @Test
    public void articlesWithAllEnglishCharacters_ShouldReturnTrueForEnglishCheck() {
        testArticle.setTitle("Trump claims he never pressured House GOP on immigration bill, despite tweeting otherwise");

        assertTrue(ArticleUtility.isArticleInEnglish(testArticle));
    }

    @Test
    public void articlesWithSpanishCharacters_ShouldReturnFalseForEnglishCheck() {
        testArticle.setTitle("Trump claims he never pressured House GOP on immigration bill, despite tweeting otherwiseñ ... was that Spanish?!");

        assertFalse(ArticleUtility.isArticleInEnglish(testArticle));
    }

    @Test
    public void articlesWithChineseCharacters_ShouldReturnFalseForEnglishCheck() {
        testArticle.setTitle("Trump claims he never pressured House GOP on immigration bill, despite tweeting otherwise诶 ... was that Chinese?!");

        assertFalse(ArticleUtility.isArticleInEnglish(testArticle));
    }

    @Test
    public void articlesWithJapaneseCharacters_ShouldReturnFalseForEnglishCheck() {
        testArticle.setTitle("Trump claims he never pressured House GOP on immigration bill, despite tweeting otherwiseぎ ... was that Japanese?!");

        assertFalse(ArticleUtility.isArticleInEnglish(testArticle));
    }

    @Test
    public void articlesWithArabicCharacters_ShouldReturnFalseForEnglishCheck() {
        testArticle.setTitle("Trump claims he never pressured House GOP on immigration bill, despite tweeting otherwiseاللغة ... was that Arabic?!");

        assertFalse(ArticleUtility.isArticleInEnglish(testArticle));
    }

    @Test
    public void articlesWithAnImageURL_ShouldReturnTrueForImageURLCheck() {
        testArticle.setUrlToImage("https://cnn.com/path-to-image.jpg");

        assertTrue(ArticleUtility.doesArticleHaveImageUrl(testArticle));
    }

    @Test
    public void articlesWithNullImageURL_ShouldReturnFalseForImageURLCheck() {
        testArticle.setUrlToImage(null);

        assertFalse(ArticleUtility.doesArticleHaveImageUrl(testArticle));
    }

    @Test
    public void articlesWithEmptyStringImageURL_ShouldReturnFalseForImageURLCheck() {
        testArticle.setUrlToImage("");

        assertFalse(ArticleUtility.doesArticleHaveImageUrl(testArticle));
    }

    @Test
    public void articlesWithAnArticleURL_ShouldReturnTrueForArticleURLCheck() {
        testArticle.setUrl("https://www.cnn.com/2018/06/30/politics/trump-republican-immigration-bill/index.html");

        assertTrue(ArticleUtility.doesArticleHaveUrl(testArticle));
    }

    @Test
    public void articlesWithNullArticleURL_ShouldReturnFalseForArticleURLCheck() {
        testArticle.setUrl(null);

        assertFalse(ArticleUtility.doesArticleHaveUrl(testArticle));
    }

    @Test
    public void articlesWithEmptyStringArticleURL_ShouldReturnFalseForArticleURLCheck() {
        testArticle.setUrl("");

        assertFalse(ArticleUtility.doesArticleHaveUrl(testArticle));
    }

    @Test
    public void articlesWithAnArticleTitle_ShouldReturnTrueForArticleTitleCheck() {
        testArticle.setTitle("Trump claims he never pressured House GOP on immigration bill, despite tweeting otherwise");

        assertTrue(ArticleUtility.doesArticleHaveTitle(testArticle));
    }

    @Test
    public void articlesWithNullArticleTitle_ShouldReturnFalseForArticleTitleCheck() {
        testArticle.setTitle(null);

        assertFalse(ArticleUtility.doesArticleHaveTitle(testArticle));
    }

    @Test
    public void articlesWithEmptyStringArticleTitle_ShouldReturnFalseForArticleTitleCheck() {
        testArticle.setTitle("");

        assertFalse(ArticleUtility.doesArticleHaveTitle(testArticle));
    }

    @Test
    public void theGetSourceLogoUrlHelperMethod_ShouldReturnACorrectlyFormattedURLEncodedURL() {
        final String expectedOutput = "https://logo.clearbit.com/https%3A%2F%2Fwww.cnn.com";
        testArticle.setUrl("https://www.cnn.com/2018/06/30/politics/trump-republican-immigration-bill/index.html");

        assertEquals(ArticleUtility.getSourceLogoUrl(testArticle.getUrl()), expectedOutput);
    }
}