package com.dt.learning;

import android.app.Application;
import android.content.Context;

/**
 * Created by dnnt9 on 2017/2/13.
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
