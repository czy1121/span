package me.reezy.cosmo.span.style

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.os.Build
import android.text.style.ReplacementSpan
import androidx.annotation.ColorInt
import androidx.annotation.Dimension


class TextStrokeSpan(
    @ColorInt private val strokeColor: Int,
    @Dimension private val strokeWidth: Int,
    @Dimension private val letterSpacing: Int = 0,
) : ReplacementSpan() {

    private val path = Path()

    override fun getSize(paint: Paint, text: CharSequence, start: Int, end: Int, fontMetrics: Paint.FontMetricsInt?): Int {
        if (fontMetrics != null && paint.fontMetricsInt != null) {
            fontMetrics.top = paint.fontMetricsInt.top
            fontMetrics.ascent = paint.fontMetricsInt.ascent
            fontMetrics.descent = paint.fontMetricsInt.descent
            fontMetrics.bottom = paint.fontMetricsInt.bottom
            fontMetrics.leading = paint.fontMetricsInt.leading
        }
        return paint.measureText(text.toString().substring(start until end)).toInt() + strokeWidth * 2
    }

    override fun draw(canvas: Canvas, text: CharSequence, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {

        canvas.drawText(text, start, end, x + strokeWidth, y.toFloat(), paint)

        paint.getTextPath(text.toString(), start, end, x + strokeWidth, y.toFloat(), path)

        canvas.drawStroke(strokeWidth.toFloat(), strokeColor, letterSpacing.toFloat(), path, paint)
    }

    private fun Canvas.drawStroke(strokeWidth: Float, strokeColor: Int, letterSpacing: Float, path: Path, paint: Paint) {
        val color = paint.color
        val width = paint.strokeWidth
        val shader = paint.shader
        val spacing = paint.letterSpacing

        paint.color = strokeColor
        paint.strokeWidth = strokeWidth * 2
        paint.style = Paint.Style.STROKE
        paint.shader = null
        paint.letterSpacing = letterSpacing / paint.textSize

        val saveCount = save()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            clipPath(path, android.graphics.Region.Op.DIFFERENCE)
        } else {
            clipOutPath(path)
        }
        drawPath(path, paint)
        restoreToCount(saveCount)

        paint.color = color
        paint.strokeWidth = width
        paint.style = Paint.Style.FILL
        paint.shader = shader
        paint.letterSpacing = spacing
    }
}