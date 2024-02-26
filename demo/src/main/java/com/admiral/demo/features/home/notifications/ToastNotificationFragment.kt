package com.admiral.demo.features.home.notifications

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtNotificationsToastBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.uikit.components.notifications.toast.ToastNotification

class ToastNotificationFragment : BaseFragment(
    layoutId = R.layout.fmt_notifications_toast,
    menuId = R.menu.menu_appbar_info
) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    private val binding by viewBinding(FmtNotificationsToastBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        binding.btnToast.setOnClickListener {
            val multipleToastNotification =
                ToastNotification.Builder(requireContext(), binding.coordinator)
                    .setLinkText("Link text")
                    .setLinkClickListener {
                        it.hide()
                    }
                    .setIsWidthMatchParent(true)
                    .setMargins(top = 20)
                    .setText("At breakpoint boundaries, mini units divide the screen into a fixed master grid.")
                    .apply()

            multipleToastNotification.show()
        }
    }
}
