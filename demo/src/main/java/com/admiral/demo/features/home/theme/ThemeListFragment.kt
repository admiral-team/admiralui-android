package com.admiral.demo.features.home.theme

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtThemeListBinding
import com.admiral.demo.ext.getEnumExtra
import com.admiral.demo.features.home.theme.utils.ThemeStorageDAO
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.demo.screen.ThemeCreateScreen
import com.admiral.demo.screen.ThemeScreen
import com.admiral.themes.THEME_LIGHT

class ThemeListFragment : BaseFragment(R.layout.fmt_theme_list) {

    private val binding by viewBinding(FmtThemeListBinding::bind)
    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val mode: ThemeListMode by lazy { requireArguments().getEnumExtra(ARG_MODE, ThemeListMode.SHOW) }

    override val isThemePickerVisible = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        val isChooseMode = mode == ThemeListMode.CHOOSE
        val adapter = ThemeListAdapter(isChooseMode) {
            navigationViewModel.open(ThemeScreen(it))
        }

        if (isChooseMode) {
            val list = ThemeStorageDAO.getThemes()
                .filter { it.name == THEME_LIGHT || it.name == com.admiral.themes.THEME_DARK }
            adapter.submitList(list)
        } else {
            adapter.submitList(ThemeStorageDAO.getThemes())
        }

        binding.rv.setHasFixedSize(true)
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        binding.rv.adapter = adapter

        binding.button.text = getString(
            if (mode == ThemeListMode.CHOOSE) R.string.themes_creates_done else R.string.themes_creates
        )

        binding.button.setOnClickListener {
            if (mode == ThemeListMode.SHOW) {
                navigationViewModel.open(ThemeCreateScreen())
            } else {
                val theme =
                    ThemeStorageDAO.getThemes().find { it.name == adapter.checkedTheme }
                        ?: return@setOnClickListener
                val bundle = Bundle().apply { putParcelable(ThemeCreateFragment.ARG_CHOSEN_THEME, theme) }
                setFragmentResult(ThemeCreateFragment.ARG_CHOSEN_THEME, bundle)
                navigationViewModel.close()
            }
        }
    }

    companion object {
        const val ARG_MODE = "ARG_MODE"
    }
}

enum class ThemeListMode {
    SHOW,
    CHOOSE
}