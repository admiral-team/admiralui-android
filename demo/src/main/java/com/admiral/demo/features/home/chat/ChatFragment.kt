package com.admiral.demo.features.home.chat

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtChatBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.demo.screen.ChatFilesScreen
import com.admiral.demo.screen.ChatImagesScreen
import com.admiral.demo.screen.ChatInputScreen
import com.admiral.demo.screen.ChatTextMessageScreen
import com.admiral.demo.screen.ChatTextOperationScreen

class ChatFragment : BaseFragment(
    layoutId = R.layout.fmt_chat,
    menuId = R.menu.menu_appbar_info
) {

    private val binding by viewBinding(FmtChatBinding::bind)
    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        with(binding) {
            bcInput.setOnClickListener {
                navigationViewModel.open(ChatInputScreen())
            }
            uploadingPhoto.setOnClickListener {
                navigationViewModel.open(ChatImagesScreen())
            }
            uploadingFiles.setOnClickListener {
                navigationViewModel.open(ChatFilesScreen())
            }
            textOperation.setOnClickListener {
                navigationViewModel.open(ChatTextOperationScreen())
            }
            bcTextMessage.setOnClickListener {
                navigationViewModel.open(ChatTextMessageScreen())
            }
        }
    }
}