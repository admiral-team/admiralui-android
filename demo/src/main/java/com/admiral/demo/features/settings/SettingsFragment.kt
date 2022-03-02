package com.admiral.demo.features.settings

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtSettingsBinding
import com.admiral.demo.features.home.home.ComposeSwitcher
import com.admiral.demo.features.home.home.ThemeSwitcher
import com.admiral.demo.features.home.theme.utils.ThemeStorageDAO

class SettingsFragment : BaseFragment(R.layout.fmt_settings) {

    private var composeSwitcher: ComposeSwitcher? = null
    private var themeSwitcher: ThemeSwitcher? = null
    private val binding by viewBinding(FmtSettingsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.switchTheme.isChecked = ThemeStorageDAO.isThemeAuto()

        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            themeSwitcher?.switchToSystemTheme(isChecked)
            ThemeStorageDAO.setIsThemeAuto(isChecked)
        }

        binding.switchCompose.setOnCheckedChangeListener { _, isChecked ->
            composeSwitcher?.switchToCompose(isChecked)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onAttachToParentFragment(requireParentFragment().requireParentFragment())
    }

    private fun onAttachToParentFragment(fragment: Fragment) {
        try {
            composeSwitcher = fragment as ComposeSwitcher
            themeSwitcher = fragment as ThemeSwitcher
        } catch (e: ClassCastException) {
            throw ClassCastException("$fragment must implement ComposeSwitcher and ThemeSwitcher")
        }
    }
}