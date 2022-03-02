package com.admiral.demo.features.home.colors

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.clearFragmentResult
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtColorsBinding
import com.admiral.demo.features.home.colors.ColorPickerFragment.Companion.ARG_COLOR
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.demo.screen.ColorPickerScreen
import com.admiral.demo.screen.KEY_THEME
import com.admiral.themes.THEME_DARK
import com.admiral.themes.THEME_LIGHT
import com.admiral.themes.changeColor
import com.admiral.themes.copy

class ColorsFragment : BaseFragment(R.layout.fmt_colors) {

    private val binding by viewBinding(FmtColorsBinding::bind)
    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private lateinit var theme: com.admiral.themes.Theme
    private lateinit var adapter: ColorsListAdapter

    override val isThemePickerVisible = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        theme = requireArguments().getParcelable(KEY_THEME)!!

        val isEditable = theme.name != THEME_LIGHT && theme.name != THEME_DARK

        adapter = ColorsListAdapter(isEditable) {
            navigationViewModel.open(ColorPickerScreen(it))
        }

        registerToolbar(binding.toolbar, false, navigationViewModel::close)

        generateColors()

        binding.rvColors.setHasFixedSize(true)
        binding.rvColors.layoutManager = LinearLayoutManager(requireContext())
        binding.rvColors.adapter = adapter

        binding.button.setOnClickListener {
            setFragmentResult(KEY_THEME, Bundle().apply { putParcelable(KEY_THEME, theme) })
            navigationViewModel.close()
        }

        setFragmentResultListener(ARG_COLOR) { requestKey: String, bundle: Bundle ->
            val color: ColorModel = bundle.getParcelable(ARG_COLOR) ?: return@setFragmentResultListener
            val newTheme = theme.copy(palette = theme.palette.changeColor(color.name, color.color))

            if (newTheme != theme) {
                theme = newTheme
                binding.button.isVisible = true
                requireArguments().putParcelable(KEY_THEME, theme)
                generateColors()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        clearFragmentResult(ARG_COLOR)
    }

    private fun generateColors() {
        val list = mutableListOf<ColorListModel>()
        val colors = theme.palette.colors()

        list.add(TitleModel(title = "Background"))
        list.addAll(colors.filter { it.name.contains("background") })

        list.add(TitleModel(title = "Text"))
        list.addAll(colors.filter { it.name.contains("text") })

        list.add(TitleModel(title = "Element"))
        list.addAll(colors.filter { it.name.contains("element") })

        list.add(TitleModel(title = "Special"))
        list.addAll(colors.filter { it.name.contains("special") })

        adapter.submitList(list)
    }
}