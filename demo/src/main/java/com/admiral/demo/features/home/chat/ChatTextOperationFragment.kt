package com.admiral.demo.features.home.chat

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtChatTextOperationBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.demo.screen.InfoScreen

class ChatTextOperationFragment : BaseFragment(
    layoutId = R.layout.fmt_chat_text_operation,
    menuId = R.menu.menu_appbar_info
) {

    private val binding by viewBinding(FmtChatTextOperationBinding::bind)
    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mainMenuHome -> {
                navigationViewModel.open(InfoScreen())
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}