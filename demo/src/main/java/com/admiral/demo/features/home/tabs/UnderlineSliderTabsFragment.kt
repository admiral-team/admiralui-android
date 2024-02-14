package com.admiral.demo.features.home.tabs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtTabsUnderlineSliderBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.uikit.view.checkable.CheckableGroup

class UnderlineSliderTabsFragment : BaseFragment(
    layoutId = R.layout.fmt_tabs_underline_slider,
    menuId = R.menu.menu_appbar_info
) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val binding by viewBinding(FmtTabsUnderlineSliderBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        binding.tabsState.onCheckedChangeListener = object : CheckableGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(radioButton: View?, isChecked: Boolean, checkedId: Int) {
                when (checkedId) {
                    R.id.defaultTab -> {
                        binding.tabsTwo.isEnabled = true
                        binding.tabsThree.isEnabled = true
                        binding.tabsNotification.isEnabled = true
                    }
                    R.id.disabledTab -> {
                        binding.tabsTwo.isEnabled = false
                        binding.tabsThree.isEnabled = false
                        binding.tabsNotification.isEnabled = false
                    }
                }
            }
        }
    }
}
