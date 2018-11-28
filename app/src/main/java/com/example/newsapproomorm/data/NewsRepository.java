package com.example.newsapproomorm.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.newsapproomorm.JsonUtils;
import com.example.newsapproomorm.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class NewsRepository {
    private static NewsItemDao mNewsItemDao;
    private LiveData<List<NewsItem>> mAllNewsItems;

    public NewsRepository(Application application) {
        NewsDatabase db = NewsDatabase.getDatabase(application.getApplicationContext());
        mNewsItemDao = db.newsItemDao();
        mAllNewsItems = mNewsItemDao.loadAllNewsItems();
    }

    private static class updateAsyncTask extends AsyncTask<URL, Void, Void>{
        private NewsItemDao mAsyncTaskDao;
        updateAsyncTask(NewsItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(URL... urls) {
            mAsyncTaskDao.clearAll();
            //put all news items inside repository
            URL url = urls[0];
            String newsApiResults = null;
            try {
                newsApiResults = NetworkUtils.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mAsyncTaskDao.insert(JsonUtils.parseNews(newsApiResults));
            return null;
        }
    }

//    private static class populateAsyncTask extends AsyncTask<Void, Void, Void> {
//        private NewsItemDao mAsyncTaskDao;
//        populateAsyncTask(NewsItemDao dao) {
//            mAsyncTaskDao = dao;
//        }
//
////        @Override
////        protected Void doInBackground(List<NewsItem>... lists) {
////            mAsyncTaskDao.loadAllNewsItems();
////            return null;
////        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            //get list from livedata
//            //run helper method in onpostexecute
//            return null;
//        }
//    }

    public static void syncDatabase() {
        new updateAsyncTask(mNewsItemDao).execute(NetworkUtils.buildUrl());
    }

//    public void updateRecyclerView() {
//        new populateAsyncTask(mNewsItemDao).execute();
//    }

    LiveData<List<NewsItem>> getAllNewsItems() {
        return mAllNewsItems;
    }
}
