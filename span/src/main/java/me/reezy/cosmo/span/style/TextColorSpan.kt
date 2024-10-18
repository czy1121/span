package me.reezy.cosmo.span.style

import android.text.TextPaint
import android.text.style.MetricAffectingSpan

class TextColorSpan(private val color: Int) : MetricAffectingSpan() {
    override fun updateMeasureState(textPaint: TextPaint) {
        textPaint.color = color
    }

    override fun updateDrawState(textPaint: TextPaint) {
        textPaint.color = color
    }
}