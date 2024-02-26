package com.admiral.demo.features.home.chat

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
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

class ChatImagesFragment : BaseFragment(
    layoutId = R.layout.fmt_chat_images,
    menuId = R.menu.menu_appbar_info
) {

    private val binding by viewBinding(FmtChatImagesBinding::bind)
    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        var isError = false

        binding.apply {
            imageFirst.setOnClickListener {
                isError = !isError
                imageFirst.isError = isError
                imageSecond.isError = isError
            }
        }

        binding.apply {
            tabsState.onCheckedChangeListener =
                object : CheckableGroup.OnCheckedChangeListener {
                    override fun onCheckedChanged(
                        radioButton: View?,
                        isChecked: Boolean,
                        checkedId: Int
                    ) {
                        binding.linearLayout.forEach {
                            if (it is ImageMessage) {
                                it.views.forEachIndexed { index, child ->
                                    (child as? ChatImageView)?.isLoading =
                                        checkedId == binding.loadingTab.id
                                }
                            }
                        }
                    }
                }

            linearLayout.forEach {
                if (it is ImageMessage) {
                    it.views.forEachIndexed { index, child ->
                        (child as? ChatImageView)?.setLoaderClickListener {
                            it.isLoading = false
                        }
                    }
                }
            }
        }
    }

    private fun ImageMessage.addImage() {
        addImageView(ChatImageView(requireContext()).apply {
            drawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_chat_image)
        })
    }
}