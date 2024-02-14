package com.admiral.demo.features.home.cell

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtCellsBaseBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.demo.screen.BaseCellsCenterScreen
import com.admiral.demo.screen.BaseCellsLeadingScreen
import com.admiral.demo.screen.BaseCellsTrailingScreen

class BaseCellsFragment : BaseFragment(
    layoutId = R.layout.fmt_cells_base,
    menuId = R.menu.menu_appbar_info
) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val binding by viewBinding(FmtCellsBaseBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        binding.btnLeading.setOnClickListener {
            navigationViewModel.open(BaseCellsLeadingScreen())
        }

        binding.btnCenter.setOnClickListener {
            navigationViewModel.open(BaseCellsCenterScreen())
        }

        binding.btnTrailing.setOnClickListener {
            navigationViewModel.open(BaseCellsTrailingScreen())
        }
    }
}