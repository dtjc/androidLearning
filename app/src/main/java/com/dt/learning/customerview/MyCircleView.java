package com.dt.learning.customerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.dt.learning.R;

/**
 * Created by dnnt9 on 2017/2/27.
 */

public class MyCircleView extends View {

    private int mLeftColor = Color.GREEN;
    private int mRightColor = Color.BLUE;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public MyCircleView(Context context) {
        super(context);
    }

    public MyCircleView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyCircleView);
        mLeftColor = typedArray.getColor(R.styleable.MyCircleView_left_color,mLeftColor);
        mRightColor = typedArray.getColor(R.styleable.MyCircleView_right_color,mRightColor);
        typedArray.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //处理wrap_content
        int defaultSize = 50;
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(defaultSize,defaultSize);
        }else if (widthMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(defaultSize,height);
        }else if (heightMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(width,defaultSize);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom= getPaddingBottom();
        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;
        int dia = Math.max(width,height);
        mPaint.setStyle(Paint.Style.FILL);
        RectF rect = new RectF(paddingLeft,paddingTop,paddingLeft+dia,paddingTop+dia);
        mPaint.setColor(mLeftColor);
        canvas.drawArc(rect,90,180,true,mPaint);
        mPaint.setColor(mRightColor);
        canvas.drawArc(rect,270,180,true,mPaint);
    }
}
