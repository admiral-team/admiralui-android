package com.admiral.demo.features.home.cell

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtCellsBaseTrailingBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.uikit.view.checkable.CheckableGroup

class BaseCellsTrailingFragment : BaseFragment(
    layoutId = R.layout.fmt_cells_base_trailing,
    menuId = R.menu.menu_appbar_info
) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val binding by viewBinding(FmtCellsBaseTrailingBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        with(binding) {
            tabs.onCheckedChangeListener = object : CheckableGroup.OnCheckedChangeListener {
                override fun onCheckedChanged(radioButton: View?, isChecked: Boolean, checkedId: Int) {
                    when (checkedId) {
                        defaultTab.id -> {
                            cardCell.isEnabled = true
                            checkBoxCell.isEnabled = true
                            iconCell.isEnabled = true
                            doubleSubtitleCell.isEnabled = true
                            labelCell.isEnabled = true
                            switchCell.isEnabled = true
                            radioButtonCell.isEnabled = true
                            subtitlePaymentCell.isEnabled = true
                        }

                        disabledTab.id -> {
                            cardCell.isEnabled = false
                            checkBoxCell.isEnabled = false
                            iconCell.isEnabled = false
                            doubleSubtitleCell.isEnabled = false
                            labelCell.isEnabled = false
                            switchCell.isEnabled = false
                            radioButtonCell.isEnabled = false
                            subtitlePaymentCell.isEnabled = false
                        }
                    }
                }
            }
        }
    }
}