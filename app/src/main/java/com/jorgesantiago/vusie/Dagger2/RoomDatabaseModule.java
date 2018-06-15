package com.jorgesantiago.vusie.Dagger2;

import android.app.Application;

import com.jorgesantiago.vusie.NewsAPI.ApiManager;
import com.jorgesantiago.vusie.RoomDB.ArticleDao;
import com.jorgesantiago.vusie.RoomDB.ArticleRepository;
import com.jorgesantiago.vusie.RoomDB.ArticleRoomDatabase;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

@Module(includes = {ApplicationModule.class, ApiModule.class})
public class RoomDatabaseModule {
    private final ArticleRoomDatabase articleRoomDatabase;

    public RoomDatabaseModule(Application application) {
        articleRoomDatabase = Room.databaseBuilder(application, ArticleRoomDatabase.class, "article_database").build();
    }

    @VusieApplicationScope
    @Provides
    public ArticleRoomDatabase articleRoomDatabase() {
        return articleRoomDatabase;
    }

    @VusieApplicationScope
    @Provides
    public ArticleDao articleDao(ArticleRoomDatabase articleRoomDatabase) {
        return articleRoomDatabase.articleDao();
    }

    @VusieApplicationScope
    @Provides
    public ArticleRepository articleRepository(ArticleDao articleDao, ApiManager newsApiManager) {
        return new ArticleRepository(articleDao, newsApiManager);
    }
}
