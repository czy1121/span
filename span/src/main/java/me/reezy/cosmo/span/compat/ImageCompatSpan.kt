package me.reezy.cosmo.span.compat

import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.style.ImageSpan

class ImageCompatSpan(drawable: Drawable, align: Int) : ImageSpan(drawable, align) {

    override fun getSize(paint: Paint, text: CharSequence, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
        if (verticalAlignment == ALIGN_CENTER && fm != null) {
            val drawable = drawable
            val rect = drawable.bounds

            val pfm = paint.fontMetricsInt
            val fontHeight = pfm.descent - pfm.ascent
            val imageHeight = rect.bottom - rect.top

            if (imageHeight <= fontHeight) {
                // 图片高度 <= 文本高度，直接使用 ImageSpan.ALIGN_CENTER 来实现垂直居中
                fm.ascent = -rect.bottom
                fm.descent = 0

                fm.top = fm.ascent
                fm.bottom = 0
            } else {
                // 图片高度 > 文本高度，将当前图片所在行的文字相对于图片的高度居中
                val offset = (imageHeight - fontHeight) / 2
                fm.top = pfm.ascent - offset
                fm.bottom = pfm.descent + offset

                fm.ascent = pfm.ascent - offset
                fm.descent = pfm.descent + offset
            }
            return rect.right
        } else {
            return super.getSize(paint, text, start, end, fm)
        }
    }
}