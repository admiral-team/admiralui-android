package com.admiral.demo.features.home.cell

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtCellsBaseCenterBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.uikit.view.checkable.CheckableGroup

class BaseCellsCenterFragment : BaseFragment(
    layoutId = R.layout.fmt_cells_base_center,
    menuId = R.menu.menu_appbar_info
) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val binding by viewBinding(FmtCellsBaseCenterBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        binding.tabs.onCheckedChangeListener = object : CheckableGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(radioButton: View?, isChecked: Boolean, checkedId: Int) {
                when (checkedId) {
                    binding.defaultTab.id -> {
                        binding.titleCell.isEnabled = true
                        binding.subtitleTitleCell.isEnabled = true
                        binding.titleSubtitleCell.isEnabled = true
                        binding.textMessageCell.isEnabled = true
                        binding.titleSubtitleTextButtonCell.isEnabled = true
                    }

                    binding.disabledTab.id -> {
                        binding.titleCell.isEnabled = false
                        binding.subtitleTitleCell.isEnabled = false
                        binding.titleSubtitleCell.isEnabled = false
                        binding.textMessageCell.isEnabled = false
                        binding.titleSubtitleTextButtonCell.isEnabled = false
                    }
                }
            }
        }
    }
}