package com.whisperict.catchthelegend.services;

import android.app.Application;

public class MobileApplication extends Application {
    private static MobileApplication instance;

    public static MobileApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}