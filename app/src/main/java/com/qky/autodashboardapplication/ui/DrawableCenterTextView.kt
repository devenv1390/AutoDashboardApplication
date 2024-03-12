package com.qky.autodashboardapplication.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.TextView

/**
 * DrawableCenterTextView class
 *
 * @author kaiyuan.qin
 * @date 2024/3/12
 */
@SuppressLint("AppCompatCustomView")
class DrawableCenterTextView: TextView{
    constructor(
        context: Context?, attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs, defStyle){}

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?) : super(context) {}

    override fun onDraw(canvas: Canvas) {
        val drawables = compoundDrawables
        val drawableLeft = drawables[0]
        if (drawableLeft != null) {
            val textWidth = paint.measureText(text.toString())
            val drawablePadding = compoundDrawablePadding
            val drawableWidth =  drawableLeft.intrinsicWidth
            val bodyWidth = textWidth + drawableWidth + drawablePadding
            canvas.translate((width - bodyWidth) / 2, 0F)
        }
        super.onDraw(canvas)
    }
}