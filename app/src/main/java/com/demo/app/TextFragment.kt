package com.demo.app

import android.content.res.Resources
import android.graphics.BlurMaskFilter
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.style.MaskFilterSpan
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.buildSpannedString
import androidx.fragment.app.Fragment
import me.reezy.cosmo.span.bold
import me.reezy.cosmo.span.br
import me.reezy.cosmo.span.clickable
import me.reezy.cosmo.span.color
import me.reezy.cosmo.span.paragraph
import me.reezy.cosmo.span.scale
import me.reezy.cosmo.span.strike
import me.reezy.cosmo.span.text
import me.reezy.cosmo.span.underline

class TextFragment : Fragment(R.layout.fragment_text) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tv = requireView() as TextView

        tv.movementMethod = LinkMovementMethod.getInstance()
        tv.text = buildSpannedString {
            paragraph(lineHeight = 30f.dp) {
                text("text no style")
                br()
                text("text Typeface.BOLD", style = Typeface.BOLD)
                br()
                text("text Typeface.ITALIC", style = Typeface.ITALIC)
                br()
                text("text relative size x 2", scale = 2f)
                br()
                text("text foreground color", color = Color.RED)
                br()
                text("text background color", bgColor = Color.RED)
                br()
                text("text underline", underline)
                br()
                text("text strike", strike)
                br()
                clickable("text clickable") {
                    Toast.makeText(requireContext(), "text clickable clicked", Toast.LENGTH_SHORT).show()
                }
                br()
                text("text multiple style", color(Color.RED), bold, underline, strike, scale(1.5f), clickable {
                    Toast.makeText(requireContext(), "text multiple style clicked", Toast.LENGTH_SHORT).show()
                })
            }
            br()

            paragraph {
                text("Blur.NORMAL", color(Color.RED), MaskFilterSpan(BlurMaskFilter(1f.dp.toFloat(), BlurMaskFilter.Blur.NORMAL)))
                br()
                text("Blur.SOLID", color(Color.RED), MaskFilterSpan(BlurMaskFilter(1f.dp.toFloat(), BlurMaskFilter.Blur.SOLID)))
                br()
                text("Blur.OUTER", color(Color.RED), MaskFilterSpan(BlurMaskFilter(1f.dp.toFloat(), BlurMaskFilter.Blur.OUTER)))
                br()
                text("Blur.INNER", color(Color.RED), MaskFilterSpan(BlurMaskFilter(1f.dp.toFloat(), BlurMaskFilter.Blur.INNER)))
            }
        }

    }

    private val Float.dp: Int get() = (Resources.getSystem().displayMetrics.density * this).toInt()
}