package com.dt.learning.customerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by dnnt on 17-11-30.
 */

public class TestView extends View {

    public static final String TAG = "TestView";

    public TestView(Context context) {
        super(context);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e(TAG,"onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.e(TAG,"onLayout");
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.e(TAG,"onDraw");
        super.onDraw(canvas);
    }

    @Override
    protected void onAttachedToWindow() {
        Log.e(TAG,"onAttachedToWindow");
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        Log.e(TAG,"onDetachedFromWindow");
        super.onDetachedFromWindow();
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        Log.e(TAG,"onVisibilityChanged");
        super.onVisibilityChanged(changedView, visibility);
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        Log.e(TAG,"onFocusChanged");
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
    }

    @Override
    protected void onFinishInflate() {
        Log.e(TAG,"onFinishInflate");
        super.onFinishInflate();
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        Log.e(TAG,"onWindowVisibilityChanged");
        super.onWindowVisibilityChanged(visibility);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        Log.e(TAG,"onWindowFocusChanged");
        super.onWindowFocusChanged(hasWindowFocus);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.e(TAG,"onSizeChanged");
        super.onSizeChanged(w, h, oldw, oldh);
    }
}
