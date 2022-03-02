package com.admiral.demo.features.home.cell

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtCellsBaseTrailingBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.uikit.view.checkable.CheckableGroup

class BaseCellsTrailingFragment : BaseFragment(R.layout.fmt_cells_base_trailing) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val binding by viewBinding(FmtCellsBaseTrailingBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        binding.tabs.onCheckedChangeListener = object : CheckableGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(radioButton: View?, isChecked: Boolean, checkedId: Int) {
                when (checkedId) {
                    binding.defaultTab.id -> {
                        binding.cardCell.isEnabled = true
                        binding.checkBoxCell.isEnabled = true
                        binding.iconCell.isEnabled = true
                        binding.doubleSubtitleCell.isEnabled = true
                        binding.labelCell.isEnabled = true
                        binding.switchCell.isEnabled = true
                        binding.radioButtonCell.isEnabled = true
                        binding.subtitlePaymentCell.isEnabled = true
                    }

                    binding.disabledTab.id -> {
                        binding.cardCell.isEnabled = false
                        binding.checkBoxCell.isEnabled = false
                        binding.iconCell.isEnabled = false
                        binding.doubleSubtitleCell.isEnabled = false
                        binding.labelCell.isEnabled = false
                        binding.switchCell.isEnabled = false
                        binding.radioButtonCell.isEnabled = false
                        binding.subtitlePaymentCell.isEnabled = false
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        binding.toolbar.inflateMenu(R.menu.menu_appbar_info, menu, inflater)
    }
}