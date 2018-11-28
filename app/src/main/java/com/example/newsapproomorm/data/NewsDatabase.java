package com.example.newsapproomorm.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {NewsItem.class}, version = 1)
public abstract class NewsDatabase extends RoomDatabase {
    public abstract NewsItemDao newsItemDao();
    private static volatile NewsDatabase INSTANCE;

    static NewsDatabase getDatabase(final Context context){
        if (INSTANCE == null) {
            synchronized (NewsDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        NewsDatabase.class, "news_database")
                        .build();
            }
        }
        return INSTANCE;
    }
}
