package com.mjd.imitate_jd.app;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.gyf.barlibrary.ImmersionBar;

public class MyApp extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        Fresco.initialize(getApplicationContext());

    }

    public static Context getContext(){
        return context;
    }
}
