package com.admiral.demo.features.home.buttons

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtButtonsRulesBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.uikit.view.checkable.CheckableGroup

class RulesButtonsFragment : BaseFragment(
    layoutId = R.layout.fmt_buttons_rules,
    menuId = R.menu.menu_appbar_info
) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    private val binding by viewBinding(FmtButtonsRulesBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        with(binding) {
            tabsState.onCheckedChangeListener = object : CheckableGroup.OnCheckedChangeListener {
                override fun onCheckedChanged(radioButton: View?, isChecked: Boolean, checkedId: Int) {
                    val isEnabled = checkedId == R.id.defaultTab
                    btnRule.isEnabled = isEnabled
                }
            }
        }
    }
}