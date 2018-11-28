package com.example.newsapproomorm.sync;

import android.content.Context;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

public class FirebaseUtils {
    private static final int REMINDER_INTERVAL_SECONDS = 10;
    private static final int SYNC_FLEXTIME_SECONDS = REMINDER_INTERVAL_SECONDS;
    private static final String REMINDER_JOB_TAG = "database-synced-tag";
    private static boolean sInitialized;

    synchronized public static void scheduleDatabaseSync(@NonNull final Context context) {
        if (sInitialized) return;

        Driver driver = new GooglePlayDriver(context);

        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        Job autoSyncJob = dispatcher.newJobBuilder()
                .setService(DatabaseJobService.class)
                .setTag(REMINDER_JOB_TAG)
                .setTrigger(Trigger.executionWindow(
                        REMINDER_INTERVAL_SECONDS,
                        REMINDER_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                .setReplaceCurrent(true)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .build();

        dispatcher.schedule(autoSyncJob);

        sInitialized = true;
    }
}