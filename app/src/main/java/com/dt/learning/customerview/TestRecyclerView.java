package com.dt.learning.customerview;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by dtjc on 2017/9/1.
 */

public class TestRecyclerView extends RecyclerView{
    public TestRecyclerView(Context context) {
        super(context);
    }

    public TestRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        Log.e("rcv","onMeasure");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.e("rcv","onLayout");
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);
        Log.e("rcv","onDraw");
    }
}
