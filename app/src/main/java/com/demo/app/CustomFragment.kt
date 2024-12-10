package com.demo.app

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.TextView
import androidx.core.text.buildSpannedString
import androidx.core.text.inSpans
import androidx.fragment.app.Fragment
import me.reezy.cosmo.span.block
import me.reezy.cosmo.span.bold
import me.reezy.cosmo.span.br
import me.reezy.cosmo.span.color
import me.reezy.cosmo.span.lineHeight
import me.reezy.cosmo.span.scale
import me.reezy.cosmo.span.setLabelStyle
import me.reezy.cosmo.span.setNumberStyle
import me.reezy.cosmo.span.setTextStyle
import me.reezy.cosmo.span.size
import me.reezy.cosmo.span.style.Text3dSpan
import me.reezy.cosmo.span.style.TextStrokeSpan
import me.reezy.cosmo.span.text
import me.reezy.cosmo.span.typeface

class CustomFragment : Fragment(R.layout.fragment_text) {

    private val wiguru2 by lazy { resources.getFont(R.font.wiguru2) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tv = requireView() as TextView

        tv.setBackgroundColor(Color.BLACK)
//        tv.setTextColor(Color.GRAY)
        tv.movementMethod = LinkMovementMethod.getInstance()
        tv.text = buildSpannedString {
            inSpans(scale(2f), bold, Text3dSpan(Color.WHITE, (-2f).dp), color(Color.GRAY)) {
                append("Text3dSpan 12345678990")
            }
            br()
            inSpans(TextStrokeSpan(Color.RED, 1f.dp)) {
                append("TextStrokeSpan 1234\n5678990")
            }
            br()

            block(lineHeight(40f.dp)) {
                val labels = listOf("label", "span", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
                val colors = listOf(Color.RED)
                text(labels.setLabelStyle(colors, 20f.dp))
            }

            block(lineHeight(40f.dp)) {
                val labels = listOf("label", "span", "一二", "三四五", "六七八", "哈哈哈哈", "five", "six", "seven", "eight", "nine")
                val colors = listOf(Color.RED, Color.BLUE, Color.MAGENTA)
                text(labels.setLabelStyle(colors, 20f.dp, corner = 0, stroke = 1f.dp, spacing = 5f.dp))
            }

            br()

            append("一1二+22三3%四+4.4%五-5.5%".setNumberStyle(2f, color = Color.RED, style = Typeface.BOLD_ITALIC))

            br()
            br()

            inSpans(TextStrokeSpan((0xa0ffffff).toInt(), 1f.dp, 1f.dp), color(0x7000ff00), size(28f.dp), typeface(wiguru2)) {
                append("11223344556677889900")
            }

            block(lineHeight(40f.dp)) {
                append("一二三123%".setTextStyle(color = Color.RED, size = 12f.dp).setNumberStyle(typeface = wiguru2, size = 18f.dp, color = Color.CYAN))

            }
        }

    }

    private val Float.dp: Int get() = (resources.displayMetrics.density * this).toInt()
}