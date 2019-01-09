package com.example.tiamo.sumproject;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

public class MyApp extends Application {
    private static Context context;


    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        context = getApplicationContext();
    }

    public static Context getApplication() {
        return context;
    }
}
