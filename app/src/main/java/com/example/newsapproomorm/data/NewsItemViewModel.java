package com.example.newsapproomorm.data;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class NewsItemViewModel extends AndroidViewModel {

    private NewsRepository mRepository;

    private LiveData<List<NewsItem>> mAllNews;

    public NewsItemViewModel(@NonNull Application application) {
        super(application);
        mRepository = new NewsRepository(application);
        mAllNews = mRepository.getAllNewsItems();
    }

    public LiveData<List<NewsItem>> getAllNews() {
        return mAllNews;
    }

    public void sync() {
        mRepository.syncDatabase();
    }
}
