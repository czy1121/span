package me.reezy.cosmo.span.style

import android.graphics.Paint
import android.text.TextPaint
import android.text.style.MetricAffectingSpan

class TextColorSpan(private val color: Int) : MetricAffectingSpan() {
    override fun updateMeasureState(textPaint: TextPaint) {
        if (textPaint.style != Paint.Style.STROKE) {
            textPaint.color = color
        }
    }

    override fun updateDrawState(textPaint: TextPaint) {
        if (textPaint.style != Paint.Style.STROKE) {
            textPaint.color = color
        }
    }
}