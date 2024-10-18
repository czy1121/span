@file:Suppress("NOTHING_TO_INLINE")

package me.reezy.cosmo.span

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Layout
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.AlignmentSpan
import android.text.style.BackgroundColorSpan
import android.text.style.CharacterStyle
import android.text.style.LeadingMarginSpan
import android.text.style.LineHeightSpan
import android.text.style.ParagraphStyle
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import androidx.core.text.inSpans
import me.reezy.cosmo.span.compat.ImageCompatSpan
import me.reezy.cosmo.span.compat.LineHeightCompatSpan
import me.reezy.cosmo.span.style.LinkSpan
import me.reezy.cosmo.span.style.TextColorSpan


inline fun SpannableStringBuilder.br(): SpannableStringBuilder = append("\n")

fun SpannableStringBuilder.text(text: CharSequence, vararg spans: CharacterStyle): SpannableStringBuilder {
    val start = length
    append(text)
    for (span in spans) setSpan(span, start, length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    return this
}

fun SpannableStringBuilder.text(
    text: CharSequence,
    scale: Float? = null,
    color: Int? = null,
    size: Int? = null,
    typeface: Typeface? = null,
    style: Int? = null,
    bgColor: Int? = null,
): SpannableStringBuilder {
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
        spans.add(typeface(it))
    }

    return text(text, *spans.toTypedArray())
}


inline fun SpannableStringBuilder.image(context: Context, resourceId: Int, width: Int = 0, height: Int = width, align: Int = 2) =
    image(context.getDrawable(resourceId)!!, width, height, align)

fun SpannableStringBuilder.image(drawable: Drawable, width: Int = 0, height: Int = width, align: Int = 2): SpannableStringBuilder {
    if (width > 0 && height > 0) {
        drawable.setBounds(0, 0, width, height)
    } else if (width > 0) {
        drawable.setBounds(0, 0, width, (width.toFloat() * drawable.intrinsicHeight / drawable.intrinsicWidth).toInt())
    } else if (height > 0) {
        drawable.setBounds(0, 0, (height.toFloat() * drawable.intrinsicWidth / drawable.intrinsicHeight).toInt(), height)
    } else {
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    }
    return inSpans(ImageCompatSpan(drawable, align)) { append(" ") }
}

inline fun SpannableStringBuilder.clickable(text: String, crossinline action: () -> Unit) = text(text, clickable(action))

inline fun SpannableStringBuilder.link(text: String, href: String, underline: Boolean = false) = text(text, LinkSpan(href, underline))







fun SpannableStringBuilder.block(vararg spans: ParagraphStyle, builderAction: SpannableStringBuilder.() -> Unit): SpannableStringBuilder {
    if (isNotEmpty() && this[length - 1] != '\n') {
        append("\n");
    }
    val start = length
    builderAction()
    if (isNotEmpty() && this[length - 1] != '\n') {
        append("\n");
    }
    for (span in spans) setSpan(span, start, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    return this
}

inline fun SpannableStringBuilder.paragraph(
    align: Layout.Alignment? = null,
    lineHeight: Int? = null,
    intentFirst: Int = 0,
    intentRest: Int = 0,
    noinline builderAction: SpannableStringBuilder.() -> Unit,
): SpannableStringBuilder {
    val styles = mutableListOf<ParagraphStyle>()

    if (align != null) {
        styles.add(AlignmentSpan.Standard(align))
    }

    if (lineHeight != null) {
        val style = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            LineHeightSpan.Standard(lineHeight)
        } else {
            LineHeightCompatSpan(lineHeight)
        }
        styles.add(style)
    }

    if (intentFirst > 0 || intentRest > 0) {
        styles.add(LeadingMarginSpan.Standard(intentFirst, intentRest))
    }
    return block(*styles.toTypedArray(), builderAction = builderAction)
}


inline fun SpannableStringBuilder.quote(
    color: Int = Color.GRAY,
    noinline builderAction: SpannableStringBuilder.() -> Unit,
) = block(quote(color), builderAction = builderAction)

inline fun SpannableStringBuilder.bullet(
    color: Int = Color.BLACK,
    noinline builderAction: SpannableStringBuilder.() -> Unit,
) = block(bullet(color), builderAction = builderAction)