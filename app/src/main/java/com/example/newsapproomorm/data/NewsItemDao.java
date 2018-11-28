package com.example.newsapproomorm.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface NewsItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<NewsItem> newsItems);

    @Query("DELETE FROM news_item")
    void clearAll();

    @Query("SELECT * FROM news_item")
    LiveData<List<NewsItem>> loadAllNewsItems();
}
