package com.example.newsapproomorm.sync;

import com.example.newsapproomorm.data.NewsRepository;

public class FirebaseSyncer {

    public static void syncDatabaseAutomatically() {
        NewsRepository.syncDatabase();
    }
}
