package com.jorgesantiago.vusie.Dagger2;

import com.jorgesantiago.vusie.Application.VusieApplication;
import com.jorgesantiago.vusie.RoomDB.ArticleDao;
import com.jorgesantiago.vusie.RoomDB.ArticleRoomDatabase;

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

    /*
    IMPORTANT REMINDER:
    We replaced the DataModule constructor that took in an Application and created our database in the constructor with the @Provides ArticleRoomDatabase
    method below that asks for an Application as a parameter. We needed to do this because in our top level ApplicationComponent Builder we pass in
    an application reference that can be used within our dependency graph. The method below asks Dagger for an Application reference, and Dagger is able to provide one.

    If we left it as the constructor, we are basically telling Dagger how to build a DataModule, and thus must add it in as apart of the top level ApplicationComponent
    Builder. Doing this keeps generating an error with Dagger where Dagger keeps telling us we must set a setter method for DataModule
     */

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
