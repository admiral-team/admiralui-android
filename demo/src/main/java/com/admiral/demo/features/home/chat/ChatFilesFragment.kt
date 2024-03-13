package com.admiral.demo.features.home.chat

import android.os.Bundle
import android.view.View
import androidx.core.view.forEach
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtChatFilesBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.uikit.components.chat.file.ChatFileView
import com.admiral.uikit.components.chat.file.FileMessage
import com.admiral.uikit.view.checkable.CheckableGroup

class ChatFilesFragment : BaseFragment(
    layoutId = R.layout.fmt_chat_files,
    menuId = R.menu.menu_appbar_info
) {

    private val binding by viewBinding(FmtChatFilesBinding::bind)
    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)

        binding.fileMessage.addChatFileView(
            ChatFileView(requireContext()).apply {
                titleText = requireContext().getString(R.string.chat_file_first)
                subtitleText = requireContext().getString(R.string.chat_file_size)
            }
        )

        binding.fileMessageOutgoing.addChatFileView(
            ChatFileView(requireContext()).apply {
                titleText = requireContext().getString(R.string.chat_file_second)
                subtitleText = requireContext().getString(R.string.chat_file_size)
            }
        )

        binding.fileMessageIncomeSecond.addChatFileView(
            ChatFileView(requireContext()).apply {
                titleText = requireContext().getString(R.string.chat_file_fird)
                subtitleText = requireContext().getString(R.string.chat_file_size)
            }
        )

        binding.fileMessageOutgoingSecond.apply {
            addChatFileView(
                ChatFileView(requireContext()).apply {
                    titleText = requireContext().getString(R.string.chat_file_fourth)
                    subtitleText = requireContext().getString(R.string.chat_file_size)
                }
            )
            addChatFileView(
                ChatFileView(requireContext()).apply {
                    titleText = requireContext().getString(R.string.chat_file_fith)
                    subtitleText = requireContext().getString(R.string.chat_file_size)
                }
            )
            addChatFileView(
                ChatFileView(requireContext()).apply {
                    titleText = requireContext().getString(R.string.chat_file_sixth)
                    subtitleText = requireContext().getString(R.string.chat_file_size)
                }
            )
        }

        binding.tabsState.onCheckedChangeListener =
            object : CheckableGroup.OnCheckedChangeListener {
                override fun onCheckedChanged(
                    radioButton: View?,
                    isChecked: Boolean,
                    checkedId: Int
                ) {
                    binding.linearLayout.forEach {
                        if (it is FileMessage) {
                            it.views.forEachIndexed { index, child ->
                                (child as? ChatFileView)?.isLoading =
                                    checkedId == binding.loadingTab.id
                            }
                        }
                    }
                }
            }

        binding.linearLayout.forEach {
            if (it is FileMessage) {
                it.views.forEachIndexed { index, child ->
                    (child as? ChatFileView)?.setLoaderClickListener {
                        it.isLoading = false
                    }
                }
            }
        }
    }
}