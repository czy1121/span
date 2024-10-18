package me.reezy.cosmo.span.compat

import android.graphics.Canvas
import android.graphics.Paint
import android.os.Parcel
import android.text.Layout
import android.text.ParcelableSpan
import android.text.style.LeadingMarginSpan


class QuoteCompatSpan : LeadingMarginSpan, ParcelableSpan {
    private val color: Int
    private val stripe: Int
    private val gap: Int

    constructor(color: Int, stripe: Int, gap: Int) {
        this.color = color
        this.stripe = stripe
        this.gap = gap
    }

    constructor(src: Parcel) {
        color = src.readInt()
        stripe = src.readInt()
        gap = src.readInt()
    }

    override fun getSpanTypeId(): Int {
        return 9
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(color)
        dest.writeInt(stripe)
        dest.writeInt(gap)
    }

    override fun getLeadingMargin(first: Boolean): Int {
        return stripe + gap
    }

    override fun drawLeadingMargin(
        c: Canvas, p: Paint, x: Int, dir: Int,
        top: Int, baseline: Int, bottom: Int,
        text: CharSequence, start: Int, end: Int,
        first: Boolean, layout: Layout,
    ) {
        val style = p.style
        val color = p.color
        p.style = Paint.Style.FILL
        p.color = this.color
        c.drawRect(x.toFloat(), top.toFloat(), (x + dir * stripe).toFloat(), bottom.toFloat(), p)
        p.style = style
        p.color = color
    }

}