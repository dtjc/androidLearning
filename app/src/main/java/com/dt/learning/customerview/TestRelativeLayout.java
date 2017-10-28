package com.dt.learning.customerview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

/**
 * Created by dtjc on 2017/8/10.
 */

public class TestRelativeLayout extends RelativeLayout {
    public TestRelativeLayout(Context context) {
        super(context);
    }

    public TestRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TestRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        long start = System.nanoTime();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        long end = System.nanoTime();
        Log.e("time_onMeasure_RL",String.valueOf((end - start)/1.0/1000000));
    }
}
