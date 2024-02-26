package com.admiral.demo.features.home.notifications

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtNotificationsStaticBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.uikit.view.checkable.CheckableGroup

class StaticNotificationFragment : BaseFragment(
    layoutId = R.layout.fmt_notifications_static,
    menuId = R.menu.menu_appbar_info
) {

    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })
    private val binding by viewBinding(FmtNotificationsStaticBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        binding.tabsState.onCheckedChangeListener = object : CheckableGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(radioButton: View?, isChecked: Boolean, checkedId: Int) {
                when (checkedId) {
                    R.id.defaultTab -> {
                        binding.notificationAttention.isEnabled = true
                        binding.notificationAttentionColored.isEnabled = true
                        binding.notificationDefault.isEnabled = true
                        binding.notificationDefaultColored.isEnabled = true
                        binding.notificationError.isEnabled = true
                        binding.notificationErrorColored.isEnabled = true
                        binding.notificationSuccess.isEnabled = true
                        binding.notificationSuccessColored.isEnabled = true
                    }
                    R.id.disabledTab -> {
                        binding.notificationAttention.isEnabled = false
                        binding.notificationAttentionColored.isEnabled = false
                        binding.notificationDefault.isEnabled = false
                        binding.notificationDefaultColored.isEnabled = false
                        binding.notificationError.isEnabled = false
                        binding.notificationErrorColored.isEnabled = false
                        binding.notificationSuccess.isEnabled = false
                        binding.notificationSuccessColored.isEnabled = false
                    }
                }
            }
        }
    }
}
