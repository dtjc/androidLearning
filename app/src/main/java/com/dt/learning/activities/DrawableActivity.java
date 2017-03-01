package com.dt.learning.activities;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dt.learning.R;

import java.util.HashMap;
import java.util.Map;

public class DrawableActivity extends AppCompatActivity {

    private ImageView ivTransition;
    private ImageView ivClip;
    private ImageView ivScale;
    private int mCount = 1;
    private boolean mClipActive = false;
    private boolean mScaleActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
    }

    public void initView(){
        ivTransition=(ImageView)findViewById(R.id.content_drawable_iv_transition);
        ivClip = (ImageView)findViewById(R.id.content_drawable_iv_clip);
        ivClip.setImageLevel(2500);
        ivScale=(ImageView)findViewById(R.id.content_drawable_iv_scale);
        ivScale.setImageLevel(2500);
    }

    public void transitionClick(View view){
        TransitionDrawable transitionDrawable = (TransitionDrawable) ivTransition.getDrawable();
        transitionDrawable.reverseTransition(1000);
    }

    public void levelListClick(View view){
        if (mCount > 8) mCount-=9;
        ((ImageView)view).setImageLevel(mCount);
        mCount++;
    }

    public void clipClick(View view){
        if (mClipActive)    return;
        mClipActive = true;
        new Thread(() -> {
            for (int level = 2500;level <= 10000 ; level+=50){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int temLevel = level;
                new Handler(getMainLooper()).post(() -> ivClip.setImageLevel(temLevel));
            }
            mClipActive = false;
        }).start();
    }

    public void rippleClick(View view){
        if(mScaleActive)    return;
        mScaleActive = true;
        new Thread(()->{
            for (int level = 2500;level <= 10000 ; level+=50){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int temLevel = level;
                new Handler(getMainLooper()).post(()->ivScale.setImageLevel(temLevel));
            }
            mScaleActive = false;
        }).start();
    }

}
