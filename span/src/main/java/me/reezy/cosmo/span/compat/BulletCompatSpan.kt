package me.reezy.cosmo.span.compat

import android.graphics.Canvas
import android.graphics.Paint
import android.os.Parcel
import android.text.Layout
import android.text.ParcelableSpan
import android.text.Spanned
import android.text.style.LeadingMarginSpan


class BulletCompatSpan : LeadingMarginSpan, ParcelableSpan {
    private val gap: Int

    private val radius: Int

    private val color: Int

    private val mWantColor: Boolean


    constructor(gap: Int, color: Int, radius: Int)  {
        this.gap = gap
        this.radius = radius
        this.color = color
        mWantColor = true
    }

    constructor(src: Parcel) {
        gap = src.readInt()
        mWantColor = src.readInt() != 0
        color = src.readInt()
        radius = src.readInt()
    }

    override fun getSpanTypeId(): Int {
        return 8
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(gap)
        dest.writeInt(if (mWantColor) 1 else 0)
        dest.writeInt(color)
        dest.writeInt(radius)
    }

    override fun getLeadingMargin(first: Boolean): Int {
        return 2 * radius + gap
    }

    override fun drawLeadingMargin(
        canvas: Canvas, paint: Paint, x: Int, dir: Int,
        top: Int, baseline: Int, bottom: Int,
        text: CharSequence, start: Int, end: Int,
        first: Boolean, layout: Layout?,
    ) {
//        var bottom = bottom
        if ((text as Spanned).getSpanStart(this) == start) {
            val style = paint.style
            var oldcolor = 0
            if (mWantColor) {
                oldcolor = paint.color
                paint.color = color
            }
            paint.style = Paint.Style.FILL
//            if (layout != null) {
//                // "bottom" position might include extra space as a result of line spacing
//                // configuration. Subtract extra space in order to show bullet in the vertical
//                // center of characters.
//                val line = layout.getLineForOffset(start)
//                bottom -= layout.getLineExtra(line)
//            }
            val yPosition = (top + bottom) / 2f
            val xPosition = (x + dir * radius).toFloat()
            canvas.drawCircle(xPosition, yPosition, radius.toFloat(), paint)
            if (mWantColor) {
                paint.color = oldcolor
            }
            paint.style = style
        }
    }
}