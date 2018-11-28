package com.example.newsapproomorm.sync;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

public class DatabaseIntentService extends IntentService {
    public static final String TAG = "intentservice";
    public DatabaseIntentService() {
        super("DatabaseIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent.getAction();
        Log.d(TAG, action);
        Context context = DatabaseIntentService.this;
        NotificationTask.executeTask(context, action);
    }
}
