package com.admiral.demo.features.home.cell

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtCellsActionbarBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.themes.ColorPaletteEnum
import com.admiral.uikit.components.actionbar.ActionBarOnSwipeTouchListener
import com.admiral.uikit.view.checkable.CheckableGroup

class ActionBarFragment : BaseFragment(R.layout.fmt_cells_actionbar) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val binding by viewBinding(FmtCellsActionbarBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        initTabs()
        initActionBarDefault()
        initActionBarSecondary()
        initTitleCell()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        binding.toolbar.inflateMenu(R.menu.menu_appbar_info, menu, inflater)
    }

    private fun initTabs() {
        binding.tabs.onCheckedChangeListener = object : CheckableGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(radioButton: View?, isChecked: Boolean, checkedId: Int) {
                initTitleCell()
                when (checkedId) {
                    binding.defaultTab.id -> {
                        binding.actionBar.isEnabled = true
                        binding.actionBar2.isEnabled = true
                    }
                    binding.disabledTab.id -> {
                        binding.actionBar.isEnabled = false
                        binding.actionBar2.isEnabled = false
                    }
                }
            }
        }
    }

    private fun initActionBarDefault() {
        with(binding.actionBar) {
            initDefaultViews()
            dots.setOnClickListener {
                showToast(getString(R.string.actionbar_more_clicked))
            }
        }
    }

    private fun initActionBarSecondary() {
        binding.actionBar2.apply {
            addAction(
                icon = R.drawable.admiral_ic_email_outline,
                description = getString(R.string.actionbar_email),
                backgroundColorNormalEnabledPalette = ColorPaletteEnum.ELEMENT_ACCENT,
                backgroundColorPressedPalette = ColorPaletteEnum.ELEMENT_ACCENT_PRESSED,
                descriptionColorNormalEnabledPalette = ColorPaletteEnum.TEXT_STATIC_WHITE,
                descriptionColorPressedPalette = ColorPaletteEnum.TEXT_STATIC_WHITE
            ) {
                showToast(getString(R.string.actionbar_email_clicked))
            }
            addAction(
                icon = R.drawable.admiral_ic_star_outline,
                description = getString(R.string.actionbar_star),
                backgroundColorNormalEnabledPalette = ColorPaletteEnum.ELEMENT_SUCCESS,
                backgroundColorPressedPalette = ColorPaletteEnum.ELEMENT_SUCCESS_PRESSED,
                descriptionColorNormalEnabledPalette = ColorPaletteEnum.TEXT_STATIC_WHITE,
                descriptionColorPressedPalette = ColorPaletteEnum.TEXT_STATIC_WHITE
            ) {
                showToast(getString(R.string.actionbar_star_clicked))
            }
            addAction(
                icon = R.drawable.admiral_ic_edit_outline,
                description = getString(R.string.actionbar_edit),
                backgroundColorNormalEnabledPalette = ColorPaletteEnum.ELEMENT_ATTENTION,
                backgroundColorPressedPalette = ColorPaletteEnum.ELEMENT_ATTENTION_PRESSED,
                descriptionColorNormalEnabledPalette = ColorPaletteEnum.TEXT_STATIC_WHITE,
                descriptionColorPressedPalette = ColorPaletteEnum.TEXT_STATIC_WHITE
            ) {
                showToast(getString(R.string.actionbar_edit_clicked))
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initTitleCell() {
        if (binding.tabs.getCheckedId() == binding.defaultTab.id) {
            binding.titleCell.setOnTouchListener(
                object : ActionBarOnSwipeTouchListener(binding.titleCell, binding.actionBar) {}
            )
            binding.titleCell2.setOnTouchListener(
                object : ActionBarOnSwipeTouchListener(binding.titleCell2, binding.actionBar2) {}
            )
        } else {
            binding.titleCell.setOnTouchListener(
                null
            )
            binding.titleCell2.setOnTouchListener(
                null
            )
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
