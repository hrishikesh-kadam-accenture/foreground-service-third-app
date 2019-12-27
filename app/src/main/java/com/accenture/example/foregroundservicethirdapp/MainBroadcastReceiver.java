package com.accenture.example.foregroundservicethirdapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class MainBroadcastReceiver extends BroadcastReceiver {

    public static final String LOG_TAG = MainBroadcastReceiver.class.getSimpleName();
    public static final String ACTION_SHOW_TOAST = "ACTION_SHOW_TOAST";

    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.v(LOG_TAG, "-> onReceive");

        if (intent.getAction() == null)
            return;

        switch (intent.getAction()) {
            case ACTION_SHOW_TOAST:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, context.getString(R.string.toast_message_receiver), Toast.LENGTH_SHORT)
                                .show();
                    }
                }, 1000);
                break;
        }
    }
}
