package com.example.newsapproomorm.sync;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat.Action;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.newsapproomorm.MainActivity;
import com.example.newsapproomorm.R;

public class NotificationUtils {
    private static final int NOTIFY_USER_NOTIFICATION_ID = 1;
    private static final int ACTION_CANCEL_NOTIFICATION_ID = 14;
    private static final int PENDING_INTENT_ID = 15;
    private static final String NOTIFICATION_ID = "notification_channel";
    private static final String TAG = "notificationutils";

    public static void notifyUser(Context context) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, NOTIFICATION_ID)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_error_black_24dp)
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(context.getString(R.string.notification_text))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        context.getString(R.string.notification_text)))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .addAction(cancelNotifications(context))
                .setAutoCancel(true);

        notificationManager.notify(NOTIFY_USER_NOTIFICATION_ID, notificationBuilder.build());
    }

    private static PendingIntent contentIntent(Context context) {
        Intent startActivityIntent = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(
                context,
                PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }

    private static Action cancelNotifications(Context context) {
        Intent cancelIntent = new Intent(context, DatabaseIntentService.class);
        cancelIntent.setAction(NotificationTask.ACTION_DISMISS);
        PendingIntent cancelNotificationPendingIntent = PendingIntent.getService(
                context,
                ACTION_CANCEL_NOTIFICATION_ID,
                cancelIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        Action cancelNotificationAction = new Action(R.drawable.ic_backspace_black_24dp,
                "Ok",
                cancelNotificationPendingIntent);
        Log.d(TAG, "ok button");
        return cancelNotificationAction;
    }

    public static void clearNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }
}
