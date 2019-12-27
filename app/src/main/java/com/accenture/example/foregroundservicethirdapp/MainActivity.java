package com.accenture.example.foregroundservicethirdapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static final String ACTION_SHOW_TOAST = "ACTION_SHOW_TOAST";

    private Button buttonStartService, buttonStopService;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(LOG_TAG, "-> onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();
        onNewIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.v(LOG_TAG, "-> onNewIntent");
        super.onNewIntent(intent);

        if (intent.getAction() == null)
            return;

        switch (intent.getAction()) {
            case ACTION_SHOW_TOAST:
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), getString(R.string.toast_message_activity), Toast.LENGTH_SHORT)
                                .show();
                    }
                }, 1000);
                break;
        }
    }

    private void bindViews() {
        Log.v(LOG_TAG, "-> bindViews");

        buttonStartService = findViewById(R.id.buttonStartService);
        buttonStopService = findViewById(R.id.buttonStopService);

        buttonStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(LOG_TAG, "-> onClick -> buttonStartService");
                startMainForegroundService();
            }
        });

        buttonStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(LOG_TAG, "-> onClick -> buttonStopService");
                stopMainForegroundService();
            }
        });
    }

    private void startMainForegroundService() {
        Log.v(LOG_TAG, "-> startMainForegroundService");

        Intent serviceIntent = new Intent(this, MainForegroundService.class);
        serviceIntent.setAction(MainForegroundService.ACTION_START_SERVICE);
        ContextCompat.startForegroundService(this, serviceIntent);
    }

    private void stopMainForegroundService() {
        Log.v(LOG_TAG, "-> stopMainForegroundService");

        Intent serviceIntent = new Intent(this, MainForegroundService.class);
        serviceIntent.setAction(MainForegroundService.ACTION_STOP_SERVICE);
        stopService(serviceIntent);
    }
}
