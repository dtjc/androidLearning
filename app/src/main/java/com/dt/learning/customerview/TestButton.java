package com.dt.learning.customerview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;

/**
 * Created by dtjc on 2017/8/14.
 */

public class TestButton extends Button {
    public TestButton(Context context) {
        super(context);
    }

    public TestButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TestButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        long start = System.nanoTime();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        long end = System.nanoTime();
        Log.e("time_onMeasure_BTN",String.valueOf((end - start)/1.0/1000000));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        long start = System.nanoTime();
        super.onLayout(changed, left, top, right, bottom);
        long end = System.nanoTime();
        Log.e("time_onLayout_BTN",String.valueOf((end - start)/1.0/1000000));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        long start = System.nanoTime();
        super.onDraw(canvas);
        long end = System.nanoTime();
        Log.e("time_onDraw_BTN",String.valueOf((end - start)/1.0/1000000));
    }
}
