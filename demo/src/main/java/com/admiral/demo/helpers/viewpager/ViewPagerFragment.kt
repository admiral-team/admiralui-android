package com.admiral.demo.helpers.viewpager

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtViewPagerBinding

class ViewPagerFragment : BaseFragment(R.layout.fmt_view_pager) {

    private val binding by viewBinding(FmtViewPagerBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arguments = arguments
        if (arguments != null) {
            val text = arguments.getString("TEXT")
            binding.admiralTextViewViewPager.text = text
        }
    }
}