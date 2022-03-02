package com.admiral.demo.features.home.cell

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtCellsBaseVsLabelBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.demo.screen.CellsLabelLeadingScreen
import com.admiral.demo.screen.CellsLabelScreen
import com.admiral.demo.screen.CellsLabelTrailingScreen

class CellsLabelBaseFragment : BaseFragment(R.layout.fmt_cells_base_vs_label) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val binding by viewBinding(FmtCellsBaseVsLabelBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        binding.btnLabel.setOnClickListener {
            navigationViewModel.open(CellsLabelScreen())
        }

        binding.btnLeading.setOnClickListener {
            navigationViewModel.open(CellsLabelLeadingScreen())
        }

        binding.btnTrailing.setOnClickListener {
            navigationViewModel.open(CellsLabelTrailingScreen())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        binding.toolbar.inflateMenu(R.menu.menu_appbar_info, menu, inflater)
    }
}