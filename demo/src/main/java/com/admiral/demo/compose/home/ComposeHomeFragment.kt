package com.admiral.demo.compose.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.themes.THEME_DARK
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.themes.compose.ThemeManagerCompose

class ComposeHomeFragment : BaseFragment(R.layout.fmt_home) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AdmiralTheme(
                    darkTheme = ThemeManagerCompose.theme.value.name == THEME_DARK
                ) {
                    HomeHost()
                }
            }
        }
    }
}