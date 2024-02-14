package com.admiral.demo.features.home

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtToolbarBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.uikit.components.toolbar.ToolbarItem
import com.admiral.uikit.view.checkable.CheckableGroup

class ToolbarFragment : BaseFragment(
    layoutId = R.layout.fmt_toolbar,
    menuId = R.menu.menu_appbar_info
) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val binding by viewBinding(FmtToolbarBinding::bind)

    private val menuTitles =
        listOf("Настройки", "Подробнее", "Пополнить", "Оплатить").reversed()

    private val iconIds =
        listOf(
            R.drawable.admiral_ic_settings_solid,
            R.drawable.admiral_ic_info_solid,
            R.drawable.admiral_ic_get_cash_solid,
            R.drawable.admiral_ic_card_solid
        ).reversed()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        binding.inputNumber.onValueChange = { old, new ->
            if (new < old) {
                binding.admiralToolbar.removeItem(old - 1)
            } else {
                val index = new - 1
                binding.admiralToolbar.addItem(
                    ToolbarItem(
                        text = menuTitles[index],
                        icon = ContextCompat.getDrawable(requireContext(), iconIds[index])
                    )
                )
            }
        }

        binding.tabsState.onCheckedChangeListener = object : CheckableGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(radioButton: View?, isChecked: Boolean, checkedId: Int) {
                when (checkedId) {
                    R.id.defaultTab -> {
                        binding.admiralToolbar.isEnabled = true
                        binding.inputNumber.isEnabled = true
                    }
                    R.id.disabledTab -> {
                        binding.admiralToolbar.isEnabled = false
                        binding.inputNumber.isEnabled = false
                    }
                }
            }
        }
    }
}