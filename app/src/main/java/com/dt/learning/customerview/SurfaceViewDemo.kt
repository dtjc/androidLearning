package com.dt.learning.customerview

import android.content.Context
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
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

    init {
        holder.addCallback(this)
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
        mIsRunning = true
        drawWhileRunning()
    }

    private fun drawWhileRunning() {
        GlobalScope.launch(Dispatchers.Default) {
            val width = context.resources.displayMetrics.widthPixels
            val a = width * 0.4
            val b = context.resources.displayMetrics.heightPixels * 0.4
            val c = Math.PI / 180
            while (mIsRunning && isActive){
                x++
                val drawX = if(x > width){
                    val matrix = Matrix()
                    matrix.setTranslate(-1f,0f)
                    mPath.transform(matrix)
                    width
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
                    x = width + (360 - width%360) + Int.MAX_VALUE%360
                }
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mPath.close()
    }
}