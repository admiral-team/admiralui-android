package com.admiral.demo.features.home.cell

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtCellsBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.demo.screen.ActionBar2Screen
import com.admiral.demo.screen.ActionBarScreen
import com.admiral.demo.screen.BaseCellsScreen
import com.admiral.demo.screen.BaseLabelCellsScreen
import com.admiral.demo.screen.CellsTextScreen
import com.admiral.demo.screen.CellsTextTitleScreen

class CellsFragment : BaseFragment(R.layout.fmt_cells) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val binding by viewBinding(FmtCellsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        binding.btnBaseCells.setOnClickListener {
            navigationViewModel.open(BaseCellsScreen())
        }

        binding.btnBaseCellsLable.setOnClickListener {
            navigationViewModel.open(BaseLabelCellsScreen())
        }

        binding.btnTextCells.setOnClickListener {
            navigationViewModel.open(CellsTextScreen())
        }

        binding.btnTitleCells.setOnClickListener {
            navigationViewModel.open(CellsTextTitleScreen())
        }

        binding.btnActionBar.setOnClickListener {
            navigationViewModel.open(ActionBarScreen())
        }

        binding.btnActionBar2.setOnClickListener {
            navigationViewModel.open(ActionBar2Screen())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        binding.toolbar.inflateMenu(R.menu.menu_appbar_info, menu, inflater)
    }
}
