package com.jorgesantiago.vusie.dagger;

import com.jorgesantiago.vusie.application.VusieApplication;
import com.jorgesantiago.vusie.data.ArticleDao;
import com.jorgesantiago.vusie.data.ArticleRoomDatabase;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

/**
 * Module for our Dagger Graph that provides elements for storing and retrieving data from the Room DB.
 * This module is dependent on elements defined in the ApiModule so we include that module in this one.
 */
@Module(includes = {ApiModule.class})
public class DataModule {

    @Singleton
    @Provides
    public ArticleRoomDatabase articleRoomDatabase(VusieApplication application) {
        return Room.databaseBuilder(application, ArticleRoomDatabase.class, "article_database")
                // we are okay with Room disposing of any saved articles and recreating the DB in the event of table changes
                // because the articles are not intended to be persisted indefinitely -- deleting all the articles
                // will just result in the new DB being populated with all the new articles once the repository refreshes
                // this also ensures that Room will not crash the app in the event of a migration we did not handle explicitly
                .fallbackToDestructiveMigration()
                .build();
    }

    @Singleton
    @Provides
    public ArticleDao articleDao(ArticleRoomDatabase articleRoomDatabase) {
        return articleRoomDatabase.articleDao();
    }
}
