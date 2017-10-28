package com.dt.learning.customerview;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

/**
 * Created by dtjc on 2017/8/10.
 */

public class TestFrameLayout extends FrameLayout {
    public TestFrameLayout(@NonNull Context context) {
        super(context);
    }

    public TestFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TestFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        long start = System.nanoTime();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        long end = System.nanoTime();
        Log.e("time_onMeasure_FL",String.valueOf((end - start)/1.0/1000000));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        long start = System.nanoTime();
        super.onLayout(changed, left, top, right, bottom);
        long end = System.nanoTime();
        Log.e("time_onLayout_FL",String.valueOf((end - start)/1.0/1000000));

    }
}
