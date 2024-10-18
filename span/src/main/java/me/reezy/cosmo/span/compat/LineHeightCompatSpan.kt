package me.reezy.cosmo.span.compat

import android.graphics.Paint.FontMetricsInt
import android.os.Parcel
import android.text.ParcelableSpan
import android.text.style.LineHeightSpan
import kotlin.math.roundToInt

class LineHeightCompatSpan : LineHeightSpan, ParcelableSpan {
    private val height: Int

    constructor(height: Int) {
        this.height = height
    }

    constructor(src: Parcel) {
        height = src.readInt()
    }

    override fun getSpanTypeId(): Int {
        return 28
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(height)
    }

    override fun chooseHeight(
        text: CharSequence, start: Int, end: Int,
        spanstartv: Int, lineHeight: Int,
        fm: FontMetricsInt,
    ) {
        val originHeight = fm.descent - fm.ascent
        // If original height is not positive, do nothing.
        if (originHeight <= 0) {
            return
        }
        val ratio = height * 1.0f / originHeight
        fm.descent = (fm.descent * ratio).roundToInt()
        fm.ascent = fm.descent - height
    }
}