package com.dt.learning.customerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.dt.learning.R;

/**
 * Created by dtjc on 2017/8/28.
 */

public class RingCircleView extends View {

    private Paint mPaint;
    private int inRadius;
    private int outRadius;

    public RingCircleView(Context context) {
        super(context);
    }

    public RingCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public RingCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs){
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RingCircleView);
        inRadius = (int)(array.getDimension(R.styleable.RingCircleView_in_radius,0)+0.5f);
        outRadius = (int)(array.getDimension(R.styleable.RingCircleView_out_radius,0)+0.5f);
        array.recycle();
        if (inRadius > outRadius){
            throw new IllegalArgumentException("outRadius must be greater than inRadius");
        }
        mPaint = new Paint();
        SweepGradient gradient = new SweepGradient(outRadius,outRadius, Color.RED,Color.BLUE);
//        LinearGradient gradient =
        mPaint.setShader(gradient);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(outRadius - inRadius);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(outRadius << 1,outRadius << 1);
        }else if (widthMode == MeasureSpec.AT_MOST){
            int height = MeasureSpec.getSize(heightMeasureSpec);
            setMeasuredDimension(outRadius << 1,height);
        }else if (heightMode == MeasureSpec.AT_MOST){
            int width = MeasureSpec.getSize(widthMeasureSpec);
            setMeasuredDimension(width,outRadius << 1);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        RadialGradient radialGradient = new RadialGradient(outRadius,outRadius,outRadius,new int[]{Color.RED,Color.BLUE},null, Shader.TileMode.CLAMP);
//        mPaint.setShader(radialGradient);
        int start = (outRadius - inRadius) >> 1;
        int end = ((inRadius + outRadius) >> 1) + start;
        canvas.drawArc(start,start,end,end,0,360,false,mPaint);
//        canvas.drawCircle(outRadius,outRadius,(inRadius + outRadius) >> 1,mPaint);
    }
}
