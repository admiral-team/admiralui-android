package com.admiral.demo.features.home.cell

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtCellsLabelLeadingBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.uikit.view.checkable.CheckableGroup

class CellsLabelLeadingFragment : BaseFragment(R.layout.fmt_cells_label_leading) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val binding by viewBinding(FmtCellsLabelLeadingBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        binding.tabs.onCheckedChangeListener = object : CheckableGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(radioButton: View?, isChecked: Boolean, checkedId: Int) {
                when (checkedId) {
                    binding.defaultTab.id -> {
                        binding.subtitleTitleCell.isEnabled = true
                        binding.textMessageCell.isEnabled = true
                        binding.titleCell.isEnabled = true
                        binding.titleSubtitleCell.isEnabled = true
                        binding.titleSubtitleTextButtonCell.isEnabled = true
                        binding.textMessageCellUnit.isEnabled = true
                        binding.textMessageIconCellUnit.isEnabled = true
                    }

                    binding.disabledTab.id -> {
                        binding.subtitleTitleCell.isEnabled = false
                        binding.textMessageCell.isEnabled = false
                        binding.titleCell.isEnabled = false
                        binding.titleSubtitleCell.isEnabled = false
                        binding.titleSubtitleTextButtonCell.isEnabled = false
                        binding.textMessageCellUnit.isEnabled = false
                        binding.textMessageIconCellUnit.isEnabled = false
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        binding.toolbar.inflateMenu(R.menu.menu_appbar_info, menu, inflater)
    }
}