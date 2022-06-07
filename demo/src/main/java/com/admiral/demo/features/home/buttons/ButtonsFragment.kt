package com.admiral.demo.features.home.buttons

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtButtonsBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.demo.screen.GhostButtonsScreen
import com.admiral.demo.screen.OtherButtonsScreen
import com.admiral.demo.screen.PrimaryButtonsScreen
import com.admiral.demo.screen.RulesButtonsScreen
import com.admiral.demo.screen.SecondaryButtonsScreen

class ButtonsFragment : BaseFragment(R.layout.fmt_buttons) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val binding by viewBinding(FmtButtonsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        with(binding) {
            btnPrimaryButtons.setOnClickListener {
                navigationViewModel.open(PrimaryButtonsScreen())
            }
            btnSecondaryButtons.setOnClickListener {
                navigationViewModel.open(SecondaryButtonsScreen())
            }
            btnGhostButtons.setOnClickListener {
                navigationViewModel.open(GhostButtonsScreen())
            }
            btnRules.setOnClickListener {
                navigationViewModel.open(RulesButtonsScreen())
            }
            btnOtherButtons.setOnClickListener {
                navigationViewModel.open(OtherButtonsScreen())
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        binding.toolbar.inflateMenu(R.menu.menu_appbar_info, menu, inflater)
    }
}
