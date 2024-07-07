package com.example.myyolov8app;

import android.app.Application;

import com.example.myyolov8app.data_local.DataLocalManager;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DataLocalManager.init(getApplicationContext());
    }
}
