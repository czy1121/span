package com.demo.app

import android.graphics.Typeface
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.demo.app.databinding.ActivityMainBinding
import me.reezy.cosmo.tabs.TabItem
import me.reezy.cosmo.tabs.setup

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.bind(findViewById<ViewGroup>(android.R.id.content).getChildAt(0)) }

    private val items = arrayOf("text", "image", "block", "custom")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding.pager.offscreenPageLimit = 30
        binding.pager.adapter = object : FragmentStateAdapter(supportFragmentManager, lifecycle) {
            override fun createFragment(position: Int): Fragment {
                return when(position) {
                    0 -> TextFragment()
                    1 -> ImageFragment()
                    2 -> BlockFragment()
                    else -> CustomFragment()
                }
            }

            override fun getItemCount(): Int = items.size
        }

        binding.tabs.setup(items.map { TabItem(it, it) }, binding.pager) {
            textView?.textSize = 18f
            textView?.setTypeface(Typeface.DEFAULT, Typeface.BOLD)
        }


    }
}