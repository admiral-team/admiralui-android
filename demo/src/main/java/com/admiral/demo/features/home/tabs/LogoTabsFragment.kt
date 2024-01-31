package com.admiral.demo.features.home.tabs

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtTabsLogoBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.core.ext.isColorDark
import com.admiral.uikit.view.checkable.CheckableGroup

class LogoTabsFragment : BaseFragment(R.layout.fmt_tabs_logo), ThemeObserver {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val binding by viewBinding(FmtTabsLogoBinding::bind)
    private var isNightModeDetect: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ThemeManager.subscribe(this)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        binding.tabsState.onCheckedChangeListener =
            object : CheckableGroup.OnCheckedChangeListener {
                override fun onCheckedChanged(
                    radioButton: View?,
                    isChecked: Boolean,
                    checkedId: Int
                ) {
                    when (checkedId) {
                        R.id.defaultTab -> {
                            with(binding) {
                                tabsTwo.isEnabled = true
                                tabsThree.isEnabled = true
                                tabsFour.isEnabled = true
                                tabsFive.isEnabled = true
                            }
                        }

                        R.id.disabledTab -> {
                            with(binding) {
                                tabsTwo.isEnabled = false
                                tabsThree.isEnabled = false
                                tabsFour.isEnabled = false
                                tabsFive.isEnabled = false
                            }
                        }
                    }
                }
            }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        binding.toolbar.inflateMenu(R.menu.menu_appbar_info, menu, inflater)
    }

    override fun onThemeChanged(theme: Theme) {
        isNightModeDetect = theme.palette.textPrimary.isColorDark(0.5f)
        invalidateIcons()
    }

    private fun invalidateIcons() {
        with(binding) {
            tabsTwoLogoTabOne.icon =
                getDrawable(
                    drawableLight = R.drawable.ic_visa_64x32_light,
                    drawableDark = R.drawable.ic_visa_64x32_dark,
                )
            tabsThreeLogoTabOne.icon =
                getDrawable(
                    drawableLight = R.drawable.ic_visa_64x32_light,
                    drawableDark = R.drawable.ic_visa_64x32_dark,
                )
            tabsFourLogoTabOne.icon =
                getDrawable(
                    drawableLight = R.drawable.ic_visa_64x32_light,
                    drawableDark = R.drawable.ic_visa_64x32_dark,
                )
            tabsFourLogoTabFour.icon =
                getDrawable(
                    drawableLight = R.drawable.ic_apple_pay_64x32_light,
                    drawableDark = R.drawable.ic_apple_pay_64x32_dark,
                )
            tabsFourLogoTabFour.icon =
                getDrawable(
                    drawableLight = R.drawable.ic_apple_pay_64x32_light,
                    drawableDark = R.drawable.ic_apple_pay_64x32_dark,
                )
            tabsFiveLogoTabOne.icon =
                getDrawable(
                    drawableLight = R.drawable.ic_visa_64x32_light,
                    drawableDark = R.drawable.ic_visa_64x32_dark,
                )
            tabsFiveLogoTabFour.icon =
                getDrawable(
                    drawableLight = R.drawable.ic_apple_pay_64x32_light,
                    drawableDark = R.drawable.ic_apple_pay_64x32_dark,
                )
            tabsFiveLogoTabFive.icon =
                getDrawable(
                    drawableLight = R.drawable.ic_google_pay_64x32_light,
                    drawableDark = R.drawable.ic_google_pay_64x32_dark,
                )
        }
    }

    private fun getDrawable(
        drawableLight: Int,
        drawableDark: Int,
    ): Drawable? {
        val drawable = if (isNightModeDetect) drawableDark else drawableLight
        return ContextCompat.getDrawable(requireContext(), drawable)
    }
}