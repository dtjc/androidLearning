package com.dt.learning.customerview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class XpermodeView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,defStyleAttr: Int = 0, defStyleRes: Int = 0)
    : View(context,attrs,defStyleAttr,defStyleRes){

    val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    var i = 0

    val enumList = PorterDuff.Mode::class.java.enumConstants
    val modes = enumList.map { PorterDuffXfermode(it) }


    init {
        mPaint.textSize = context.resources.displayMetrics.density * 18
        mPaint.style = Paint.Style.FILL_AND_STROKE

        setOnClickListener {
            i = ++i % 16
            invalidate()
        }
    }


    override fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas)
        val radius = width * 3f / 8
        val top = (height - width) shr 1

        val bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888)

        val myCanvas = Canvas(bitmap)

        mPaint.color = Color.GREEN
        myCanvas.drawCircle(left + radius,top + radius,radius,mPaint)

        mPaint.xfermode = modes[i]

        mPaint.color = Color.BLUE
        myCanvas.drawRect(left + width / 4f,top + width / 4f,left + width.toFloat(),top + width.toFloat(),mPaint)

        mPaint.xfermode = null
        mPaint.color = Color.BLACK
        myCanvas.drawText(enumList[i].name,0f,top / 2f,mPaint)
        canvas.drawBitmap(bitmap,0f,0f,mPaint)
    }
}