package com.admiral.demo.features.home.cell

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtCellsBaseLeadingBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.uikit.view.checkable.CheckableGroup

class BaseCellsLeadingFragment : BaseFragment(
    layoutId = R.layout.fmt_cells_base_leading,
    menuId = R.menu.menu_appbar_info
) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val binding by viewBinding(FmtCellsBaseLeadingBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        binding.tabs.onCheckedChangeListener = object : CheckableGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(radioButton: View?, isChecked: Boolean, checkedId: Int) {
                when (checkedId) {
                    binding.defaultTab.id -> {
                        binding.cellCard.isEnabled = true
                        binding.cellLabel.isEnabled = true
                        binding.cellIcon.isEnabled = true
                        binding.cellIconBackground.isEnabled = true
                        binding.cellIconPlace.isEnabled = true
                    }

                    binding.disabledTab.id -> {
                        binding.cellCard.isEnabled = false
                        binding.cellLabel.isEnabled = false
                        binding.cellIcon.isEnabled = false
                        binding.cellIconBackground.isEnabled = false
                        binding.cellIconPlace.isEnabled = false
                    }
                }
            }
        }
    }
}