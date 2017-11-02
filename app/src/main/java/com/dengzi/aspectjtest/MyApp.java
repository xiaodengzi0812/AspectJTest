package com.dengzi.aspectjtest;

import android.app.Application;

/**
 * @author Djk
 * @Title:
 * @Time: 2017/11/2.
 * @Version:1.0.0
 */

public class MyApp extends Application {

    private static MyApp mInstance;

    public static MyApp getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}
