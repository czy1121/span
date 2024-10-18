package me.reezy.cosmo.span.style

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.text.style.ReplacementSpan

class LabelSpan(
    private val color: Int,
    private val height: Int,
    private var corner: Int = -1,
    private val padding: Int = 0,
    private val spacing: Int = 0,
    private val stroke: Int = 0,
) : ReplacementSpan() {


    private var mWidth = 0

    override fun draw(canvas: Canvas, text: CharSequence, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
        val newPaint = Paint(paint)

        newPaint.color = color
        newPaint.strokeWidth = stroke.toFloat()
        newPaint.style = if (stroke > 0) Paint.Style.STROKE else Paint.Style.FILL_AND_STROKE

        val half = stroke / 2f

        val rect = RectF(x, top.toFloat(), x + mWidth + padding * 2, (top + height).toFloat())
        rect.inset(half, half)
        canvas.drawRoundRect(rect, corner.toFloat(), corner.toFloat(), newPaint)

        newPaint.color = if (stroke > 0) color else paint.color
        newPaint.style = paint.style
        newPaint.strokeWidth = paint.strokeWidth
        newPaint.textAlign = Paint.Align.CENTER
        canvas.drawText(text, start, end, rect.centerX(), (rect.centerY() - (newPaint.descent() + newPaint.ascent()) / 2), newPaint)
    }

    override fun getSize(paint: Paint, text: CharSequence, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
        val newPaint = Paint(paint)
        if (fm != null) {
            fm.top = -height
            fm.ascent = -height
            fm.descent = 0
            fm.bottom = 0
        }
        mWidth = (newPaint.measureText(text, start, end) + padding * 2).toInt()
        if (mWidth < height || end - start == 1) {
            mWidth = height
        }
        if (corner == -1) {
            corner = height / 2
        }
        return mWidth + padding * 2 + spacing
    }
}