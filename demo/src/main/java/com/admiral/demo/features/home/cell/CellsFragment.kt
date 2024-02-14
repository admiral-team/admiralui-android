package com.admiral.demo.features.home.cell

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtCellsBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.demo.screen.ActionBarScreen
import com.admiral.demo.screen.BaseCellsScreen

class CellsFragment : BaseFragment(
    layoutId = R.layout.fmt_cells,
    menuId = R.menu.menu_appbar_info
) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val binding by viewBinding(FmtCellsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        binding.btnBaseCells.setOnClickListener {
            navigationViewModel.open(BaseCellsScreen())
        }

        binding.btnActionBar.setOnClickListener {
            navigationViewModel.open(ActionBarScreen())
        }
    }
}
