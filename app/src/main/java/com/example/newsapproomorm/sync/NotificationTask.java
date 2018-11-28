package com.example.newsapproomorm.sync;

import android.content.Context;
import android.util.Log;

import java.lang.annotation.Target;

public class NotificationTask {
    public static final String ACTION_NOTIFICATION = "action-notification";
    public static final String ACTION_DISMISS = "dismiss-notification";
    public static final String TAG = "notificationtask";

    public static void executeTask(Context context, String action) {
        if (ACTION_NOTIFICATION.equals(action)) {
            Log.d(TAG, ";lkj;lkj;lkj;lkj");
            syncAndNotify(context);
        }
        else if (ACTION_DISMISS.equals(action)) {
            Log.d(TAG, "asdf;lkjasdf;lkj");
            NotificationUtils.clearNotifications(context);
        }
    }

    private static void syncAndNotify(Context context) {
        FirebaseSyncer.syncDatabaseAutomatically();
        NotificationUtils.notifyUser(context);
    }
}
