package com.dt.learning;

import android.app.Application;
import android.content.Context;

import com.dt.learning.customerview.StrokeTextView;

/**
 * Created by dnnt9 on 2017/2/13.
 */

public class MyApplication extends Application {
    private static Context context;
    private StrokeTextView windowStv;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }

    public void setWindowStv(StrokeTextView windowStv) {
        this.windowStv = windowStv;
    }

    public StrokeTextView getWindowStv() {
        return windowStv;
    }
}
