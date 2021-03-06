package com.jorgesantiago.vusie.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jorgesantiago.vusie.R;
import com.jorgesantiago.vusie.api.NewsCategory;
import com.jorgesantiago.vusie.data.ArticleDatabaseEntity;
import com.jorgesantiago.vusie.utilities.ArticleUtility;
import com.jorgesantiago.vusie.utilities.DisplayUtility;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import shivam.developer.featuredrecyclerview.FeatureRecyclerViewAdapter;
import xyz.klinker.android.article.ArticleIntent;
import xyz.klinker.android.article.ArticleUtils;

/**
 * Simple adapter that populates and recycles our ui cards containing different news articles for the user to browse through
 */
class ArticleCardContentAdapter extends FeatureRecyclerViewAdapter {

    private static final int ITEM_TYPE_ARTICLE = 0;
    private static final int ITEM_TYPE_DUMMY = 1;

    private final Context context;
    private List<ArticleDatabaseEntity> newsArticles; // cached copy of articles

    ArticleCardContentAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateFeaturedViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case ITEM_TYPE_ARTICLE:
                return new ArticleContentViewHolder(LayoutInflater.from(context), viewGroup);
            case ITEM_TYPE_DUMMY:
            default:
                return new DummyViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_dummy_item, viewGroup, false));
        }
    }

    @Override
    public void onBindFeaturedViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ArticleContentViewHolder) {
            final ArticleContentViewHolder articleCardViewHolder = ArticleContentViewHolder.class.cast(viewHolder);
            ArticleDatabaseEntity article = newsArticles.get(position);
            //TODO hide API key?
            ArticleUtils utils = new ArticleUtils("ab629f3fdd29eb3c05da832fc1760b18");
            // preloading our articles with the Article Viewer API so when the user clicks on an article it will load almost instantly
            utils.preloadArticle(context, article.getUrl(), null);
            articleCardViewHolder.updateCardContents(article);
        } else if (viewHolder instanceof DummyViewHolder) {
            // nothing, this is a dummy view holder
        }
    }

    @Override
    public int getFeaturedItemsCount() {
        // need to add 2 dummy layouts to get last 2 items of featured recycler view to scroll
        return newsArticles == null ? 0 : newsArticles.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position >= 0 && position < newsArticles.size() ? ITEM_TYPE_ARTICLE : ITEM_TYPE_DUMMY;
    }

    @Override
    public void onSmallItemResize(RecyclerView.ViewHolder viewHolder, int position, float offset) {
        //No-op
    }

    @Override
    public void onBigItemResize(RecyclerView.ViewHolder viewHolder, int position, float offset) {
        //No-op
    }

    /**
     * This gets called from within a {@link androidx.lifecycle.LiveData}'s observe callback, to notify the ui to update when our database has been updated
     * with more articles
     *
     * @param articles to be displayed in the adapter
     */
    void setNewsArticles(List<ArticleDatabaseEntity> articles) {
        newsArticles = articles;
        notifyDataSetChanged();
    }

    private class DummyViewHolder extends RecyclerView.ViewHolder {

        DummyViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class ArticleContentViewHolder extends RecyclerView.ViewHolder {

        private View articleLayout;
        private ImageView articlePicture;
        private ImageView articleSourceLogo;
        private TextView articleSource;
        private TextView articleTitle;
        private TextView articleDescription;
        private Picasso picasso;

        private ArticleContentViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.article_card_layout, parent, false));

            articleLayout = itemView.findViewById(R.id.articleLayout);
            articlePicture = itemView.findViewById(R.id.articlePicture);
            articleSourceLogo = itemView.findViewById(R.id.sourceLogo);
            articleSource = itemView.findViewById(R.id.articleSource);
            articleTitle = itemView.findViewById(R.id.articleTitle);
            articleDescription = itemView.findViewById(R.id.articleDescription);
            picasso = Picasso.get();
        }

        private void updateCardContents(final ArticleDatabaseEntity article) {

            // category reference for the current article being displayed
            NewsCategory category = NewsCategory.values()[article.getArticleCategory()];

            // load our Article image
            picasso.load(article.getUrlToImage())
                    // resize the image to a little bit larger than the screen width,
                    // this helps with device memory while also displaying the highest quality image possible for the specific device's screen
                    // height of 0 tells picasso to respect original image aspect ratio
                    .resize((int) (DisplayUtility.getScreenWidthInPixels(context) * 1.25), 0)
                    // only scale large photos down, do not scale smaller photos up, that will cause them to look distorted and makes the ui ugly
                    .onlyScaleDown()
                    //placeholder image while picasso is loading our image or is unable to load it
                    .placeholder(R.drawable.placeholder_gradient)
                    // fill our image view
                    .centerCrop()
                    // tell picasso to load the image right into our image view
                    .into(articlePicture);

            // loading our source logo to be displayed
            picasso.load(ArticleUtility.getSourceLogoUrl(article.getUrl()))
                    //placeholder image while picasso is loading our image or is unable to load it
                    .placeholder(R.drawable.placeholder_gradient)
                    .into(articleSourceLogo);

            // set the article source text
            articleSource.setText(article.getSource());

            // set the article title text
            articleTitle.setText(article.getTitle());

            // not all articles from api come with a description
            if (article.getDescription() != null && !article.getDescription().isEmpty()) {
                // if we have a description, set the description text
                articleDescription.setText(article.getDescription());
            }

            articleLayout.setOnClickListener(view -> {
                // we use the ArticleViewer API to display articles
                ArticleIntent intent = new ArticleIntent.Builder(context, "ab629f3fdd29eb3c05da832fc1760b18")
                        .setTheme(ArticleIntent.THEME_LIGHT)
                        .build();

                intent.launchUrl(context, article.getUrl());
            });
        }
    }
}
