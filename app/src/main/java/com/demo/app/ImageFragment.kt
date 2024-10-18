package com.demo.app

import android.content.res.Resources
import android.graphics.BlurMaskFilter
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.style.ImageSpan
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
import me.reezy.cosmo.span.image
import me.reezy.cosmo.span.paragraph
import me.reezy.cosmo.span.scale
import me.reezy.cosmo.span.strike
import me.reezy.cosmo.span.text
import me.reezy.cosmo.span.underline

class ImageFragment : Fragment(R.layout.fragment_text) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tv = requireView() as TextView

        tv.movementMethod = LinkMovementMethod.getInstance()
        tv.text = buildSpannedString {
            paragraph {
                text("IMAGE", size = 20f.dp, bgColor = Color.RED)
                image(requireContext(), R.mipmap.ic_launcher, 10f.dp, align = ImageSpan.ALIGN_BOTTOM)
                text("ALIGN", bgColor = Color.RED)
                image(requireContext(), R.mipmap.ic_launcher, 30f.dp, align = ImageSpan.ALIGN_BOTTOM)
                text("BOTTOM", bgColor = Color.RED)
            }
            br()
            paragraph {
                text("IMAGE", size = 20f.dp, bgColor = Color.RED)
                image(requireContext(), R.mipmap.ic_launcher, 10f.dp, align = ImageSpan.ALIGN_BASELINE)
                text("ALIGN", bgColor = Color.RED)
                image(requireContext(), R.mipmap.ic_launcher, 30f.dp, align = ImageSpan.ALIGN_BASELINE)
                text("BASELINE", bgColor = Color.RED)
            }
            br()
            paragraph {
                text("IMAGE", size = 20f.dp, bgColor = Color.RED)
                image(requireContext(), R.mipmap.ic_launcher, 10f.dp)
                text("ALIGN", bgColor = Color.RED)
                image(requireContext(), R.mipmap.ic_launcher, 30f.dp)
                text("CENTER", bgColor = Color.RED)
                text(" one two three four five six seven eight nine ten", bgColor = Color.RED)
                image(requireContext(), R.mipmap.ic_launcher, 10f.dp)
            }
            br()
            paragraph {
                text("IMAGE", size = 20f.dp, bgColor = Color.RED)
                image(requireContext(), R.mipmap.ic_launcher, 10f.dp, align = ImageSpan.ALIGN_CENTER)
                text("ALIGN", bgColor = Color.RED)
                image(requireContext(), R.mipmap.ic_launcher, 15f.dp, align = ImageSpan.ALIGN_CENTER)
                text("CENTER", bgColor = Color.RED)
            }
        }

    }

    private val Float.dp: Int get() = (Resources.getSystem().displayMetrics.density * this).toInt()
}