package com.dt.learning.customerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by dtjc on 2017/8/1.
 */

public class StrokeTextView extends androidx.appcompat.widget.AppCompatTextView {

    private int mStrokeColor = Color.BLACK;
    private float mStrokeWidth = 1.5f;
    private int mTextColor = Color.WHITE;

    public StrokeTextView(Context context) {
        super(context);
        init();
    }

    public StrokeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StrokeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setStrokeWidth(mStrokeWidth);
    }

    public int getStrokeColor() {
        return mStrokeColor;
    }

    public void setStrokeColor(int strokeColor) {
        mStrokeColor = strokeColor;
        invalidate();
    }

    public float getStrokeWidth() {
        return mStrokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        if (strokeWidth < 0) {
            strokeWidth = 0;
        }
        mStrokeWidth = strokeWidth;
        getPaint().setStrokeWidth(strokeWidth);
        invalidate();
    }

    @Override
    public void setTextColor(@ColorInt int color) {
        mTextColor = color;
        super.setTextColor(color);
    }

    public int getTextColor() {
        return mTextColor;
    }

    @Override
    public void append(CharSequence text, int start, int end) {
        //使用TextView的append()会导致描边无效。
        text = text.subSequence(start, end);
        text = getText().toString() + text;
        setText(text);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        TextPaint paint = getPaint();
        //不能调用setTextColor(),因为setTextColor()会调用invalidate(),导致递归
        setTextColorWithReflection(mTextColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setFakeBoldText(false);
        super.onDraw(canvas);
        setTextColorWithReflection(mStrokeColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setFakeBoldText(true);
        super.onDraw(canvas);
    }

    private void setTextColorWithReflection(int color) {
        Field fieldColor;
        try {
            fieldColor = TextView.class.getDeclaredField("mCurTextColor");
            fieldColor.setAccessible(true);
            fieldColor.set(this, color);
            fieldColor.setAccessible(false);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
