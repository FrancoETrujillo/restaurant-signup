package com.mvatech.ftrujillo.prestoloyalty.core;

import android.app.Application;

import com.mvatech.ftrujillo.prestoloyalty.BuildConfig;

import timber.log.Timber;

public class PrestoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
