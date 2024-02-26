package com.admiral.demo.features.home.chat

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtChatInputBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.uikit.view.checkable.CheckableGroup

class ChatInputFragment : BaseFragment(
    layoutId = R.layout.fmt_chat_input,
    menuId = R.menu.menu_appbar_info
) {

    private val binding by viewBinding(FmtChatInputBinding::bind)
    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        with(binding) {
            tabsState.onCheckedChangeListener = object : CheckableGroup.OnCheckedChangeListener {
                override fun onCheckedChanged(radioButton: View?, isChecked: Boolean, checkedId: Int) {
                    input.isEnabled = checkedId == R.id.defaultTab
                }
            }
        }
    }
}