package com.lanou3g.zxw.thewayoftravel.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by dllo on 16/5/13.
 */
public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = this;
    }

    public static Context getContext() {
        return context;
    }
}
