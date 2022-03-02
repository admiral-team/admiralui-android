package com.admiral.demo.features.home.tabs

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtTabsBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.demo.screen.IconTabsScreen
import com.admiral.demo.screen.InformerTabsScreen
import com.admiral.demo.screen.LogoTabsScreen
import com.admiral.demo.screen.OutlineTabsScreen
import com.admiral.demo.screen.StandardTabsScreen
import com.admiral.demo.screen.UnderlineTabsScreen

class TabsFragment : BaseFragment(R.layout.fmt_tabs) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val binding by viewBinding(FmtTabsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        binding.btnStandardTabs.setOnClickListener {
            navigationViewModel.open(StandardTabsScreen())
        }

        binding.btnLogoTabs.setOnClickListener {
            navigationViewModel.open(LogoTabsScreen())
        }

        binding.btnInformerTabs.setOnClickListener {
            navigationViewModel.open(InformerTabsScreen())
        }

        binding.btnOutlineTabs.setOnClickListener {
            navigationViewModel.open(OutlineTabsScreen())
        }

        binding.btnUnderlineTabs.setOnClickListener {
            navigationViewModel.open(UnderlineTabsScreen())
        }

        binding.btnIconTabs.setOnClickListener {
            navigationViewModel.open(IconTabsScreen())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        binding.toolbar.inflateMenu(R.menu.menu_appbar_info, menu, inflater)
    }
}
