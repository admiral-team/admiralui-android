package com.admiral.demo.features.home.currency

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtCurrencyBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.demo.screen.CurrencyDefaultScreen
import com.admiral.demo.screen.CurrencyFlagsScreen
import com.admiral.demo.screen.CurrencyIconsFlagsScreen
import com.admiral.demo.screen.CurrencyIconsScreen

class CurrencyFragment : BaseFragment(
    layoutId = R.layout.fmt_currency,
    menuId = R.menu.menu_appbar_info
) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val binding by viewBinding(FmtCurrencyBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        binding.btnCurrencyDefault.setOnClickListener {
            navigationViewModel.open(CurrencyDefaultScreen())
        }

        binding.btnCurrencyIcons.setOnClickListener {
            navigationViewModel.open(CurrencyIconsScreen())
        }

        binding.btnCurrencyFlags.setOnClickListener {
            navigationViewModel.open(CurrencyFlagsScreen())
        }

        binding.btnCurrencyIconsFlags.setOnClickListener {
            navigationViewModel.open(CurrencyIconsFlagsScreen())
        }
    }
}