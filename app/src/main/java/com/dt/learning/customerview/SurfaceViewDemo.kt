package com.dt.learning.customerview

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.Surface
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.dt.learning.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class SurfaceViewDemo @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0, defStyleRef: Int = 0
) : SurfaceView(context, attrs, defStyleAttr, defStyleRef), SurfaceHolder.Callback2 {

    @Volatile
    private var mIsRunning = true


    private val mPaint = Paint()

    private val mPath = Path()

    private var x = -1
    private var screenWidth = 0
    private var a: Double = 0.0
    private var b: Double = 0.0
    private var c: Double = 0.0
    val pathMatrix = Matrix()

    init {
        holder.addCallback(this)
        screenWidth = context.resources.displayMetrics.widthPixels
        a = context.resources.displayMetrics.heightPixels * 0.2
        b = context.resources.displayMetrics.heightPixels * 0.4
        c = Math.PI / 180
        mPaint.color = Color.BLACK
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = context.resources.getDimension(R.dimen.sin_line_width)
        mPaint.flags = Paint.ANTI_ALIAS_FLAG
        isFocusable = true
        isFocusableInTouchMode = true
        keepScreenOn = true
        pathMatrix.setTranslate(-1f,0f)

    }


    override fun surfaceRedrawNeeded(holder: SurfaceHolder?) {
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        mIsRunning = false
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        mIsRunning = true
        drawWhileRunning()
    }

    private fun drawWhileRunning() {
        GlobalScope.launch(Dispatchers.Default) {
            while (mIsRunning){
                x++
                val drawX = if(x > screenWidth){
                    mPath.transform(pathMatrix)
                    screenWidth
                }else{
                    x
                }
                val y = (a * Math.sin(x * c) + b).toFloat()
                mPath.lineTo(drawX.toFloat(),y)
                val canvas = holder.lockCanvas()
                canvas?.let {
                    canvas.drawColor(Color.WHITE)
                    canvas.drawPath(mPath,mPaint)
                    holder.unlockCanvasAndPost(canvas)
                }
                if (x == Int.MAX_VALUE){
                    x = screenWidth + (360 - screenWidth%360) + Int.MAX_VALUE%360
                }
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mPath.close()
    }

}