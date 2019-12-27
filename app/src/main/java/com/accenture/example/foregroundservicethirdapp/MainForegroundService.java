package com.accenture.example.foregroundservicethirdapp;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class MainForegroundService extends Service {

    public static final String LOG_TAG = MainForegroundService.class.getSimpleName();
    public static final int NOTIFICATION_ID = 201;
    public static final String ACTION_START_SERVICE = "ACTION_START_SERVICE";
    public static final String ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE";
    public static final String ACTION_SHOW_TOAST = "ACTION_SHOW_TOAST";

    @Override
    public void onCreate() {
        Log.v(LOG_TAG, "-> onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v(LOG_TAG, "-> onStartCommand");

        switch (intent.getAction()) {

            case ACTION_START_SERVICE:

                Intent serviceToastIntent = new Intent(this, MainForegroundService.class);
                serviceToastIntent.setAction(ACTION_SHOW_TOAST);
                PendingIntent serviceToastPendingIntent = PendingIntent.getService(this, 0, serviceToastIntent, 0);

                Intent activityToastIntent = new Intent(this, MainActivity.class);
                activityToastIntent.setAction(MainActivity.ACTION_SHOW_TOAST);
                PendingIntent activityPendingToastIntent = PendingIntent.getActivity(this, 0, activityToastIntent, 0);

                Intent receiverToastIntent = new Intent(this, MainBroadcastReceiver.class);
                receiverToastIntent.setAction(MainBroadcastReceiver.ACTION_SHOW_TOAST);
                PendingIntent receiverToastPendingIntent = PendingIntent.getBroadcast(this, 0, receiverToastIntent, 0);

                Intent stopServiceIntent = new Intent(this, MainForegroundService.class);
                stopServiceIntent.setAction(ACTION_STOP_SERVICE);
                PendingIntent stopServicePendingIntent = PendingIntent.getService(this, 0, stopServiceIntent, 0);

                Notification notification =
                        new NotificationCompat.Builder(this, Constants.MAIN_FOREGROUND_SERVICE_CHANNEL_ID)
                                .setSmallIcon(R.drawable.ic_android)
                                .setContentTitle(getString(R.string.main_foreground_service_notification_title))
                                .setContentText(getString(R.string.main_foreground_service_notification_text))
                                .setContentIntent(serviceToastPendingIntent)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .addAction(R.drawable.ic_message, getString(R.string.show_toast_activity), activityPendingToastIntent)
                                .addAction(R.drawable.ic_message, getString(R.string.show_toast_receier), receiverToastPendingIntent)
                                .addAction(R.drawable.ic_cancel, getString(R.string.stop_service), stopServicePendingIntent)
                                .build();

                startForeground(NOTIFICATION_ID, notification);
                break;

            case ACTION_SHOW_TOAST:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), getString(R.string.toast_message_service), Toast.LENGTH_SHORT)
                                .show();
                    }
                }, 1000);
                break;

            case ACTION_STOP_SERVICE:
                stopForeground(true);
                stopSelf();
                break;
        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.v(LOG_TAG, "-> onDestroy");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.v(LOG_TAG, "-> onBind");
        return null;
    }
}
