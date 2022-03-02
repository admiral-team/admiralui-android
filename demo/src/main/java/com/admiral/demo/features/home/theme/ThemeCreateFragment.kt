package com.admiral.demo.features.home.theme

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtThemeCreateBinding
import com.admiral.demo.features.home.theme.utils.ThemeStorageDAO
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.demo.screen.ThemeListScreen
import com.admiral.themes.Theme
import com.admiral.themes.copy
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ThemeCreateFragment : BaseFragment(R.layout.fmt_theme_create) {

    private val binding by viewBinding(FmtThemeCreateBinding::bind)
    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    override val isThemePickerVisible = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerToolbar(binding.toolbar, false, navigationViewModel::close)
        binding.button.isEnabled = false
        binding.button.setOnClickListener {
            navigationViewModel.open(ThemeListScreen(ThemeListMode.CHOOSE))
        }

        viewLifecycleOwner.lifecycleScope.launch {
            binding.textField.textFlow
                .debounce(DEBOUNCE_TIME)
                .distinctUntilChanged()
                .onEach {
                    binding.button.isEnabled = it?.isNotEmpty() ?: false
                }
                .launchIn(lifecycleScope)
        }

        setFragmentResultListener(ARG_CHOSEN_THEME) { key, bundle ->
            if (key != ARG_CHOSEN_THEME) return@setFragmentResultListener
            val theme = bundle.getParcelable<Theme>(ARG_CHOSEN_THEME) ?: return@setFragmentResultListener
            val name = binding.textField.inputText

            ThemeStorageDAO.saveTheme(theme.copy(name = name))
            navigationViewModel.close()
        }
    }

    companion object {
        const val DEBOUNCE_TIME = 300L
        const val ARG_CHOSEN_THEME = "ARG_CHOSEN_THEME"
    }
}