package me.reezy.cosmo.span.style

import android.content.Context
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View


class LinkSpan(private val href: String, private val underline: Boolean = false) : ClickableSpan() {

    fun interface LinkHandler {
        fun handle(context: Context, href: String)
    }

    companion object {
        var handler: LinkHandler? = null
    }
    override fun updateDrawState(ds: TextPaint) {
        ds.color = ds.linkColor
        ds.isUnderlineText = underline
    }

    override fun onClick(widget: View) {
        handler?.handle(widget.context, href)
    }
}