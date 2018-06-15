package com.jorgesantiago.vusie;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jorgesantiago.vusie.NewsAPI.NewsCategory;
import com.jorgesantiago.vusie.RoomDB.ArticleDatabaseEntity;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import shivam.developer.featuredrecyclerview.FeatureRecyclerViewAdapter;
import xyz.klinker.android.article.ArticleIntent;
import xyz.klinker.android.article.ArticleUtils;

/**
 * Simple adapter that populates and recycles our UI cards containing different news articles for the user to browse through
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
                return new ArticleContentViewHolder(LayoutInflater.from(viewGroup.getContext()), viewGroup);
            case ITEM_TYPE_DUMMY:
            default:
                return new DummyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_dummy_item, viewGroup, false));
        }
    }

    @Override
    public void onBindFeaturedViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ArticleContentViewHolder) {
            final ArticleContentViewHolder articleCardViewHolder = ArticleContentViewHolder.class.cast(viewHolder);
            ArticleDatabaseEntity article = newsArticles.get(position);
            ArticleUtils utils = new ArticleUtils("ab629f3fdd29eb3c05da832fc1760b18");
            utils.preloadArticle(context, article.getUrl(), null);
            articleCardViewHolder.updateCardContents(article);
        } else if (viewHolder instanceof DummyViewHolder) {
            // nothing, this is a dummy view holder
        }
    }

    @Override
    public int getFeaturedItemsCount() {
        return newsArticles == null ? 0 : newsArticles.size() + 2; // need to add 2 dummy layouts to get last 2 items of recycler view to scroll
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
     * This gets called from within a {@link androidx.lifecycle.LiveData}'s observe callback, to notify the UI to update when our database has been updated
     *
     * @param articles to be displayed in the adapter
     */
    void setNewsArticles(List<ArticleDatabaseEntity> articles) {
        newsArticles = articles;
        notifyDataSetChanged();
    }

    static class DummyViewHolder extends RecyclerView.ViewHolder {

        DummyViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class ArticleContentViewHolder extends RecyclerView.ViewHolder {

        private ImageView articlePicture;
        private ImageView articleSourceLogo;
        private TextView articleSource;
        private TextView articleTitle;
        private TextView articleDescription;
        private Button readMoreButton;

        private ArticleContentViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.article_card_layout, parent, false));

            articlePicture = itemView.findViewById(R.id.articlePicture);
            articleSourceLogo = itemView.findViewById(R.id.sourceLogo);
            articleSource = itemView.findViewById(R.id.articleSource);
            articleTitle = itemView.findViewById(R.id.articleTitle);
            articleDescription = itemView.findViewById(R.id.articleDescription);
            readMoreButton = itemView.findViewById(R.id.readMoreButton);
        }

        private void updateCardContents(final ArticleDatabaseEntity article) {

            NewsCategory category = NewsCategory.values()[article.getArticleCategory()];

            // we load our images in real time instead of dealing with the additional database or FileManager over head of saving these images locally
            // Picasso is extremely efficient and fast in loading images in real time for the user to see, tradeoffs seemed worth it for this simple app
            Picasso.get()
                    // tell picasso where to find the image we want
                    .load(article.getUrlToImage())
                    // resize the image, this helps with device memory -- height of 0 tells picasso to respect original image aspect ratio
                    .resize(1500, 0)
                    // fill our image view
                    .centerCrop()
                    // tell picasso to load the image right into our image view
                    .into(articlePicture);

            String query = null;
            try {
                query = URLEncoder.encode(article.getSourceLogoUrl(), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if (query != null) {
                String sourceLogoUrl = "https://logo.clearbit.com/" + query;

                Picasso.get().load(sourceLogoUrl).placeholder(category.getColorResource()).into(articleSourceLogo);
            }

            articleSource.setText(article.getSource());

            articleTitle.setText(article.getTitle());

            // not all articles from ApiInterface come with a description, we handle this to show something to the user instead of a blank area
            if (article.getDescription() != null && !article.getDescription().isEmpty()) {
                articleDescription.setText(article.getDescription());
            }

            readMoreButton.getBackground().setTint(ContextCompat.getColor(readMoreButton.getContext(), category.getColorResource()));

            readMoreButton.setOnClickListener(view -> {
                ArticleIntent intent = new ArticleIntent.Builder(context, "ab629f3fdd29eb3c05da832fc1760b18")
                        .setTheme(ArticleIntent.THEME_LIGHT)
                        .build();

                intent.launchUrl(context, article.getUrl());
            });
        }
    }
}
