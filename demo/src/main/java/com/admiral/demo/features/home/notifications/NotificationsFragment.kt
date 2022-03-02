package com.admiral.demo.features.home.notifications

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtNotificationsBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.demo.screen.ActionScreen
import com.admiral.demo.screen.StaticScreen
import com.admiral.demo.screen.ToastScreen

class NotificationsFragment : BaseFragment(R.layout.fmt_notifications) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    private val binding by viewBinding(FmtNotificationsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        binding.btnToast.setOnClickListener {
            navigationViewModel.open(ToastScreen())
        }

        binding.btnStatic.setOnClickListener {
            navigationViewModel.open(StaticScreen())
        }

        binding.btnAction.setOnClickListener {
            navigationViewModel.open(ActionScreen())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        binding.toolbar.inflateMenu(R.menu.menu_appbar_info, menu, inflater)
    }
}