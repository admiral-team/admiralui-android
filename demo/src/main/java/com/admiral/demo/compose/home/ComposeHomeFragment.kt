package com.admiral.demo.compose.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment

class ComposeHomeFragment : BaseFragment(R.layout.fmt_home) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                HomeHost()
            }
        }
    }
}