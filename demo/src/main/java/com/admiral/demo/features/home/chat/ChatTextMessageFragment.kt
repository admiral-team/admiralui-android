package com.admiral.demo.features.home.chat

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtChatTextMessageBinding
import com.admiral.demo.features.main.NavigationViewModel

class ChatTextMessageFragment : BaseFragment(R.layout.fmt_chat_text_message) {

    private val binding by viewBinding(FmtChatTextMessageBinding::bind)
    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        var isError = false

        binding.apply {
            textMessageFirst.setOnClickListener {
                isError = !isError
                textMessageFirst.isError = isError
                chat.isError = isError
            }
        }
    }
}