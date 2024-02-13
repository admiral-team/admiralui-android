package com.admiral.demo.features.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtLinksBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.themes.ColorPaletteEnum
import com.admiral.uikit.view.checkable.CheckableGroup

class LinksFragment : BaseFragment(
    layoutId = R.layout.fmt_links,
    menuId = R.menu.menu_appbar_info
) {

    private val binding by viewBinding(FmtLinksBinding::bind)
    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        with(binding) {
            tabsState.onCheckedChangeListener = object : CheckableGroup.OnCheckedChangeListener {
                override fun onCheckedChanged(radioButton: View?, isChecked: Boolean, checkedId: Int) {
                    when (checkedId) {
                        R.id.defaultTab -> {
                            admiralLink.isEnabled = true
                            admiralLink2.isEnabled = true
                            admiralLink3.isEnabled = true
                            admiralLink4.isEnabled = true
                            admiralLink5.isEnabled = true
                            admiralLink6.isEnabled = true
                        }
                        R.id.disabledTab -> {
                            admiralLink.isEnabled = false
                            admiralLink2.isEnabled = false
                            admiralLink3.isEnabled = false
                            admiralLink4.isEnabled = false
                            admiralLink5.isEnabled = false
                            admiralLink6.isEnabled = false
                        }
                    }
                }
            }

            admiralLink.apply {
                compoundDrawablesNormalEnabledPalette = ColorPaletteEnum.ELEMENT_ACCENT
                compoundDrawablesPressedPalette = ColorPaletteEnum.ELEMENT_ACCENT_PRESSED
            }
            admiralLink2.apply {
                compoundDrawablesNormalEnabledPalette = ColorPaletteEnum.ELEMENT_ACCENT
                compoundDrawablesPressedPalette = ColorPaletteEnum.ELEMENT_ACCENT_PRESSED
            }
            admiralLink3.apply {
                compoundDrawablesNormalEnabledPalette = ColorPaletteEnum.ELEMENT_ACCENT
                compoundDrawablesPressedPalette = ColorPaletteEnum.ELEMENT_ACCENT_PRESSED
            }
            admiralLink4.apply {
                compoundDrawablesNormalEnabledPalette = ColorPaletteEnum.ELEMENT_ACCENT
                compoundDrawablesPressedPalette = ColorPaletteEnum.ELEMENT_ACCENT_PRESSED
            }
        }

        binding.admiralLink.compoundDrawablesNormalEnabledPalette =
            ColorPaletteEnum.ELEMENT_ACCENT
        binding.admiralLink2.compoundDrawablesNormalEnabledPalette =
            ColorPaletteEnum.ELEMENT_ACCENT
        binding.admiralLink3.compoundDrawablesNormalEnabledPalette =
            ColorPaletteEnum.ELEMENT_ACCENT
        binding.admiralLink4.compoundDrawablesNormalEnabledPalette =
            ColorPaletteEnum.ELEMENT_ACCENT
    }
}