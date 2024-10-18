package me.reezy.cosmo.span.style

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.text.style.ReplacementSpan


class DotSpan(private val color: Int, private val size: Int) : ReplacementSpan() {

    override fun draw(canvas: Canvas, text: CharSequence, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
        val newPaint = Paint(paint) // make a copy for not editing the referenced paint

        newPaint.color = color
        val rect = RectF(x, top.toFloat(), x + size, (top + size).toFloat())
        canvas.drawCircle(rect.centerX(), rect.centerY(), (size / 2).toFloat(), newPaint)


        newPaint.color = Color.WHITE
        newPaint.textAlign = Paint.Align.CENTER
        canvas.drawText(text, start, end, rect.centerX(), rect.centerY() - (newPaint.descent() + newPaint.ascent()) / 2, newPaint)
    }

    override fun getSize(paint: Paint, text: CharSequence, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
        if (fm != null) {
            fm.top = -size
            fm.ascent = -size
            fm.descent = 0
            fm.bottom = 0
        }
        return size
    }
}