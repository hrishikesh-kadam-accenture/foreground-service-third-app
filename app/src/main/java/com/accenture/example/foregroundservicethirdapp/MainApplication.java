package com.accenture.example.foregroundservicethirdapp;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

public class MainApplication extends Application {

    public static final String LOG_TAG = MainApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        Log.v(LOG_TAG, "-> onCreate");
        super.onCreate();

        createNotificationChannel();
    }

    private void createNotificationChannel() {
        Log.v(LOG_TAG, "-> createNotificationChannel");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.main_foreground_service_channel_name);
            String description = getString(R.string.main_foreground_service_channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel =
                    new NotificationChannel(Constants.MAIN_FOREGROUND_SERVICE_CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
