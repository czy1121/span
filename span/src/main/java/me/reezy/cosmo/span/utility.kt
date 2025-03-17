@file:Suppress("NOTHING_TO_INLINE")

package me.reezy.cosmo.span

import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.BackgroundColorSpan
import android.text.style.CharacterStyle
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import androidx.core.text.buildSpannedString
import androidx.core.text.inSpans
import me.reezy.cosmo.span.compat.TypefaceCompatSpan
import me.reezy.cosmo.span.style.LabelSpan
import me.reezy.cosmo.span.style.TextColorSpan
import java.util.regex.Pattern



fun CharSequence.setTextStyle(regex: String? = null, spansBuilder: SpannableStringBuilder.() -> Array<CharacterStyle>): Spanned {
    val s = SpannableStringBuilder(this)
    if (regex != null) {
        val m = Pattern.compile(regex).matcher(this)

        while (m.find()) {
            val start = m.start()
            val end = m.end()

            for (span in s.spansBuilder()) {
                s.setSpan(span, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
            }
        }
    } else {
        for (span in s.spansBuilder()) {
            s.setSpan(span, 0, length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        }
    }
    return s
}

fun CharSequence.setTextStyle(vararg spans: CharacterStyle): Spanned {
    val s = SpannableStringBuilder(this)
    for (span in spans) {
        s.setSpan(span, 0, length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    }
    return s
}

fun CharSequence.setTextStyle(regex: String? = null, scale: Float? = null, color: Int? = null, size: Int? = null, typeface: Typeface? = null, style: Int? = null, bgColor: Int? = null) = setTextStyle(regex) {
    val spans = mutableListOf<CharacterStyle>()
    color?.let {
        spans.add(TextColorSpan(it))
    }

    scale?.let {
        spans.add(RelativeSizeSpan(it))
    }

    size?.let {
        spans.add(AbsoluteSizeSpan(it))
    }

    style?.let {
        spans.add(StyleSpan(it))
    }

    bgColor?.let {
        spans.add(BackgroundColorSpan(it))
    }

    typeface?.let {
        val span = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            TypefaceSpan(typeface)
        } else {
            TypefaceCompatSpan(typeface)
        }
        spans.add(span)
    }
    spans.toTypedArray()
}

inline fun CharSequence.setNumberStyle(scale: Float? = null, color: Int? = null, size: Int? = null, typeface: Typeface? = null, style: Int? = null, bgColor: Int? = null) =
    setTextStyle("[+-]?[0-9]+(\\.[0-9]+)*[%]?", scale, color, size, typeface, style, bgColor)

fun CharSequence.setStrokeStyle(color: Int = Color.BLACK, width: Int = 1f.dp, letterSpacing: Int = 0) = buildSpannedString {
    inSpans(stroke(color, width, letterSpacing)) {
        append(this@setStrokeStyle)
    }
}

inline fun List<String>.setLabelStyle(colors: List<Int>, height: Int, corner: Int = -1, padding: Int = 3f.dp, spacing: Int = 3f.dp, stroke: Int = 0) = buildSpannedString {
    for ((index, text) in this@setLabelStyle.withIndex()) {
        text(text, LabelSpan(colors[index % colors.size], height, corner, padding, spacing, stroke))
    }
}

inline fun List<String>.setLabelStyle(color: Int, height: Int, corner: Int = -1, padding: Int = 3f.dp, spacing: Int = 3f.dp, stroke: Int = 0) =
    setLabelStyle(listOf(color), height, corner, padding, spacing, stroke)



@PublishedApi
internal val Float.dp: Int get() = (Resources.getSystem().displayMetrics.density * this).toInt()



