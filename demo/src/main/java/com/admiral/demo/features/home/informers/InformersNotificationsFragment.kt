package com.admiral.demo.features.home.informers

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtInformersNotificationsBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.demo.screen.InformersScreen
import com.admiral.demo.screen.NotificationsScreen

class InformersNotificationsFragment : BaseFragment(
    layoutId = R.layout.fmt_informers_notifications,
    menuId = R.menu.menu_appbar_info
) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val binding by viewBinding(FmtInformersNotificationsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        binding.btnInformers.setOnClickListener {
            navigationViewModel.open(InformersScreen())
        }

        binding.btnNotifications.setOnClickListener {
            navigationViewModel.open(NotificationsScreen())
        }
    }
}
