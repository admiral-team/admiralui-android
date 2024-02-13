package com.admiral.demo.features.home.tabs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtTabsUnderlineBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.demo.screen.UnderlineCenterTabsScreen
import com.admiral.demo.screen.UnderlineSliderTabsScreen

class UnderlineTabsFragment : BaseFragment(
    layoutId = R.layout.fmt_tabs_underline,
    menuId = R.menu.menu_appbar_info
) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val binding by viewBinding(FmtTabsUnderlineBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        binding.sliderTabs.setOnClickListener {
            navigationViewModel.open(UnderlineSliderTabsScreen())
        }

        binding.centerTabs.setOnClickListener {
            navigationViewModel.open(UnderlineCenterTabsScreen())
        }
    }
}