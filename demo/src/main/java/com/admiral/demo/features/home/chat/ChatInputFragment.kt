package com.admiral.demo.features.home.chat

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.children
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.admiral.demo.R
import com.admiral.demo.common.BaseFragment
import com.admiral.demo.databinding.FmtChatInputBinding
import com.admiral.demo.features.main.NavigationViewModel
import com.admiral.uikit.components.chat.MessageStatus
import com.admiral.uikit.components.chat.TextMessage
import com.admiral.uikit.core.ext.pixels
import com.admiral.uikit.view.checkable.CheckableGroup
import java.util.Calendar

class ChatInputFragment : BaseFragment(
    layoutId = R.layout.fmt_chat_input,
    menuId = R.menu.menu_appbar_info
) {

    private val binding by viewBinding(FmtChatInputBinding::bind)
    private val navigationViewModel: NavigationViewModel by viewModels({ requireParentFragment() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(binding.toolbar, true, navigationViewModel::close)
        val calendar = Calendar.getInstance()

        with(binding) {
            tabsState.onCheckedChangeListener = object : CheckableGroup.OnCheckedChangeListener {
                override fun onCheckedChanged(
                    radioButton: View?,
                    isChecked: Boolean,
                    checkedId: Int
                ) {
                    input.isEnabled = checkedId == R.id.defaultTab
                }
            }

            input.onButtonClickListener = View.OnClickListener {
                if (input.text.isEmpty() && input.text.isBlank()) return@OnClickListener
                val hour = calendar.get(Calendar.HOUR_OF_DAY)
                val minute = calendar.get(Calendar.MINUTE)

                linearLayout.children.forEach { if (it is TextMessage) it.isLast = false }

                linearLayout.addView(
                    TextMessage(requireContext()).apply {
                        updatePadding(
                            top = context.pixels(com.admiral.uikit.R.dimen.module_x1),
                            left = context.pixels(com.admiral.uikit.R.dimen.module_x4),
                            right = context.pixels(com.admiral.uikit.R.dimen.module_x4)
                        )
                        LinearLayout.LayoutParams.WRAP_CONTENT
                        LinearLayout.LayoutParams.WRAP_CONTENT
                        gravity = Gravity.END
                        isOutgoing = true
                        messageStatus = MessageStatus.SEND
                        text = input.text
                        time = "$hour:$minute"
                        isLast = true
                    }
                )
            }
        }
    }
}