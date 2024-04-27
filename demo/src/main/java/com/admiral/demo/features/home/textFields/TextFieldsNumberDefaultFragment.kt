package com.admiral.demo.features.home.textFields

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtTextFieldsNumberDefaultBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.themes.ThemeManager
import com.admiral.uikit.core.foundation.ColorState
import com.admiral.uikit.view.checkable.CheckableGroup

class TextFieldsNumberDefaultFragment : BaseFragment(
    layoutId = R.layout.fmt_text_fields_number_default,
    menuId = R.menu.menu_appbar_info
) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val binding by viewBinding(FmtTextFieldsNumberDefaultBinding::bind)

    @Suppress("MagicNumber")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        with(binding) {

            inputNumberFiveSymbols.value = 1
            inputNumberSixSymbols.value = 100_000
            inputNumberEightSymbols.value = 10_000_000
            inputNumberTenSymbols.value = 100_000_000
            inputNumberUnlimited.value = 1

            tvFiveSymbols.textColor = ColorState(ThemeManager.theme.palette.textSecondary)
            tvSixSymbols.textColor = ColorState(ThemeManager.theme.palette.textSecondary)
            tvEightSymbols.textColor = ColorState(ThemeManager.theme.palette.textSecondary)
            tvTenSymbols.textColor = ColorState(ThemeManager.theme.palette.textSecondary)
            tvUnlimitedSymbols.textColor = ColorState(ThemeManager.theme.palette.textSecondary)

            tabs.onCheckedChangeListener = object : CheckableGroup.OnCheckedChangeListener {
                override fun onCheckedChanged(
                    radioButton: View?,
                    isChecked: Boolean,
                    checkedId: Int
                ) {
                    when (checkedId) {
                        defaultTab.id -> {
                            inputNumberFiveSymbols.isEnabled = true
                            inputNumberSixSymbols.isEnabled = true
                            inputNumberEightSymbols.isEnabled = true
                            inputNumberTenSymbols.isEnabled = true
                            inputNumberUnlimited.isEnabled = true
                        }

                        disabledTab.id -> {
                            inputNumberFiveSymbols.isEnabled = false
                            inputNumberSixSymbols.isEnabled = false
                            inputNumberEightSymbols.isEnabled = false
                            inputNumberTenSymbols.isEnabled = false
                            inputNumberUnlimited.isEnabled = false
                        }
                    }
                }
            }
        }
    }
}
