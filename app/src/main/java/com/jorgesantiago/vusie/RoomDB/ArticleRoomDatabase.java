package com.jorgesantiago.vusie.RoomDB;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * This {@link Room} database class serves as an abstraction layer between us and the SQLite database.
 * By using Room we save ourselves all of the work we would usually have to do by implementing a SQLiteHelper class.
 * <p>
 * We only ever need one instance of a {@link RoomDatabase} so this class needs to follow Singleton protocol, and must be thread safe.
 */
@Database(entities = {ArticleDatabaseEntity.class}, version = 1)
public abstract class ArticleRoomDatabase extends RoomDatabase {
    // No implementation needed, Room knows what to do
    public abstract ArticleDao articleDao();
}