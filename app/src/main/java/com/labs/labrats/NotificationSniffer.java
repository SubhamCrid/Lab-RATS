package com.labs.labrats;

import android.app.Notification;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class NotificationSniffer extends NotificationListenerService {
    private static final String TAG = "NotificationSniffer";
    public static final List<NotificationData> history = new ArrayList<>();

    public static class NotificationData {
        public String packageName;
        public String title;
        public String text;
        public long timestamp;

        public NotificationData(String packageName, String title, String text, long timestamp) {
            this.packageName = packageName;
            this.title = title;
            this.text = text;
            this.timestamp = timestamp;
        }
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        String packageName = sbn.getPackageName();
        Notification notification = sbn.getNotification();
        Bundle extras = notification.extras;

        String title = extras.getString(Notification.EXTRA_TITLE);
        String text = extras.getString(Notification.EXTRA_TEXT);
        long timestamp = sbn.getPostTime();

        if (title == null) title = "";
        if (text == null) text = "";

        Log.d(TAG, "Notification from " + packageName + ": " + title + " - " + text);
        
        synchronized (history) {
            history.add(0, new NotificationData(packageName, title, text, timestamp));
            // Keep last 100 notifications
            if (history.size() > 100) {
                history.remove(history.size() - 1);
            }
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        // Not needed for now
    }

    public static List<NotificationData> getHistory() {
        synchronized (history) {
            return new ArrayList<>(history);
        }
    }
    
    public static void clearHistory() {
        synchronized (history) {
            history.clear();
        }
    }
}
