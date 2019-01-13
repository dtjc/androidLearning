package com.dt.learning.customerview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.ScrollView
import com.dt.learning.R

class SurfaceViewDemo @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0, defStyleRef: Int = 0
) : SurfaceView(context, attrs, defStyleAttr, defStyleRef), SurfaceHolder.Callback2, Runnable {

    @Volatile
    private var mIsRunning = true


    private val mPaint = Paint()

    private val mPath = Path()

    private val mHolder: SurfaceHolder = holder

    init {
        mHolder.addCallback(this)
    }


    override fun surfaceRedrawNeeded(holder: SurfaceHolder?) {
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        mIsRunning = false
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        mPaint.color = Color.BLACK
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = context.resources.getDimension(R.dimen.sin_line_width)
        mPaint.flags = Paint.ANTI_ALIAS_FLAG
        isFocusable = true
        isFocusableInTouchMode = true
        keepScreenOn = true
        Thread(this).start()
    }

    override fun run() {
        var x = 0
        while (mIsRunning){
            val canvas = mHolder.lockCanvas()
            canvas?.let {
                canvas.drawColor(Color.WHITE)
                canvas.drawPath(mPath,mPaint)
                x += 1
                val y = (500 * Math.sin(2 * x * Math.PI / 180.0) + 800.0).toFloat()
                mPath.lineTo(x.toFloat(),y)
                mHolder.unlockCanvasAndPost(canvas)
            }
        }
    }
}