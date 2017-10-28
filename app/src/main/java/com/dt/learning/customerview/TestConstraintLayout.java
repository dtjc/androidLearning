package com.dt.learning.customerview;

import android.content.Context;
import android.graphics.Canvas;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by dtjc on 2017/8/10.
 */

public class TestConstraintLayout extends ConstraintLayout {
    public TestConstraintLayout(Context context) {
        super(context);
    }

    public TestConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        long start = System.nanoTime();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        long end = System.nanoTime();
        Log.e("time_onMeasure_CL",String.valueOf((end - start)/1.0/1000000));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        long start = System.nanoTime();
        super.onLayout(changed, left, top, right, bottom);
        long end = System.nanoTime();
        Log.e("time_onLayout_CL",String.valueOf((end - start)/1.0/1000000));
    }
}
