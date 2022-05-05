package com.admiral.demo.features.home.tabs

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtTabsUnderlineCenterBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.uikit.components.badge.BadgeType
import com.admiral.uikit.view.checkable.CheckableGroup

class UnderlineCenterTabsFragment : BaseFragment(R.layout.fmt_tabs_underline_center) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val binding by viewBinding(FmtTabsUnderlineCenterBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        binding.tabNotificationAdditional.badge.badgeType = BadgeType.ADDITIONAL

        binding.tabsState.onCheckedChangeListener = object : CheckableGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(radioButton: View?, isChecked: Boolean, checkedId: Int) {
                when (checkedId) {
                    R.id.defaultTab -> {
                        binding.tabsTwo.isEnabled = true
                        binding.tabsThree.isEnabled = true
                        binding.tabsFour.isEnabled = true
                        binding.tabsFive.isEnabled = true
                        binding.tabsNotification.isEnabled = true
                    }
                    R.id.disabledTab -> {
                        binding.tabsTwo.isEnabled = false
                        binding.tabsThree.isEnabled = false
                        binding.tabsFour.isEnabled = false
                        binding.tabsFive.isEnabled = false
                        binding.tabsNotification.isEnabled = false
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        binding.toolbar.inflateMenu(R.menu.menu_appbar_info, menu, inflater)
    }
}