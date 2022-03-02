package com.admiral.demo.features.home.notifications

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtNotificationsToastBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.uikit.components.notifications.toast.ToastNotification

class ToastNotificationFragment : BaseFragment(R.layout.fmt_notifications_toast) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    private val binding by viewBinding(FmtNotificationsToastBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        binding.btnToast.setOnClickListener {
            val multipleToastNotification = ToastNotification.Builder(requireContext(), binding.coordinator)
                .setLinkText("Link text")
                .setLinkClickListener {
                    it.hide()
                }
                .setText("At breakpoint boundaries, mini units divide the screen into a fixed master grid.")
                .apply()

            multipleToastNotification.show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        binding.toolbar.inflateMenu(R.menu.menu_appbar_info, menu, inflater)
    }
}
