package com.admiral.demo.features.home.badges

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtBadgesBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.demo.screen.BadgesNormalScreen
import com.admiral.demo.screen.BadgesSmallScreen

class BadgesFragment : BaseFragment(R.layout.fmt_badges) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val binding by viewBinding(FmtBadgesBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        binding.btnNormalBadges.setOnClickListener {
            navigationViewModel.open(BadgesNormalScreen())
        }

        binding.btnSmallBadges.setOnClickListener {
            navigationViewModel.open(BadgesSmallScreen())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        binding.toolbar.inflateMenu(R.menu.menu_appbar_info, menu, inflater)
    }
}
