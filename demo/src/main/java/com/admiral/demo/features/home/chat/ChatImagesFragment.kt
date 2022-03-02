package com.admiral.demo.features.home.chat

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.core.view.forEach
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtChatImagesBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.uikit.components.chat.image.ChatImageView
import com.admiral.uikit.components.chat.image.ImageMessage
import com.admiral.uikit.view.checkable.CheckableGroup

class ChatImagesFragment : BaseFragment(R.layout.fmt_chat_images) {

    private val binding by viewBinding(FmtChatImagesBinding::bind)
    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        binding.tabsState.onCheckedChangeListener = object : CheckableGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(radioButton: View?, isChecked: Boolean, checkedId: Int) {
                binding.linearLayout.forEach {
                    if (it is ImageMessage) {
                        it.views.forEachIndexed { index, child ->
                            (child as? ChatImageView)?.isLoading = checkedId == binding.loadingTab.id
                        }
                    }
                }
            }
        }

        binding.linearLayout.forEach {
            if (it is ImageMessage) {
                it.views.forEachIndexed { index, child ->
                    (child as? ChatImageView)?.setLoaderClickListener {
                        it.isLoading = false
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        binding.toolbar.inflateMenu(R.menu.menu_appbar_info, menu, inflater)
    }
}