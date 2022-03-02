package com.admiral.demo.features.home.theme

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.clearFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtThemeBinding
import com.admiral.demo.features.home.theme.utils.ThemeStorageDAO
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.demo.screen.ColorsScreen
import com.admiral.demo.screen.KEY_THEME
import com.admiral.demo.screen.TypographyScreen
import com.admiral.themes.THEME_DARK
import com.admiral.themes.THEME_LIGHT
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.copy
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ThemeFragment : BaseFragment(R.layout.fmt_theme) {

    private val binding by viewBinding(FmtThemeBinding::bind)
    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private lateinit var theme: Theme
    private var themeChanged = false
    private var newThemeName: String? = null

    override val isThemePickerVisible = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        theme = requireArguments().getParcelable(KEY_THEME)!!
        val customTheme = theme.name != THEME_DARK && theme.name != THEME_LIGHT

        registerToolbar(binding.toolbar, customTheme, navigationViewModel::close)
        binding.toolbar.title = theme.name

        binding.btnColors.setOnClickListener {
            navigationViewModel.open(ColorsScreen(theme))
        }

        binding.btnTypography.setOnClickListener {
            navigationViewModel.open(TypographyScreen(theme))
        }

        binding.button.setOnClickListener {
            if (themeChanged) {
                if (newThemeName != theme.name) {
                    ThemeStorageDAO.removeTheme(theme)
                }

                theme = theme.copy(name = newThemeName ?: theme.name)
                ThemeStorageDAO.saveTheme(theme)
                themeChanged = false
                binding.button.text = getString(R.string.themes_creates_apply)
                binding.toolbar.title = theme.name
            } else {
                ThemeManager.theme = theme
            }
        }

        binding.textField.inputText = theme.name
        binding.textField.isVisible = customTheme

        viewLifecycleOwner.lifecycleScope.launch {
            binding.textField.textFlow.collect {
                if (it != null && theme.name != it.toString()) {
                    newThemeName = it.toString()
                    themeChanged = true
                    binding.button.text = getString(R.string.themes_creates_save)
                }
            }
        }

        setFragmentResultListener(KEY_THEME) { key, bundle ->
            if (key != KEY_THEME) return@setFragmentResultListener
            val newTheme: Theme = bundle.getParcelable(KEY_THEME) ?: return@setFragmentResultListener

            if (newTheme != theme) {
                theme = newTheme
                themeChanged = true
                binding.button.text = getString(R.string.themes_creates_save)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        clearFragmentResult(KEY_THEME)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.themeDelete) {
            MaterialAlertDialogBuilder(requireContext())
                .setMessage(R.string.themes_creates_delete_question)
                .setPositiveButton(R.string.yes) { dialog, which ->
                    ThemeStorageDAO.removeTheme(theme)
                    navigationViewModel.close()
                }
                .setNegativeButton(R.string.no, null)
                .create()
                .show()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_appbar_theme, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}